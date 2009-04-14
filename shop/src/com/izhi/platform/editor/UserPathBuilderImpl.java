package com.izhi.platform.editor;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.izhi.platform.security.support.SecurityUser;

import net.fckeditor.requestcycle.UserPathBuilder;

public class UserPathBuilderImpl implements UserPathBuilder {

	@Override
	public String getUserFilesPath(HttpServletRequest arg0) {
		String path="";
		if(SecurityUser.isOnline()){
			String username=SecurityUser.getUsername();
			path+=username+"/";
		}
		Date d=new Date();
		SimpleDateFormat sdf =new SimpleDateFormat("yyyy/MM/dd/");
		SimpleDateFormat sdf1 =new SimpleDateFormat("yyyyMMddhhmmss");
		String pname=path+sdf.format(d);
		File pf=new File(pname);
		if(!pf.exists()){
			pf.mkdirs();
		}
		String fname=sdf1.format(d);
		return pname+fname;
	}

}
