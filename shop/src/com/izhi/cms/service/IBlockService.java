package com.izhi.cms.service;

import java.util.List;

import com.izhi.cms.model.Block;
import com.izhi.platform.util.PageParameter;

public interface IBlockService {
	int saveBlock(Block obj);
	boolean updateBlock(Block obj);
	boolean deleteBlock(int id);
	boolean deleteBlocks(List<Integer> ids) ;
	Block findBlockById(int id);
	Block findBlockByName(String name);
	List<Block> findPage(PageParameter pp);
	int findTotalCount();
}
