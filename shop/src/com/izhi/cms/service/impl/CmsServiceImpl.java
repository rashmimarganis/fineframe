package com.izhi.cms.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Service;

import com.izhi.cms.model.TemplateTag;
import com.izhi.cms.service.CmsService;
@Service(value="cmsService")
public class CmsServiceImpl extends HibernateDaoSupport implements CmsService {

	/* (non-Javadoc)
	 * @see com.izhi.cms.service.impl.ICmsService#findData(com.izhi.cms.model.TemplateTag)
	 */
	public Map<String,Object> findData(TemplateTag obj){
		Map<String,Object> m=new HashMap<String, Object>();
		obj.getItemCount();
		String sql="from "+obj.getModel().getClassName();
		Query q=this.getSession().createQuery(sql);
		q.setMaxResults(obj.getItemCount());
		m.put("data", q.list());
		return m;
	}
}
