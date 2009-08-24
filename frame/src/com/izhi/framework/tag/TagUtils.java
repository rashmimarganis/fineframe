package com.izhi.framework.tag;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import com.izhi.framework.model.FrameComponent;
import com.izhi.framework.model.FrameModel;
import com.izhi.framework.model.FrameProject;

import freemarker.core.Environment;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;

public final class TagUtils {
	private static Map<String,TemplateDirectiveModel> tags=new HashMap<String, TemplateDirectiveModel>();

	public static void addTag(String name,TemplateDirectiveModel tag) {
		tags.put(name, tag);
	}

	public static Map<String,TemplateDirectiveModel> getTags() {
		return tags;
	}
	
	public static String getGenerateFileName(FrameProject fp,FrameComponent fc,FrameModel fm){
		String fn=null;
		Configuration config=new Configuration();
		config.setObjectWrapper(new DefaultObjectWrapper());
		String templateStr=fc.getFileName();
		try {
			Template fileNameTpl = new Template("fileName", new StringReader(templateStr),
			               config);
			Map<String,Object> m=new HashMap<String, Object>();
			m.put("p", fp);
			if(fc.getLevel().equals(FrameComponent.LEVEL_MODEL)){
				m.put("m", fm);
			}
			StringWriter out=new StringWriter();
			fileNameTpl.process(m, out);
			fn=out.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}
		return fn;
	}
	public static String getGenerateFileName(FrameProject fp,FrameComponent fc){
		String fn=null;
		Configuration config=new Configuration();
		config.setObjectWrapper(new DefaultObjectWrapper());
		String templateStr=fc.getFileName();
		try {
			Template fileNameTpl = new Template("fileName", new StringReader(templateStr),
			              config);
			Map<String,Object> m=new HashMap<String, Object>();
			m.put("p", fp);
			StringWriter out=new StringWriter();
			fileNameTpl.process(m, out);
			fn=out.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}
		return fn;
	}
	
	public static String getGeneratePackageName(FrameProject fp,FrameComponent fc,FrameModel fm){
		String bp=fp.getBasePath();
		String fpn="";
		if(!bp.endsWith(File.separator)){
			bp+=File.separator;
		}
		fpn=bp;
		if(fc.getFileType().equals(FrameComponent.TYPE_JAVA)){
			fpn+=fp.getSourcePath()+File.separator;
			String pckn=fp.getPackageName();
			pckn=pckn.replace(".",File.separator);
			fpn+=pckn+File.separator;
			String pkn=fc.getPackageName();
			pkn=pkn.replace(".", File.separator);
			fpn+=pkn+File.separator;
		}else{
			fpn+=fp.getWebPath();
			fpn+=fc.getPackageName()+File.separator;
		}
		
		
		
		File pack=new File(fpn);
		if(!pack.exists()){
			pack.mkdirs();
		}
		
		return fpn;
	}
	public static String getGeneratePackageName(FrameProject fp,FrameComponent fc){
		String bp=fp.getBasePath();
		String fpn="";
		if(!bp.endsWith(File.separator)){
			bp+=File.separator;
		}
		fpn=bp;
		if(fc.getFileType().equals(FrameComponent.TYPE_JAVA)){
			fpn+=fp.getSourcePath()+File.separator;
			String pckn=fp.getPackageName();
			pckn=pckn.replace(".", File.separator);
			fpn+=pckn;
			String pkn=fc.getPackageName();
			pkn=pkn.replace(".", File.separator)+File.separator;
			fpn+=pkn+File.separator;
			
		}else{
			fpn+=fp.getWebPath()+File.separator;
			fpn+=fc.getPackageName()+File.separator;
		}
		
		
		
		File pack=new File(fpn);
		if(!pack.exists()){
			pack.mkdirs();
		}
		
		return fpn;
	}
	public static void main(String[] args){
		String pckn="com.xxx.tttt.eee";
		pckn=pckn.replace(".",File.separator);
		System.out.println(pckn);
	}
}
