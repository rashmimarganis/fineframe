package com.izhi.platform.action;

import org.apache.struts2.convention.annotation.Action;

import com.izhi.platform.util.PageParameter;

public class BasePageAction extends BaseAction {

	private static final long serialVersionUID = -1813480563281107682L;
	
	protected PageParameter pp;
	protected int p=1;
	public String init(){
		return "success";
	}
	
	public BasePageAction(){
		pp=new PageParameter();
	}
	public PageParameter getPageParameter() {
		return pp;
	}

	public void setPageParameter(PageParameter pp) {
		this.pp = pp;
	}
	public void setCurrentPage(int cp){
		pp.setCurrentPage(cp);
	}
	public PageParameter getPp() {
		return pp;
	}

	public String getSort() {
		return pp.getSort();
	}

	public void setSort(String sort) {
		pp.setSort(sort);
	}

	public String getDir() {
		return pp.getDir();
	}

	public void setDir(String dir) {
		pp.setDir(dir);
	}

	public int getStart() {
		return pp.getStart();
	}

	public void setStart(int start) {
		pp.setStart(start);
	}
	public int getLimit() {
		return pp.getLimit();
	}

	public void setLimit(int limit) {
		pp.setLimit(limit);
	}


	public int getP() {
		return p;
	}


	public void setP(int p) {
		this.p = p;
	}
	
	@Action("index")
	public String index(){
		return SUCCESS;
	}

}
