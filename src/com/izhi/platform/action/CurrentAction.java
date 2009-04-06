package com.izhi.platform.action;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import net.sf.json.JSONObject;

import com.izhi.platform.model.Org;
import com.izhi.platform.model.User;
import com.izhi.platform.security.support.SecurityUser;
@Service
@Scope(value="prototype")
public class CurrentAction extends BaseAction {
	private static final long serialVersionUID = 5992368099576062313L;

	public String execute() throws IOException{
		Map<String,String> map=new HashMap<String, String>();
		if(SecurityUser.isAnonymous()){
			map.put("online", "0");
		}else{
			User u=SecurityUser.getCurrentUser();
			Org o=SecurityUser.getCurrentOrg();
			map.put("username", u.getUsername());
			map.put("realname", u.getPerson().getRealname());
			map.put("orgTitle", o.getTitle());
			map.put("lastLogin", dateToString(u.getLastLoginTime()));
			map.put("loginTimes", ""+u.getLoginTimes());
			map.put("lastIp", u.getLastLoginIp());
			map.put("online", "1");
		}
		this.out(JSONObject.fromObject(map).toString());
		return null;
	}
	private String dateToString(Date date){
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy年MM月dd日");
		String r=sdf.format(date);
		if("1899-11-30".equals(r)){
			return "";
		}
		return r;
	}
}
