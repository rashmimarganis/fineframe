package com.izhi.framework.action;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.stereotype.Service;

import com.izhi.framework.model.FrameComponent;
import com.izhi.framework.model.FrameModel;
import com.izhi.framework.model.FrameProject;
import com.izhi.framework.service.IFrameComponentService;
import com.izhi.framework.service.IFrameModelService;
import com.izhi.framework.service.IFrameProjectService;
import com.izhi.framework.tag.TagUtils;
import com.izhi.platform.action.BaseAction;
import com.izhi.platform.util.WebUtils;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@Service
@Namespace("/frame/generate")
public class FrameGenerateAction extends BaseAction {

	private static final long serialVersionUID = -525194915380788157L;
	@Resource(name = "frameComponentService")
	private IFrameComponentService componentService;
	@Resource(name = "frameProjectService")
	private IFrameProjectService projectService;
	@Resource(name = "frameModelService")
	private IFrameModelService frameModelService;
	private int cid;

	private int pid;

	@Action("index")
	public String execute() {
		Map<String,String> result=new HashMap<String, String>();
		FrameProject fp = projectService.findProjectById(pid);
		
		if (cid != 0) {
			FrameComponent fc = componentService.findComponentById(cid);
			if (fc != null) {
				Configuration config=new Configuration();
				try {
					config.setDirectoryForTemplateLoading(new File(WebUtils.getFrameTemplateRoot()));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				config.setObjectWrapper(new DefaultObjectWrapper());
				Map<String, Object> data = new HashMap<String, Object>();
				data.put("p", fp);
				String ftpl =fc.getTemplate().getFileName()+".ftl";

				if (fc.getLevel().equals(FrameComponent.LEVEL_PROJECT)) {
					String fileName = TagUtils.getGeneratePackageName(fp, fc)
							+ TagUtils.getGenerateFileName(fp, fc);
					log.debug("生成文件名称："+fileName);
					File file = new File(fileName);
					try {
						if (!file.exists()) {

							file.createNewFile();

						}

					} catch (IOException e) {
						e.printStackTrace();
					}
					try {
						Writer out = new BufferedWriter(new OutputStreamWriter(
								new FileOutputStream(file), "UTF-8"));
						Template tpl = config.getTemplate(ftpl);
						tpl.process(data, out);
						out.flush();
						result.put("component_"+fc.getComponentId(), fc.getName()+"生成成功！");
					} catch (IOException e) {
						result.put("component_"+fc.getComponentId(), fc.getName()+"生成失败！");
						e.printStackTrace();
					} catch (TemplateException e) {
						result.put("component_"+fc.getComponentId(), fc.getName()+"生成失败！");
						e.printStackTrace();
					}

				} else {
					List<FrameModel> models = frameModelService
							.findModelByProject(pid);
					
					for (FrameModel fm : models) {
						data.put("m", fm);
						String fileName = TagUtils.getGeneratePackageName(fp, fc,fm)
						+ TagUtils.getGenerateFileName(fp, fc,fm);
						log.debug("生成文件名称："+fileName);
						File file = new File(fileName);
						try {
							if (!file.exists()) {
		
								file.createNewFile();
		
							}
							
						} catch (IOException e) {
							e.printStackTrace();
						}
						try {
							Writer out = new BufferedWriter(
									new OutputStreamWriter(
											new FileOutputStream(file), "UTF-8"));
							Template tpl = config.getTemplate(
									ftpl);
							tpl.process(data, out);
							out.flush();
							result.put("component_"+fc.getComponentId()+"_"+fm.getModelId(), fc.getName()+"["+fm.getLabel()+"]"+"生成成功！");
						} catch (IOException e) {
							result.put("component_"+fc.getComponentId()+"_"+fm.getModelId(), fc.getName()+"["+fm.getLabel()+"]"+"生成失败！");
							e.printStackTrace();
						} catch (TemplateException e) {
							result.put("component_"+fc.getComponentId()+"_"+fm.getModelId(), fc.getName()+"["+fm.getLabel()+"]"+"生成失败！");
							e.printStackTrace();
						}
					}
				}

			}
		}
		this.getRequest().setAttribute("result", JSONObject.fromObject(result).toString());
		return SUCCESS;
	}


	public IFrameComponentService getComponentService() {
		return componentService;
	}

	public void setComponentService(IFrameComponentService componentService) {
		this.componentService = componentService;
	}

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public IFrameProjectService getProjectService() {
		return projectService;
	}

	public void setProjectService(IFrameProjectService projectService) {
		this.projectService = projectService;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public IFrameModelService getFrameModelService() {
		return frameModelService;
	}

	public void setFrameModelService(IFrameModelService frameModelService) {
		this.frameModelService = frameModelService;
	}

}
