package com.izhi.workflow.model;

import java.io.Serializable;

import com.izhi.workflow.util.FlowMetaConstants;

public class WorkflowNode implements Serializable{

	private static final long serialVersionUID = 9083857927651704996L;
	private String nodeID;
	private String nodeName;
	private String[] variableToProcessOUT;
	private String[] variableToProcessIN;
	private String joinType;
	private String splitType;
	private String nodeDescription;
	private String[] postTrans;
	private String[] preTrans;
	private String stateID;
	private String stateName;
	private String[] variableToPreview;
	private String personState;
	private String distributable;
	private String nodeType;

	public WorkflowNode() {
		personState = null;
	}

	public String getNodeID() {
		return nodeID;
	}

	public void setNodeID(String nodeID) {
		this.nodeID = nodeID;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String[] getVariableToProcessOUT() {
		return variableToProcessOUT;
	}

	public void setVariableToProcessOUT(String[] variableToProcessOUT) {
		this.variableToProcessOUT = variableToProcessOUT;
	}

	public String[] getVariableToProcessIN() {
		return variableToProcessIN;
	}

	public void setVariableToProcessIN(String[] variableToProcessIN) {
		this.variableToProcessIN = variableToProcessIN;
	}

	public String getJoinType() {
		return joinType;
	}

	public void setJoinType(String joinType) {
		this.joinType = joinType;
	}

	public String getSplitType() {
		return splitType;
	}

	public void setSplitType(String splitType) {
		this.splitType = splitType;
	}

	public String getNodeDescription() {
		return nodeDescription;
	}

	public void setNodeDescription(String nodeDescription) {
		this.nodeDescription = nodeDescription;
	}

	public String[] getPostTrans() {
		return postTrans;
	}

	public void setPostTrans(String[] postTrans) {
		this.postTrans = postTrans;
	}

	public String getStateID() {
		return stateID;
	}

	public void setStateID(String stateID) {
		this.stateID = stateID;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String[] getVariableToPreview() {
		return variableToPreview;
	}

	public void setVariableToPreview(String[] variableToPreview) {
		this.variableToPreview = variableToPreview;
	}

	public String getPersonState() {
		return personState;
	}

	public void setPersonState(String personState) {
		this.personState = personState;
	}

	public String getDistributable() {
		return distributable;
	}

	public void setDistributable(String distributable) {
		this.distributable = distributable;
	}

	public boolean isDistributable() {
		if (distributable == null || distributable.trim().length() == 0) {
			return false;
		} else {
			return true;
		}
	}

	public String getNodeType() {
		return nodeType;
	}

	public boolean isRouteNode() {
		return (getNodeType() != null && getNodeType().equals(
				FlowMetaConstants.ACTIVITY_TYPE_ROUTE));
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

	public String[] getPreTrans() {
		return preTrans;
	}

	public void setPreTrans(String[] preTrans) {
		this.preTrans = preTrans;
	}

}
