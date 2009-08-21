package com.izhi.workflow.flowform;

import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.izhi.platform.action.BasePageAction;
import com.izhi.platform.security.support.SecurityUser;
import com.izhi.workflow.model.ActivityReport;
import com.izhi.workflow.model.FlowTask;
import com.izhi.workflow.model.WorkflowDriver;
import com.izhi.workflow.service.IFlowTaskService;
import com.izhi.workflow.service.IWorkflowDriverService;
import com.izhi.workflow.service.IWorkflowEngineService;
import com.izhi.workflow.service.IFlowDriver;
import com.izhi.workflow.service.IWorkflowExecuter;
public abstract class AbstractWorkflowForm<T> extends BasePageAction {

	private static final long serialVersionUID = -4581178932410035972L;

	protected String flagDriverFlow;

	protected String flowTaskID;

	protected String flagCanNotDriverFlow;

	private static IWorkflowEngineService flowEngineService;
	private static IWorkflowDriverService formService;
	private static IFlowTaskService taskService;
	private static IWorkflowExecuter wfExecuterService;
	private static boolean debug = false;

	private T obj;

	public AbstractWorkflowForm() {
	}

	public void setDebug(boolean debug) {
		AbstractWorkflowForm.debug = debug;
	}

	public void setTaskService(IFlowTaskService taskManager) {
		AbstractWorkflowForm.taskService = taskManager;
	}

	public final String execute() throws Exception {

		doDriveWorkflow();
		return doExecute();
	}

	protected void driveWorkflow() {
		flagDriverFlow = "##";
	}

	protected abstract String doExecute() throws Exception;

	@SuppressWarnings("unchecked")
	protected abstract HashMap getOutputParameters(HttpServletRequest request,
			HttpServletResponse response, Object command) throws Exception;

	protected final Object formBackingObject() throws ServletException {
		Object result = doFormBackingObject(getDriverInputParameters());
		return result;
	}

	protected final ModelAndView showForm() throws Exception {
		canNotDriveFlow();
		String url = this.getRequest().getServletPath();
		if (log.isDebugEnabled()) {
			log.debug("WorkflowDriver read:[" + url + "]");
		}

		if (flowTaskID == null) {
			log.warn("ReadDO[" + this.getRequest().getContextPath() + "|" + url
					+ "]����ȱ�ٹؼ����["
					+ IFlowDriver.DEFAULT_FLOW_TASK_PARAM_NAME + "]��");
		} else {
			List readDrivers = formService.findDriverByReadDO(this.getRequest()
					.getContextPath(), url);
			WorkflowDriver flowDriver = null;
			if (readDrivers.size() > 0) {
				flowDriver = (WorkflowDriver) readDrivers.get(0);
				// ����cookie��WriteDOʹ��
				this.processCookieOnFormBacking(flowTaskID, flowDriver
						.getWriteURL(), this.getResponse());
			}
		}

		/*
		 * ModelAndView result = super.showForm(request, response, errors);
		 * result.addAllObjects(getDriverInputParameters(request));
		 */
		return null;
	}

	@SuppressWarnings("unchecked")
	protected abstract Object doFormBackingObject(HashMap inputParameters);

	@SuppressWarnings( { "unchecked", "unchecked" })
	private void sendActivityReport() throws Exception {
		HttpServletRequest request = this.getRequest();
		HttpServletResponse response = this.getResponse();
		if (log.isDebugEnabled()) {
			log.debug("���ͻͨ��ǰ--------------" + request);
		}

		String url = request.getServletPath();
		HashMap outputParameters = getOutputParameters(request, response, obj);

		if (log.isDebugEnabled()) {
			log.debug("sending ActivityReport[" + outputParameters + "]");
		}

		List writeDrivers = formService.findDriverByWriteDO(request
				.getContextPath(), url);

		WorkflowDriver flowDriver = null;
		if (writeDrivers.size() > 0) {
			flowDriver = (WorkflowDriver) writeDrivers.get(0);
			if (writeDrivers.size() > 1) {
				log.warn("request[" + request.getContextPath() + "|" + url
						+ "]was registered as more than one application!");
			}
		} else {
			log.warn("request[" + request.getContextPath() + "|" + url
					+ "]has not beed registered as any application!");
			return;
		}

		String taskIDInCookie = processCookieOnSubmit();
		if (log.isDebugEnabled()) {
			log.debug("obtain taskID[" + taskIDInCookie + "]from cookie");
		}

		FlowTask task = null;
		if (taskIDInCookie != null) {
			task = taskService.findFlowTask(taskIDInCookie);
		}

		ActivityReport report = new ActivityReport();

		if (task != null) {
			if (task.getFlowNodeBinding().getWorkflowDriver()
					.equals(flowDriver)) {
				report.setFlowTaskID(taskIDInCookie);
				if (log.isDebugEnabled()) {
					log.debug("A saving of task[" + taskIDInCookie + "]");
				}
			} else {
				log.warn("Task[" + taskIDInCookie + "]doesn't match with app["
						+ flowDriver.getFlowDriverName() + "]!");
				return;
			}
		} else {
			report.setDriverID(flowDriver.getFlowDriverID().toString());
			if (log.isDebugEnabled()) {
				log.debug("App[" + flowDriver.getFlowDriverName()
						+ "]triggers workflow");
			}
		}

		report.setDriverOutputData(outputParameters);
		if (log.isDebugEnabled()) {
			log.debug("outputParameters" + outputParameters);
		}

		flowEngineService.processActivityReport(SecurityUser.getUserId() + "",
				report);
	}

	private void removeCookie(HttpServletRequest request,
			HttpServletResponse response) {
		Cookie[] cks = request.getCookies();
		if (cks != null) {
			for (int i = 0; i < cks.length; i++) {
				Cookie tok = cks[i];
				if (tok.getName().equals(IFlowDriver.COOKIE_NAME)) {
					tok.setMaxAge(0);
					tok.setPath("/");
					response.addCookie(tok);
					break;
				}
			}
		}
	}

	private String processCookieOnSubmit() {
		return flowTaskID;
	}

	private void processCookieOnFormBacking(String flowTaskID, String writeURL,
			HttpServletResponse response) {
		String cookieValue = flowTaskID + IFlowDriver.COOKIE_DIVIDER + writeURL;
		Cookie ck = new Cookie(IFlowDriver.COOKIE_NAME, cookieValue);
		ck.setPath("/");
		response.addCookie(ck);
		if (log.isDebugEnabled()) {
			log.debug("set cookie:" + cookieValue);
		}
	}

	private HashMap getDriverInputParameters() {
		HttpServletRequest request = this.getRequest();
		String uri = request.getServletPath();
		if (log.isDebugEnabled()) {
			log.debug(uri);
		}
		String flowTaskID = request
				.getParameter(IFlowDriver.DEFAULT_FLOW_TASK_PARAM_NAME);
		if (flowTaskID == null) {
			log.warn("ReadDO[" + request.getContextPath() + "|" + uri
					+ "]lacks key parameter["
					+ IFlowDriver.DEFAULT_FLOW_TASK_PARAM_NAME + "]��");
			return new HashMap();
		} else {
			List readDrivers = formService.findDriverByReadDO(request
					.getContextPath(), uri);
			if (readDrivers.size() > 1) {
				log.warn("Request[" + request.getContextPath() + "|" + uri
						+ "]was registered as more than one application!");
			}
			if (readDrivers.size() <= 0) {
				log.warn("Request[" + request.getContextPath() + "|" + uri
						+ "]hasn't been registered as any application!");
				return new HashMap();
			}

			FlowTask flowTask = taskService.findFlowTask(flowTaskID);
			HashMap procState = flowTask.getFlowProc()
					.generateProcStateForDriver(flowTask.getFlowNodeBinding());

			if (log.isDebugEnabled()) {
				log.debug("Inject parameters" + procState + "into app.");
			}
			return procState;
		}
	}

	private void doDriveWorkflow() throws Exception {
		if (debug) { // ����ǵ���ģʽ
			log.debug("The system is in debug state,just now");
			return;
		}
		if (flagCanNotDriverFlow != null) {
			log
					.warn("Found flag["
							+ flagCanNotDriverFlow
							+ "],indicating there were validate errors during processing a POST request(and showForm was called),wont driver workflow engine!");
			return;
		}

		sendActivityReport();

		if (flagDriverFlow != null) {
			String userID = SecurityUser.getUserId() + "";
			if (log.isDebugEnabled()) {
				log.debug("User[" + userID + "]submited task[" + flowTaskID
						+ "]to drive workflow engine");
			}
			flowEngineService.processSubmitTask(userID, flowTaskID);
		}
	}

	private void canNotDriveFlow() {
		flagCanNotDriverFlow = "XX";
	}

	public IWorkflowEngineService getFlowEngineService() {
		return flowEngineService;
	}

	public void setFlowEngineService(IWorkflowEngineService flowEngineService) {
		AbstractWorkflowForm.flowEngineService = flowEngineService;
	}

	public IWorkflowDriverService getFormService() {
		return formService;
	}

	public void setFormService(IWorkflowDriverService formService) {
		AbstractWorkflowForm.formService = formService;
	}

	public static IWorkflowExecuter getWfExecuterService() {
		return wfExecuterService;
	}

	public void setWfExecuterService(IWorkflowExecuter wfExecuterService) {
		AbstractWorkflowForm.wfExecuterService = wfExecuterService;
	}

	public IFlowTaskService getTaskService() {
		return taskService;
	}

	public T getObj() {
		return obj;
	}

	public void setObj(T obj) {
		this.obj = obj;
	}

	public String getFlagDriverFlow() {
		return flagDriverFlow;
	}

	public void setFlagDriverFlow(String flagDriverFlow) {
		this.flagDriverFlow = flagDriverFlow;
	}

	public String getFlagCanNotDriverFlow() {
		return flagCanNotDriverFlow;
	}

	public void setFlagCanNotDriverFlow(String flagCanNotDriverFlow) {
		this.flagCanNotDriverFlow = flagCanNotDriverFlow;
	}

	public String getFlowTaskID() {
		return flowTaskID;
	}

	public void setFlowTaskID(String flowTaskID) {
		this.flowTaskID = flowTaskID;
	}

}
