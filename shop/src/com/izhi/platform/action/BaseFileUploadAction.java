package com.izhi.platform.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
/**
 * 
 * @author Tiger Wang
 * @email  jchwang78@gmail.com
 * used for file loader ;
 */
public class BaseFileUploadAction extends BasePageAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1626851078859435795L;
	protected File file;
	protected String contentType;
	protected String fileName;
	protected String svrFileName;
	protected String fileType;
	// 相对路径
	protected String uploadPath;
	// 绝对路径
	protected String dirPath;
	// 文件后缀
	protected String extension;
	
	protected File svrfile ;
	/**
	 * @param dirPath the dirPath to set
	 */
	public void setDirPath(String dirPath) {
		this.dirPath = dirPath;
	}
	
	public void reinitUploadPath(){
		// do nothing 
		// subclass may implement this function 
		// initialize uploadPath string ;
	}
	
	public String upload() {
		//log.debug("pinfophoto file upload start ");
		reinitUploadPath();
		extension = getExtention(fileName);
		svrFileName = new Date().getTime() + extension;
		/*String dirPath = ServletActionContext.getServletContext().getRealPath(uploadPath)
					 + "/" + obj.getOrgId() + '/' ;*/
		dirPath = getDirPath();
		File dir = new File(dirPath);
		if(! dir.exists()) {
			dir.mkdir();
		}
		String fileRelapath = dirPath + svrFileName;
		
		svrfile = new File(fileRelapath);
		copy(file, svrfile);
		//File svrfile = new File(fileRelapath);
		//copy(file, fileRelapath);
		file.delete();
		
		//log.debug("file pagh : " + fileRelapath );
		//log.debug("relapaht : " + uploadPath + svrFileName);
		this.out( fileCheck(fileRelapath, uploadPath + "/" + svrFileName) );
		
		return null;
	}
	//
	// 删除文件
	public String delete( String filename ) {
		return null;
	}
	// 文件后缀是否
	public boolean fileExtension( String ext){
		return extension.toUpperCase().equals(ext.toUpperCase());
	}
	
	// 在destDir的目录下面建立一个 由 当前时间值命名的 目录名
	public String makeNewTimeNameDir(String destDir) {
		File dir = new File(destDir);
		if(! dir.exists()) {
			dir.mkdir();
		}
		long time = new Date().getTime();
		File ndir = new File(destDir + "/" + time);
		if( !ndir.exists() ){
			ndir.mkdir();
		}
		return new String("" + time);
	}
	
	public void copy(File src, File dst) {
	//public void copy(File src, String dstFilename ) {
		//File dst = new File( dstFilename );
		
		int length = 0 ;
		try {
			InputStream in = null;
			OutputStream out = null;
			FileInputStream fis = null;
			FileOutputStream fos = null;
			try {
				fis = new FileInputStream(src);
				fos = new FileOutputStream(dst);
				in = new BufferedInputStream( fis , 1024*10);
				out= new BufferedOutputStream( fos ,1024*10);
				byte [] buffer = new byte[1024*10];
				int count = 0 ;
				while( (count = in.read(buffer)) > 0) {
					out.write(buffer, 0, count);
					length += count;
				}
			} finally {
				if( null != in) {
					fis.close();
					in.close();
					src.delete();
				}
				if( null != out){
					out.flush();
					fos.close();
					out.close();
				}
			}
		} catch( Exception e){
			e.printStackTrace();
		}
	}
	
	public static String getExtention(String _fileName){
		int pos = _fileName.lastIndexOf(".");
		return _fileName.substring(pos);
	}
	/**
	 * 
	 * @param file : File引用
	 * @param path : 文件的相对路径
	 * @return
	 */
	public String fileCheck( String fileRealPath, String relaPath ){
		return null;
	}
	/**
	 * @return the file
	 */
	public File getFile() {
		return file;
	}
	
	/**
	 * @param file the file to set
	 */
	public void setFile(File file) {
		this.file = file;
	}

	/**
	 * @return the contentType
	 */
	public String getContentType() {
		return contentType;
	}

	/**
	 * @param contentType the contentType to set
	 */
	public void setFileContentType(String contentType) {
		this.contentType = contentType;
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName the fileName to set
	 */
	public void setFileFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return the svrFileName
	 */
	public String getSvrFileName() {
		return svrFileName;
	}

	/**
	 * @param svrFileName the svrFileName to set
	 */
	public void setSvrFileName(String svrFileName) {
		this.svrFileName = svrFileName;
	}

	/**
	 * @return the uploadPath
	 */
	public String getUploadPath() {
		return uploadPath;
	}

	/**
	 * @param uploadPath the uploadPath to set
	 */
	public void setUploadPath(String uploadPath) {
		this.uploadPath = uploadPath;
	}
	/**
	 * @return the dirPath
	 */
	public String getDirPath() {
		return dirPath;
	}
	/**
	 * @return the extension
	 */
	public String getExtension() {
		return extension;
	}
	/**
	 * @param extension the extension to set
	 */
	public void setExtension(String extension) {
		this.extension = extension;
	}

	/**
	 * @return the fileType
	 */
	public String getFileType() {
		return fileType;
	}

	/**
	 * @param fileType the fileType to set
	 */
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	
}
