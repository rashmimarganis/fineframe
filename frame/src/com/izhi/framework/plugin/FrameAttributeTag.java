package com.izhi.framework.plugin;

import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.izhi.framework.service.IFrameControlService;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
@Service("frameAttibuteTag")
public class FrameAttributeTag implements TemplateDirectiveModel{

	@Resource(name="frameControlService")
	private IFrameControlService frameControlService;
	@SuppressWarnings("unchecked")
	@Override
	public void execute(Environment env, Map params, TemplateModel[] models,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		if(params.size()>=3){
		params.get("type");
		frameControlService.findControlByName("");
		}
		
	}
	public IFrameControlService getFrameControlService() {
		return frameControlService;
	}
	public void setFrameControlService(IFrameControlService frameControlService) {
		this.frameControlService = frameControlService;
	}
	
}
