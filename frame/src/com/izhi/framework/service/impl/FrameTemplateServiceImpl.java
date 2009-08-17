package com.izhi.framework.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springmodules.cache.annotations.CacheFlush;
import org.springmodules.cache.annotations.Cacheable;

import com.izhi.framework.dao.IFrameTemplateDao;
import com.izhi.framework.model.FrameTemplate;
import com.izhi.framework.service.IFrameTemplateService;
import com.izhi.platform.util.PageParameter;
import com.izhi.platform.util.WebUtils;
@Service("frameTemplateService")
public class FrameTemplateServiceImpl implements IFrameTemplateService {

	private final static Logger log=LoggerFactory.getLogger(FrameTemplateServiceImpl.class);
	@Resource(name="frameTemplateDao")
	private IFrameTemplateDao frameTemplateDao;
	@Resource(name="frameTemplatePath")
	private String templatePath;
	@Override
	@CacheFlush(modelId="frameTemplateFlushing")
	public boolean deleteTemplate(int id) {
		return frameTemplateDao.deleteTemplate(id);
	}

	@Override
	@CacheFlush(modelId="frameTemplateFlushing")
	public boolean deleteTemplates(List<Integer> ids) {
		return frameTemplateDao.deleteTemplates(ids);
	}

	@Override
	@Cacheable(modelId="frameTemplateCaching")
	public FrameTemplate findTemplateById(int id) {
		FrameTemplate obj=frameTemplateDao.findTemplateById(id);
		String file=this.getFilePath(obj);
		obj.setContent(this.loadFile(file));
		return obj;
	}

	@Override
	@Cacheable(modelId="frameTemplateCaching")
	public List<Map<String,Object>> findPage(PageParameter pp) {
		return frameTemplateDao.findPage(pp);
	}

	@Override
	@Cacheable(modelId="frameTemplateCaching")
	public int findTotalCount() {
		return frameTemplateDao.findTotalCount();
	}

	@Override
	@CacheFlush(modelId="frameTemplateFlushing")
	public int saveTemplate(FrameTemplate obj) {
		if(this.isTemplateNameExist(obj)){
			return -1;
		}
		String file=this.getFilePath(obj);
		this.saveFile(file, obj.getContent());
		return frameTemplateDao.saveTemplate(obj);
	}

	@Override
	@CacheFlush(modelId="frameTemplateFlushing")
	public boolean updateTemplate(FrameTemplate obj) {
		String file=this.getFilePath(obj);
		
		this.saveFile(file, obj.getContent());
		return frameTemplateDao.updateTemplate(obj);
	}

	@Override
	@Cacheable(modelId="frameTemplateCaching")
	public FrameTemplate findTemplateByName(String name) {
		return frameTemplateDao.findTemplateByName(name);
	}

	public IFrameTemplateDao getFrameTemplateDao() {
		return frameTemplateDao;
	}

	public void setFrameTemplateDao(IFrameTemplateDao frameTemplateDao) {
		this.frameTemplateDao = frameTemplateDao;
	}

	@Override
	public List<Map<String,Object>> findJsonById(int id) {
		List<Map<String,Object>> list=frameTemplateDao.findJsonById(id);
		Map<String,Object> m=list.get(0);
		String fn=(String)m.get("fileName");
		String file=this.getFilePath(fn);
		m.put("content",this.loadFile(file));
		return list;
	}
	private String getFilePath(FrameTemplate obj){
		String fn=obj.getFileName();
		String file=WebUtils.getFrameTemplateRoot()+fn+".ftl";
		return file;
	}
	private String getFilePath(String fileName){
		String file=WebUtils.getFrameTemplateRoot()+fileName+".ftl";
		return file;
	}
	@Override
	public String loadFile(FrameTemplate obj) {
		String path=obj.getFileName();
		String content="";
		try {
			File file = new File(path);
			if(!file.exists()){
				file.createNewFile();
			}
			content=IOUtils.toString(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return content;
	}
	@Override
	public String loadFile(String path) {
		String content="";
		try {
			File file = new File(path);
			if(!file.exists()){
				file.createNewFile();
			}
			content=IOUtils.toString(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return content;
	}

	@Override
	public boolean saveFile(String path,String content) {
		boolean result=false;
		File file = new File(path);
		
		try {
			if(!file.exists()){
				file.createNewFile();
			}
			OutputStream  os = new FileOutputStream(file);
			IOUtils.write(content, os);
			result=true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public String getTemplatePath() {
		return templatePath;
	}

	public void setTemplatePath(String templatePath) {
		this.templatePath = templatePath;
	}

	@Override
	public boolean isTemplateNameExist(FrameTemplate obj) {
		FrameTemplate ft=frameTemplateDao.findTemplateByName(obj.getName());
		if(ft!=null){
			return true;
		}
		return false;
	}
}
