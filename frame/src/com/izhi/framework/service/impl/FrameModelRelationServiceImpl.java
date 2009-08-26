package com.izhi.framework.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springmodules.cache.annotations.CacheFlush;
import org.springmodules.cache.annotations.Cacheable;

import com.izhi.framework.dao.IFrameModelRelationDao;
import com.izhi.framework.model.FrameModelRelation;
import com.izhi.framework.service.IFrameModelRelationService;
import com.izhi.platform.util.PageParameter;
@Service("frameModelRelationService")
public class FrameModelRelationServiceImpl implements IFrameModelRelationService {

	@Resource(name="frameModelRelationDao")
	private IFrameModelRelationDao frameModelRelationDao;
	
	@Override
	@CacheFlush(modelId="frameModelRelationFlushing")
	public boolean deleteRelation(int id) {
		return frameModelRelationDao.deleteRelation(id);
	}

	@Override
	@CacheFlush(modelId="frameModelRelationFlushing")
	public boolean deleteRelations(List<Integer> ids) {
		return frameModelRelationDao.deleteRelations(ids);
	}

	@Override
	@Cacheable(modelId="frameModelRelationCaching")
	public FrameModelRelation findRelationById(int id) {
		return frameModelRelationDao.findRelationById(id);
	}

	@Override
	@Cacheable(modelId="frameModelRelationCaching")
	public List<Map<String,Object>> findPage(PageParameter pp) {
		return frameModelRelationDao.findPage(pp);
	}

	@Override
	@Cacheable(modelId="frameModelRelationCaching")
	public int findTotalCount() {
		return frameModelRelationDao.findTotalCount();
	}

	@Override
	@CacheFlush(modelId="frameModelRelationFlushing")
	public int saveRelation(FrameModelRelation obj) {
		return frameModelRelationDao.saveRelation(obj);
	}

	@Override
	@CacheFlush(modelId="frameModelRelationFlushing")
	public boolean updateRelation(FrameModelRelation obj) {
		return frameModelRelationDao.updateRelation(obj);
	}

	@Override
	@Cacheable(modelId="frameModelRelationCaching")
	public FrameModelRelation findRelationByName(String name) {
		return frameModelRelationDao.findRelationByName(name);
	}



	@Override
	@Cacheable(modelId="frameModelRelationCaching")
	public List<Map<String,Object>> findJsonById(int id) {
		return frameModelRelationDao.findJsonById(id);
	}
	


	public IFrameModelRelationDao getFrameModelRelationDao() {
		return frameModelRelationDao;
	}

	public void setFrameModelRelationDao(
			IFrameModelRelationDao frameModelRelationDao) {
		this.frameModelRelationDao = frameModelRelationDao;
	}

	@Override
	public List<Map<String, Object>> findPageByModel(int mid, PageParameter pp) {
		return frameModelRelationDao.findPageByModel(mid, pp);
	}

	@Override
	public int findTotalCount(int mid) {
		return frameModelRelationDao.findTotalCount(mid);
	}

}
