package com.izhi.platform.dao;

import org.apache.log4j.Logger;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public abstract class BaseDao extends SqlMapClientDaoSupport {
	protected final Logger log=Logger.getLogger(getClass());
}
