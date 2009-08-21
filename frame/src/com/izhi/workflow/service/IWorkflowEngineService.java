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

import java.util.Collection;
import java.util.HashMap;

import com.izhi.workflow.model.ActivityReport;
@SuppressWarnings("unchecked")
public interface IWorkflowEngineService {
	public void processSubmitTask(String userID, String taskID);

	public void processActivityReport(String userID,
			ActivityReport activityReport);

	public void processBatchActivityReports(String userID,
			Collection activityReports);

	public void simulateFLowStart(String flowDeployID, HashMap driverOutputData);

	public void simulateFLowSubmitTask(String flowProcID, String nodeID,
			HashMap driverOutputData);

}
