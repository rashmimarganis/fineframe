package com.izhi.platform.webapp.listener;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springmodules.cache.CachingModel;
import org.springmodules.cache.interceptor.caching.CachingListener;

public class EhCacheListener implements CachingListener {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	@Override
	public void onCaching(Serializable arg0, Object arg1, CachingModel arg2) {
		
		logger.debug("Arg0:"+arg0+" arg1"+arg1+" arg2:"+arg2);
	}

}
