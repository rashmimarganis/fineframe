package com.izhi.shop.action;

import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.izhi.platform.action.BasePageAction;
import com.izhi.platform.util.PageParameter;
import com.izhi.shop.model.MemberLevel;
import com.izhi.shop.service.IMemberLevelService;
@Service
@Scope(value="prototype")
@Namespace("/memberlevel")
public class MemberLevelAction extends BasePageAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8190220809475487574L;
	@Resource(name="memberLevelService")
	private IMemberLevelService memberLevelService;
	private MemberLevel obj;
	private List<Integer> ids;
	
	private int id;
	
	
	@Action("list")
	public String list(){
		PageParameter pp=this.getPageParameter();
		int totalCount=(int)memberLevelService.findTotalCount();
		pp.setCurrentPage(p);
		pp.setTotalCount(totalCount);
		pp.setSort("memberLevelId");
		pp.setDir("desc");
		List<MemberLevel> l=memberLevelService.findPage(pp);
		this.getRequest().setAttribute("objs", l);
		this.getRequest().setAttribute("page", pp);
		return SUCCESS;
	}
	@Action("add")
	public String add(){
		obj=new MemberLevel();
		return SUCCESS;
	}
	@Action("load")
	public String load(){
		obj=memberLevelService.findMemberLevelById(id);
		return SUCCESS;
	}
	
	@Action("delete")
	public String delete(){
		boolean i=memberLevelService.deleteMemberLevel(id);
		this.getRequest().setAttribute("success", i);
		return SUCCESS;
	}
	@Action("default")
	public String setDefault(){
		boolean i=memberLevelService.setDefaultLevel(id);
		this.getRequest().setAttribute("success", i);
		return SUCCESS;
	}
	@Action("deletes")
	public String deletes(){
		boolean i=memberLevelService.deleteMemberLevels(ids);
		this.getRequest().setAttribute("success", i);
		return SUCCESS;
	}
	
	@Action("save")
	public String save(){
		if(obj.getMemberLevelId()==0){
			int i=memberLevelService.saveMemberLevel(obj);
			this.getRequest().setAttribute("success", i>0);
		}else{
			boolean i=memberLevelService.updateMemberLevel(obj);
			this.getRequest().setAttribute("success", i);
		}
		return SUCCESS;
	}
	
	public IMemberLevelService getMemberLevelService() {
		return memberLevelService;
	}
	public void setMemberLevelService(IMemberLevelService memberLevelService) {
		this.memberLevelService = memberLevelService;
	}
	public List<Integer> getIds() {
		return ids;
	}
	public void setIds(List<Integer> ids) {
		this.ids = ids;
	}
	public MemberLevel getObj() {
		return obj;
	}
	public void setObj(MemberLevel obj) {
		this.obj = obj;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}
