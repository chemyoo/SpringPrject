package com.chemyoo.test;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.PropertiesBeanDefinitionReader;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.UrlResource;

import com.chemyoo.beans.ChemyooBean;
import com.chemyoo.beans.HelloBean;

public class Test {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public static void main(String[] args) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		
			ClassPathResource resource = new ClassPathResource("applicationContext.xml");
			BeanFactory factory = new XmlBeanFactory(resource);
			
			HelloBean hello=(HelloBean) factory.getBean("helloBean");
			//hello.setHelloworld("fdashjfak");
			System.out.println(hello.getHelloworld());
			
			ListableBeanFactory lisfactory= new XmlBeanFactory(resource);
			Map<String, HelloBean> hellobeans = lisfactory.getBeansOfType(HelloBean.class,false,false );
			System.out.println(hellobeans.get("helloBean").getHelloworld());
			
			BeanDefinitionRegistry registry = new DefaultListableBeanFactory();
			XmlBeanDefinitionReader read = new XmlBeanDefinitionReader(registry);
			read.loadBeanDefinitions("applicationContext.xml");
			BeanFactory bf = (BeanFactory) registry;
			System.out.println(((HelloBean)bf.getBean("helloBean")).getHelloworld());
			
			ResourceLoader loader = new DefaultResourceLoader();  
			Resource resource1 = loader.getResource("http://www.google.com.hk");  
			System.out.println(resource1 instanceof UrlResource); //true  
			//注意这里前缀不能使用“classpath*:”，这样不能真正访问到对应的资源，exists()返回false  
			resource1 = loader.getResource("classpath:test.txt");  
			System.out.println(resource1 instanceof ClassPathResource); //true  
			resource1 = loader.getResource("test.txt");  
			System.out.println(resource1 instanceof ClassPathResource); //true  
			resource1 = loader.getResource("applicationContext.xml");
			DefaultListableBeanFactory factory1 = new DefaultListableBeanFactory();  
			XmlBeanDefinitionReader  reader = new XmlBeanDefinitionReader(factory1);  
		    int classfound =reader.loadBeanDefinitions(resource1);  
		    if(classfound==1)
		    {
		    	hello = (HelloBean) factory1.getBean("helloBean");
		    	System.out.println(hello.getHelloworld());
		    }

	}

}
