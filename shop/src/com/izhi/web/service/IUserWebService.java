package com.izhi.web.service;

import javax.jws.WebService;

import com.izhi.platform.model.User;
import com.izhi.web.model.WebUser;
@WebService(name = "UserService")
public interface IUserWebService {
	WebUser findUserByName(String username);
	boolean saveUser(User user);
	boolean findExist(String username);
	WebUser register(WebUser u);
	String validateUser(String un,String code);
	
}
