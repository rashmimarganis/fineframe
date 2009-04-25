package com.izhi.shop.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springmodules.cache.annotations.CacheFlush;
import org.springmodules.cache.annotations.Cacheable;

import com.izhi.platform.util.PageParameter;
import com.izhi.shop.dao.ILinkDao;
import com.izhi.shop.model.Link;
import com.izhi.shop.service.ILinkService;
@Service("linkService")
public class LinkServiceImpl implements ILinkService {

	@Resource(name="linkDao")
	private ILinkDao linkDao;
	public ILinkDao getLinkDao() {
		return linkDao;
	}

	public void setLinkDao(ILinkDao linkDao) {
		this.linkDao = linkDao;
	}

	@Override
	@CacheFlush(modelId="linkFlushing")
	public boolean deleteLink(int id) {
		return linkDao.deleteLink(id);
	}

	@Override
	@CacheFlush(modelId="linkFlushing")
	public boolean deleteLinks(List<Integer> ids) {
		return linkDao.deleteLinks(ids);
	}

	@Override
	@Cacheable(modelId="linkCaching")
	public Link findLinkById(int id) {
		return linkDao.findLinkById(id);
	}

	@Override
	@Cacheable(modelId="linkCaching")
	public List<Link> findPage(PageParameter pp) {
		return linkDao.findPage(pp);
	}

	@Override
	@Cacheable(modelId="linkCaching")
	public int findTotalCount() {
		return linkDao.findTotalCount();
	}

	@Override
	@CacheFlush(modelId="linkFlushing")
	public int saveLink(Link obj) {
		return linkDao.saveLink(obj);
	}

	@Override
	@CacheFlush(modelId="linkFlushing")
	public boolean updateLink(Link obj) {
		return linkDao.updateLink(obj);
	}

}
