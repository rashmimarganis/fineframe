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

public class FlowDataField {
  private String fieldID;
  private String fieldName;
  private String initialValue;
  private String fieldDescription;
  private String[] valueEnumerations;
  public FlowDataField() {
  }
  public String getFieldID() {
    return fieldID;
  }
  public void setFieldID(String fieldID) {
    this.fieldID = fieldID;
  }
  public String getFieldName() {
    return fieldName;
  }
  public void setFieldName(String fieldName) {
    this.fieldName = fieldName;
  }
  public String getInitialValue() {
    return initialValue;
  }
  public void setInitialValue(String initialValue) {
    this.initialValue = initialValue;
  }
  public String getFieldDescription() {
    return fieldDescription;
  }
  public void setFieldDescription(String fieldDescription) {
    this.fieldDescription = fieldDescription;
  }
  public String[] getValueEnumerations() {
    return valueEnumerations;
  }
  public void setValueEnumerations(String[] valueEnumerations) {
    this.valueEnumerations = valueEnumerations;
  }
}
