package com.izhi.platform.webapp.security.captcha;

import org.acegisecurity.captcha.CaptchaServiceProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.octo.captcha.service.CaptchaServiceException;
import com.octo.captcha.service.image.ImageCaptchaService;

public class JCaptchaServiceProxyImpl implements CaptchaServiceProxy {
	private ImageCaptchaService jcaptchaService;
	private static Logger log = LoggerFactory.getLogger(JCaptchaServiceProxyImpl.class);
	@Override
	public boolean validateReponseForId(String id, Object captchaResponse) {
		try {
			boolean valid=jcaptchaService.validateResponseForID(id, captchaResponse);
			if(log.isDebugEnabled()){
				log.debug("验证码是否正确："+valid);
			}
			return valid;

		} catch (CaptchaServiceException cse) {
			return false;
		}

	}

	public ImageCaptchaService getJcaptchaService() {
		return jcaptchaService;
	}

	public void setJcaptchaService(ImageCaptchaService jcaptchaService) {
		this.jcaptchaService = jcaptchaService;
	}

	

}
