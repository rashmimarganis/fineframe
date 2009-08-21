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
package com.izhi.workflow.service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import com.izhi.platform.util.PageParameter;
import com.izhi.workflow.dao.IBusinessTypeDAO;
import com.izhi.workflow.dao.IFlowMetaFileDAO;
import com.izhi.workflow.dao.IWorkflowMetaDAO;
import com.izhi.workflow.model.FlowDeploy;
import com.izhi.workflow.model.FlowMetaFile;
import com.izhi.workflow.model.WorkflowMeta;
@SuppressWarnings("unchecked")
public interface IFlowMetaService {
	public void setFlowMetaDAO(IWorkflowMetaDAO dao);

	public void setFlowMetaFileDAO(IFlowMetaFileDAO dao);

	//public void setFlowMetaFileStoreDAO(FlowMetaFileDAO dao);

	public void setBusinessTypeDAO(IBusinessTypeDAO dao);

	public List findAllWorkflowMetas();
	
	public Map<String,Object> findPage(PageParameter pp,Long typeId);
	public List<Map<String,Object>> findByType(Long typeId);

	public List findWorkflowMetasNoBusinessType();

	public WorkflowMeta findWorkflowMeta(String flowMetaID);

	public WorkflowMeta findWorkflowMetaWithFile(String flowMetaID);

	public WorkflowMeta saveWorkflowMeta(WorkflowMeta workflowMeta);

	public void deleteWorkflowMeta(String flowMetaID);

	public WorkflowMeta findWorkflowMetaByProcess(String flowProcessID);

	public void updateFlowFileInUse(String flowMetaID, String flowFileID);

	public void updateBusinessType(String flowMetaID, String businessTypeID);

	public FlowMetaFile findFlowMetaFile(String flowFileID);

	public FlowMetaFile saveFlowMetaFile(FlowMetaFile flowMetaFile);

	public WorkflowMeta uploadFlowMetaFile(InputStream flowInput,
			InputStream flowInputTest, InputStream previewInput, Long flowSize,
			Long previewSize,Long typeId);

	public void deleteFlowMetaFile(String flowFileID);

	public void addFlowFileVersion(String flowMetaID, String flowFileID);

	public void addFlowDeploy(String flowMetaID, FlowDeploy flowDeploy);

	public void deleteFlowFileVersion(String flowMetaID, String flowFileID);

	/**
	 * updatePreviewImage
	 * 
	 * @param flowMetaID
	 *            String
	 * @param fileInputStream
	 *            InputStream
	 */
	public void updatePreviewImage(String flowMetaID,
			InputStream fileInputStream, Long previewSize);

}
