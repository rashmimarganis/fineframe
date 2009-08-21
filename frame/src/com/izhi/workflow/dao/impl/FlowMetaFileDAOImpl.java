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
package com.izhi.workflow.dao.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Service;

import com.izhi.platform.util.PageParameter;
import com.izhi.workflow.dao.IFlowMetaFileDAO;
import com.izhi.workflow.model.FlowMetaFile;
import com.izhi.workflow.model.FlowMetaFileStore;

@Service("workflowFlowMetaFileDao")
@SuppressWarnings("unchecked")
public class FlowMetaFileDAOImpl extends HibernateDaoSupport implements
		IFlowMetaFileDAO {
	
	private IFlowMetaFileDAO fileStoreDAO;

	public List<FlowMetaFile> findAllFlowMetaFiles() {
		return getHibernateTemplate().findByNamedQuery("AllFlowMetaFiles");
	}

	public FlowMetaFile findFlowMetaFile(Long flowFileID) {
		FlowMetaFile fmf= (FlowMetaFile) getHibernateTemplate().load(FlowMetaFile.class,
		        flowFileID);
	  FlowMetaFileStore fmfs=(FlowMetaFileStore)this.getHibernateTemplate().load(FlowMetaFileStore.class, flowFileID);
	  try {
		fmf.setPreviewImageInput(fmfs.getPreviewImage().getBinaryStream());
		fmf.setWorkflowFileInput(fmfs.getWorkflowFile().getBinaryStream());
	} catch (SQLException e) {
		e.printStackTrace();
	}
	  
    return fmf;
	}

	public void saveFlowMetaFile(final FlowMetaFile flowMetaFile) {
		/*try {
			log.debug(">>>>>>>>工作流文件:"+flowMetaFile.getWorkflowFileInput().available());
			log.debug(">>>>>>>>图片文件:"+flowMetaFile.getPreviewImageInput().available());
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		//fileStoreDAO.saveFlowMetaFile(flowMetaFile);
		if(flowMetaFile.getFlowFileID()==-1){
			this.getHibernateTemplate().save(flowMetaFile);
		}else{
			this.getHibernateTemplate().update(flowMetaFile);
		}
		//this.getHibernateTemplate().saveOrUpdate(flowMetaFile);
		
		getHibernateTemplate().flush();
	}

	public void deleteFlowMetaFile(Long flowFileID) {
		FlowMetaFile flowMetaFile = (FlowMetaFile) getHibernateTemplate().load(
				FlowMetaFile.class, flowFileID);
		getHibernateTemplate().delete(flowMetaFile);
		getHibernateTemplate().flush();
	}

	public void updatePreviewImage(FlowMetaFile fmFile) {
		throw new java.lang.UnsupportedOperationException(
				"Method updatePreviewImage(FlowMetaFile fmFile) not yet implemented.");
	}

	@Override
	public List<Map<String, Object>> findPage(PageParameter pp,Long typeId) {
		String sql="select new map(o.flowFileID as flowFileID,o.flowMetaName as flowMetaName,o.createdTime as createdTime,o.workflowMeta.businessType.typeName as typeName) from FlowMetaFile o where o.workflowMeta.businessType.typeID=:typeId order by o."+pp.getSort()+" "+pp.getDir()+")";
		Query q=this.getSession().createQuery(sql);
		q.setLong("typeId", typeId);
		q.setMaxResults(pp.getLimit());
		q.setFirstResult(pp.getStart());
		return q.list();
	}
	
	public int findTotalCount(Long typeId){
		String sql="select count(*) from FlowMetaFile o where o.workflowMeta.businessType.typeID=:typeId )";
		Query q=this.getSession().createQuery(sql);
		q.setLong("typeId", typeId);
		List<?> l=q.list();
		return ((Long)l.get(0)).intValue();
	}

	public IFlowMetaFileDAO getFileStoreDAO() {
		return fileStoreDAO;
	}

	public void setFileStoreDAO(IFlowMetaFileDAO fileStoreDAO) {
		this.fileStoreDAO = fileStoreDAO;
	}

	@Override
	public List<Map<String, Object>> findByType(Long typeId) {
		String sql="select new map(o.workflowMeta.flowMetaID as id,o.flowMetaName as text,'file' as cls,'true' as leaf,'bt_"+typeId+"' as parentId) from FlowMetaFile o where o.workflowMeta.businessType.typeID=? order by o.flowFileID asc)";
		List<Map<String,Object>> l=this.getHibernateTemplate().find(sql, typeId);
		return l;
	}

}
