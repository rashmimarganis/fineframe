package com.izhi.platform.action;

import com.izhi.platform.model.PageParameter;

public class BasePageAction extends BaseAction {

	private static final long serialVersionUID = -1813480563281107682L;
	
	private PageParameter pp=null;
	
	public String init(){
		return "success";
	}
	
	
	public PageParameter getPageParameter() {
		if(pp==null){
			pp=new PageParameter();
			pp.setDir("asc");
			pp.setLimit(10);
			
		}
		return pp;
	}

	public void setPageParameter(PageParameter pp) {
		this.pp = pp;
	}
	public void setCurrentPage(int cp){
		this.getPageParameter().setCurrentPage(cp);
	}
	public PageParameter getPp() {
		return pp;
	}
	public void setPp(PageParameter pp) {
		this.pp = pp;
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

	public Integer getStart() {
		return pp.getStart();
	}


	public Integer getLimit() {
		return pp.getLimit();
	}

	public void setLimit(Integer limit) {
		pp.setLimit(limit);
	}

}
