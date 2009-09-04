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
import org.springframework.stereotype.Service;

import com.izhi.cms.dao.ICmsTemplateDao;
import com.izhi.cms.model.CmsTemplate;
import com.izhi.cms.service.ICmsTemplateService;
import com.izhi.platform.util.PageParameter;
import com.izhi.platform.util.WebUtils;

@Service("cmsTemplateService")
public class CmsTemplateServiceImpl implements ICmsTemplateService {

	@Resource(name = "cmsTemplateDao")
	private ICmsTemplateDao cmsTemplateDao;

	@Resource(name = "cmsTemplatePath")
	private String templatePath;

	@Override
	public boolean deleteTemplate(int id) {
		return cmsTemplateDao.deleteTemplate(id);
	}

	@Override
	public boolean deleteTemplates(List<Integer> ids) {
		return cmsTemplateDao.deleteTemplates(ids);
	}

	@Override
	public List<Map<String, Object>> findJsonById(int id) {
		List<Map<String, Object>> list = cmsTemplateDao.findJsonById(id);
		Map<String, Object> m = list.get(0);
		String fn = (String) m.get("fileName");
		String file = this.getFilePath(fn);
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
			if (obj.getTemplateId() == 0) {
				String fileName = this.getFilePath(obj.getFileName());
				File file = new File(fileName);
				if (file.exists()) {
					id = -1;
				} else {
					this.saveFile(fileName, obj.getContent());
					id = cmsTemplateDao.saveTemplate(obj);
				}
			} else {
				String fileName = this.getFilePath(obj.getFileName());

				if (obj.getFileName().equals(obj.getOldFileName())) {
					this.saveFile(fileName, obj.getContent());
					id = cmsTemplateDao.updateTemplate(obj);
				} else {
					File file = new File(fileName);
					if (file.exists()) {
						id = -1;
					} else {
						this.saveFile(fileName, obj.getContent());
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

	public boolean saveFile(String path, String content) {
		boolean result = false;
		File file = new File(path);

		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			OutputStream os = new FileOutputStream(file);
			IOUtils.write(content, os);
			os.flush();
			os.close();
			result = true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;
	}

	private String getFilePath(String fileName) {
		String file = WebUtils.getWebRoot() + templatePath + fileName + ".ftl";
		return file;
	}
}
