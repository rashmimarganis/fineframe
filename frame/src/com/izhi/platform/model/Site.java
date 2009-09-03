package com.izhi.platform.model;

import java.io.Serializable;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
@Service("site")
public class Site implements Serializable{
	private static final long serialVersionUID = 1944781804671515567L;

	@Resource(name="siteName")
	private String siteName;
	@Resource(name="siteStyle")
	private String siteStyle;

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getSiteStyle() {
		return siteStyle;
	}

	public void setSiteStyle(String siteStyle) {
		this.siteStyle = siteStyle;
	}
	
	
}
