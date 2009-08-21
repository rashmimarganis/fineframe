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

import org.springframework.stereotype.Service;

import com.izhi.workflow.flowform.WorkFlowDriverTransactionable;
import com.izhi.workflow.service.IWorkflowExecuter;

@Service("workflowWorkflowExecuter")
public class WorkflowExecuterImpl implements IWorkflowExecuter {
	public String doExecute(WorkFlowDriverTransactionable driver) throws Exception {
		return driver.doTransactionExecute();
	}
}
