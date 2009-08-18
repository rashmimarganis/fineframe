package com.izhi.framework.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.izhi.framework.model.FrameTemplate;
import com.izhi.framework.service.IFrameTemplateService;
import com.izhi.platform.action.BasePageAction;
import com.izhi.platform.util.PageParameter;
@Service
@Scope(value="prototype")
@Namespace("/frame/template")
public class FrameTemplateAction extends BasePageAction{

	private static final long serialVersionUID = 8190220809475487574L;
	@Resource(name="frameTemplateService")
	private IFrameTemplateService templateService;
	private FrameTemplate obj;
	private List<Integer> ids;
	private boolean success;
	private int id;
	
	private String type;
	
	
	@Action("list")
	public String list(){
		PageParameter pp=this.getPageParameter();
		int totalCount=(int)templateService.findTotalCount();
		List<Map<String,Object>> l=templateService.findPage(pp);
		Map<String,Object> map =new HashMap<String, Object>();
		map.put("objs", l);
		map.put("totalCount",totalCount);
		String result=JSONObject.fromObject(map).toString();
		this.getRequest().setAttribute("result", result);
		return SUCCESS;
	}
	@Action("add")
	public String add(){
		obj=new FrameTemplate();
		return SUCCESS;
	}
	@Action("load")
	public String load(){
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("success", true);
		map.put("data", templateService.findJsonById(id));
		String result=JSONObject.fromObject(map).toString();
		this.getRequest().setAttribute("result", result);
		return SUCCESS;
	}
	
	@Action("delete")
	public String delete(){
		boolean i=templateService.deleteTemplate(id);
		success=i;
		return SUCCESS;
	}
	@Action("deletes")
	public String deletes(){
		success=templateService.deleteTemplates(ids);
		return SUCCESS;
	}
	
	@Action("save")
	public String save(){
		if(obj.getTemplateId()==0){
			int i=templateService.saveTemplate(obj);
			success= i>0;
		}else{
			success=templateService.updateTemplate(obj);
		}
		return SUCCESS;
	}
	@Action("listByType")
	public String listByType(){
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("objs", templateService.findJsonByType(type));
		map.put("totalCount", templateService.findTotalCountByType(type));
		this.getRequest().setAttribute("result", JSONObject.fromObject(map).toString());
		return SUCCESS;
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
	public IFrameTemplateService getTemplateService() {
		return templateService;
	}
	public void setTemplateService(IFrameTemplateService templateService) {
		this.templateService = templateService;
	}
	public FrameTemplate getObj() {
		return obj;
	}
	public void setObj(FrameTemplate obj) {
		this.obj = obj;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
}
