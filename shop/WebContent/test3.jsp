
<%@page import="java.io.File"%>
<%@page import="javax.mail.internet.MimeUtility"%>
<%@page import="org.springframework.mail.javamail.MimeMessageHelper"%>
<%@page import="javax.mail.internet.MimeMessage"%>
<%@page import="org.springframework.mail.MailException"%>
<%@page import="org.springframework.mail.SimpleMailMessage"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@page import="org.springframework.mail.javamail.JavaMailSender"%><%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<% 
	WebApplicationContext wac=WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());

	JavaMailSender sender=(JavaMailSender)wac.getBean("mailSender");
	SimpleMailMessage message=(SimpleMailMessage)wac.getBean("templateMessage");
	
	MimeMessage mailMessage = sender.createMimeMessage();  
	//设置utf-8或GBK编码，否则邮件会有乱码  
	 MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage,true,"utf-8");
	
	messageHelper.setTo("admin@hengdong8.cn");
	messageHelper.setText("<html><head></head><body><h1>恭喜你成功注册乐佳购物！</h1><img src='http://www.gzxing.com/hotelpics/zhgc.jpg'><hr></body></html>",true);  
	messageHelper.setSubject("恭喜你成功注册乐佳购物！");
	try{
		messageHelper.addInline(MimeUtility.encodeWord("美女"), new File("C:\\Documents and Settings\\zhuzhsh\\My Documents\\My Pictures\\15800120_20090330150045_0.jpg"));  
		File file=new File("d:/ipmsg.log");    
		// 这里的方法调用和插入图片是不同的，使用MimeUtility.encodeWord()来解决附件名称的中文问题  
		messageHelper.addAttachment(MimeUtility.encodeWord(file.getName()), file);
		sender.send(mailMessage);
	}
	catch(MailException ex) {
		out.println(ex.getMessage());
	}
	
%>