package com.izhi.platform.util;

import java.io.Serializable;

public class PageParameter implements Serializable{

	public static final String ORDER_ASC="asc";
	public static final String ORDER_DESC="desc";
	private static final long serialVersionUID = -5685164358480942334L;
	private int start=0;
	public void setStart(int start) {
		this.start = start;
	}
	private int limit=10;
	private String dir=ORDER_ASC;
	private String sort;
	private int totalCount=0;
	private int totalPage=0;
	private int currentPage=0;
	
	public int getStart() {
		return start;
	}

	public int getLimit() {
		if( limit == 0 ){
			return 100;
		}
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public String getDir() {
		return dir;
	}
	public void setDir(String dir) {
		this.dir = dir;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public int getTotalPage() {
		totalPage=(int)Math.ceil((double)totalCount/limit);
		return totalPage;
	}
	
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
}
