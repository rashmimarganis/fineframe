package com.izhi.platform.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.izhi.platform.model.Function;
import com.izhi.platform.model.Shop;
import com.izhi.platform.model.User;
import com.izhi.platform.security.support.SecurityUser;
import com.izhi.platform.service.IFunctionService;
@Service
@Scope("prototype")
@Namespace("/")
public class LeftMenuAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	@Resource(name="functionService")
	private IFunctionService functionService;
	private int pid;
	private List<Function> nextFunctions;
	public IFunctionService getFunctionService() {
		return functionService;
	}
	public void setFunctionService(IFunctionService functionService) {
		this.functionService = functionService;
	}
	@Action(value="leftMenu",results={@Result(name="success",location="leftMenu.ftl")})
	public String execute(){
		if(SecurityUser.isOnline()){
			User user=SecurityUser.getUser();
			Shop org=SecurityUser.getShop(); 
			nextFunctions=functionService.findNextFunctions(org.getShopId(),user.getUserId(), pid);
		}else{
			nextFunctions=new ArrayList<Function>();
		}
		return SUCCESS;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public List<Function> getNextFunctions() {
		return nextFunctions;
	}
	
}
