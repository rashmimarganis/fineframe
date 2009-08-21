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
package com.izhi.workflow.service;

import java.util.List;
import java.util.Map;

import com.izhi.platform.util.PageParameter;
import com.izhi.workflow.dao.IWorkflowDriverDAO;
import com.izhi.workflow.model.WFDriverInputParam;
import com.izhi.workflow.model.WFDriverOutputParam;
import com.izhi.workflow.model.WFDriverOutputParamEnum;
import com.izhi.workflow.model.WorkflowDriver;
@SuppressWarnings("unchecked")
public interface IWorkflowDriverService {
	public void setWorkflowDriverDAO(IWorkflowDriverDAO dao);

	public List findAllWorkflowDrivers();

	public WorkflowDriver findWorkflowDriver(String flowDriverID);

	public WFDriverInputParam findDriverInputParam(String driverInputParamID);

	public WFDriverOutputParam findDriverOutputParam(String driverOutputParamID);

	public WFDriverOutputParamEnum findDriverOutputParamEnume(
			String driverOutputParamEnumeID);

	public WorkflowDriver saveWorkflowDriver(WorkflowDriver flowDriver);

	public WorkflowDriver updateWorkflowDriver(WorkflowDriver flowDriver);

	public void deleteWorkflowDriver(String flowDriverID);

	public List findFlowDriversByContextPath(String contextPath);

	public List findDriverByReadDO(String contextPath, String requestURL);

	public List findDriverByWriteDO(String contextPath, String requestURL);

	public WorkflowDriver doAddDriverInputParam(String driverID,
			WFDriverInputParam driverInputParam);

	public WorkflowDriver doAddDriverOutputParam(String driverID,
			WFDriverOutputParam driverOutputParam);

	public void deleteDriverInputParam(String driverInputParamID);

	public void deleteDriverOutputParam(String driverOutputParamID);

	public void doAddDriverOutputParamEnume(String driverOutputParamID,
			WFDriverOutputParamEnum driverOutputParamEnume);

	public void deleteDriverOutputParamEnume(String driverOutputParamEnumeID);

	public Map<String, Object> findPage(PageParameter pp, String contextPath);

	public Long findTotalCount(String contextPath);

	public List<Map<String, Object>> findAllDriverContextPath();

	public Map<String, Object> findById(Long flowDriverId);

	public Map<String, Object> findDriverInputParamPage(PageParameter pp,
			Long driverId);

	public Long findDriverInputParamCount(Long driverId);

	public Map<String, Object> findDriverOutputParamPage(PageParameter pp,
			Long driverId);

	public Long findDriverOutputParamCount(Long driverId);

	public Map<String, Object> findDriverOutputParamById(
			Long driverOutputParamID);

	public Map<String, Object> findDriverInputParamById(Long driverInputParamID);
}
