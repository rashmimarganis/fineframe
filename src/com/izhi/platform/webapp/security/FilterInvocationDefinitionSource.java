package com.izhi.platform.webapp.security;

import java.util.Iterator;

import org.acegisecurity.ConfigAttributeDefinition;
import org.acegisecurity.intercept.web.AbstractFilterInvocationDefinitionSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.izhi.platform.service.SecurityFilterService;
@SuppressWarnings("unused")
public class FilterInvocationDefinitionSource extends
		AbstractFilterInvocationDefinitionSource {
	
	private Logger log=LoggerFactory.getLogger(this.getClass()); 
	
	private SecurityFilterService service;


	@SuppressWarnings("unchecked")
	@Override
	public ConfigAttributeDefinition lookupAttributes(String url) {
		ConfigAttributeDefinition o=service.lookupAttributes(url);
		return o;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Iterator getConfigAttributeDefinitions() {
		return null;
	}

	public SecurityFilterService getService() {
		return service;
	}

	public void setService(SecurityFilterService service) {
		this.service = service;
	}


	
}
