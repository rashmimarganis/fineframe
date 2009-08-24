package com.izhi.framework.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
@Entity
@Table(name="frame_project")
public class FrameProject implements Serializable{

	private static final long serialVersionUID = 2966389089541539271L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="project_id")
	private int projectId;
	@Column(name="name")
	private String name;
	
	@Column(name="title")
	private String title;
	@Column(name="base_path")
	private String basePath;
	@Column(name="package_name")
	private String packageName;
	@Column(name="encode")
	private String encode;
	@Column(name="source_path")
	private String sourcePath;
	@Column(name="web_path")
	private String webPath;
	@Column(name="js_path")
	private String javascriptPath;
	
	@Column(name="database_type")
	private String databaseType;
	@Column(name="driver_class")
	private String driverClass;
	@Column(name="database_url")
	private String databaseUrl;
	@Column(name="database_user")
	private String databaseUser;
	@Column(name="database_password")
	private String databasePassword;
	@Column(name="database_name")
	private String databaseName;
	@Column(name="table_prefix")
	private String tablePrefix;
	
	
	@OneToMany
	@JoinColumn(name="project_id",updatable=false,insertable=false)
	private List<FrameModel> models;
	
	public int getProjectId() {
		return projectId;
	}
	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBasePath() {
		return basePath;
	}
	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public String getEncode() {
		return encode;
	}
	public void setEncode(String encode) {
		this.encode = encode;
	}
	public String getSourcePath() {
		return sourcePath;
	}
	public void setSourcePath(String sourcePath) {
		this.sourcePath = sourcePath;
	}
	public String getWebPath() {
		return webPath;
	}
	public void setWebPath(String webPath) {
		this.webPath = webPath;
	}
	public String getDatabaseType() {
		return databaseType;
	}
	public void setDatabaseType(String databaseType) {
		this.databaseType = databaseType;
	}
	public String getDriverClass() {
		return driverClass;
	}
	public void setDriverClass(String driverClass) {
		this.driverClass = driverClass;
	}
	public String getDatabaseUrl() {
		return databaseUrl;
	}
	public void setDatabaseUrl(String databaseUrl) {
		this.databaseUrl = databaseUrl;
	}
	public String getDatabaseUser() {
		return databaseUser;
	}
	public void setDatabaseUser(String databaseUser) {
		this.databaseUser = databaseUser;
	}
	public String getDatabasePassword() {
		return databasePassword;
	}
	public void setDatabasePassword(String databasePassword) {
		this.databasePassword = databasePassword;
	}
	public String getDatabaseName() {
		return databaseName;
	}
	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}
	public String getTablePrefix() {
		return tablePrefix;
	}
	public void setTablePrefix(String tablePrefix) {
		this.tablePrefix = tablePrefix;
	}
	public List<FrameModel> getModels() {
		return models;
	}
	public void setModels(List<FrameModel> models) {
		this.models = models;
	}
	public String getJavascriptPath() {
		return javascriptPath;
	}
	public void setJavascriptPath(String javascriptPath) {
		this.javascriptPath = javascriptPath;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	
	
}
