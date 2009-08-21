package com.izhi.workflow.action;

import org.apache.struts2.convention.annotation.Action;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.izhi.platform.action.BaseAction;

@Service
@Scope("prototype")
@Action("/flow/workDistributeTask")
public class WorkDistributeTaskAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8727627415835847388L;
	

}
