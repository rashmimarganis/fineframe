package com.izhi.platform.webapp.security.captcha;

import org.acegisecurity.captcha.CaptchaSecurityContextImpl;

public class FixedCaptchaSecurityContextImpl extends CaptchaSecurityContextImpl {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1753395501371508751L;

	public int hashCode() {

		if (getAuthentication() == null) {
			return (int) System.currentTimeMillis();
		} else {
			return this.getAuthentication().hashCode();
		}
	}
}
