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

import com.izhi.framework.model.FrameComponent;
import com.izhi.framework.service.IFrameComponentService;
import com.izhi.platform.action.BasePageAction;
import com.izhi.platform.util.PageParameter;
@Service
@Scope(value="prototype")
@Namespace("/frame/component")
public class FrameComponentAction extends BasePageAction{

	private static final long serialVersionUID = 8190220809475487574L;
	@Resource(name="frameComponentService")
	private IFrameComponentService componentService;
	private FrameComponent obj;
	private List<Integer> ids;
	
	private int id;
	
	@Action("list")
	public String list(){
		PageParameter pp=this.getPageParameter();
		int totalCount=(int)componentService.findTotalCount();
		List<Map<String,Object>> l=componentService.findPage(pp);
		Map<String,Object> map =new HashMap<String, Object>();
		map.put("objs", l);
		map.put("totalCount",totalCount);
		String result=JSONObject.fromObject(map).toString();
		this.getRequest().setAttribute("result", result);
		return SUCCESS;
	}
	@Action("add")
	public String add(){
		obj=new FrameComponent();
		return SUCCESS;
	}
	@Action("load")
	public String load(){
		
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("success", true);
		map.put("data", componentService.findJsonById(id));
		String result=JSONObject.fromObject(map).toString();
		this.getRequest().setAttribute("result", result);
		return SUCCESS;
	}
	
	@Action("delete")
	public String delete(){
		boolean i=componentService.deleteComponent(id);
		this.getRequest().setAttribute("success", i);
		return SUCCESS;
	}
	@Action("deletes")
	public String deletes(){
		log.debug("Id size:"+ids.size());
		boolean i=componentService.deleteComponents(ids);
		this.getRequest().setAttribute("success", i);
		return SUCCESS;
	}
	
	@Action("save")
	public String save(){
		
		if(obj.getComponentId()==0){
			int i=componentService.saveComponent(obj);
			this.getRequest().setAttribute("success", i>0);
		}else{
			boolean i=componentService.updateComponent(obj);
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
	public IFrameComponentService getComponentService() {
		return componentService;
	}
	public void setComponentService(IFrameComponentService templateService) {
		this.componentService = templateService;
	}
	public FrameComponent getObj() {
		return obj;
	}
	public void setObj(FrameComponent obj) {
		this.obj = obj;
	}
	
	
}
