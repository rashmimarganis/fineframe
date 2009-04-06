package com.izhi.platform.webapp.security.captcha;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.octo.captcha.service.image.ImageCaptchaService;
import com.sun.image.codec.jpeg.ImageFormatException;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class CaptchaServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1134383899834735860L;

	public void init(ServletConfig servletConfig) throws ServletException {
		super.init(servletConfig);

	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		byte[] captchaChallengeAsJpeg = null;

		ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
		String captchaId = request.getSession().getId();
		WebApplicationContext wac=WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
		ImageCaptchaService captchaService=(ImageCaptchaService)wac.getBean("jcaptchaService");
		BufferedImage challenge = captchaService.getImageChallengeForID(
				captchaId, request.getLocale());

		JPEGImageEncoder jpegEncoder = JPEGCodec
				.createJPEGEncoder(jpegOutputStream);
		try {
			jpegEncoder.encode(challenge);
		} catch (ImageFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		captchaChallengeAsJpeg = jpegOutputStream.toByteArray();
		response.setHeader("Cache-Control", "no-store");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpeg");
		ServletOutputStream responseOutputStream;
		try {
			responseOutputStream = response.getOutputStream();
			responseOutputStream.write(captchaChallengeAsJpeg);
			responseOutputStream.flush();
			responseOutputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}

}
