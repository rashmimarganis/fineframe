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

import com.izhi.framework.model.FrameProject;
import com.izhi.framework.service.IFrameProjectService;
import com.izhi.platform.action.BasePageAction;
import com.izhi.platform.util.PageParameter;
@Service
@Scope(value="prototype")
@Namespace("/frame/project")
public class FrameProjectAction extends BasePageAction{

	private static final long serialVersionUID = 8190220809475487574L;
	@Resource(name="frameProjectService")
	private IFrameProjectService projectService;
	private FrameProject obj;
	private List<Integer> ids;
	
	private int id;
	
	
	@Action("list")
	public String list(){
		PageParameter pp=this.getPageParameter();
		int totalCount=(int)projectService.findTotalCount();
		List<Map<String,Object>> l=projectService.findPage(pp);
		Map<String,Object> map =new HashMap<String, Object>();
		map.put("objs", l);
		map.put("totalCount",totalCount);
		String result=JSONObject.fromObject(map).toString();
		this.getRequest().setAttribute("result", result);
		return SUCCESS;
	}
	@Action("add")
	public String add(){
		obj=new FrameProject();
		return SUCCESS;
	}
	@Action("load")
	public String load(){
		obj=projectService.findProjectById(id);
		return SUCCESS;
	}
	
	@Action("delete")
	public String delete(){
		boolean i=projectService.deleteProject(id);
		this.getRequest().setAttribute("success", i);
		return SUCCESS;
	}
	@Action("deletes")
	public String deletes(){
		log.debug("Id size:"+ids.size());
		boolean i=projectService.deleteProjects(ids);
		this.getRequest().setAttribute("success", i);
		return SUCCESS;
	}
	
	@Action("save")
	public String save(){
		
		if(obj.getProjectId()==0){
			int i=projectService.saveProject(obj);
			this.getRequest().setAttribute("success", i>0);
		}else{
			boolean i=projectService.updateProject(obj);
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
	public IFrameProjectService getProjectService() {
		return projectService;
	}
	public void setProjectService(IFrameProjectService templateService) {
		this.projectService = templateService;
	}
	public FrameProject getObj() {
		return obj;
	}
	public void setObj(FrameProject obj) {
		this.obj = obj;
	}
	
	
}
