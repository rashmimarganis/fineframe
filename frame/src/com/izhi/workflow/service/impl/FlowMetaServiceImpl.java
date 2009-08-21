/*
 * Copyright 2004-2005 the original author or authors.
 *
 * Licensed under the LGPL license, Version 2.1 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.gnu.org/copyleft/lesser.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * @author daquanda(liyingquan@gmail.com)
 * @author kevin(diamond_china@msn.com)
 */
package com.izhi.workflow.service.impl;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Document;
import org.jdom.input.SAXBuilder;
import org.springframework.stereotype.Service;

import com.izhi.platform.util.PageParameter;
import com.izhi.workflow.dao.IBusinessTypeDAO;
import com.izhi.workflow.dao.IFlowMetaFileDAO;
import com.izhi.workflow.dao.FlowModelDAO;
import com.izhi.workflow.dao.IWorkflowMetaDAO;
import com.izhi.workflow.exception.ExceptionMessage;
import com.izhi.workflow.exception.FlowMetaException;
import com.izhi.workflow.model.BusinessType;
import com.izhi.workflow.model.FlowDeploy;
import com.izhi.workflow.model.FlowMetaFile;
import com.izhi.workflow.model.WorkflowMeta;
import com.izhi.workflow.service.IFlowMetaService;

@Service("workflowFlowMetaService")
public class FlowMetaServiceImpl implements IFlowMetaService {
	private static Log log = LogFactory.getLog(FlowMetaServiceImpl.class);
	@Resource(name="workflowMetaDao")
	private IWorkflowMetaDAO flowMetaDAO;
	@Resource(name="workflowFlowMetaFileDao")
	private IFlowMetaFileDAO flowMetaFileDAO;
	@Resource(name="workflowFlowMetaFileStoreDao")
	private IFlowMetaFileDAO flowMetaFileStoreDAO;
	@Resource(name="workflowBusinessTypeDao")
	private IBusinessTypeDAO btDAO;

	public void setFlowMetaDAO(IWorkflowMetaDAO dao) {
		flowMetaDAO = dao;
	}

	public void setFlowMetaFileDAO(IFlowMetaFileDAO dao) {
		flowMetaFileDAO = dao;
	}


	public void setBusinessTypeDAO(IBusinessTypeDAO dao) {
		this.btDAO = dao;
	}

	public List findAllWorkflowMetas() {
		List result = flowMetaDAO.getAllWorkflowMetas();
		if (log.isDebugEnabled()) {
			log.debug("���ҵ�WorkflowMeta[" + new Integer(result.size()) + "]��");
		}
		return result;
	}

	public WorkflowMeta findWorkflowMeta(String flowMetaID) {
		return flowMetaDAO.getWorkflowMeta(new Long(flowMetaID));
	}

	public WorkflowMeta findWorkflowMetaWithFile(String flowMetaID) {
		WorkflowMeta workflowMeta = findWorkflowMeta(flowMetaID);
		FlowMetaFile flowMetaFile = findFlowMetaFile(workflowMeta
				.getFlowFileInUse().getFlowFileID().toString());
		
		workflowMeta.setFlowFileInUse(flowMetaFile);
		return workflowMeta;
	}

	public WorkflowMeta saveWorkflowMeta(WorkflowMeta workflowMeta) {
		flowMetaDAO.saveWorkflowMeta(workflowMeta);
		
		return workflowMeta;
	}

	public void deleteWorkflowMeta(String flowMetaID) {
		// WorkflowMeta wm = this.getWorkflowMeta(flowMetaID);
		// if (wm.getFlowFileVersions().size() > 0) {
		// for (Iterator it = wm.getFlowFileVersions().iterator(); it.hasNext();
		// ) {
		// FlowMetaFile fmf = (FlowMetaFile) it.next();
		// flowMetaStoreDAO.removeFlowMetaFile(fmf.getFlowFileID());
		// }
		// }
		// BusinessType bt = wm.getBusinessType();
		// if (bt != null) {
		// bt.removeWorkflowMeta(wm);
		// }

		flowMetaDAO.removeWorkflowMeta(new Long(flowMetaID));
	}

	public WorkflowMeta findWorkflowMetaByProcess(String flowProcessID) {
		return flowMetaDAO.getWorkflowMetaByProcess(flowProcessID);
	}

	public void updateFlowFileInUse(String flowMetaID, String flowFileID) {
		WorkflowMeta wm = this.findWorkflowMeta(flowMetaID);
		FlowMetaFile fmFile = this.findFlowMetaFile(flowFileID);
		
		if (fmFile.getWorkflowMeta() == null
				|| !((fmFile.getWorkflowMeta().getFlowMetaID().toString())
						.equals(flowMetaID))) {
			String mess = "��ͼ��һ���������FlowMetaFile[" + flowFileID
					+ "]��ΪWorkflowMeta[" + flowMetaID + "]�ĵ�ǰ�汾��";
			log.debug(mess);
			throw new RuntimeException(mess);
		}
		wm.setFlowFileInUse(fmFile);

		// flowMetaFileDAO.saveFlowMetaFile(fmFile);
		this.saveWorkflowMeta(wm);
	}

	public void updateBusinessType(String flowMetaID, String businessTypeID) {
		WorkflowMeta wm = flowMetaDAO.getWorkflowMeta(new Long(flowMetaID));
		BusinessType bt = btDAO.findBusinessType(new Long(businessTypeID));
		wm.updateBusinessType(bt);
		btDAO.saveBusinessType(bt);
	}

	public FlowMetaFile findFlowMetaFile(String flowFileID) {
		FlowMetaFile fmFile = this.flowMetaFileDAO.findFlowMetaFile(new Long(
				flowFileID));
		FlowMetaFile fmFileStore = this.flowMetaFileDAO
				.findFlowMetaFile(new Long(flowFileID));
		fmFile.setPreviewImageInput(fmFileStore.getPreviewImageInput());
		fmFile.setWorkflowFileInput(fmFileStore.getWorkflowFileInput());

		return fmFile;
	}

	public FlowMetaFile saveFlowMetaFile(FlowMetaFile flowMetaFile) {
		this.flowMetaFileDAO.saveFlowMetaFile(flowMetaFile);
		//this.flowMetaFileStoreDAO.saveFlowMetaFile(flowMetaFile);
		return flowMetaFile;
	}

	public void deleteFlowMetaFile(String flowFileID) {
		FlowMetaFile fmFile = this.findFlowMetaFile(flowFileID);
		WorkflowMeta wm = fmFile.getWorkflowMeta();
		if (wm.getFlowFileInUse() != null
				&& wm.getFlowFileInUse().getFlowFileID().toString().equals(
						flowFileID)) {
			String mess = "��ͼɾ��WorkflowMeta[" + wm.getFlowMetaID()
					+ "]�ĵ�ǰ�汾FlowFile[" + flowFileID + "]��";
			log.debug(mess);
			throw new RuntimeException(mess);
		}

		wm.removeFlowFileVersion(fmFile);
		flowMetaFileDAO.deleteFlowMetaFile(new Long(flowFileID));
		//flowMetaStoreDAO.deleteFlowMetaFile(new Long(flowFileID));
	}

	public void addFlowFileVersion(String flowMetaID, String flowFileID) {
		WorkflowMeta wm = this.findWorkflowMeta(flowMetaID);
		FlowMetaFile fmFile = this.findFlowMetaFile(flowFileID);
		wm.addFlowFileVersion(fmFile);
		flowMetaDAO.saveWorkflowMeta(wm);
	}

	public void deleteFlowFileVersion(String flowMetaID, String flowFileID) {
		// WorkflowMeta wm = flowMetaDAO.getWorkflowMeta(new Long(flowMetaID));
		// FlowMetaFile fmFile =wm.getFlowFileInUse();
		// if(fmFile!=null&&
		// fmFile.getFlowFileID().toString().equals(flowFileID)){
		// String mess = "��ͼɾ��WorkflowMeta["+flowMetaID
		// +"]�ĵ�ǰ�汾FlowFile["+flowFileID+"]��";
		// log.debug(mess);
		// throw new RuntimeException(mess);
		// }
		this.deleteFlowMetaFile(flowFileID);
	}

	/**
	 * getWorkflowMetasNoBusinessType
	 * 
	 * @return List
	 */
	public List findWorkflowMetasNoBusinessType() {
		return flowMetaDAO.getWorkflowMetasNoBusinessType();
	}

	/**
	 * 
	 * @param flowInput
	 *            InputStream
	 * @param flowInputTest
	 *            InputStream
	 * @param previewInput
	 *            InputStream
	 * @param flowSize
	 *            Long
	 * @param previewSize
	 *            Long
	 * @return WorkflowMeta
	 */
	public WorkflowMeta uploadFlowMetaFile(InputStream flowInput,
			InputStream flowInputTest, InputStream previewInput, Long flowSize,
			Long previewSize,Long typeId) {
		
		SAXBuilder saxBuilder = new SAXBuilder(false);
		Document doc = null;
		try {
			doc = saxBuilder.build(flowInputTest);
		} catch (Exception ex) {
			log.debug(ex.getMessage());
			throw new FlowMetaException(
					ExceptionMessage.ERROR_FLOWMETAFILE_DIGESTER);
		}
		FlowModelDAO fmDAO = new FlowModelDAO(doc);
		FlowMetaFile[] flowMetaFile = fmDAO.getAllFlowMetaFiles();
		if (flowMetaFile == null || flowMetaFile.length == 0) {
			throw new FlowMetaException(
					ExceptionMessage.ERROR_FLOWMETAFILE_NO_METAS);
		} else if (flowMetaFile.length > 1) {
			throw new FlowMetaException(
					ExceptionMessage.ERROR_FLOWMETAFILE_MULTI_METAS);
		}

		FlowMetaFile fmf = flowMetaFile[0];
		WorkflowMeta wm = this.findWorkflowMetaByProcess(fmf.getFlowProcessID());
	
		if (wm != null) {
			throw new FlowMetaException(
					ExceptionMessage.ERROR_FLOWMETA_DUPLICATE_PROCESS_ID);
		}
		fmf.setWorkflowFileInput(flowInput);
		fmf.setPreviewImageInput(previewInput);
		fmf.setPreviewImageSize(previewSize);
		fmf.setWorkflowFileSize(flowSize);
		this.saveFlowMetaFile(fmf);
		BusinessType bt=btDAO.findBusinessType(typeId);
		wm = new WorkflowMeta();
		wm.setBusinessType(bt);
		wm.setFlowProcessID(fmf.getFlowProcessID());
		wm.addFlowFileVersion(fmf);
		wm.setFlowFileInUse(fmf);
		this.flowMetaFileStoreDAO.saveFlowMetaFile(fmf);
		return this.saveWorkflowMeta(wm);
	}

	public void addFlowDeploy(String flowMetaID, FlowDeploy flowDeploy) {
		WorkflowMeta wm = this.findWorkflowMeta(flowMetaID);
		wm.addFlowDeploy(flowDeploy);
		this.saveWorkflowMeta(wm);
	}

	public void updatePreviewImage(String flowMetaID,
			InputStream fileInputStream, Long previewSize) {
		WorkflowMeta wm = this.findWorkflowMetaWithFile(flowMetaID);
		FlowMetaFile fmFile = wm.getFlowFileInUse();
		fmFile.setPreviewImageInput(fileInputStream);
		fmFile.setPreviewImageSize(previewSize);
		this.flowMetaFileDAO.updatePreviewImage(fmFile);
	}

	@Override
	public Map<String, Object> findPage(PageParameter pp, Long typeId) {
		Map<String,Object> m=new HashMap<String, Object>();
		m.put("objs", this.flowMetaFileDAO.findPage(pp, typeId));
		m.put("totalCount", this.flowMetaFileDAO.findTotalCount(typeId));
		return m;
	}

	public IFlowMetaFileDAO getFlowMetaFileStoreDAO() {
		return flowMetaFileStoreDAO;
	}

	public void setFlowMetaFileStoreDAO(IFlowMetaFileDAO flowMetaFileStoreDAO) {
		this.flowMetaFileStoreDAO = flowMetaFileStoreDAO;
	}

	@Override
	public List<Map<String, Object>> findByType(Long typeId) {
		return this.flowMetaFileDAO.findByType(typeId);
	}

}
