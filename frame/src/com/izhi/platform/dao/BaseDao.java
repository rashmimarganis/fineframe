package com.izhi.platform.dao;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public abstract class BaseDao extends HibernateDaoSupport {
	protected final Logger log=Logger.getLogger(getClass());
}
