/*
 * FCKeditor - The text editor for internet
 * Copyright (C) 2003-2005 Frederico Caldeira Knabben
 * 
 * Licensed under the terms of the GNU Lesser General Public License:
 * 		http://www.opensource.org/licenses/lgpl-license.php
 * 
 * For further information visit:
 * 		http://www.fckeditor.net/
 * 
 * File Name: SimpleUploaderServlet.java
 * 	Java File Uploader class.
 * 
 * Version:  2.3
 * Modified: 2005-08-11 16:29:00
 * 
 * File Authors:
 * 		Simone Chiaretta (simo@users.sourceforge.net)
 */

package com.izhi.platform.editor;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Servlet to upload files.<br>
 * 
 * This servlet accepts just file uploads, eventually with a parameter
 * specifying file type
 * 
 * @author Simone Chiaretta (simo@users.sourceforge.net)
 */

public class SimpleUploader extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3096800116651263134L;
	private static String baseDir;
	private static boolean enabled = false;
	private static Hashtable<String, Object> allowedExtensions;
	private static Hashtable<String, Object> deniedExtensions;
	protected final Logger log=LoggerFactory.getLogger(this.getClass());

	/**
	 * Initialize the servlet.<br>
	 * Retrieve from the servlet configuration the "baseDir" which is the root
	 * of the file repository:<br>
	 * If not specified the value of "/UserFiles/" will be used.<br>
	 * Also it retrieve all allowed and denied extensions to be handled.
	 * 
	 */
	public void init() throws ServletException {

		// debug=(new Boolean(getInitParameter("debug"))).booleanValue();

		// if(debug) System.out.println("\r\n---- SimpleUploaderServlet
		// initialization started ----");

		baseDir = getInitParameter("baseDir");
		log.debug("FCKEditor 上传目录："+baseDir);
		enabled = (new Boolean(getInitParameter("enabled"))).booleanValue();
		if (baseDir == null) {
			baseDir = "/upload/";
		}
		String realBaseDir = getServletContext().getRealPath(baseDir);
		File baseFile = new File(realBaseDir);
		if (!baseFile.exists()) {
			baseFile.mkdir();
		}

		allowedExtensions = new Hashtable<String, Object>(3);
		deniedExtensions = new Hashtable<String, Object>(3);

		allowedExtensions.put("File",
				stringToArrayList(getInitParameter("AllowedExtensionsFile")));
		deniedExtensions.put("File",
				stringToArrayList(getInitParameter("DeniedExtensionsFile")));

		allowedExtensions.put("Image",
				stringToArrayList(getInitParameter("AllowedExtensionsImage")));
		deniedExtensions.put("Image",
				stringToArrayList(getInitParameter("DeniedExtensionsImage")));

		allowedExtensions.put("Flash",
				stringToArrayList(getInitParameter("AllowedExtensionsFlash")));
		deniedExtensions.put("Flash",
				stringToArrayList(getInitParameter("DeniedExtensionsFlash")));

		// if(debug) System.out.println("---- SimpleUploaderServlet
		// initialization completed ----\r\n");

	}

	/**
	 * Manage the Upload requests.<br>
	 * 
	 * The servlet accepts commands sent in the following format:<br>
	 * simpleUploader?Type=ResourceType<br>
	 * <br>
	 * It store the file (renaming it in case a file with the same name exists)
	 * and then return an HTML file with a javascript command in it.
	 * 
	 */
	@SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// if (debug) System.out.println("--- BEGIN DOPOST ---");

		response.setContentType("text/html; charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();

		String typeStr = request.getParameter("Type");
		log.debug("FCKEditor 上传目录："+baseDir);
		String currentPath = baseDir + typeStr;
		String currentDirPath = getServletContext().getRealPath(currentPath);
		currentPath = request.getContextPath() + currentPath;

		// if (debug) System.out.println(currentDirPath);

		String retVal = "0";
		String newName = "";
		String fileUrl = "";
		String errorMessage = "";

		if (enabled) {
			DiskFileItemFactory factory = new DiskFileItemFactory();

			// Set factory constraints
			factory.setSizeThreshold(1024*1024*10);
			factory.setRepository(new File("/temp/"));

			// Create a new file upload handler
			ServletFileUpload upload = new ServletFileUpload(factory);

			// Set overall request size constraint
			upload.setSizeMax(1024*1024*2);

			// DiskFileUpload upload = new DiskFileUpload();
			try {
				List<FileItem> items = upload.parseRequest(request);

				Map<String, Object> fields = new HashMap<String, Object>();

				Iterator<FileItem> iter = items.iterator();
				while (iter.hasNext()) {
					FileItem item = (FileItem) iter.next();
					if (item.isFormField())
						fields.put(item.getFieldName(), item.getString());
					else
						fields.put(item.getFieldName(), item);
				}
				FileItem uplFile = (FileItem) fields.get("NewFile");
				String fileNameLong = uplFile.getName();
				fileNameLong = fileNameLong.replace('\\', '/');
				String[] pathParts = fileNameLong.split("/");
				String fileName = pathParts[pathParts.length - 1];

				String nameWithoutExt = getNameWithoutExtension(fileName);
				String ext = getExtension(fileName);
				File pathToSave = new File(currentDirPath, fileName);
				fileUrl = currentPath + "/" + fileName;
				if (extIsAllowed(typeStr, ext)) {
					int counter = 1;
					while (pathToSave.exists()) {
						newName = nameWithoutExt + "(" + counter + ")" + "."
								+ ext;
						fileUrl = currentPath + "/" + newName;
						retVal = "201";
						pathToSave = new File(currentDirPath, newName);
						counter++;
					}
					uplFile.write(pathToSave);
				} else {
					retVal = "202";
					errorMessage = "";
					// if (debug) System.out.println("Invalid file type: " +
					// ext);
				}
			} catch (Exception ex) {
				// if (debug) ex.printStackTrace();
				retVal = "203";
			}
		} else {
			retVal = "1";
			errorMessage = "This file uploader is disabled. Please check the WEB-INF/web.xml file";
		}

		out.println("<script type=\"text/javascript\">");
		out.println("window.parent.OnUploadCompleted(" + retVal + ",'"
				+ fileUrl + "','" + newName + "','" + errorMessage + "');");
		out.println("</script>");
		out.flush();
		out.close();

		//if (debug)
			//System.out.println("--- END DOPOST ---");

	}

	/*
	 * This method was fixed after Kris Barnhoorn (kurioskronic) submitted SF
	 * bug #991489
	 */
	private static String getNameWithoutExtension(String fileName) {
		return fileName.substring(0, fileName.lastIndexOf("."));
	}

	/*
	 * This method was fixed after Kris Barnhoorn (kurioskronic) submitted SF
	 * bug #991489
	 */
	private String getExtension(String fileName) {
		return fileName.substring(fileName.lastIndexOf(".") + 1);
	}

	/**
	 * Helper function to convert the configuration string to an ArrayList.
	 */

	private List<String> stringToArrayList(String str) {

		//if (debug)
		//	System.out.println(str);
		String[] strArr = str.split("\\|");

		List<String> tmp = new ArrayList<String>();
		if (str.length() > 0) {
			for (int i = 0; i < strArr.length; ++i) {
				//if (debug)
				//	System.out.println(i + " - " + strArr[i]);
				tmp.add(strArr[i].toLowerCase());
			}
		}
		return tmp;
	}

	/**
	 * Helper function to verify if a file extension is allowed or not allowed.
	 */

	private boolean extIsAllowed(String fileType, String ext) {

		ext = ext.toLowerCase();

		List<?> allowList = (List<?>) allowedExtensions.get(fileType);
		List<?> denyList = (List<?>) deniedExtensions.get(fileType);

		if (allowList.size() == 0)
			if (denyList.contains(ext))
				return false;
			else
				return true;

		if (denyList.size() == 0)
			if (allowList.contains(ext))
				return true;
			else
				return false;

		return false;
	}

}
