/*
 * Copyright 2004-2005 the original author or authors.
 *
 * Licensed under the LGPL license, Version 2.1 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.gnu.org/copyleft/lesser.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * @author daquanda(liyingquan@gmail.com)
 * @author kevin(diamond_china@msn.com)
 */
package com.izhi.workflow.flowform;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.izhi.platform.action.BaseAction;
import com.izhi.platform.security.support.SecurityUser;
import com.izhi.workflow.model.ActivityReport;
import com.izhi.workflow.model.FlowTask;
import com.izhi.workflow.model.WorkflowDriver;
import com.izhi.workflow.service.IFlowTaskService;
import com.izhi.workflow.service.IWorkflowDriverService;
import com.izhi.workflow.service.IWorkflowEngineService;
import com.izhi.workflow.service.IWorkflowExecuter;
@SuppressWarnings("unchecked")
public abstract class WorkflowDriverWriteAction extends BaseAction implements
		WorkFlowDriverTransactionable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1542253767296507845L;
	private IWorkflowExecuter workflowExecuter;
	private IWorkflowEngineService workflowEngineService;
	private IWorkflowDriverService workflowDriverService;
	private IFlowTaskService flowTaskService;
	private static boolean debug = false;
	private String flowTaskID;
	private Map<String,Object> outputParams=new HashMap<String, Object>();
	public final String execute() throws Exception {
		try {
			if (workflowExecuter != null) {
				// 实际上是在回调自己的doTransactionExecute()
				return workflowExecuter.doExecute(this);
			} else {
				log.warn("没有使用WorkflowExecuter，数据一致性没有保证！");
				return doTransactionExecute();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "noDriver";
		}
	}

	/**
	 * 这个方法需要JTA来保证事务性，它将被WorkflowExecuter调用
	 * 
	 * @throws Exception
	 * @return ActionForward
	 */
	public final String doTransactionExecute() throws Exception {
		// 发送活动通报
		if (!isDebug()) { // 如果不是调试模式
			sendActivityReport();
		}

		// 执行业务逻辑(期间可能调用driveWorkflow()来提交任务)
		String forward = doBusiness();

		// 删除cookie
		if (log.isDebugEnabled()) {
			log.debug("after doBusiness:ActionForward["
					+ (forward != null ? forward : "null") + "]");
		}
		if (forward == null) {
			// 关闭父窗口：parent.submitTask();

		}
		return forward;
	}

	/**
	 * 提交任务,驱动工作流引擎
	 */
	protected void driveWorkflow() {
		if (isDebug()) { // 如果是调试模式
			log.debug("目前处于工作流调试模式！");
			return;
		}
		String userID = SecurityUser.getUserId() + "";

		if (log.isDebugEnabled()) {
			log.debug("用户[" + userID + "]提交任务[" + flowTaskID + "]驱动工作流");
		}
		workflowEngineService.processSubmitTask(userID, flowTaskID);
	}

	/**
	 * 从cookie获取taskID，
	 * 如果与uri对应的驱动匹配,就发送ActivityReport(taskID，driverOutputData),
	 * 否则发送ActivityReport(driverID，driverOutputData) 发送活动通报
	 */
	private void sendActivityReport() {
		HttpServletRequest request = this.getRequest();
		if (log.isDebugEnabled()) {
			log.debug("发送活动通报前--------------" + request);
		}
		String uri = request.getServletPath();
		outputParams = getDriverOutputParameters();
		if (log.isDebugEnabled()) {
			log.debug("发送活动通报[" + outputParams + "]");
		}

		List writeDrivers = workflowDriverService.findDriverByWriteDO(request
				.getContextPath(), uri);

		WorkflowDriver flowDriver = null;
		if (writeDrivers.size() > 0) {
			flowDriver = (WorkflowDriver) writeDrivers.get(0);
			if (writeDrivers.size() > 1) {
				log.warn("请求[" + request.getContextPath() + "|" + uri
						+ "]被注册为多个驱动！");
			}
		} else {
			log.warn("请求[" + request.getContextPath() + "|" + uri
					+ "]没有被注册为任何驱动！");
			return;
		}

	

		FlowTask task = null;
		if (flowTaskID != null) {
			task = flowTaskService.findFlowTask(flowTaskID);
		}

		ActivityReport report = new ActivityReport();

		if (task != null) {
			// 如果task与uri对应的驱动匹配,就发送ActivityReport(taskID，driverOutputData)
			if (task.getFlowNodeBinding().getWorkflowDriver()
					.equals(flowDriver)) {
				report.setFlowTaskID(flowTaskID);
				if (log.isDebugEnabled()) {
					log.debug("任务[" + flowTaskID + "]的一次保存");
				}
			} else {
				log.warn("任务[" + flowTaskID + "]与驱动["
						+ flowDriver.getFlowDriverName() + "]不匹配！");
				return;
			}
		}
		// 否则发送ActivityReport(driverID，driverOutputData)
		else {
			report.setDriverID(flowDriver.getFlowDriverID().toString());
			if (log.isDebugEnabled()) {
				log.debug("驱动[" + flowDriver.getFlowDriverName() + "]触发工作流");
			}
		}

		report.setDriverOutputData(outputParams);
		if (log.isDebugEnabled()) {
			log.debug("驱动输出参数：" + outputParams);
		}

		workflowEngineService.processActivityReport(SecurityUser.getUserId() + "",
				report);
	}


	/**
	 * 实现驱动的业务逻辑
	 */
	public abstract String doBusiness() throws Exception;

	/**
	 * 获得驱动输出参数:此处决定了注册驱动输出参数
	 * 
	 * @return HashMap
	 */
	public abstract Map<String,Object> getDriverOutputParameters();
	public IWorkflowExecuter getWorkflowExecuter() {
		return workflowExecuter;
	}

	public void setWorkflowExecuter(IWorkflowExecuter workflowExecuter) {
		this.workflowExecuter = workflowExecuter;
	}

	public IWorkflowEngineService getWorkflowEngineService() {
		return workflowEngineService;
	}

	public void setWorkflowEngineService(
			IWorkflowEngineService workflowEngineService) {
		this.workflowEngineService = workflowEngineService;
	}

	public boolean isDebug() {
		return debug;
	}

	public void setDebug(boolean debug) {
		WorkflowDriverWriteAction.debug = debug;
	}

	public IWorkflowDriverService getWorkflowDriverService() {
		return workflowDriverService;
	}

	public void setWorkflowDriverService(
			IWorkflowDriverService workflowDriverService) {
		this.workflowDriverService = workflowDriverService;
	}

	public IFlowTaskService getFlowTaskService() {
		return flowTaskService;
	}

	public void setFlowTaskService(IFlowTaskService flowTaskService) {
		this.flowTaskService = flowTaskService;
	}

	public String getFlowTaskID() {
		return flowTaskID;
	}

	public void setFlowTaskID(String flowTaskID) {
		this.flowTaskID = flowTaskID;
	}

	public Map<String, Object> getOutputParams() {
		return outputParams;
	}

	public void setOutputParams(Map<String, Object> outputParams) {
		this.outputParams = outputParams;
	}
}
