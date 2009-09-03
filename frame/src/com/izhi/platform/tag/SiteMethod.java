package com.izhi.platform.tag;

import java.util.List;

import org.springframework.stereotype.Service;

import com.izhi.platform.model.Site;
import com.izhi.platform.service.ISiteService;
import com.izhi.platform.util.SpringUtils;

import freemarker.template.TemplateModelException;
@Service
public class SiteMethod extends BaseMethod{

	@Override
	public String getName() {
		return "site";
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object  exec(List params) throws TemplateModelException {
		String attr=null;
		String result="";
		if(params.size()==1){
			attr=params.get(0).toString();
		}
		if(attr!=null){
			ISiteService service=(ISiteService)SpringUtils.getBean("siteService");
			Site site=service.getSite();
			if(attr.equals("name")){
				result= site.getSiteName(); 
			}else if(attr.equals("style")){
				result= site.getSiteStyle();
			}
		}
		return result;
	}

}
