package com.chemyoo.test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.PropertiesBeanDefinitionReader;
import org.springframework.core.io.ClassPathResource;

import com.chemyoo.beans.ChemyooBean;


public class readBeanFromProperties {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		BeanDefinitionRegistry reg = new DefaultListableBeanFactory();
		PropertiesBeanDefinitionReader reader = new PropertiesBeanDefinitionReader(reg);
		//int classfound = reader.loadBeanDefinitions(new ClassPathResource("beans.properties"));
		int classfound = reader.loadBeanDefinitions("beans.properties");
		System.err.println(classfound);
		BeanFactory factory = (BeanFactory) reg;
		ChemyooBean chemyoo = (ChemyooBean) factory.getBean("chemyoo");
		System.err.println(chemyoo.getUsername());
	}

}
