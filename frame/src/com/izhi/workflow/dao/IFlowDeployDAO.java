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
import com.izhi.workflow.model.FlowDeploy;
import com.izhi.workflow.model.FlowNodeBinding;


public interface IFlowDeployDAO {
  public FlowDeploy findFlowDeploy(Long flowDeployID);
  public Map<String,Object> findById(Long id);

  public void saveFlowDeploy(FlowDeploy flowDeploy);

  public FlowNodeBinding findFlowNodeBinding(Long flowNodeBindingID);

  public void saveFlowNodeBinding(FlowNodeBinding flowNodeBinding);


  public void deleteFlowDeploy(Long flowDeployID);

  public List<FlowNodeBinding> findFlowNodeBindingsByDriver(Long flowDriverID);

  public List<FlowNodeBinding> findFlowNodeBindsByUserPerformer(Long userID);

  public List<FlowNodeBinding> findFlowNodeBindsByRolePerformer(Long roleID);
  
  public List<Map<String,Object>> findPage(PageParameter pp,Long flowMetaId);
  public int findTotalCount(Long flowMetaId);
}
