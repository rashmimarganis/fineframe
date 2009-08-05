package com.izhi.platform.service;

import java.util.Date;
import java.util.Map;

import com.izhi.platform.model.Log;
import com.izhi.platform.util.PageParameter;

public interface ILogService extends IBaseService<Log,Integer> {
	Map<String,Object> findPage(int userId,int orgId,Date beginTime,Date endTime,PageParameter pp);
	long findTotalCount(int userId,int orgId,Date beginTime,Date endTime);
	long findTotalCount();
	Map<String,Object> findByUserId(int id,String beginTime,String endTime);
}
