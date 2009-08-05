package com.izhi.shop.service;

import java.util.List;

import com.izhi.platform.util.PageParameter;
import com.izhi.shop.model.Bulletin;

public interface IBulletinService {
	int saveBulletin(Bulletin obj);
	boolean updateBulletin(Bulletin obj);
	boolean deleteBulletin(int id,int shopId);
	boolean deleteBulletins(List<Integer> ids,int shopId) ;
	Bulletin findBulletinById(int id);
	List<Bulletin> findPage(PageParameter pp);
	List<Bulletin> findPage(PageParameter pp,int shopId);
	int findTotalCount();
	int findTotalCount(int shopId);
	List<Bulletin> findTopAll();
}
