package com.izhi.platform.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import net.sf.json.JSONObject;

import com.izhi.platform.service.IEhCacheService;
@Service
@Scope(value="prototype")
@Namespace("/cache")
public class EhCacheAction extends BasePageAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 427178363872171042L;
	private String[] names;
	@Resource(name="ehCacheService")
	private IEhCacheService service;
	@Action("list")
	public String list(){
		List<Map<String,Object>> l= service.findAll();
		this.getRequest().setAttribute("objs", l);
		this.getRequest().setAttribute("totalCount", l.size());
		return SUCCESS;
	}
	@Action("clear")
	public String clear(){
		service.clear(names);
		return SUCCESS;
	}
	@Action("clearAll")
	public String clearAll(){
		service.clearAll();
		return SUCCESS;
	}

	public String[] getNames() {
		return names;
	}

	public void setNames(String[] names) {
		this.names = names;
	}

	public IEhCacheService getService() {
		return service;
	}

	public void setService(IEhCacheService service) {
		this.service = service;
	}
}
