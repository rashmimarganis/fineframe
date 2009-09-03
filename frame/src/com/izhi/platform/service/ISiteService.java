package com.izhi.platform.service;

import com.izhi.platform.model.Site;

public interface ISiteService {
	boolean saveSite(Site s);
	Site getSite();
}
