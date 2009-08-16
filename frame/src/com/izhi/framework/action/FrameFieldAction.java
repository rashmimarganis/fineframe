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

import com.izhi.framework.model.FrameField;
import com.izhi.framework.service.IFrameFieldService;
import com.izhi.platform.action.BasePageAction;
import com.izhi.platform.util.PageParameter;
@Service
@Scope(value="prototype")
@Namespace("/frame/field")
public class FrameFieldAction extends BasePageAction{

	private static final long serialVersionUID = 8190220809475487574L;
	@Resource(name="frameFieldService")
	private IFrameFieldService fieldService;
	private FrameField obj;
	private List<Integer> ids;
	
	private int id;
	
	
	@Action("list")
	public String list(){
		PageParameter pp=this.getPageParameter();
		int totalCount=(int)fieldService.findTotalCount();
		List<FrameField> l=fieldService.findPage(pp);
		Map<String,Object> map =new HashMap<String, Object>();
		map.put("objs", l);
		map.put("totalCount",totalCount);
		String result=JSONObject.fromObject(map).toString();
		this.getRequest().setAttribute("result", result);
		return SUCCESS;
	}
	@Action("add")
	public String add(){
		obj=new FrameField();
		return SUCCESS;
	}
	@Action("load")
	public String load(){
		obj=fieldService.findFieldById(id);
		return SUCCESS;
	}
	
	@Action("delete")
	public String delete(){
		boolean i=fieldService.deleteField(id);
		this.getRequest().setAttribute("success", i);
		return SUCCESS;
	}
	@Action("deletes")
	public String deletes(){
		log.debug("Id size:"+ids.size());
		boolean i=fieldService.deleteFields(ids);
		this.getRequest().setAttribute("success", i);
		return SUCCESS;
	}
	
	@Action("save")
	public String save(){
		
		if(obj.getFieldId()==0){
			int i=fieldService.saveField(obj);
			this.getRequest().setAttribute("success", i>0);
		}else{
			boolean i=fieldService.updateField(obj);
			this.getRequest().setAttribute("success", i);
		}
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
	public IFrameFieldService getFieldService() {
		return fieldService;
	}
	public void setFieldService(IFrameFieldService templateService) {
		this.fieldService = templateService;
	}
	public FrameField getObj() {
		return obj;
	}
	public void setObj(FrameField obj) {
		this.obj = obj;
	}
	
	
}
