package com.izhi.platform.action;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
@Service
@Scope("prototype")
@Namespace("/upload")
public class UploadAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	private File docFile;
	public File getDocFile() {
		return docFile;
	}
	public void setDocFile(File docFile) {
		this.docFile = docFile;
	}
	@Action("doc")
	public String execute() throws IOException{
		FileUtils.copyFile(docFile, new File("D:\\eclipse3.3\\groovy\\lejia\\WebContent\\1124.doc"));
		return null;
	}
	
}
