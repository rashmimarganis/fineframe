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

import com.izhi.framework.model.FrameControl;
import com.izhi.framework.service.IFrameControlService;
import com.izhi.platform.action.BasePageAction;
import com.izhi.platform.util.PageParameter;
@Service
@Scope(value="prototype")
@Namespace("/frame/control")
public class FrameControlAction extends BasePageAction{

	private static final long serialVersionUID = 8190220809475487574L;
	@Resource(name="frameControlService")
	private IFrameControlService controlService;
	private FrameControl obj;
	private List<Integer> ids;
	
	private int id;
	
	@Action("list")
	public String list(){
		PageParameter pp=this.getPageParameter();
		int totalCount=(int)controlService.findTotalCount();
		List<Map<String,Object>> l=controlService.findPage(pp);
		Map<String,Object> map =new HashMap<String, Object>();
		map.put("objs", l);
		map.put("totalCount",totalCount);
		String result=JSONObject.fromObject(map).toString();
		this.getRequest().setAttribute("result", result);
		return SUCCESS;
	}
	@Action("add")
	public String add(){
		obj=new FrameControl();
		return SUCCESS;
	}
	@Action("load")
	public String load(){
		
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("success", true);
		map.put("data", controlService.findJsonById(id));
		String result=JSONObject.fromObject(map).toString();
		this.getRequest().setAttribute("result", result);
		return SUCCESS;
	}
	
	@Action("delete")
	public String delete(){
		boolean i=controlService.deleteControl(id);
		this.getRequest().setAttribute("success", i);
		return SUCCESS;
	}
	@Action("deletes")
	public String deletes(){
		log.debug("Id size:"+ids.size());
		boolean i=controlService.deleteControls(ids);
		this.getRequest().setAttribute("success", i);
		return SUCCESS;
	}
	
	@Action("save")
	public String save(){
		
		if(obj.getControlId()==0){
			int i=controlService.saveControl(obj);
			this.getRequest().setAttribute("success", i>0);
		}else{
			boolean i=controlService.updateControl(obj);
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
	public IFrameControlService getControlService() {
		return controlService;
	}
	public void setControlService(IFrameControlService templateService) {
		this.controlService = templateService;
	}
	public FrameControl getObj() {
		return obj;
	}
	public void setObj(FrameControl obj) {
		this.obj = obj;
	}
	
	
}
