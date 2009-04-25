package com.izhi.shop.dao;

import java.util.List;

import com.izhi.platform.util.PageParameter;
import com.izhi.shop.model.Link;

public interface ILinkDao  {
	int saveLink(Link obj);
	boolean updateLink(Link obj);
	boolean deleteLink(int id);
	boolean deleteLinks(List<Integer> ids) ;
	Link findLinkById(int id);
	List<Link> findPage(PageParameter pp);
	int findTotalCount();
	
}