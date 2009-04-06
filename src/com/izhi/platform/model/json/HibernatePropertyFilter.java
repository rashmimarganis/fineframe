package com.izhi.platform.model.json;

import net.sf.json.util.PropertyFilter;

public class HibernatePropertyFilter implements PropertyFilter {

	@Override
	public boolean apply(Object source, String name, Object value) {
		if(name==null||"hibernateLazyInitializer".equals(name)){
			return true;
		}
		return false;
	}

}
