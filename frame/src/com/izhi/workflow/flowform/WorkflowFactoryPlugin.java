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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.PlugIn;
import org.apache.struts.config.ModuleConfig;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.izhi.platform.security.support.SecurityUser;
import com.izhi.workflow.service.IFlowTaskService;
import com.izhi.workflow.service.IWorkflowDriverService;
import com.izhi.workflow.service.IWorkflowEngineService;
import com.izhi.workflow.service.IWorkflowExecuter;

/**
 * Spring工厂
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author daquan
 * @version 1.0
 */
public class WorkflowFactoryPlugin implements PlugIn {
	private static Log log = LogFactory.getLog(WorkflowFactoryPlugin.class);

	public static final String DEFAULT_FLOW_ENGINE_BEAN_NAME = "workflowEngine";
	private String flowEngineBeanName = DEFAULT_FLOW_ENGINE_BEAN_NAME;

	public static final String DEFAULT_DRIVER_MANAGER_BEAN_NAME = "workflowDriverManager";
	private String driverManagerBeanName = DEFAULT_DRIVER_MANAGER_BEAN_NAME;

	public static final String DEFAULT_CA_DELEGATER_BEAN_NAME = "caDelegater";
	private String caDelegaterBeanName = DEFAULT_CA_DELEGATER_BEAN_NAME;

	public static final String DEFAULT_TASK_MANAGER_BEAN_NAME = "flowTaskManager";
	private String taskManagerBeanName = DEFAULT_TASK_MANAGER_BEAN_NAME;

	public static final String DEFAULT_FLOW_EXECUTER_BEAN_NAME = "workflowService";
	private String flowExecuterBeanName = DEFAULT_FLOW_EXECUTER_BEAN_NAME;

	private static IWorkflowEngineService flowService;
	private static IWorkflowDriverService driverService;
	private static IFlowTaskService taskService;
	private static IWorkflowExecuter wfExecuter;
	private static boolean debug = false;

	public WorkflowFactoryPlugin() {
	}

	public void destroy() {
		flowService = null;
		driverService = null;
		taskService = null;
		wfExecuter = null;
	}

	public void init(ActionServlet servlet, ModuleConfig config)
			throws javax.servlet.ServletException {
		try {
			WebApplicationContext wac = WebApplicationContextUtils
					.getRequiredWebApplicationContext(servlet
							.getServletContext());
			if (wac != null) {
				flowService = (IWorkflowEngineService) wac
						.getBean(getFlowEngineBeanName());
				driverService = (IWorkflowDriverService) wac
						.getBean(getDriverManagerBeanName());
				taskService= (IFlowTaskService) wac
						.getBean(getTaskManagerBeanName());
				// wfExecuter =
				// (WorkflowExecuter) wac.getBean(getFlowExecuterBeanName());

				log.info("初始化WorkflowFactoryPlugin");
			} else {
				log.warn("没有得到WebApplicationContext");
			}
		} catch (Exception e) {
			log.warn(e);
		}
	}


	public static boolean authenticate(String userID, String pass,
			HttpServletRequest request, HttpServletResponse response) {
		return !SecurityUser.isAnonymous();
	}

	

	public static IWorkflowExecuter getWfExecuter() {
		return wfExecuter;
	}

	// ------------------------------------------------------------------------------
	public String getFlowEngineBeanName() {
		return flowEngineBeanName;
	}

	public void setFlowEngineBeanName(String flowEngineBeanName) {
		this.flowEngineBeanName = flowEngineBeanName;
	}

	public String getCaDelegaterBeanName() {
		return caDelegaterBeanName;
	}

	public void setCaDelegaterBeanName(String caDelegaterBeanName) {
		this.caDelegaterBeanName = caDelegaterBeanName;
	}

	public String getDriverManagerBeanName() {
		return driverManagerBeanName;
	}

	public void setDriverManagerBeanName(String driverManagerBeanName) {
		this.driverManagerBeanName = driverManagerBeanName;
	}

	public String getTaskManagerBeanName() {
		return taskManagerBeanName;
	}

	public void setTaskManagerBeanName(String taskManagerBeanName) {
		this.taskManagerBeanName = taskManagerBeanName;
	}

	public String getFlowExecuterBeanName() {
		return flowExecuterBeanName;
	}

	public void setFlowExecuterBeanName(String flowExecuterBeanName) {
		this.flowExecuterBeanName = flowExecuterBeanName;
	}

	public static boolean isDebug() {
		return debug;
	}

	public void setDebug(boolean debug2) {
		debug = debug2;
	}

	public static IWorkflowEngineService getFlowService() {
		return flowService;
	}

	public static void setFlowService(IWorkflowEngineService flowService) {
		WorkflowFactoryPlugin.flowService = flowService;
	}

	public static IWorkflowDriverService getDriverService() {
		return driverService;
	}

	public static void setDriverService(IWorkflowDriverService driverService) {
		WorkflowFactoryPlugin.driverService = driverService;
	}

	public static IFlowTaskService getTaskService() {
		return taskService;
	}

	public static void setTaskService(IFlowTaskService taskService) {
		WorkflowFactoryPlugin.taskService = taskService;
	}

	public static void setWfExecuter(IWorkflowExecuter wfExecuter) {
		WorkflowFactoryPlugin.wfExecuter = wfExecuter;
	}
}
