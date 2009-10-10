package com.izhi.cms.service.impl;

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

import com.izhi.cms.dao.ICmsTemplateDao;
import com.izhi.cms.model.CmsTemplate;
import com.izhi.cms.model.CmsTemplateSuit;
import com.izhi.cms.service.ICmsTemplateService;
import com.izhi.cms.service.ICmsTemplateSuitService;
import com.izhi.platform.util.PageParameter;
import com.izhi.platform.util.WebUtils;

@Service("cmsTemplateService")
public class CmsTemplateServiceImpl implements ICmsTemplateService {

	protected final Logger log = LoggerFactory.getLogger(this.getClass());
	@Resource(name = "cmsTemplateDao")
	private ICmsTemplateDao cmsTemplateDao;
	@Resource(name="cmsTemplateSuitService")
	private ICmsTemplateSuitService suitService;

	@Resource(name = "cmsTemplatePath")
	private String templatePath;

	@Override
	public boolean deleteTemplate(int id) {
		CmsTemplate obj = this.findTemplateById(id);
		String fileName = this.getFilePath(obj);
		boolean r = cmsTemplateDao.deleteTemplate(id);
		if (r) {
			File file = new File(fileName);
			if (file.exists() && file.isFile()) {
				boolean r1 = file.delete();
				log.debug("删除模板文件：" + fileName + " " + r1);
			}

		}
		return r;
	}

	@Override
	public boolean deleteTemplates(List<Integer> ids) {
		boolean r=true;
		for(Integer id:ids){
			r=r&&deleteTemplate(id);
		}
		return r;
	}

	@Override
	public List<Map<String, Object>> findJsonById(int id) {
		List<Map<String, Object>> list = cmsTemplateDao.findJsonById(id);
		Map<String, Object> m = list.get(0);
		CmsTemplate obj=cmsTemplateDao.findTemplateById(id);
		String file = this.getFilePath(obj);
		m.put("content", this.loadFile(file));
		return list;
	}

	@Override
	public CmsTemplate findTemplateById(int id) {
		return cmsTemplateDao.findTemplateById(id);
	}

	@Override
	public List<Map<String, Object>> findPage(PageParameter pp) {
		return cmsTemplateDao.findPage(pp);
	}

	@Override
	public int findTotalCount() {
		return cmsTemplateDao.findTotalCount();
	}

	@Override
	public int saveTemplate(CmsTemplate obj) {
		if (obj != null) {
			int id = 0;
			CmsTemplateSuit suit=this.getSuitService().findSuitById(obj.getSuit().getSuitId());
			obj.setSuit(suit);
			if (obj.getTemplateId() == 0) {
				String fileName = this.getFilePath(obj);
				File file = new File(fileName);
				if (file.exists()) {
					id = -1;
				} else {
					this.saveFile(file, obj.getContent());
					id = cmsTemplateDao.saveTemplate(obj);
				}
			} else {
				String fileName = this.getFilePath(obj);

				if (obj.getFileName().equals(obj.getOldFileName())) {
					File file = new File(fileName);
					this.saveFile(file, obj.getContent());
					id = cmsTemplateDao.updateTemplate(obj);
				} else {
					File file = new File(fileName);
					if (file.exists()) {
						id = -1;
					} else {
						String oldFileName = this.getFilePath(obj);
						File oldFile = new File(oldFileName);
						log.debug("重命名：" + oldFileName + " to " + fileName);
						oldFile.renameTo(file);
						this.saveFile(file, obj.getContent());
						id = cmsTemplateDao.updateTemplate(obj);
					}
				}
			}
			return id;
		}
		return 0;
	}

	@Override
	public int updateTemplate(CmsTemplate obj) {
		return cmsTemplateDao.updateTemplate(obj);
	}

	public ICmsTemplateDao getCmsTemplateDao() {
		return cmsTemplateDao;
	}

	public void setCmsTemplateDao(ICmsTemplateDao cmsTemplateDao) {
		this.cmsTemplateDao = cmsTemplateDao;
	}

	public String getTemplatePath() {
		return templatePath;
	}

	public void setTemplatePath(String templatePath) {
		this.templatePath = templatePath;
	}

	public String loadFile(String path) {
		String content = "";
		try {
			File file = new File(path);
			if (!file.exists()) {
				file.createNewFile();
			}
			content = IOUtils.toString(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return content;
	}

	public boolean saveFile(File file, String content) {
		boolean result = false;

		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			OutputStream os = new FileOutputStream(file);
			IOUtils.write(content, os);
			IOUtils.closeQuietly(os);
			result = true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;
	}

	private String getFilePath(CmsTemplate obj) {
		String file = WebUtils.getWebRoot() + templatePath +obj.getSuit().getPackageName()+File.separator+ obj.getFileName() + ".ftl";
		return file;
	}

	public ICmsTemplateSuitService getSuitService() {
		return suitService;
	}

	public void setSuitService(ICmsTemplateSuitService suitService) {
		this.suitService = suitService;
	}
	
	
}
