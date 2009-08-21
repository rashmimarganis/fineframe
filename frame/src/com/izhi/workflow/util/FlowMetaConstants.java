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

public interface FlowMetaConstants {
  public static final String VARIABLE_TO_PROCESS_OUT = "VariableToProcess_OUT";
  public static final String VARIABLE_TO_PROCESS_IN = "VariableToProcess_IN";
  public static final String PREVIEW_VARIABLE = "Preview_Variable"; 
  public static final String FLOW_PREVIEW_VARIABLE = "Flow_Preview_Variable";

  public static final String ACTIVITY_TYPE_ROUTE = "Route";
  public static final String ACTIVITY_TYPE_SUBFLOW = "SubFlow";
  public static final String ACTIVITY_TYPE_TOOL = "Tool";
  public static final String ACTIVITY_TYPE_NO = "No";

  public static final String STATE_ID = "stateID";
  public static final String STATE_NAME = "stateName";
  public static final String PERSON_STATE = "personState"; 
  public static final String DISTRIBUTABLE = "distributable"; 
}
