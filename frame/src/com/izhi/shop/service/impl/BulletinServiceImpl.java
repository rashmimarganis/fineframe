package com.izhi.shop.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springmodules.cache.annotations.CacheFlush;
import org.springmodules.cache.annotations.Cacheable;

import com.izhi.platform.util.PageParameter;
import com.izhi.shop.dao.IBulletinDao;
import com.izhi.shop.model.Bulletin;
import com.izhi.shop.service.IBulletinService;
@Service("bulletinService")
public class BulletinServiceImpl implements IBulletinService {

	@Resource(name="bulletinDao")
	private IBulletinDao bulletinDao;
	public IBulletinDao getBulletinDao() {
		return bulletinDao;
	}

	public void setBulletinDao(IBulletinDao bulletinDao) {
		this.bulletinDao = bulletinDao;
	}

	@Override
	@CacheFlush(modelId="bulletinFlushing")
	public boolean deleteBulletin(int id,int shopId) {
		return bulletinDao.deleteBulletin(id,shopId);
	}

	@Override
	@CacheFlush(modelId="bulletinFlushing")
	public boolean deleteBulletins(List<Integer> ids,int shopId) {
		return bulletinDao.deleteBulletins(ids,shopId);
	}

	@Override
	@Cacheable(modelId="bulletinCaching")
	public Bulletin findBulletinById(int id) {
		return bulletinDao.findBulletinById(id);
	}

	@Override
	@Cacheable(modelId="bulletinCaching")
	public List<Bulletin> findPage(PageParameter pp) {
		return bulletinDao.findPage(pp);
	}

	@Override
	@Cacheable(modelId="bulletinCaching")
	public int findTotalCount() {
		return bulletinDao.findTotalCount();
	}

	@Override
	@CacheFlush(modelId="bulletinFlushing")
	public int saveBulletin(Bulletin obj) {
		return bulletinDao.saveBulletin(obj);
	}

	@Override
	@CacheFlush(modelId="bulletinFlushing")
	public boolean updateBulletin(Bulletin obj) {
		return bulletinDao.updateBulletin(obj);
	}

	@Override
	@Cacheable(modelId="bulletinCaching")
	public List<Bulletin> findPage(PageParameter pp, int parentId) {
		return bulletinDao.findPage(pp, parentId);
	}

	@Override
	@Cacheable(modelId="bulletinCaching")
	public int findTotalCount(int id) {
		return bulletinDao.findTotalCount(id);
	}

	@Override
	@Cacheable(modelId="bulletinCaching")
	public List<Bulletin> findTopAll() {
		return bulletinDao.findTopAll();
	}

}
