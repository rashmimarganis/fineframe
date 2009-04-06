package com.izhi.platform.service;

import java.util.List;
import java.util.Map;

public interface IEhCacheService {
	public List<Map<String,Object>> findAll();
	public void clearAll();
	public void clear(String[] names);
}
