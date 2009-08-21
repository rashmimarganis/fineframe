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

import com.izhi.platform.util.PageParameter;
import com.izhi.workflow.model.FlowTask;
import com.izhi.workflow.model.FlowTaskRole;
import com.izhi.workflow.model.FlowTaskUser;

public interface IFlowTaskDAO {
	public FlowTask getFlowTask(Long taskID);

	public void saveFlowTask(FlowTask flowTask);

	public boolean isTaskOwner(String userID, Long taskID);

	public void removeFlowTask(Long taskID);

	public FlowTaskUser getFlowTaskUser(Long id);

	public FlowTaskRole getFlowTaskRole(Long id);

	public List findAllMyNewTasks(List myPerformingNodes);

	public List findMyTasksToAssign(String userID);

	public Integer findMyTasksToAssignNum(String userID);

	public List findMyRefusedTasks(String userID);

	public List findNewTasksNotEmailed();

	public List findMyFinishedTasksKinds(String userID);

	public List findMyExecutingTasksKinds(String userID);

	public Integer findMyExecutingTasksNum(String userID);

	public Integer findMyExecutingTasksNumByType(String userID, Long typeID);

	public List findMyExecutingTasksByType(String userID, Long typeID,PageParameter pp);
	public List findMyExecutingTasksByType(String userID, Long typeID);

	public List findMyNewTasksKinds(String userID);

	public Integer findMyNewTasksNum(String userID);

	public Integer findMyNewTasksNumByType(String userID, Long typeID);

	public List findMyNewTasksByType(String userID, Long typeID, PageParameter pp);

	public Integer findMyOtherNewTasksNum(String userID);

	public Integer findMyOtherExecutingTasksNum(String userID);

	public Integer findMyRefusedTasksNum(String userID);

	public Integer findMyFinishedTasksNum(String userID);

	public Integer findMyOtherFinishedTasksNum(String userID);

	public Integer findMyFinishedTasksNumByType(String userID, Long typeID);

	public List findMyFinishedTasksByType(String userID, Long typeID,
			PageParameter pp);

	public FlowTask findTaskByNodeAndProc(String flowNodeID, Long flowProcID);

	public List findTasksByProc(Long flowProcID);

	public boolean isTaskAssigner(String userID, Long taskID);

}
