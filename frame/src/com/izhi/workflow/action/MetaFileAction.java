package com.izhi.workflow.action;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.annotation.Resource;
import javax.imageio.ImageIO;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import net.sf.json.JSONObject;

import com.izhi.platform.action.BasePageAction;
import com.izhi.workflow.model.FlowMetaFile;
import com.izhi.workflow.model.WorkflowMeta;
import com.izhi.workflow.service.IFlowMetaService;
@Service
@Scope("prototype")
@Namespace("/workflow/metafile")
public class MetaFileAction extends BasePageAction {
	public static final int BUFFER_SIZE = 16 * 1024;
	private static final long serialVersionUID = 119084852334836226L;
	private static final int IMAGE_SIZE=120;
	private File metaFile;
	private File imageFile;
	

	private String imageFileContentType;
	private String imageFileFileName;
	private String metaFileContentType;
	private String metaFileFileName;

	private Long typeId;
	
	private String flowMetaId;
	@Resource(name="workflowFlowMetaService")
	private IFlowMetaService service;

	@Action("upload")
	public String upload() {
		try {
			InputStream mf = new FileInputStream(metaFile);
			InputStream imgf = new FileInputStream(imageFile);
			InputStream testMf=new FileInputStream(metaFile);
			WorkflowMeta wm = service.uploadFlowMetaFile(mf, testMf, imgf,
					new Long(metaFile.length()), new Long(imageFile.length()),typeId);
			boolean success = wm != null;
			this.out("{'success':" + success + "}");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	@Action("previewMeta")
	public String previewMeta() throws IOException{
		WorkflowMeta wm = service.findWorkflowMeta(flowMetaId);
		log.debug(">>>>>>>WorkflowMeta:"+(wm!=null));
		FlowMetaFile fmFile = service.findFlowMetaFile(wm
				.getFlowFileInUse().getFlowFileID().toString());
		log.debug(">>>>>>>fmFile:"+(wm.getFlowFileInUse()!=null));
		BufferedImage bis = ImageIO.read(fmFile.getPreviewImageInput());
		if (bis != null) {
			int w = bis.getWidth();
			int h = bis.getHeight();
			int nw = IMAGE_SIZE; // final int IMAGE_SIZE = 120;
			int nh = (nw * h) / w;
			if (nh > IMAGE_SIZE) {
				nh = IMAGE_SIZE;
				nw = (nh * w) / h;
			}
			double sx = (double) nw / w;
			double sy = (double) nh / h;
			AffineTransform transform = new AffineTransform();
			transform.setToScale(sx, sy);
			AffineTransformOp ato = new AffineTransformOp(transform, null);
			BufferedImage bid = new BufferedImage(nw, nh,
					BufferedImage.TYPE_3BYTE_BGR);
			ato.filter(bis, bid);
			ImageIO.write(bid, "jpeg", this.getResponse().getOutputStream());
		} else {
			//
		}
		return null;
	}
	@Action("page")
	public String page() {
		/*String s = JSONObject.fromObject(
				service.findPage(this.getPageParameter(), typeId)).toString();*/
		String ss=JSONObject.fromObject(service.findPage(this.getPageParameter(), typeId)).toString();
		this.out(ss);
		return null;
	}

	public File getMetaFile() {
		return metaFile;
	}

	public void setMetaFile(File metaFile) {
		this.metaFile = metaFile;
	}

	public File getImageFile() {
		return imageFile;
	}

	public void setImageFile(File imageFile) {
		this.imageFile = imageFile;
	}

	public IFlowMetaService getService() {
		return service;
	}

	public void setService(IFlowMetaService service) {
		this.service = service;
	}

	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	public String getImageFileContentType() {
		return imageFileContentType;
	}

	public void setImageFileContentType(String imageFileContentType) {
		this.imageFileContentType = imageFileContentType;
	}

	public String getImageFileFileName() {
		return imageFileFileName;
	}

	public void setImageFileFileName(String imageFileFileName) {
		this.imageFileFileName = imageFileFileName;
	}

	public String getMetaFileContentType() {
		return metaFileContentType;
	}

	public void setMetaFileContentType(String metaFileContentType) {
		this.metaFileContentType = metaFileContentType;
	}

	public String getMetaFileFileName() {
		return metaFileFileName;
	}

	public void setMetaFileFileName(String metaFileFileName) {
		this.metaFileFileName = metaFileFileName;
	}

	public byte[] InputStreamToByte(InputStream iStrm) throws IOException {
		ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
		int ch;
		while ((ch = iStrm.read()) != -1) {
			bytestream.write(ch);
		}
		byte imgdata[] = bytestream.toByteArray();
		bytestream.close();
		return imgdata;
	}

	public String getFlowMetaId() {
		return flowMetaId;
	}

	public void setFlowMetaId(String flowMetaId) {
		this.flowMetaId = flowMetaId;
	}

}
