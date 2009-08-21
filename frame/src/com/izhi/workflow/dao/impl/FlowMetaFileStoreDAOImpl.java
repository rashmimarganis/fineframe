package com.izhi.workflow.dao.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Service;

import com.izhi.platform.util.PageParameter;
import com.izhi.workflow.dao.IFlowMetaFileDAO;
import com.izhi.workflow.model.FlowMetaFile;
import com.izhi.workflow.model.FlowMetaFileStore;
@Service("workflowFlowMetaFileStoreDao")
public class FlowMetaFileStoreDAOImpl extends HibernateDaoSupport implements
		IFlowMetaFileDAO {
	final static Logger log=Logger.getLogger(FlowMetaFileStoreDAOImpl.class);
	@Override
	public List findAllFlowMetaFiles() {
		return null;
	}

	@Override
	public FlowMetaFile findFlowMetaFile(Long flowFileID) {
		FlowMetaFile fmFile =(FlowMetaFile)this.getHibernateTemplate().load(FlowMetaFile.class, flowFileID);
		return fmFile;
	}

	@Override
	public void deleteFlowMetaFile(Long flowFileID) {
		String sql="delete from FlowMetaFile o where o.flowFileID=?";
		this.getHibernateTemplate().bulkUpdate(sql, flowFileID);
	}

	@Override
	public void saveFlowMetaFile(FlowMetaFile fmf) {
		//fmf=this.findFlowMetaFile(fmf.getFlowFileID());
		InputStream piIs=fmf.getPreviewImageInput();
		InputStream wfIs=fmf.getWorkflowFileInput();
		
		FlowMetaFileStore fmfs=new FlowMetaFileStore();
		fmfs.setFlowFileID(fmf.getFlowFileID());
		try {
			log.debug(">>>>>>>>>>PreviewImage:"+piIs.available());
			log.debug(">>>>>>>>>>FlowFile:"+wfIs.available());
			
			fmfs.setPreviewImage(Hibernate.createBlob(piIs));
			fmfs.setWorkflowFile(Hibernate.createBlob(wfIs));
//			fmfs.setPreviewImage(inputStream2Byte(piIs));
//			fmfs.setWorkflowFile(inputStream2Byte(wfIs));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.getHibernateTemplate().save(fmfs);
	}

	@Override
	public void updatePreviewImage(FlowMetaFile fmFile) {
		
	}

	@Override
	public List<Map<String, Object>> findPage(PageParameter pp, Long typeId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int findTotalCount(Long typeId) {
		// TODO Auto-generated method stub
		return 0;
	}
	private byte[] inputStream2Byte(InputStream iStrm) throws IOException {
		ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
		int ch;
		while ((ch = iStrm.read()) != -1) {
			bytestream.write(ch);
		}
		byte imgdata[] = bytestream.toByteArray();
		bytestream.close();
	
		return imgdata;
	}

	@Override
	public List<Map<String, Object>> findByType(Long typeId) {
		// TODO Auto-generated method stub
		return null;
	}
}
