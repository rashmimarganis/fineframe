package com.izhi.cms.service.impl;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.izhi.cms.dao.ICmsTemplateSuitDao;
import com.izhi.cms.model.CmsTemplateSuit;
import com.izhi.cms.service.ICmsTemplateSuitService;
import com.izhi.platform.util.PageParameter;
import com.izhi.platform.util.WebUtils;
@Service("cmsTemplateSuitService")
public class CmsTemplateSuitServiceImpl implements ICmsTemplateSuitService{

	@Resource(name="cmsTemplateSuitDao")
	private ICmsTemplateSuitDao cmsTemplateSuitDao;
	
	@Resource(name = "cmsTemplatePath")
	private String templatePath;
	@Override
	public boolean deleteSuit(int id) {
		return cmsTemplateSuitDao.deleteSuit(id);
	}

	@Override
	public boolean deleteSuits(List<Integer> ids) {
		return cmsTemplateSuitDao.deleteSuits(ids);
	}

	@Override
	public List<Map<String, Object>> findJsonById(int id) {
		return cmsTemplateSuitDao.findJsonById(id);
	}

	@Override
	public CmsTemplateSuit findSuitById(int id) {
		return cmsTemplateSuitDao.findSuitById(id);
	}

	@Override
	public List<Map<String, Object>>  findPage(PageParameter pp) {
		return cmsTemplateSuitDao.findPage(pp);
	}

	@Override
	public int findTotalCount() {
		return cmsTemplateSuitDao.findTotalCount();
	}

	@Override
	public int saveSuit(CmsTemplateSuit obj) {
		if(obj!=null){
			if(obj.getSuitId()==0){
				if(cmsTemplateSuitDao.findPackageExist(obj.getPackageName())){
					return -1;
				}
				String fn=getFilePath(obj.getPackageName());
				File file=new File(fn);
				if(!file.exists()){
					file.mkdirs();
				}
				return cmsTemplateSuitDao.saveSuit(obj);
			}else{
				if(cmsTemplateSuitDao.findPackageExist(obj.getPackageName(),obj.getOldPackageName())){
					return -1;
				}
				String fn=getFilePath(obj.getPackageName());
				String oldFn=getFilePath(obj.getOldPackageName());
				File file=new File(fn);
				File oldFile=new File(oldFn);
				if(!oldFile.exists()){
					file.mkdirs();
				}else{
					oldFile.renameTo(file);
				}
				return cmsTemplateSuitDao.updateSuit(obj);
			}
		}
		return 0;
	}

	@Override
	public int updateSuit(CmsTemplateSuit obj) {
		return cmsTemplateSuitDao.updateSuit(obj);
	}

	public ICmsTemplateSuitDao getCmsTemplateSuitDao() {
		return cmsTemplateSuitDao;
	}

	public void setCmsTemplateSuitDao(ICmsTemplateSuitDao cmsTemplateSuitDao) {
		this.cmsTemplateSuitDao = cmsTemplateSuitDao;
	}

	@Override
	public List<Map<String, Object>> findAll() {
		return cmsTemplateSuitDao.findAll();
	}

	@Override
	public boolean findPackageExist(String name) {
		return cmsTemplateSuitDao.findPackageExist(name);
	}

	@Override
	public boolean findPackageExist(String name, String oldName) {
		return cmsTemplateSuitDao.findPackageExist(name,oldName);
	}

	private String getFilePath(String pn) {
		String file = WebUtils.getWebRoot() + templatePath +pn;
		return file;
	}

	public String getTemplatePath() {
		return templatePath;
	}

	public void setTemplatePath(String templatePath) {
		this.templatePath = templatePath;
	}
	
	
}
