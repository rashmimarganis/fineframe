package com.izhi.platform.action;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.acegisecurity.providers.encoding.Md5PasswordEncoder;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.izhi.platform.model.User;
import com.izhi.platform.security.support.SecurityUser;
import com.izhi.platform.service.IUserService;
@Service
@Scope(value="prototype")
public class ChangePasswordAction extends BaseAction {
	@Resource(name="userService")
	private IUserService service;
	private Integer userId;
	private String oldPassword;
	private String password;
	private String password1;
	private Md5PasswordEncoder encoder=new Md5PasswordEncoder();
	/**
	 * 
	 */
	private static final long serialVersionUID = -3120874849956514687L;
	
	public String execute(){
		int result=0;
		if(oldPassword!=null){
			if(userId!=null){
				User user = service.findById(userId);
				oldPassword=encoder.encodePassword(oldPassword, null);
				if(!user.getPassword().equals(oldPassword)){
					result=-3;
				}else{
					if(password==null||password1==null){
						result=-1;
					}else if(!password.equals(password1)){
						result=-2;
					}else{
						String username=SecurityUser.getUsername();
						
						service.updatePassword(username,password);
						result=1;
					}
				}
			}else{
				result = -4;
			}
		}
			
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("success", result==1);
		map.put("result", result);
		this.out(JSONObject.fromObject(map).toString());
		return null;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword1() {
		return password1;
	}

	public void setPassword1(String password1) {
		this.password1 = password1;
	}

	public IUserService getService() {
		return service;
	}

	public void setService(IUserService service) {
		this.service = service;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public Md5PasswordEncoder getEncoder() {
		return encoder;
	}

	public void setEncoder(Md5PasswordEncoder encoder) {
		this.encoder = encoder;
	}
}
