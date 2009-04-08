package com.izhi.shop.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springmodules.cache.annotations.CacheFlush;
import org.springmodules.cache.annotations.Cacheable;

import com.izhi.platform.util.PageParameter;
import com.izhi.shop.dao.IAgentLevelDao;
import com.izhi.shop.model.AgentLevel;
import com.izhi.shop.service.IAgentLevelService;
@Service("agentLevelService")
public class AgentLevelServiceImpl implements IAgentLevelService {

	@Resource(name="agentLevelDao")
	private IAgentLevelDao agentLevelDao;
	public IAgentLevelDao getAgentLevelDao() {
		return agentLevelDao;
	}

	public void setAgentLevelDao(IAgentLevelDao agentLevelDao) {
		this.agentLevelDao = agentLevelDao;
	}

	@Override
	@CacheFlush(modelId="agentLevelFlushing")
	public boolean deleteAgentLevel(int id) {
		return agentLevelDao.deleteAgentLevel(id);
	}

	@Override
	@CacheFlush(modelId="agentLevelFlushing")
	public boolean deleteAgentLevels(List<Integer> ids) {
		return agentLevelDao.deleteAgentLevels(ids);
	}

	@Override
	@Cacheable(modelId="agentLevelCaching")
	public AgentLevel findAgentLevelById(int id) {
		return agentLevelDao.findAgentLevelById(id);
	}

	@Override
	@Cacheable(modelId="agentLevelCaching")
	public List<AgentLevel> findPage(PageParameter pp) {
		return agentLevelDao.findPage(pp);
	}

	@Override
	@Cacheable(modelId="agentLevelCaching")
	public int findTotalCount() {
		return agentLevelDao.findTotalCount();
	}

	@Override
	@CacheFlush(modelId="agentLevelFlushing")
	public int saveAgentLevel(AgentLevel obj) {
		return agentLevelDao.saveAgentLevel(obj);
	}

	@Override
	@CacheFlush(modelId="agentLevelFlushing")
	public boolean updateAgentLevel(AgentLevel obj) {
		return agentLevelDao.updateAgentLevel(obj);
	}

}
