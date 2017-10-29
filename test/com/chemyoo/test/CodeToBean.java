package com.chemyoo.test;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.RootBeanDefinition;

import com.chemyoo.beans.ChemyooBean;

public class CodeToBean {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		MutablePropertyValues properties = new MutablePropertyValues();
		properties.add("username", "simple is simple");
		
//		@SuppressWarnings("deprecation")
		/* @deprecation 不推荐的，不赞成的 */
//		RootBeanDefinition definition = new RootBeanDefinition(ChemyooBean.class,properties);
		
		//RootBeanDefinition definition = new RootBeanDefinition(ChemyooBean.class);
		//definition.setPropertyValues(properties);
		
		RootBeanDefinition definition = new RootBeanDefinition();
		definition.setBeanClass(ChemyooBean.class);
		definition.setPropertyValues(properties);
		
		BeanDefinitionRegistry reg = new DefaultListableBeanFactory();
		reg.registerBeanDefinition("simple",definition);
		BeanFactory factory = (BeanFactory)reg;
		ChemyooBean hello = (ChemyooBean)factory.getBean("simple");
		System.out.println(hello.getUsername());
		
	}

}
