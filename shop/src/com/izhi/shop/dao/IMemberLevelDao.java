package com.izhi.shop.dao;

import java.util.List;

import com.izhi.platform.util.PageParameter;
import com.izhi.shop.model.MemberLevel;

public interface IMemberLevelDao  {
	int saveMemberLevel(MemberLevel obj);
	boolean updateMemberLevel(MemberLevel obj);
	boolean deleteMemberLevel(int id);
	boolean deleteMemberLevels(List<Integer> ids) ;
	MemberLevel findMemberLevelById(int id);
	List<MemberLevel> findPage(PageParameter pp);
	int findTotalCount();
	
}
