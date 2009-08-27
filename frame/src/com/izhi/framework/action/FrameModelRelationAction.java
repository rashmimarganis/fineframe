package com.izhi.framework.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.stereotype.Service;

import com.izhi.framework.model.FrameModelRelation;
import com.izhi.framework.service.IFrameModelRelationService;
import com.izhi.platform.action.BasePageAction;
import com.izhi.platform.util.PageParameter;

@Service
@Namespace("/frame/modelrelation")
public class FrameModelRelationAction extends BasePageAction {
	private static final long serialVersionUID = 6293157009040379372L;
	@Resource(name = "frameModelRelationService")
	private IFrameModelRelationService modelRelationService;
	private FrameModelRelation obj;

	private int id;
	private int mid;

	@Action("list")
	public String list() {
		Map<String, Object> map = new HashMap<String, Object>();
		int totalCount = 0;
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		PageParameter pp = this.getPageParameter();
		if (pp.getSort() == null) {
			pp.setSort("relationId");
		}
		if (pp.getDir() == null) {
			pp.setDir("asc");
		}
		totalCount = modelRelationService.findTotalCount();
		list = modelRelationService.findPage(this.getPageParameter());
		map.put("totalCount", totalCount);
		map.put("objs", list);
		this.getRequest().setAttribute("result",
				JSONObject.fromObject(map).toString());
		return SUCCESS;
	}

	@Action("save")
	public String save() {
		if(obj!=null){
			if(obj.getRelationId()==0){
				int i=modelRelationService.saveRelation(obj);
				this.getRequest().setAttribute("success", i>0);
			}else{
				boolean i=modelRelationService.updateRelation(obj);
				this.getRequest().setAttribute("success", i);
			}
		}else{
			this.getRequest().setAttribute("success", false);
		}
		
		return SUCCESS;
	}

	@Action("add")
	public String add() {
		obj = new FrameModelRelation();
		return SUCCESS;
	}

	@Action("deletes")
	public String deletes() {
		boolean i = false;
		if (id != 0) {
			i = modelRelationService.deleteRelation(id);
		}
		this.getRequest().setAttribute("success", i);
		return SUCCESS;
	}

	@Action("load")
	public String load() {
		Map<String, Object> map = new HashMap<String, Object>();
		if (id != 0) {
			map.put("success", true);
			map.put("data", modelRelationService.findJsonById(id));
		}
		String result = JSONObject.fromObject(map).toString();
		this.getRequest().setAttribute("result", result);
		return SUCCESS;
	}

	@Action("else")
	public String elseModel(){
		Map<String, Object> map = new HashMap<String, Object>();
		int totalCount = modelRelationService.findNoRelationTotalCount(mid);
		List<Map<String, Object>> list = modelRelationService.findNoRelation(mid);
		map.put("totalCount", totalCount);
		map.put("objs", list);
		this.getRequest().setAttribute("result", JSONObject.fromObject(map).toString());
		return SUCCESS;
	}
	@Action("listByModel")
	public String loadByModel() {
		Map<String, Object> map = new HashMap<String, Object>();
		int totalCount = 0;
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		if (mid != 0) {
			PageParameter pp = this.getPageParameter();
			if (pp.getSort() == null) {
				pp.setSort("relationId");
			}
			if (pp.getDir() == null) {
				pp.setDir("asc");
			}
			totalCount = modelRelationService.findTotalCount(mid);
			list = modelRelationService.findPageByModel(mid,pp);
		}
		map.put("totalCount", totalCount);
		map.put("objs", list);
		this.getRequest().setAttribute("result",
				JSONObject.fromObject(map).toString());
		return SUCCESS;
	}

	public IFrameModelRelationService getModelRelationService() {
		return modelRelationService;
	}

	public void setModelRelationService(
			IFrameModelRelationService modelRelationService) {
		this.modelRelationService = modelRelationService;
	}

	public FrameModelRelation getObj() {
		return obj;
	}

	public void setObj(FrameModelRelation obj) {
		this.obj = obj;
	}

	public int getMid() {
		return mid;
	}

	public void setMid(int mid) {
		this.mid = mid;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
