package com.izhi.cms.service;

import java.util.Map;

import com.izhi.cms.model.TemplateTag;

public interface ICmsService {

	public abstract Map<String, Object> findData(TemplateTag obj);

}