package com.izhi.platform.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

import org.springframework.stereotype.Service;

import com.izhi.platform.service.IEhCacheService;
@Service("ehCacheService")
public class EhCacheServiceImpl implements IEhCacheService {
	@Resource(name="ehCacheFacade")
	private EhCacheFacade ehCachefacade;
	@Override
	public List<Map<String,Object>> findAll() {
		List<Map<String,Object>> l=new ArrayList<Map<String,Object>>();
		CacheManager cm=ehCachefacade.getCacheManager();
		String names[]=cm.getCacheNames();
		for(String n:names){
			Cache c=cm.getCache(n);
			Map<String,Object> m=new HashMap<String, Object>();
			m.put("name", c.getName());
			m.put("averageGetTime", c.getAverageGetTime());
			m.put("guid", c.getGuid());
			m.put("memoryStoreSize", c.getMemoryStoreSize());
			m.put("size", c.getSize());
			m.put("diskStoreSize", c.getDiskStoreSize());
			m.put("associatedCacheName", c.getStatistics().getAssociatedCacheName());
			m.put("accuracyDescription", c.getStatistics().getStatisticsAccuracyDescription());
			m.put("cacheHints", c.getStatistics().getCacheHits());
			m.put("cacheMisses", c.getStatistics().getCacheMisses());
			m.put("accuracy", c.getStatistics().getStatisticsAccuracy());
			m.put("onDiskHits", c.getStatistics().getOnDiskHits());
			m.put("evictionCount", c.getStatistics().getEvictionCount());
			l.add(m);
		}
		return l;
	}

	@Override
	public void clear(String[] names) {
		CacheManager cm=ehCachefacade.getCacheManager();
		for(String name:names){
			if(cm.cacheExists(name)){
				Cache c=cm.getCache(name);
				c.removeAll();
				c.clearStatistics();
			}
		}
	}

	@Override
	public void clearAll() {
		CacheManager cm=ehCachefacade.getCacheManager();
		cm.clearAll();
	}

	public EhCacheFacade getEhCachefacade() {
		return ehCachefacade;
	}

	public void setEhCachefacade(EhCacheFacade facade) {
		this.ehCachefacade = facade;
	}

}
