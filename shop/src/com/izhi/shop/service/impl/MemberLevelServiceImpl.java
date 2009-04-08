package com.izhi.shop.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springmodules.cache.annotations.CacheFlush;
import org.springmodules.cache.annotations.Cacheable;

import com.izhi.platform.util.PageParameter;
import com.izhi.shop.dao.IMemberLevelDao;
import com.izhi.shop.model.MemberLevel;
import com.izhi.shop.service.IMemberLevelService;
@Service("memberLevelService")
public class MemberLevelServiceImpl implements IMemberLevelService {

	@Resource(name="memberLevelDao")
	private IMemberLevelDao memberLevelDao;
	public IMemberLevelDao getMemberLevelDao() {
		return memberLevelDao;
	}

	public void setMemberLevelDao(IMemberLevelDao memberLevelDao) {
		this.memberLevelDao = memberLevelDao;
	}

	@Override
	@CacheFlush(modelId="memberLevelFlushing")
	public boolean deleteMemberLevel(int id) {
		return memberLevelDao.deleteMemberLevel(id);
	}

	@Override
	@CacheFlush(modelId="memberLevelFlushing")
	public boolean deleteMemberLevels(List<Integer> ids) {
		return memberLevelDao.deleteMemberLevels(ids);
	}

	@Override
	@Cacheable(modelId="memberLevelCaching")
	public MemberLevel findMemberLevelById(int id) {
		return memberLevelDao.findMemberLevelById(id);
	}

	@Override
	@Cacheable(modelId="memberLevelCaching")
	public List<MemberLevel> findPage(PageParameter pp) {
		return memberLevelDao.findPage(pp);
	}

	@Override
	@Cacheable(modelId="memberLevelCaching")
	public int findTotalCount() {
		return memberLevelDao.findTotalCount();
	}

	@Override
	@CacheFlush(modelId="memberLevelFlushing")
	public int saveMemberLevel(MemberLevel obj) {
		return memberLevelDao.saveMemberLevel(obj);
	}

	@Override
	@CacheFlush(modelId="memberLevelFlushing")
	public boolean updateMemberLevel(MemberLevel obj) {
		return memberLevelDao.updateMemberLevel(obj);
	}

}
