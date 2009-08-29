package com.izhi.platform.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.izhi.platform.model.Function;
import com.izhi.platform.model.Org;
import com.izhi.platform.model.User;
import com.izhi.platform.security.support.SecurityUser;
import com.izhi.platform.service.IFunctionService;
@Service
@Scope("prototype")
@Namespace("/")
public class MenuAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	@Resource(name="functionService")
	private IFunctionService functionService;
	private Integer pid;
	public Integer getPid() {
		return pid;
	}
	private List<Function> nextFunctions;
	private List<Map<String,Object>> menus=new ArrayList<Map<String,Object>>();
	public List<Map<String, Object>> getMenus() {
		return menus;
	}
	public void setMenus(List<Map<String, Object>> menus) {
		this.menus = menus;
	}
	private String jsonString;
	public String getJsonString() {
		return jsonString;
	}
	public void setJsonString(String jsonString) {
		this.jsonString = jsonString;
	}
	public IFunctionService getFunctionService() {
		return functionService;
	}
	public void setFunctionService(IFunctionService functionService) {
		this.functionService = functionService;
	}
	@Action(value="menu")
	public String execute(){
		if(SecurityUser.isOnline()){
			User user=SecurityUser.getUser();
			Org org=SecurityUser.getOrg(); 
			try{
				menus=functionService.findMenus(org.getOrgId(),user.getUserId(), pid);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		jsonString=JSONArray.fromObject(menus).toString();
		return SUCCESS;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	public List<Function> getNextFunctions() {
		return nextFunctions;
	}
	
}
