package com.izhi.shop.service;

import java.util.List;

import com.izhi.platform.util.PageParameter;
import com.izhi.shop.model.AgentLevel;

public interface IAgentLevelService {
	int saveAgentLevel(AgentLevel obj);
	boolean updateAgentLevel(AgentLevel obj);
	boolean deleteAgentLevel(int id);
	boolean deleteAgentLevels(List<Integer> ids) ;
	AgentLevel findAgentLevelById(int id);
	List<AgentLevel> findPage(PageParameter pp);
	int findTotalCount();
}
