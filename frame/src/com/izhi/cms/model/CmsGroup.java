package com.izhi.cms.model;

import java.io.Serializable;

public class CmsGroup implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6446325140053410086L;
	private int groupId;
	private String name;
	private int levelValue;
	
	private int defaultGold;
	
	private int defaultStore;

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLevelValue() {
		return levelValue;
	}

	public void setLevelValue(int levelValue) {
		this.levelValue = levelValue;
	}

	public int getDefaultGold() {
		return defaultGold;
	}

	public void setDefaultGold(int defaultGold) {
		this.defaultGold = defaultGold;
	}

	public int getDefaultStore() {
		return defaultStore;
	}

	public void setDefaultStore(int defaultStore) {
		this.defaultStore = defaultStore;
	}
	
	
}
