package com.chemyoo.pub.interfaces.impl;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.InitializingBean;

public class ImplInitializingBean implements InitializingBean {

	private String name ;
	
	private Date date;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		this.name = "chemyoo";
		this.date = Calendar.getInstance().getTime();
	}
	
	public String doSalutation() {
		return name+":"+date;
	}

}
