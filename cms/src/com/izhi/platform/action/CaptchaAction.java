package com.izhi.platform.action;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.octo.captcha.service.image.ImageCaptchaService;
import com.sun.image.codec.jpeg.ImageFormatException;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
@Service
@Scope(value="prototype")
public class CaptchaAction extends BaseAction implements InitializingBean {
	private static final long serialVersionUID = 5561504083890088950L;
	@Resource(name="jcaptchaService")
	private ImageCaptchaService jcaptchaService;
	private String original_requestUrl;
	private String original_request_method;

	public String execute() {
		HttpServletRequest request = this.getRequest();
		HttpServletResponse response = this.getResponse();
		byte[] captchaChallengeAsJpeg = null;

		ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
		String captchaId = request.getSession().getId();

		BufferedImage challenge = jcaptchaService.getImageChallengeForID(
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

		return null;

	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if (jcaptchaService == null) {
			throw new RuntimeException("Image captcha service wasn`t set!");

		}
	}

	public ImageCaptchaService getJcaptchaService() {
		return jcaptchaService;
	}

	public void setJcaptchaService(ImageCaptchaService jcaptchaService) {
		this.jcaptchaService = jcaptchaService;
	}

	public String getOriginal_requestUrl() {
		return original_requestUrl;
	}

	public void setOriginal_requestUrl(String original_requestUrl) {
		this.original_requestUrl = original_requestUrl;
	}

	public String getOriginal_request_method() {
		return original_request_method;
	}

	public void setOriginal_request_method(String original_request_method) {
		this.original_request_method = original_request_method;
	}
}
