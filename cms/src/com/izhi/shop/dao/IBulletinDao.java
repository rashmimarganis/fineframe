package com.izhi.shop.dao;

import java.util.List;

import com.izhi.platform.util.PageParameter;
import com.izhi.shop.model.Bulletin;

public interface IBulletinDao  {
	int saveBulletin(Bulletin obj);
	boolean updateBulletin(Bulletin obj);
	boolean deleteBulletin(int id,int shipId);
	boolean deleteBulletins(List<Integer> ids,int shopId) ;
	Bulletin findBulletinById(int id);
	List<Bulletin> findPage(PageParameter pp);
	List<Bulletin> findPage(PageParameter pp,int shopId);
	int findTotalCount();
	int findTotalCount(int id);
	List<Bulletin> findTopAll();
}
