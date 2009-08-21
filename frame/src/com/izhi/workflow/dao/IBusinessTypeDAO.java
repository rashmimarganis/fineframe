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

import com.izhi.workflow.model.BusinessType;

import com.izhi.platform.util.PageParameter;

/**
 * <p>Title: PowerStone</p>
 */

public interface IBusinessTypeDAO {
  public List<BusinessType> findAllBusinessTypes();
  public List<Map<String,Object>> findAll();
  
  public BusinessType findBusinessType(Long businessTypeID);
  public Map<String,Object> findById(Long businessTypeID);
  public void saveBusinessType(BusinessType businessType);
  public void deleteBusinessType(String ids);
  public List<Map<String,Object>> findPage(PageParameter pp);
  public int findTotalCount();
  List<Map<String,Object>> findMyNewTaskKinds();
  public List<Map<String,Object>> findTree();
  
  public List<Map<String, Object>> findDeployTree();
}
