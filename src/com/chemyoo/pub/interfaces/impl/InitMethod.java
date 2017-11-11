package com.chemyoo.pub.interfaces.impl;

import java.util.Calendar;
import java.util.Date;

import com.chemyoo.pub.interfaces.Hello;

public class InitMethod implements Hello {
	
	private String name ;
	
	private Date date;

	@Override
	public String doSalutation() {
		return name+":"+date;
	}

	public void init()
	{
		this.name = "chemyoo";
		this.date = Calendar.getInstance().getTime();
	}
	
	public void cleanUp()
	{
		this.name = null;
		this.date = null;
	}

}
