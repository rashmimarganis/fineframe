package com.izhi.web.service.impl;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.acegisecurity.providers.dao.salt.SystemWideSaltSource;
import org.acegisecurity.providers.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springmodules.cache.annotations.CacheFlush;
import org.springmodules.cache.annotations.Cacheable;

import com.izhi.platform.dao.IUserDao;
import com.izhi.platform.model.Org;
import com.izhi.platform.model.User;
import com.izhi.web.model.WebUser;
import com.izhi.web.service.IUserWebService;

@Service("userWebService")
public class UserWebServiceImpl implements IUserWebService {

	private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };
	@Resource(name = "userDao")
	private IUserDao userDao;
	@Resource(name = "passwordEncoder")
	private Md5PasswordEncoder passwordEncoder;
	@Resource(name = "saltSource")
	private SystemWideSaltSource saltSource;

	@Override
	@Cacheable(modelId = "userCacheing")
	public WebUser findUserByName(String username) {
		return userDao.findUser(username);
	}

	@Override
	@CacheFlush(modelId = "userFlushing")
	public boolean saveUser(User user) {
		if (!this.findExist(user.getUsername())) {
			return userDao.save(user) > 0;
		}
		return false;
	}

	public IUserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	@Cacheable(modelId = "userCacheing")
	public boolean findExist(String username) {
		return userDao.findIsExist(username);
	}

	@Override
	@CacheFlush(modelId = "userFlushing")
	public WebUser register(WebUser u) {
		u.setPassword(passwordEncoder.encodePassword(u.getPassword(),
				saltSource));
		User user = new User();
		user.setUsername(u.getUsername());
		user.setEmail(u.getEmail());
		user.setAddress(u.getAddress());
		user.setAge(u.getAge());
		user.setPostcode(u.getPostcode());
		user.setPassword(u.getPassword());
		user.setHintAnswer(u.getHintAnswer());
		user.setHintQuestion(u.getHintQuestion());
		user.setExpired(false);
		user.setLocked(false);
		user.setConcurrentMax(10);
		user.setEnabled(true);
		Org org = new Org();
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		SimpleDateFormat sdf = new SimpleDateFormat();
		String date = sdf.format(new Date()) + "_lejiago";
		String validate=byteArrayToString(md.digest(date.getBytes()));
		u.setValidateCode(validate);
		user.setValidateCode(validate);
		user.setValidated(false);
		org.setOrgId(1);
		user.setOrg(org);
		Integer id = userDao.save(user);
		u.setUserId(id);
		return u;
	}

	public static String byteArrayToString(byte[] b) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			// resultSb.append(byteToHexString(b[i]));//若使用本函数转换则可得到加密结果的16进制表示，即数字字母混合的形式
			resultSb.append(byteToNumString(b[i]));// 使用本函数则返回加密结果的10进制数字字串，即全数字形式
		}
		return resultSb.toString();
	}

	private static String byteToNumString(byte b) {

		int _b = b;
		if (_b < 0) {
			_b = 256 + _b;
		}

		return String.valueOf(_b);
	}

	public static String byteToHexString(byte b) {
		int n = b;
		if (n < 0) {
			n = 256 + n;
		}
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	public Md5PasswordEncoder getPasswordEncoder() {
		return passwordEncoder;
	}

	public void setPasswordEncoder(Md5PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	public SystemWideSaltSource getSaltSource() {
		return saltSource;
	}

	public void setSaltSource(SystemWideSaltSource saltSource) {
		this.saltSource = saltSource;
	}

	@Override
	@CacheFlush(modelId = "userFlushing")
	public String validateUser(String un, String code) {
		Boolean validated=false;
		Boolean success=false;
		WebUser u=this.findUserByName(un);
		if(u.isValidated()){
			validated=true;
		}else{
			if(code.equals(u.getValidateCode())){
				success=userDao.validateUser(un, code);
			}
		}
		Map<String,Boolean> m=new HashMap<String, Boolean>();
		m.put("success", success);
		m.put("validated", validated);
		return JSONObject.fromObject(m).toString();
	}

}
