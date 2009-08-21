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

import org.springframework.stereotype.Service;

import com.izhi.platform.util.PageParameter;
import com.izhi.workflow.dao.IBusinessTypeDAO;
import com.izhi.workflow.service.IBusinessTypeService;
import com.izhi.workflow.model.BusinessType;
@SuppressWarnings("unchecked")
@Service("workflowBusinessTypeService")
public class BusinessTypeServiceImpl implements IBusinessTypeService {
	@Resource(name="workflowBusinessTypeDao")
	private IBusinessTypeDAO btDAO;

	public void setBusinessTypeDAO(IBusinessTypeDAO dao) {
		this.btDAO = dao;
	}

	public List findAllBusinessTypes() {
		return btDAO.findAllBusinessTypes();
	}

	public BusinessType findBusinessType(long businessTypeID) {
		return btDAO.findBusinessType(new Long(businessTypeID));
	}

	public BusinessType saveBusinessType(BusinessType businessType) {
		btDAO.saveBusinessType(businessType);
		return businessType;
	}

	public int deleteBusinessType(String ids) {
		btDAO.deleteBusinessType(ids);
		return this.findTotalCount();
	}

	public void updateBusinessType(BusinessType bt) {
		btDAO.saveBusinessType(bt);
	}

	@Override
	public Map<String, Object> findPage(PageParameter pp) {
		Map<String,Object> m=new HashMap<String, Object>();
		m.put("objs",this.findPageList(pp));
		m.put("totalCount", this.findTotalCount());
		return m;
	}

	@Override
	public List<Map<String,Object>> findPageList(PageParameter pp) {
		return btDAO.findPage(pp);
	}

	@Override
	public int findTotalCount() {
		return btDAO.findTotalCount();
	}

	@Override
	public List<Map<String, Object>> findAll() {
		List<Map<String, Object>> l= btDAO.findAll();
		
		return l;
	}

	@Override
	public Map<String, Object> findById(long businessTypeID) {
		return btDAO.findById(businessTypeID);
	}

	@Override
	public List<Map<String, Object>> findTree() {
		return btDAO.findTree();
	}

	@Override
	public List<Map<String, Object>> findDeployTree() {
		return btDAO.findDeployTree();
	}

	
	

}
