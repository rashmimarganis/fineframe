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
import com.izhi.workflow.dao.IBusinessTypeDAO;
import com.izhi.workflow.model.BusinessType;

public interface IBusinessTypeService {

	public void setBusinessTypeDAO(IBusinessTypeDAO dao);

	public List<BusinessType> findAllBusinessTypes();
	public List<Map<String,Object>> findAll();
	
	public Map<String,Object> findPage(PageParameter pp);
	public List<Map<String,Object>> findPageList(PageParameter pp);
	
	public int findTotalCount();

	public BusinessType findBusinessType(long businessTypeID);
	public Map<String,Object> findById(long businessTypeID);

	public BusinessType saveBusinessType(BusinessType businessType);
	
	public int deleteBusinessType(String businessTypeID);

	public void updateBusinessType(BusinessType bt);
	
	public List<Map<String,Object>> findTree();
	
	
	public List<Map<String, Object>> findDeployTree();

}
