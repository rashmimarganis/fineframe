package com.izhi.cms.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="cms_site")
public class CmsSite implements Serializable{

	private static final long serialVersionUID = 3910368675314836573L;

	public static final String WATERMARK_LEFTTOP="lt";
	public static final String WATERMARK_RIGHTTOP="rt";
	public static final String WATERMARK_LEFTBOTTOM="lb";
	public static final String WATERMARK_RIGHTBOTTOM="rb";
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="site_id")
	private int siteId;
	
	private String name;
	
	private String title;
	
	@Column(name="site_url")
	private String siteUrl;
	
	@ManyToOne
	@JoinColumn(name="template_suit_id")
	private CmsTemplateSuit templateSuit;
	
	@Column(name="html_path")
	private String htmlPath;
	
	private boolean closed;

	@Column(name="close_reason")
	private String closeReason;
	
	@Column(name="watermark_pic")
	private String watermarkPic;
	
	@Column(name="watermark_position")
	private String watermarkPosition;
	@Column(name="is_watermark")
	private boolean watermark;
	public int getSiteId() {
		return siteId;
	}

	public void setSiteId(int siteId) {
		this.siteId = siteId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public CmsTemplateSuit getTemplateSuit() {
		return templateSuit;
	}

	public void setTemplateSuit(CmsTemplateSuit templateSuit) {
		this.templateSuit = templateSuit;
	}

	public String getHtmlPath() {
		return htmlPath;
	}

	public void setHtmlPath(String htmlPath) {
		this.htmlPath = htmlPath;
	}

	public boolean isClosed() {
		return closed;
	}

	public void setClosed(boolean closed) {
		this.closed = closed;
	}

	public String getCloseReason() {
		return closeReason;
	}

	public void setCloseReason(String closeReason) {
		this.closeReason = closeReason;
	}

	public String getWatermarkPic() {
		return watermarkPic;
	}

	public void setWatermarkPic(String watermarkPic) {
		this.watermarkPic = watermarkPic;
	}

	public String getWatermarkPosition() {
		return watermarkPosition;
	}

	public void setWatermarkPosition(String watermarkPosition) {
		this.watermarkPosition = watermarkPosition;
	}

	public String getSiteUrl() {
		return siteUrl;
	}

	public void setSiteUrl(String siteUrl) {
		this.siteUrl = siteUrl;
	}

	public boolean isWatermark() {
		return watermark;
	}

	public void setWatermark(boolean watermark) {
		this.watermark = watermark;
	}

	
	
}
