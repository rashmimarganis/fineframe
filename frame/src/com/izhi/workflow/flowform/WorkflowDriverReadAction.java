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
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


import com.izhi.platform.action.BaseAction;
import com.izhi.workflow.model.FlowTask;
import com.izhi.workflow.model.WorkflowDriver;
import com.izhi.workflow.service.IFlowTaskService;
import com.izhi.workflow.service.IWorkflowDriverService;
@SuppressWarnings("unchecked")
abstract public class WorkflowDriverReadAction<T> extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7384297156639835620L;
	private IWorkflowDriverService workflowDriverService;
	private String flowTaskID;
	private IFlowTaskService flowTaskService;
	private T obj;
	private Map<String,Object> inputParams=new HashMap<String, Object>();

	public final String execute() throws Exception {
		
		String forward = doExecute();
		// 如果驱动没有调用getDriverInputParameters就不能向客户端写cookie，因此补充调用一次
		inputParams = getDriverInputParameters();
		obj=this.getBackObject(inputParams);
		if (log.isDebugEnabled()) {
			log.debug("驱动输入参数" + inputParams);
		}
		return forward;
	}

	/**
	 * 实现驱动的业务逻辑
	 * 
	 * @param mapping
	 *            ActionMapping
	 * @param form
	 *            ActionForm
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @throws Exception
	 * @return ActionForward
	 */
	public abstract String doExecute() throws Exception;

	/**
	 * 供子类型调用获得注入的参数:此处应该和注册的驱动输入参数一致
	 * 
	 * @return HashMap
	 */
	
	protected Map<String,Object> getDriverInputParameters() {
		// 获取请求
		HttpServletRequest request=this.getRequest();
		String uri = request.getServletPath();

		if (log.isDebugEnabled()) {
			log.debug(uri);
		}

		// 给驱动注入参数
		HashMap<String,Object> driverInputParameters = new HashMap<String,Object>();

		if (flowTaskID == null) {
			log.warn("ReadDO[" + request.getContextPath() + "|" + uri
					+ "]后面缺少关键参数[flasTaskID]！");
			return driverInputParameters;
		} else {
			List<WorkflowDriver> readDrivers = workflowDriverService.findDriverByReadDO(request.getContextPath(), uri);
			if (readDrivers.size() > 0) {
				if (readDrivers.size() > 1) {
					log.warn("请求[" + request.getContextPath() + "|" + uri
							+ "]被注册为多个驱动！");
				}
			} else {
				log.warn("请求[" + request.getContextPath() + "|" + uri
						+ "]没有被注册为任何驱动！");
				return driverInputParameters;
			}

			// 给驱动注入参数
			FlowTask flowTask = flowTaskService.findFlowTask(flowTaskID);
			HashMap<String,String> procState = flowTask.getFlowProc()
					.generateProcStateForDriver(flowTask.getFlowNodeBinding());
			if (procState != null) {
				for (Iterator it = procState.keySet().iterator(); it.hasNext();) {
					String paraName = (String) it.next();
					String paraValue = (String) procState.get(paraName);
					driverInputParameters.put(paraName, paraValue);
				}
			}
			if (log.isDebugEnabled()) {
				log.debug("进程状态{" + procState + "}\n给驱动注入参数{"
						+ driverInputParameters + "}");
			}

			return driverInputParameters;
		}
	}


	public IWorkflowDriverService getWorkflowDriverService() {
		return workflowDriverService;
	}

	public void setWorkflowDriverService(IWorkflowDriverService workflowDriverService) {
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
	
	public abstract T getBackObject(Map<String,Object> map);

	public T getObj() {
		return obj;
	}

	public void setObj(T obj) {
		this.obj = obj;
	}

	public Map<String, Object> getInputParams() {
		return inputParams;
	}

	public void setInputParams(Map<String, Object> inputParams) {
		this.inputParams = inputParams;
	}

}
