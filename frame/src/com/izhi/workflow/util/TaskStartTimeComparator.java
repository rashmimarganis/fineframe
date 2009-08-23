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
package com.izhi.workflow.util;

import java.util.Comparator;

import com.izhi.workflow.model.FlowTask;

public class TaskStartTimeComparator implements Comparator {
	public TaskStartTimeComparator() {
		super();
	}

	public int compare(Object o1, Object o2) {
		FlowTask tm1 = (FlowTask) o1;
		FlowTask tm2 = (FlowTask) o2;
		if (tm2.getStartTime() != null
				&& tm2.getStartTime().trim().length() > 0) {
			return tm2.getStartTime().compareTo(tm1.getStartTime());
		} else {
			return 1; // ���ַ����
		}
	}

}