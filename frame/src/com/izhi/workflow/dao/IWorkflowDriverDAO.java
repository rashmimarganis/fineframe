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
package com.izhi.workflow.dao;

import java.util.List;
import java.util.Map;

import com.izhi.platform.util.PageParameter;
import com.izhi.workflow.model.WFDriverInputParam;
import com.izhi.workflow.model.WFDriverOutputParam;
import com.izhi.workflow.model.WFDriverOutputParamEnum;
import com.izhi.workflow.model.WorkflowDriver;

public interface IWorkflowDriverDAO {
	public List getAllWorkflowDrivers();

	public WorkflowDriver getWorkflowDriver(Long flowDriverID);

	public void saveWorkflowDriver(WorkflowDriver flowDriver);

	public void removeWorkflowDriver(Long flowDriverID);

	public List getFlowDriversByContextPath(String contextPath);

	public List findDriverByReadDO(String contextPath, String requestURL);

	public List findDriverByWriteDO(String contextPath, String requestURL);

	public List<String> getAllDriverContextPath();
	
	public List<Map<String,Object>> findAllDriverContextPath();

	public WFDriverOutputParam getDriverOutputParam(Long driverOutputParamID);

	public WFDriverInputParam getDriverInputParam(Long driverInputParamID);
	public Map<String,Object> findDriverOutputParamById(Long driverOutputParamID);
	
	public Map<String,Object> findDriverInputParamById(Long driverInputParamID);

	public WFDriverOutputParamEnum getDriverOutputParamEnume(
			Long driverOutputParamEnumeID);

	public void saveDriverOutputParam(WFDriverOutputParam driverOutputParam);

	public List<Map<String,Object>> findPage(PageParameter pp,String contextPath);
	public Long findTotalCount(String contextPath);
	
	public Map<String,Object> findById(Long flowDriverId);
	
	public List<Map<String,Object>> findDriverInputParamPage(PageParameter pp,Long driverId);
	public Long findDriverInputParamCount(Long driverId);
	public List<Map<String,Object>> findDriverOutputParamPage(PageParameter pp,Long driverId);
	public Long findDriverOutputParamCount(Long driverId);
}
