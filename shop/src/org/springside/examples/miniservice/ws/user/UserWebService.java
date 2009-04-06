package org.springside.examples.miniservice.ws.user;

import javax.jws.WebParam;
import javax.jws.WebService;

import org.springside.examples.miniservice.entity.user.User;
import org.springside.examples.miniservice.ws.Constants;
import org.springside.examples.miniservice.ws.WSResult;
import org.springside.examples.miniservice.ws.user.dto.CreateUserResult;
import org.springside.examples.miniservice.ws.user.dto.GetAllUserResult;
import org.springside.examples.miniservice.ws.user.dto.UserDTO;
import org.codehaus.jra.HttpResource;

/**
 * JAX-WS2.0的WebService接口定义类.
 * 
 * 使用JAX-WS2.0 annotation设置WSDL中的定义.
 * 使用WSResult及其子类类包裹返回结果.
 * 使用DTO传输对象隔绝系统内部领域对象的修改对外系统的影响.
 * 
 * @author sky
 * @author calvin
 */
@WebService(name = "UserService", targetNamespace = Constants.NS)
public interface UserWebService {
	/**
	 * 显示所有用户.
	 */
	GetAllUserResult getAllUser();
	
	GetAllUserResult getAllUser1(@WebParam(name = "orgId") int orgId);
	@HttpResource(location="/userservice/{userId}") 
	User getById(@WebParam(name = "userId") int userId);

	/**
	 * 新建用户.
	 */
	CreateUserResult createUser(@WebParam(name = "user") UserDTO user);

	/**
	 * 验证用户名密码.
	 */
	WSResult authUser(@WebParam(name = "loginName") String loginName, @WebParam(name = "password") String password);
}
