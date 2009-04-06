package org.springside.examples.miniservice.ws.user.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlType;

import org.springside.examples.miniservice.ws.Constants;
import org.springside.examples.miniservice.ws.WSResult;

/**
 * GetAllUser方法的返回结果类型.
 * 
 * @author calvin
 */
@XmlType(name = "GetAllUserResult", namespace = Constants.NS)
public class GetAllUserResult extends WSResult {

	private List<UserDTO> userList;

	public List<UserDTO> getUserList() {
		return userList;
	}

	public void setUserList(List<UserDTO> userList) {
		this.userList = userList;
	}
}
