package com.izhi.platform.service.impl;


import java.beans.PropertyEditor;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.constructs.blocking.BlockingCache;
import net.sf.ehcache.constructs.blocking.SelfPopulatingCache;
import net.sf.ehcache.constructs.blocking.UpdatingCacheEntryFactory;
import net.sf.ehcache.constructs.blocking.UpdatingSelfPopulatingCache;

import org.springframework.beans.propertyeditors.StringArrayPropertyEditor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springmodules.cache.CacheException;
import org.springmodules.cache.CachingModel;
import org.springmodules.cache.FatalCacheException;
import org.springmodules.cache.FlushingModel;
import org.springmodules.cache.provider.AbstractCacheProviderFacade;
import org.springmodules.cache.provider.CacheAccessException;
import org.springmodules.cache.provider.CacheModelValidator;
import org.springmodules.cache.provider.CacheNotFoundException;
import org.springmodules.cache.provider.ReflectionCacheModelEditor;
import org.springmodules.cache.provider.ehcache.EhCacheCachingModel;
import org.springmodules.cache.provider.ehcache.EhCacheFlushingModel;
import org.springmodules.cache.provider.ehcache.EhCacheModelValidator;

@SuppressWarnings("unchecked")
@Service("ehCacheFacade")
public final class EhCacheFacade extends AbstractCacheProviderFacade {

	private Map caches = Collections.synchronizedMap(new HashMap());
	@Resource(name="cacheManager")
	private CacheManager cacheManager;

	private CacheModelValidator cacheModelValidator;

	public EhCacheFacade() {
		cacheModelValidator = new EhCacheModelValidator();
	}


	public CacheModelValidator modelValidator() {
		return cacheModelValidator;
	}


	public PropertyEditor getCachingModelEditor() {
		ReflectionCacheModelEditor editor = new ReflectionCacheModelEditor();
		editor.setCacheModelClass(EhCacheCachingModel.class);
		return editor;
	}


	public PropertyEditor getFlushingModelEditor() {
		Map propertyEditors = new HashMap();
		propertyEditors.put("cacheNames", new StringArrayPropertyEditor());

		ReflectionCacheModelEditor editor = new ReflectionCacheModelEditor();
		editor.setCacheModelClass(EhCacheFlushingModel.class);
		editor.setCacheModelPropertyEditors(propertyEditors);
		return editor;
	}


	public void setCacheManager(CacheManager newCacheManager) {
		cacheManager = newCacheManager;
	}


	protected Ehcache getCache(CachingModel model) throws CacheNotFoundException,
			CacheAccessException {
		EhCacheCachingModel ehCacheCachingModel = (EhCacheCachingModel) model;
		String cacheName = ehCacheCachingModel.getCacheName();

		Ehcache cache = (Cache) caches.get(cacheName);
		if (cache == null) {
			cache = decorateCache(getCache(cacheName), ehCacheCachingModel);
		}
		return cache;
	}


	protected Cache getCache(String name) throws CacheNotFoundException,
			CacheAccessException {
		Cache cache = null;

		try {
			if (cacheManager.cacheExists(name)) {
				cache = cacheManager.getCache(name);
			}
		} catch (Exception exception) {
			throw new CacheAccessException(exception);
		}

		if (cache == null) {
			throw new CacheNotFoundException(name);
		}

		return cache;
	}

	protected Ehcache decorateCache(Cache cache, EhCacheCachingModel model) {
		if (model.getCacheEntryFactory() != null) {
			if (model.getCacheEntryFactory() instanceof UpdatingCacheEntryFactory) {
				return new UpdatingSelfPopulatingCache(cache, (UpdatingCacheEntryFactory) model.getCacheEntryFactory());
			} else {
				return new SelfPopulatingCache(cache, model.getCacheEntryFactory());
			}
		}
		if (model.isBlocking()) {
			return new BlockingCache(cache);
		}
		return cache;
	}

	protected boolean isSerializableCacheElementRequired() {
		return true;
	}

	protected void onFlushCache(FlushingModel model) throws CacheException {
		EhCacheFlushingModel flushingModel = (EhCacheFlushingModel) model;
		String[] cacheNames = flushingModel.getCacheNames();

		if (!ObjectUtils.isEmpty(cacheNames)) {
			CacheException cacheException = null;
			int nameCount = cacheNames.length;

			try {
				for (int i = 0; i < nameCount; i++) {
					Cache cache = getCache(cacheNames[i]);
					cache.removeAll();
				}
			} catch (CacheException exception) {
				cacheException = exception;
			} catch (Exception exception) {
				cacheException = new CacheAccessException(exception);
			}

			if (cacheException != null) {
				throw cacheException;
			}
		}
	}

	protected Object onGetFromCache(Serializable key, CachingModel model)
			throws CacheException {
		Ehcache cache = getCache(model);
		Object cachedObject = null;

		try {
			Element cacheElement = cache.get(key);
			if (cacheElement != null) {
				cachedObject = cacheElement.getValue();
			}

		} catch (Exception exception) {
			throw new CacheAccessException(exception);
		}

		return cachedObject;
	}


	protected void onPutInCache(Serializable key, CachingModel model, Object obj)
			throws CacheException {
		Ehcache cache = getCache(model);
		Element newCacheElement = new Element(key, (Serializable) obj);

		try {
			cache.put(newCacheElement);

		} catch (Exception exception) {
			throw new CacheAccessException(exception);
		}
	}

	protected void onRemoveFromCache(Serializable key, CachingModel model)
			throws CacheException {
		Ehcache cache = getCache(model);

		try {
			cache.remove(key);

		} catch (Exception exception) {
			throw new CacheAccessException(exception);
		}
	}

	protected void validateCacheManager() throws FatalCacheException {
		assertCacheManagerIsNotNull(cacheManager);
	}

	public CacheManager getCacheManager() {
		return cacheManager;
	}

}