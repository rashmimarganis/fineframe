package com.izhi.framework.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springmodules.cache.annotations.CacheFlush;
import org.springmodules.cache.annotations.Cacheable;

import com.izhi.framework.dao.IFrameProjectDao;
import com.izhi.framework.model.FrameProject;
import com.izhi.framework.service.IFrameProjectService;
import com.izhi.platform.util.PageParameter;
@Service("frameProjectService")
public class FrameProjectServiceImpl implements IFrameProjectService {

	@Resource(name="frameProjectDao")
	private IFrameProjectDao frameProjectDao;
	
	@Override
	@CacheFlush(modelId="frameProjectFlushing")
	public boolean deleteProject(int id) {
		return frameProjectDao.deleteProject(id);
	}

	@Override
	@CacheFlush(modelId="frameProjectFlushing")
	public boolean deleteProjects(List<Integer> ids) {
		return frameProjectDao.deleteProjects(ids);
	}

	@Override
	@Cacheable(modelId="frameProjectCaching")
	public FrameProject findProjectById(int id) {
		return frameProjectDao.findProjectById(id);
	}

	@Override
	@Cacheable(modelId="frameProjectCaching")
	public List<Map<String,Object>> findPage(PageParameter pp) {
		return frameProjectDao.findPage(pp);
	}

	@Override
	@Cacheable(modelId="frameProjectCaching")
	public int findTotalCount() {
		return frameProjectDao.findTotalCount();
	}

	@Override
	@CacheFlush(modelId="frameProjectFlushing")
	public int saveProject(FrameProject obj) {
		return frameProjectDao.saveProject(obj);
	}

	@Override
	@CacheFlush(modelId="frameProjectFlushing")
	public boolean updateProject(FrameProject obj) {
		return frameProjectDao.updateProject(obj);
	}

	@Override
	@Cacheable(modelId="frameProjectCaching")
	public FrameProject findProjectByName(String name) {
		return frameProjectDao.findProjectByName(name);
	}

	public IFrameProjectDao getFrameProjectDao() {
		return frameProjectDao;
	}

	public void setFrameProjectDao(IFrameProjectDao frameProjectDao) {
		this.frameProjectDao = frameProjectDao;
	}

}
