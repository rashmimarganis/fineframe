package com.izhi.cms.dao;

import java.util.List;

import com.izhi.cms.model.Block;
import com.izhi.platform.util.PageParameter;

public interface IBlockDao {
	int saveBlock(Block obj);
	boolean updateBlock(Block obj);
	boolean deleteBlock(int id);
	boolean deleteBlocks(List<Integer> ids) ;
	Block findBlockById(int id);
	Block findBlockByName(String name);
	List<Block> findPage(PageParameter pp);
	int findTotalCount();
}
