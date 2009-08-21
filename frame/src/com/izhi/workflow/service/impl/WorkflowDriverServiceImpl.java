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
package com.izhi.workflow.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.izhi.platform.util.PageParameter;
import com.izhi.workflow.dao.IWorkflowDriverDAO;
import com.izhi.workflow.model.WFDriverInputParam;
import com.izhi.workflow.model.WFDriverOutputParam;
import com.izhi.workflow.model.WFDriverOutputParamEnum;
import com.izhi.workflow.model.WorkflowDriver;
import com.izhi.workflow.service.IWorkflowDriverService;
@Service("workflowDriverService")
public class WorkflowDriverServiceImpl implements IWorkflowDriverService {
	private static Logger log = Logger.getLogger(WorkflowDriverServiceImpl.class);
	@Resource(name="workflowDriverDao")
	private IWorkflowDriverDAO workflowDriverDAO;

	public void setWorkflowDriverDAO(IWorkflowDriverDAO dao) {
		workflowDriverDAO = dao;
	}

	public List findAllWorkflowDrivers() {
		return workflowDriverDAO.getAllWorkflowDrivers();
	}

	public WorkflowDriver findWorkflowDriver(String flowDriverID) {
		return workflowDriverDAO.getWorkflowDriver(new Long(flowDriverID));
	}

	public WorkflowDriver saveWorkflowDriver(WorkflowDriver flowDriver) {
		workflowDriverDAO.saveWorkflowDriver(flowDriver);
		return flowDriver;
	}

	public WorkflowDriver updateWorkflowDriver(WorkflowDriver flowDriver) {
		WorkflowDriver result = findWorkflowDriver(flowDriver.getFlowDriverID()
				.toString());
		result.setContextPath(flowDriver.getContextPath());
		result.setFlowDriverID(flowDriver.getFlowDriverID());
		result.setFlowDriverName(flowDriver.getFlowDriverName());
		result.setMemo(flowDriver.getMemo());
		result.setReadURL(flowDriver.getReadURL());
		result.setWriteURL(flowDriver.getWriteURL());
		workflowDriverDAO.saveWorkflowDriver(result);
		return result;
	}

	public void deleteWorkflowDriver(String flowDriverID) {
		WorkflowDriver wd = this.findWorkflowDriver(flowDriverID);
		// wd.removeAllParams();
		workflowDriverDAO.removeWorkflowDriver(wd.getFlowDriverID());
	}

	public List findFlowDriversByContextPath(String contextPath) {
		return workflowDriverDAO.getFlowDriversByContextPath(contextPath);
	}

	public List findDriverByReadDO(String contextPath, String requestURL) {
		return workflowDriverDAO.findDriverByReadDO(contextPath, requestURL);
	}

	public List findDriverByWriteDO(String contextPath, String requestURL) {
		return workflowDriverDAO.findDriverByWriteDO(contextPath, requestURL);
	}


	public WorkflowDriver doAddDriverInputParam(String driverID,
			WFDriverInputParam driverInputParam) {
		WorkflowDriver wd = this.findWorkflowDriver(driverID);
		wd.addInputParam(driverInputParam);
		workflowDriverDAO.saveWorkflowDriver(wd);
		return wd;
	}

	public WorkflowDriver doAddDriverOutputParam(String driverID,
			WFDriverOutputParam driverOutputParam) {
		WorkflowDriver wd = this.findWorkflowDriver(driverID);
		wd.addOutputParam(driverOutputParam);
		workflowDriverDAO.saveWorkflowDriver(wd);
		return wd;
	}

	public void deleteDriverInputParam(String driverInputParamID) {
		WFDriverInputParam driverInputParam = workflowDriverDAO
				.getDriverInputParam(new Long(driverInputParamID));
		WorkflowDriver wd = driverInputParam.getWorkflowDriver();
		if (wd != null) {
			wd.removeInputParam(driverInputParam);
		}

		workflowDriverDAO.saveWorkflowDriver(wd);
	}

	public void deleteDriverOutputParam(String driverOutputParamID) {
		WFDriverOutputParam driverOutputParam = workflowDriverDAO
				.getDriverOutputParam(new Long(driverOutputParamID));
		WorkflowDriver wd = driverOutputParam.getWorkflowDriver();
		if (wd != null) {
			wd.removeOutputParam(driverOutputParam);
		}

		workflowDriverDAO.saveWorkflowDriver(wd);
	}

	public void doAddDriverOutputParamEnume(String driverOutputParamID,
			WFDriverOutputParamEnum driverOutputParamEnume) {
		WFDriverOutputParam driverOutputParam = workflowDriverDAO
				.getDriverOutputParam(new Long(driverOutputParamID));
		driverOutputParam.addParamEnume(driverOutputParamEnume);
		workflowDriverDAO.saveDriverOutputParam(driverOutputParam);
	}

	public void deleteDriverOutputParamEnume(String driverOutputParamEnumeID) {
		WFDriverOutputParamEnum driverOutputParamEnume = workflowDriverDAO
				.getDriverOutputParamEnume(new Long(driverOutputParamEnumeID));
		WFDriverOutputParam driverOutputParam = driverOutputParamEnume
				.getWfDriverOutputParam();

		if (driverOutputParam != null) {
			driverOutputParam.removeParamEnume(driverOutputParamEnume);
		}
		workflowDriverDAO.saveDriverOutputParam(driverOutputParam);
	}

	public WFDriverInputParam findDriverInputParam(String driverInputParamID) {
		return workflowDriverDAO.getDriverInputParam(new Long(
				driverInputParamID));
	}

	public WFDriverOutputParam findDriverOutputParam(String driverOutputParamID) {
		return workflowDriverDAO.getDriverOutputParam(new Long(
				driverOutputParamID));
	}

	public WFDriverOutputParamEnum findDriverOutputParamEnume(
			String driverOutputParamEnumeID) {
		return workflowDriverDAO.getDriverOutputParamEnume(new Long(
				driverOutputParamEnumeID));
	}

	@Override
	public Map<String, Object> findById(Long flowDriverId) {
		return workflowDriverDAO.findById(flowDriverId);
	}

	@Override
	public Map<String, Object> findPage(PageParameter pp, String contextPath) {
		Map<String,Object> m=new HashMap<String, Object>();
		m.put("objs", workflowDriverDAO.findPage(pp, contextPath));
		m.put("totalCount", this.findTotalCount(contextPath));
		return m;
	}

	@Override
	public Long findTotalCount(String contextPath) {
		return workflowDriverDAO.findTotalCount(contextPath);
	}

	@Override
	public List<Map<String, Object>> findAllDriverContextPath() {
		return this.workflowDriverDAO.findAllDriverContextPath();
	}

	@Override
	public Long findDriverInputParamCount(Long driverId) {
		return this.workflowDriverDAO.findDriverInputParamCount(driverId);
	}

	@Override
	public Map<String, Object> findDriverInputParamPage(PageParameter pp,
			Long driverId) {
		Map<String,Object> m=new HashMap<String, Object>();
		m.put("objs", this.workflowDriverDAO.findDriverInputParamPage(pp, driverId));
		m.put("totalCount", this.workflowDriverDAO.findDriverInputParamCount(driverId));
		return m;
	}

	@Override
	public Long findDriverOutputParamCount(Long driverId) {
		return this.workflowDriverDAO.findDriverOutputParamCount(driverId);
	}

	@Override
	public Map<String, Object> findDriverOutputParamPage(PageParameter pp,
			Long driverId) {
		Map<String,Object> m=new HashMap<String, Object>();
		m.put("objs", this.workflowDriverDAO.findDriverOutputParamPage(pp, driverId));
		m.put("totalCount", this.workflowDriverDAO.findDriverOutputParamCount(driverId));
		return m;
	}

	@Override
	public Map<String, Object> findDriverInputParamById(Long driverInputParamID) {
		return this.workflowDriverDAO.findDriverInputParamById(driverInputParamID);
	}

	@Override
	public Map<String, Object> findDriverOutputParamById(
			Long driverOutputParamID) {
		return this.workflowDriverDAO.findDriverOutputParamById(driverOutputParamID);
	}
}
