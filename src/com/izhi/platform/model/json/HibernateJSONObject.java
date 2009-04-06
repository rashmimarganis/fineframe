package com.izhi.platform.model.json;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

public class HibernateJSONObject {
	public final static String fromObject(Object obj){
		
		if(obj==null){
			return "{}";
		}
		JsonConfig conf = new JsonConfig();
		conf.setJsonPropertyFilter(new HibernatePropertyFilter() );
		return JSONObject.fromObject(obj, conf).toString();
	}
}

