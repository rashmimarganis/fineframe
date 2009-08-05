package com.izhi.cms.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springmodules.cache.annotations.CacheFlush;
import org.springmodules.cache.annotations.Cacheable;

import com.izhi.cms.dao.IBlockDao;
import com.izhi.cms.model.Block;
import com.izhi.cms.service.IBlockService;
import com.izhi.platform.util.PageParameter;
@Service("blockService")
public class BlockServiceImpl implements IBlockService {

	@Resource(name="blockDao")
	private IBlockDao blockDao;
	public IBlockDao getBlockDao() {
		return blockDao;
	}

	public void setBlockDao(IBlockDao blockDao) {
		this.blockDao = blockDao;
	}

	@Override
	@CacheFlush(modelId="blockFlushing")
	public boolean deleteBlock(int id) {
		return blockDao.deleteBlock(id);
	}

	@Override
	@CacheFlush(modelId="blockFlushing")
	public boolean deleteBlocks(List<Integer> ids) {
		return blockDao.deleteBlocks(ids);
	}

	@Override
	@Cacheable(modelId="blockCaching")
	public Block findBlockById(int id) {
		return blockDao.findBlockById(id);
	}

	@Override
	@Cacheable(modelId="blockCaching")
	public List<Block> findPage(PageParameter pp) {
		return blockDao.findPage(pp);
	}

	@Override
	@Cacheable(modelId="blockCaching")
	public int findTotalCount() {
		return blockDao.findTotalCount();
	}

	@Override
	@CacheFlush(modelId="blockFlushing")
	public int saveBlock(Block obj) {
		return blockDao.saveBlock(obj);
	}

	@Override
	@CacheFlush(modelId="blockFlushing")
	public boolean updateBlock(Block obj) {
		return blockDao.updateBlock(obj);
	}

	@Override
	public Block findBlockByName(String name) {
		return blockDao.findBlockByName(name);
	}

}
