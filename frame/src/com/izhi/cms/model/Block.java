package com.izhi.cms.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="cms_block")
public class Block implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1722547246177826402L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="block_id")
	private int blockId;
	@Column(name="name")
	private String name;
	@Column(name="content")
	private String content;
	public int getBlockId() {
		return blockId;
	}
	public void setBlockId(int blockId) {
		this.blockId = blockId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContent() {
		if(content==null){
			content="";
		}
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
}
