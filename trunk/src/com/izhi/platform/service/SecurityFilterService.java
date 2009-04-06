package com.izhi.platform.service;

import org.acegisecurity.ConfigAttributeDefinition;

import com.izhi.platform.model.Org;

public interface SecurityFilterService {
	ConfigAttributeDefinition lookupAttributes(String url);
	void saveLogOperation(String url,Org org);
}
