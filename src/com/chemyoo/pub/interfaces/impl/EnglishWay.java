package com.chemyoo.pub.interfaces.impl;

import java.util.Date;

import com.chemyoo.pub.interfaces.Hello;

public class EnglishWay  implements Hello{

	private String helloMessage;
	
	private Date date;
	
	public EnglishWay(String helloMessage ,Date date)
	{
		this.helloMessage = helloMessage;
		this.date = date;
	}
	
	@Override
	public String doSalutation() {
		return "Hello , Chemyoo" + this.helloMessage;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
