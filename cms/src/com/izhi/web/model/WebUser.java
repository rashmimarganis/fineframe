package com.izhi.web.model;

import java.io.Serializable;

public class WebUser implements Serializable{

	private static final long serialVersionUID = -3718744705353992856L;
	private Integer userId;
	
	private String username;
	private String password;
	private String email;
	private String realname;
	private int age;
	private String gender;
	private String address;
	private String postcode;
	
	private String hintAnswer;
	private String hintQuestion;
	
	private String validateCode;
	
	private boolean validated;
	
	public WebUser(){
		
	}
	public  WebUser(String username,String password,String email,String realname,int age,String gender,String address,String postcode){
		
		this.username=username;
		this.password=password;
		this.email=email;
		this.realname=realname;
		this.age=age;
		this.gender=gender;
		this.address=address;
		this.postcode=postcode;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPostcode() {
		return postcode;
	}
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	public String getHintAnswer() {
		return hintAnswer;
	}
	public void setHintAnswer(String hintAnswer) {
		this.hintAnswer = hintAnswer;
	}
	public String getHintQuestion() {
		return hintQuestion;
	}
	public void setHintQuestion(String hintQuestion) {
		this.hintQuestion = hintQuestion;
	}
	public String getValidateCode() {
		return validateCode;
	}
	public void setValidateCode(String validateCode) {
		this.validateCode = validateCode;
	}
	public boolean isValidated() {
		return validated;
	}
	public void setValidated(boolean validated) {
		this.validated = validated;
	}
	
}
