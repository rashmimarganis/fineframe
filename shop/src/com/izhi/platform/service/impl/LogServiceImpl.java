package com.izhi.platform.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.izhi.platform.dao.ILogDao;
import com.izhi.platform.model.Log;
import com.izhi.platform.service.BaseService;
import com.izhi.platform.service.ILogService;
import com.izhi.platform.util.PageParameter;
@Service("logService")
public class LogServiceImpl extends BaseService implements ILogService {
	@Resource(name="logDao")
	private ILogDao logDao;
	@Override
	@Transactional
	public void delete(Integer id) {
		logDao.delete(id);
	}

	@Override
	@Transactional
	public void delete(Log obj) {
		logDao.delete(obj);
	}

	@Override
	@Transactional
	public int delete(String ids, String id) {
		return logDao.delete(ids, id);
	}

	@Override
	@Transactional
	public int delete(String ids) {
		return logDao.delete(ids);
	}

	@Override
	public void deleteAll() {
		logDao.deleteAll();
	}

	@Override
	@Transactional(readOnly=true)
	public List<Log> find(String sql) {
		return logDao.find(sql);
	}

	@Override
	@Transactional(readOnly=true)
	public List<Log> find(String sql, Object obj) {
		return logDao.find(sql, obj);
	}

	@Override
	@Transactional(readOnly=true)
	public List<Log> find(String sql, String[] keys, Object[] objs) {
		return logDao.find(sql, keys, objs);
	}

	@Override
	@Transactional(readOnly=true)
	public Log findById(Integer id) {
		return logDao.findById(id);
	}

	@Override
	@Transactional(readOnly=true)
	public List<Log> findPage(int firstResult, int maxResult, String sortField,
			String sort) {
		return logDao.findPage(firstResult, maxResult, sortField, sort);
	}

	@Override
	@Transactional
	public Integer save(Log obj) {
		return logDao.save(obj);
	}

	@Override
	@Transactional
	public Integer save(Log obj, String oldName) {
		return null;
	}


	public ILogDao getLogDao() {
		return logDao;
	}

	public void setLogDao(ILogDao dao) {
		this.logDao = dao;
	}

	@Override
	@Transactional(readOnly=true)
	public Map<String, Object> findPage(int userId, int orgId, Date beginTime,
			Date endTime,PageParameter pp) {
		Map<String,Object> m=new HashMap<String, Object>();
		m.put("totalCount", this.findTotalCount(userId, orgId, beginTime, endTime));
		m.put("objs", logDao.findPage(userId, orgId, beginTime, endTime,pp));
		return m;
	}

	@Override
	@Transactional(readOnly=true)
	public long findTotalCount(int userId, int orgId, Date beginTime,
			Date endTime) {
		return logDao.findTotalCount(userId, orgId, beginTime, endTime);
	}

	@Override
	@Transactional(readOnly=true)
	public Map<String, Object> findByUserId(int id, String beginTime,
			String endTime) {
		Map<String,Object> m=new HashMap<String, Object>();
		m.put("objs", logDao.findByUserId(id, beginTime, endTime));
		m.put("totalCount", logDao.findTotalCount(id, beginTime, endTime));
		return m;
	}

	@Override
	@Transactional
	public void update(Log obj) {
		logDao.update(obj);
	}

	@Override
	public long findTotalCount() {
		return logDao.findTotalCount();
	}

}
