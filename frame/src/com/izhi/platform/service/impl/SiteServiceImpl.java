package com.izhi.platform.service.impl;

import java.io.FileOutputStream;
import java.util.Properties;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.izhi.platform.model.Site;
import com.izhi.platform.service.ISiteService;

@Service("siteService")
public class SiteServiceImpl implements ISiteService {
	@Resource(name = "site")
	private Site site;

	@Override
	public Site getSite() {
		return site;
	}

	@Override
	public boolean saveSite(Site s) {
		this.site=s;
		try {
			Properties property = new Properties();
			property.setProperty("site.name", site.getSiteName());
			property.setProperty("site.style", site.getSiteStyle());
			property.store(new FileOutputStream("site.properties"),
					"site.properties");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public void setSite(Site site) {
		this.site = site;
	}

}
