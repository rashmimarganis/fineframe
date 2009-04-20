package com.izhi.web.action;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.izhi.platform.webapp.security.captcha.JCaptchaServiceProxyImpl;
import com.izhi.web.model.WebUser;
import com.izhi.web.service.IUserWebService;
import com.opensymphony.xwork2.ActionSupport;

@Service
@Scope(value = "prototype")
@Namespace(value = "/member")
public class RegisterAction extends ActionSupport {

	private static final long serialVersionUID = -3882621714010141888L;
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	@Resource(name = "userClient")
	private IUserWebService service;
	@Resource(name = "captchaService")
	private JCaptchaServiceProxyImpl captchaService;
	@Resource(name = "mailSender")
	private JavaMailSender mailSender;
	private WebUser obj;
	private String code;
	@Resource(name="emailFrom")
	private String from;

	@Action(value="/register")
	public String input(){
		
		return SUCCESS;
	}
	@Action(value = "register")
	public String execute() {
		boolean isResponseCorrect = false;
		String captchaId = this.getSession().getId();
		isResponseCorrect = captchaService
				.validateReponseForId(captchaId, code);

		this.getRequest().setAttribute("codeError", (!isResponseCorrect)+"");

		boolean success = false;
		if (obj == null) {
			log.debug("提交数据位空，请重新提交！！！");
		} else {
			String username = obj.getUsername();
			String password = obj.getPassword();
			String email = obj.getEmail();
			if (username == null || "".equals(username) || password == null
					|| "".equals(password) || email == null || "".equals(email)) {
			} else {
				if (!service.findExist(obj.getUsername())) {
					obj = service.register(obj);
					if (obj.getUserId() != null && obj.getUserId() != 0) {
						success = true;
						sendMail(obj,from);
					}

				} else {
					this.getRequest().setAttribute("userExist", "true");
				}
			}

			this.getRequest().setAttribute("success", success+"");
		}
		return SUCCESS;
	}

	private void sendMail(WebUser u,String from) {
		String url = "http://localhost:8080/lejia/member/validateUser.jhtm?un="
				+ u.getUsername() + "&code=" + u.getValidateCode();
		MimeMessage mailMessage = mailSender.createMimeMessage();
		// 设置utf-8或GBK编码，否则邮件会有乱码
		MimeMessageHelper messageHelper = null;
		try {
			messageHelper = new MimeMessageHelper(mailMessage, true, "utf-8");
			messageHelper.setTo(u.getEmail());
			messageHelper.setFrom(from);
			messageHelper.setText(
					"<html><head></head><body><h1>恭喜你成功注册乐佳购物！</h1><hr><div><a href='"
							+ url + "'>" + url + "</a></div></body></html>",
					true);
			messageHelper.setSubject("恭喜你成功注册乐佳购物！");
				// 这里的方法调用和插入图片是不同的，使用MimeUtility.encodeWord()来解决附件名称的中文问题  
			mailSender.send(mailMessage);
			
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(MailException ex) {
			ex.printStackTrace();
		}
	}

	public WebUser getObj() {
		return obj;
	}

	public void setObj(WebUser obj) {
		this.obj = obj;
	}

	public IUserWebService getService() {
		return service;
	}

	public void setService(IUserWebService service) {
		this.service = service;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	protected HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	protected HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}

	protected HttpSession getSession() {
		return getRequest().getSession();
	}

	protected ServletContext getServletContext() {
		return ServletActionContext.getServletContext();
	}

	public JCaptchaServiceProxyImpl getCaptchaService() {
		return captchaService;
	}

	public void setCaptchaService(JCaptchaServiceProxyImpl captchaService) {
		this.captchaService = captchaService;
	}

	public JavaMailSender getMailSender() {
		return mailSender;
	}

	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

}
