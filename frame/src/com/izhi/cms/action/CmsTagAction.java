package com.izhi.cms.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.izhi.cms.model.CmsTag;
import com.izhi.cms.service.ICmsTagService;
import com.izhi.platform.action.BasePageAction;
@Service
@Scope("prototype")
@Namespace("/cms/tag")
public class CmsTagAction extends BasePageAction{
	private static final long serialVersionUID = 3892345222936288848L;
	@Resource(name="cmsTagService")
	private ICmsTagService cmsTagService;
	
	private CmsTag obj;
	
	private List<Integer> ids;
	
	private int id;
	
	
	@Action("index")
	public String index(){
		return SUCCESS;
	}
	@Action("list")
	public String list(){
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("totalCount", cmsTagService.findTotalCount());
		map.put("objs", cmsTagService.findPage(this.getPageParameter()));
		String result=JSONObject.fromObject(map).toString();
		this.getRequest().setAttribute("result", result);
		return SUCCESS;
	}
	@Action("save")
	public String save(){
		if(obj!=null){
			int id=cmsTagService.saveTag(obj);
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("success", id>0);
			map.put("id", id);
			this.getRequest().setAttribute("result", JSONObject.fromObject(map).toString());
		}
		return SUCCESS;
	}
	
	@Action("load")
	public String load(){
		if(id!=0){
			List<Map<String,Object>> m=cmsTagService.findJsonById(id);
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("data", m);
			map.put("success", true);
			this.getRequest().setAttribute("result", JSONObject.fromObject(map).toString());
		}
		return SUCCESS;
	}
	
	@Action("delete")
	public String delete(){
		if(ids!=null){
			boolean success=cmsTagService.deleteTags(ids);
			int totalCount=cmsTagService.findTotalCount();
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("success", success);
			map.put("totalCount", totalCount);
			this.getRequest().setAttribute("result", JSONObject.fromObject(map).toString());
		}
		return SUCCESS;
	}
	
	public ICmsTagService getCmsTagService() {
		return cmsTagService;
	}
	public void setCmsTagService(ICmsTagService cmsTagService) {
		this.cmsTagService = cmsTagService;
	}
	public CmsTag getObj() {
		return obj;
	}
	public void setObj(CmsTag obj) {
		this.obj = obj;
	}
	public List<Integer> getIds() {
		return ids;
	}
	public void setIds(List<Integer> ids) {
		this.ids = ids;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
	

}
