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
import com.izhi.workflow.model.FlowNodeBinding;
import com.izhi.workflow.model.FlowProcTransaction;
import com.izhi.workflow.model.FlowTask;

public interface IFlowTaskService {

	public boolean isTaskOwner(String userID, String taskID);

	public boolean isTaskAssigner(String userID, String taskID);

	public FlowTask findFlowTask(String taskID);

	public FlowTask doCreateFlowTask(FlowProcTransaction procTransaction,
			FlowNodeBinding flowNodeBinding);

	public void doCheckOutTask(String userID, String taskID);

	public void doFinishTask(String taskID);

	public void doEmailTask(String taskID);

	public void doAbortTask(String userID, String taskID);

	public void doAssignTask(String userID, String taskID);

	public List<FlowTask> findAllMyNewTasks(String userID);

	public List<FlowTask> findMyExecutingTasksKinds(String userID);

	public List<FlowTask> findMyTasksToAssign(String userID);

	public List<FlowTask> findMyRefusedTasks(String userID);

	public List<FlowTask> findNewTasksNotEmailed();

	public List<FlowTask> findMyFinishedTasksKinds(String userID);

	public Integer findMyNewTasksNum(String userID);

	public List<FlowTask> findMyNewTasksKinds(String userID);

	public Integer findMyNewTasksNumByType(String userID, String typeID);

	public Map<String,Object> findMyNewTasksByType(String userID, String typeID, PageParameter pp);

	public Integer findMyExecutingTasksNum(String userID);

	public Integer findMyExecutingTasksNumByType(String userID, String typeID);

	public Map<String,Object> findMyExecutingTasksByType(String userID, String typeID,PageParameter pp);
	
	public Integer findMyRefusedTasksNum(String userID);

	public Integer findMyFinishedTasksNum(String userID);

	public Integer findMyFinishedTasksNumByType(String userID, String typeID);

	public  Map<String,Object> findMyFinishedTasksByType(String userID, String typeID,
			PageParameter pp);

	public FlowTask findTaskByNodeAndProc(String flowNodeID, String flowProcID);

	public Integer findMyTasksToAssignNum(String userID);

	public void doDistributeTask(String userID, String taskID,
			String userToDistribute);

	@SuppressWarnings("unchecked")
	public void doTaskPreviewProcess(List result);

	/**
	 * �����������б�
	 * 
	 * @param userID
	 *            String
	 * @param taskID
	 *            String
	 */
	public void doFenfaNewTask(String userID, String taskID);

	public List<FlowTask> findTasksByProc(String flowProcID);

	public void doNeedAsssign(String userID, String taskID);

	public int doRefuseTasks(String taskID, String[] tasksToRefuse,
			String refuseFor, String refUserID);

	public List<FlowTask> findTasksToRefuse(String taskID);

	public boolean findBackFlowTask(String userID, String taskID);
}
