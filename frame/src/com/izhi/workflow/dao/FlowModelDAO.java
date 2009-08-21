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

import java.util.HashMap;
import java.util.Map;

import org.jdom.Document;
import org.jdom.Element;

import com.izhi.workflow.model.FlowMetaFile;
import com.izhi.workflow.model.FlowProcTransition;
import com.izhi.workflow.model.WorkflowNode;
import com.izhi.workflow.util.ElementUtils;
import com.izhi.workflow.util.FlowDataField;
import com.izhi.workflow.util.FlowMetaConstants;

public class FlowModelDAO {
	private Element root;
	private Map<String,String[]> TypeDeclarations;

	public FlowModelDAO(Document flowMeta) {
		Document doc = null;
		try {
			doc = flowMeta;
			this.root = doc.getRootElement();
			this.TypeDeclarations = getTypeDeclarations();
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException("invalade model file doc:" + doc);
		}
	}

	// ------------------------------------------------------------------------------
	public Map<String,String[]> getTypeDeclarations() {

		Map<String, String[]> result = new HashMap<String,String[]>();

		Element declarations[] = ElementUtils.findElements(root,
				"TypeDeclaration", null, null, null);
		if (declarations != null && declarations.length > 0) {
			for (int i = 0; i < declarations.length; i++) {
				Element temTD = declarations[i];
				String declareID = temTD.getAttributeValue("Id");
				Element enumerations[] = ElementUtils.findElements(temTD,
						"EnumerationValue", null, null, null);
				if (enumerations != null && enumerations.length > 0) {
					String[] values = new String[enumerations.length];
					for (int j = 0; j < enumerations.length; j++) {
						values[j] = enumerations[j].getAttributeValue("Name");
					}
					result.put(declareID, values);
				}
			}
		}
		return result;
	}

	// ------------------------------------------------------------------------------
	public String[] getFlowVariablesToPreview(String flowProcessID) {
		String[] result = null;
		Element processRoot = this.getFlowProcessRoot(flowProcessID);

		String attr[] = new String[] { "Name" };
		String val[] = new String[] { FlowMetaConstants.FLOW_PREVIEW_VARIABLE };
		Element[] elemArray = ElementUtils.findElements(processRoot, null,
				null, attr, val);

		if (elemArray != null && elemArray.length > 0) {
			result = new String[elemArray.length];
			for (int i = 0; i < elemArray.length; i++) {
				Element temTD = elemArray[i];
				result[i] = temTD.getAttributeValue("Value");
			}
		}
		return result;
	}

	// ------------------------------------------------------------------------------
	private Element getFlowProcessRoot(String flowProcessID) {
		try {
			String[] attrs = new String[] { "Id" };
			String[] vals = new String[] { flowProcessID };
			Element[] processRoot = ElementUtils.findElements(root,
					"WorkflowProcess", null, attrs, vals);
			if (processRoot == null || processRoot.length < 1) {
				throw new RuntimeException("�Ҳ������㣺" + flowProcessID);
			} else {
				return processRoot[0];
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException("");
		}
	}

	// ------------------------------------------------------------------------------
	public String getStartNodeID(String flowProcessID) {
		Element processRoot = this.getFlowProcessRoot(flowProcessID);
		String attr[] = new String[] { "Name" };
		String val[] = new String[] { "StartOfWorkflow" };

		Element[] elemArray = ElementUtils.findElements(processRoot, null,
				null, attr, val);
		if (elemArray == null || elemArray.length == 0) {
			throw new RuntimeException("�Ҳ�����ʼ��㣺" + flowProcessID);
		} else {
			String result = elemArray[0].getAttributeValue("Value");

			String result2 = result.substring(result.indexOf(";") + 1);
			return result2.substring(0, result2.indexOf(";"));
		}
	}

	// ------------------------------------------------------------------------------
	public String[] getEndNodeIDs(String flowProcessID) {
		Element processRoot = this.getFlowProcessRoot(flowProcessID);
		String attr[] = new String[] { "Name" };
		String val[] = new String[] { "EndOfWorkflow" };
		Element[] elemArray = ElementUtils.findElements(processRoot, null,
				null, attr, val);
		if (elemArray == null || elemArray.length == 0) {
			throw new RuntimeException("�Ҳ��������㣺" + flowProcessID);
		} else {
			String results[] = new String[elemArray.length];
			for (int i = 0; i < elemArray.length; i++) {
				String result = elemArray[0].getAttributeValue("Value");

				String result2 = result.substring(result.indexOf(";") + 1);
				results[i] = result2.substring(0, result2.indexOf(";"));
			}
			return results;
		}
	}

	// ------------------------------------------------------------------------------
	public WorkflowNode findWorkflowNodeByID(String flowProcessID, String nodeID) {
		Element processRoot = this.getFlowProcessRoot(flowProcessID);
		String attr[] = new String[] { "Id" };
		String val[] = new String[] { nodeID };

		Element[] elemArray = ElementUtils.findElements(processRoot,
				"Activity", null, attr, val);
		if (elemArray == null || elemArray.length == 0) {
			throw new RuntimeException("�����ڵĹ�����ڵ�:" + nodeID + "!");
		} else {
			String activityName = "";
			String activityType = "";
			String description = "";
			String[] variablesOut = null;
			String[] variablesIn = null;
			String[] variablesPreview = null;
			String joinType = "";
			String splitType = "";
			String[] postTrans = null;
			String stateID = "";
			String stateName = "";
			String personState = null;
			String distributable = null;

			Element activityRoot = elemArray[0];

			// ����
			activityName = activityRoot.getAttributeValue("Name");

			// �����
			Element[] temDescription = ElementUtils.findElements(activityRoot,
					"Description", null, null, null);
			if (temDescription == null || temDescription.length == 0) {
				description = null;
			} else {
				description = temDescription[0].getText();
			}

			// �����
			Element[] temRoute = ElementUtils.findElements(activityRoot,
					"Route", null, null, null);
			Element[] temImp = ElementUtils.findElements(activityRoot,
					"Implementation", null, null, null);
			if (temRoute != null && temRoute.length > 0) {
				activityType = FlowMetaConstants.ACTIVITY_TYPE_ROUTE;
			}
			if (temImp != null && temImp.length > 0) {
				Element typeNo = temImp[0]
						.getChild(FlowMetaConstants.ACTIVITY_TYPE_NO);
				Element typeSubFlow = temImp[0]
						.getChild(FlowMetaConstants.ACTIVITY_TYPE_SUBFLOW);
				Element typeTool = temImp[0]
						.getChild(FlowMetaConstants.ACTIVITY_TYPE_TOOL);
				if (typeNo != null) {
					activityType = FlowMetaConstants.ACTIVITY_TYPE_NO;
				}
				if (typeSubFlow != null) {
					activityType = FlowMetaConstants.ACTIVITY_TYPE_SUBFLOW;
				}
				if (typeTool != null) {
					activityType = FlowMetaConstants.ACTIVITY_TYPE_TOOL;
				}
			}

			// stateID
			attr = new String[] { "Name" };
			val = new String[] { FlowMetaConstants.STATE_ID };
			Element[] temStateID = ElementUtils.findElements(activityRoot,
					null, null, attr, val);
			if (temStateID == null || temStateID.length == 0) {
				stateID = null;
			} else {
				stateID = temStateID[0].getAttributeValue("Value");
			}

			// stateName
			attr = new String[] { "Name" };
			val = new String[] { FlowMetaConstants.STATE_NAME };
			Element[] temStateName = ElementUtils.findElements(activityRoot,
					null, null, attr, val);
			if (temStateName == null || temStateName.length == 0) {
				stateName = null;
			} else {
				stateName = temStateName[0].getAttributeValue("Value");
			}

			// personState
			attr = new String[] { "Name" };
			val = new String[] { FlowMetaConstants.PERSON_STATE };
			Element[] temPersonState = ElementUtils.findElements(activityRoot,
					null, null, attr, val);
			if (temPersonState == null || temPersonState.length == 0) {
				personState = null;
			} else {
				personState = temPersonState[0].getAttributeValue("Value");
			}

			// distributable
			attr = new String[] { "Name" };
			val = new String[] { FlowMetaConstants.DISTRIBUTABLE };
			Element[] temDistributable = ElementUtils.findElements(
					activityRoot, null, null, attr, val);
			if (temDistributable == null || temDistributable.length == 0) {
				distributable = null;
			} else {
				distributable = temDistributable[0].getAttributeValue("Value");
			}

			// �������
			attr = new String[] { "Name" };
			val = new String[] { FlowMetaConstants.VARIABLE_TO_PROCESS_OUT };
			Element[] temVariableOut = ElementUtils.findElements(activityRoot,
					null, null, attr, val);

			if (temVariableOut != null && temVariableOut.length > 0) {
				variablesOut = new String[temVariableOut.length];
				for (int i = 0; i < temVariableOut.length; i++) {
					variablesOut[i] = temVariableOut[i]
							.getAttributeValue("Value");
				}
			}

			// ��������
			attr = new String[] { "Name" };
			val = new String[] { FlowMetaConstants.VARIABLE_TO_PROCESS_IN };
			Element[] temVariableIn = ElementUtils.findElements(activityRoot,
					null, null, attr, val);
			if (temVariableIn != null && temVariableIn.length > 0) {
				variablesIn = new String[temVariableIn.length];
				for (int i = 0; i < temVariableIn.length; i++) {
					variablesIn[i] = temVariableIn[i]
							.getAttributeValue("Value");
				}
			}

			// �Ԥ�1�
			attr = new String[] { "Name" };
			val = new String[] { FlowMetaConstants.PREVIEW_VARIABLE };
			Element[] temVariablePrev = ElementUtils.findElements(activityRoot,
					null, null, attr, val);
			if (temVariablePrev != null && temVariablePrev.length > 0) {
				variablesPreview = new String[temVariablePrev.length];
				for (int i = 0; i < temVariablePrev.length; i++) {
					variablesPreview[i] = temVariablePrev[i]
							.getAttributeValue("Value");
				}
			}

			// �ת������:�ϲ�
			Element[] temJoinType = ElementUtils.findElements(activityRoot,
					"Join", null, null, null);
			if (temJoinType == null || temJoinType.length == 0) {
				joinType = null;
			} else {
				joinType = temJoinType[0].getAttributeValue("Type");
			}

			// �ת������:����
			Element[] temSplitType = ElementUtils.findElements(activityRoot,
					"Split", null, null, null);
			if (temSplitType == null || temSplitType.length == 0) {
				splitType = null;
			} else {
				splitType = temSplitType[0].getAttributeValue("Type");
			}

			// ���ת��
			Element[] temPostTrans = ElementUtils.findElements(activityRoot,
					"TransitionRef", null, null, null);
			if (temPostTrans != null && temPostTrans.length > 0) {
				postTrans = new String[temPostTrans.length];
				for (int i = 0; i < temPostTrans.length; i++) {
					postTrans[i] = temPostTrans[i].getAttributeValue("Id");
				}
			}

			// ���
			WorkflowNode workflowNode = new WorkflowNode();
			workflowNode.setNodeID(nodeID);
			workflowNode.setNodeName(activityName);
			workflowNode.setNodeDescription(description);
			workflowNode.setNodeType(activityType);
			workflowNode.setVariableToProcessIN(variablesIn);
			workflowNode.setVariableToProcessOUT(variablesOut);
			workflowNode.setVariableToPreview(variablesPreview);
			workflowNode.setJoinType(joinType);
			workflowNode.setSplitType(splitType);
			workflowNode.setStateID(stateID);
			workflowNode.setStateName(stateName);
			workflowNode.setPersonState(personState);
			workflowNode.setDistributable(distributable);

			if (postTrans != null && postTrans.length > 0) {// ���Ⱥ�����
				workflowNode.setPostTrans(postTrans);
			} else {
				workflowNode.setPostTrans(getTransitionIDsFrom(flowProcessID,
						nodeID));
			}
			workflowNode.setPreTrans(this.getTransitionIDsTo(flowProcessID,
					nodeID));

			return workflowNode;
		}
	}

	// ------------------------------------------------------------------------------
	public String[] getTransitionIDsFrom(String flowProcessID, String fromNodeID) {
		Element processRoot = this.getFlowProcessRoot(flowProcessID);
		String attr[] = new String[] { "From" };
		String val[] = new String[] { fromNodeID };

		Element[] elemArray = ElementUtils.findElements(processRoot, null,
				null, attr, val);
		if (elemArray == null || elemArray.length == 0) {
			return null;
		} else {
			String[] results = new String[elemArray.length];
			for (int i = 0; i < elemArray.length; i++) {
				results[i] = elemArray[i].getAttributeValue("Id");
			}
			return results;
		}
	}

	// ------------------------------------------------------------------------------
	public String[] getTransitionIDsTo(String flowProcessID, String toNodeID) {
		Element processRoot = this.getFlowProcessRoot(flowProcessID);
		String attr[] = new String[] { "To" };
		String val[] = new String[] { toNodeID };

		Element[] elemArray = ElementUtils.findElements(processRoot, null,
				null, attr, val);
		if (elemArray == null || elemArray.length == 0) {
			return null;
		} else {
			String[] results = new String[elemArray.length];
			for (int i = 0; i < elemArray.length; i++) {
				results[i] = elemArray[i].getAttributeValue("Id");
			}
			return results;
		}
	}

	// ------------------------------------------------------------------------------
	public FlowProcTransition getTransitionByID(String flowProcessID,
			String transitionID) {
		Element processRoot = this.getFlowProcessRoot(flowProcessID);
		String attr[] = new String[] { "Id" };
		String val[] = new String[] { transitionID };

		Element[] elemArray = ElementUtils.findElements(processRoot,
				"Transition", null, attr, val);
		if (elemArray == null || elemArray.length == 0) {
			throw new RuntimeException("�Ҳ���transition��" + transitionID);
		} else {
			Element transRoot = elemArray[0];
			FlowProcTransition ft = new FlowProcTransition();
			ft.setWorkflowTransitionID(transitionID);

			String fromNodeID = transRoot.getAttributeValue("From");
			ft.setFromNodeID(fromNodeID);

			String toNodeID = transRoot.getAttributeValue("To");
			ft.setToNodeID(toNodeID);

			Element conditions[] = ElementUtils.findElements(transRoot,
					"Condition", null, null, null);
			if (conditions != null && conditions.length > 0) {
				Element condition = conditions[0];
				ft.setConditionType(condition.getAttributeValue("Type"));
				ft.setConditionExpress(condition.getText());
			}

			return ft;
		}
	}

	// ------------------------------------------------------------------------------
	public HashMap<String,FlowDataField> getDataFields(String flowProcessID) {
		Element processRoot = this.getFlowProcessRoot(flowProcessID);
		Element[] temDataFields = ElementUtils.findElements(processRoot,
				"DataField", null, null, null);
		if (temDataFields == null) {
			return null;
		}
		HashMap<String,FlowDataField> flowDataFields = new HashMap<String,FlowDataField>();
		for (int i = 0; i < temDataFields.length; i++) {
			Element eleField = temDataFields[i];

			FlowDataField aDataField = new FlowDataField();

			aDataField.setFieldID(eleField.getAttributeValue("Id"));
			aDataField.setFieldName(eleField.getAttributeValue("Name"));

			Element[] eleDescription = ElementUtils.findElements(eleField,
					"Description", null, null, null);
			Element[] eleInitialValue = ElementUtils.findElements(eleField,
					"InitialValue", null, null, null);
			if (eleDescription != null && eleDescription.length > 0) {
				aDataField.setFieldDescription(eleDescription[0].getText());
			}
			if (eleDescription != null && eleDescription.length > 0) {
				aDataField.setInitialValue(eleInitialValue[0].getText());
			}

			Element[] eleDeclaredType = ElementUtils.findElements(eleField,
					"DeclaredType", null, null, null);
			if (eleDeclaredType != null && eleDeclaredType.length > 0) {
				String declareID = eleDeclaredType[0].getAttributeValue("Id");
				Object obj = this.TypeDeclarations.get(declareID);
				String[] valueEnumerations = (String[]) obj;

				aDataField.setValueEnumerations(valueEnumerations);
			}

			flowDataFields.put(aDataField.getFieldID(), aDataField);
		}
		return flowDataFields;
	}

	// ------------------------------------------------------------------------------
	public String[] getAllNodeIDs(String flowProcessID) {
		Element processRoot = this.getFlowProcessRoot(flowProcessID);
		Element[] temNodes = ElementUtils.findElements(processRoot, "Activity",
				null, null, null);
		if (temNodes == null) {
			return null;
		}
		String nodeIDs[] = new String[temNodes.length];
		for (int i = 0; i < temNodes.length; i++) {
			nodeIDs[i] = temNodes[i].getAttributeValue("Id");
		}

		return nodeIDs;
	}

	// ------------------------------------------------------------------------------
	public FlowMetaFile[] getAllFlowMetaFiles() {
		Element[] temMetas = ElementUtils.findElements(root, "WorkflowProcess",
				null, null, null);
		if (temMetas == null) {
			return null;
		}
		System.out.println("=================FlowMetaFiles Size:"+temMetas.length);
		FlowMetaFile flowMetas[] = new FlowMetaFile[temMetas.length];
		for (int i = 0; i < temMetas.length; i++) {
			flowMetas[i] = new FlowMetaFile();
			Element eleMeta = temMetas[i];

			flowMetas[i].setFlowProcessID(eleMeta.getAttributeValue("Id"));
			flowMetas[i].setFlowMetaName(eleMeta.getAttributeValue("Name"));
			flowMetas[i].setAccessLevel(eleMeta
					.getAttributeValue("AccessLevel"));

			Element[] temCreated = ElementUtils.findElements(eleMeta,
					"Created", null, null, null);
			if (temCreated != null && temCreated.length > 0) {
				String createdTime = temCreated[0].getText();
				flowMetas[i].setCreatedTime(createdTime);
			}
		}

		return flowMetas;
	}

}
