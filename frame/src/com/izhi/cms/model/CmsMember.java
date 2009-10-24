package com.izhi.cms.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.izhi.platform.model.User;
@Entity
@DiscriminatorValue("member")
public class CmsMember extends User{

	private static final long serialVersionUID = 1L;
	
	

}
