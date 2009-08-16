package com.izhi.platform.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.izhi.platform.model.Log;
import com.izhi.platform.util.PageParameter;

public interface ILogDao extends IBaseDao<Log,Integer> {
	List<Map<String,Object>> findPage(int userId,int orgId,Date beginTime,Date endTime,PageParameter pp);
	long findTotalCount(int userId,int orgId,Date beginTime,Date endTime);
	List<Map<String,Object>> findByUserId(int id,String beginTime,String endTime);
	long findTotalCount(int userId,String beginTime,String endTime);
	List<Map<String, Object>> findPage(PageParameter pp);
}
