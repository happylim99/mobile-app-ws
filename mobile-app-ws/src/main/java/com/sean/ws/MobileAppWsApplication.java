package com.sean.ws;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.sean.ws.security.AppProperties;
import com.sean.ws.ui.controller.UserController;

@SpringBootApplication // same as @Configuration @EnableAutoConfiguration @ComponentScan
//@EnableTransactionManagement
public class MobileAppWsApplication extends SpringBootServletInitializer implements CommandLineRunner{
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private UserController userController;
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(MobileAppWsApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(MobileAppWsApplication.class, args);
		//String str = "hello manually";
		//System.out.println(reverseHello(str));
	}
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SpringApplicationContext springApplicationContext()
	{
		return new SpringApplicationContext();
	}
	/*
	@Bean 
	public EntityManager entityManager()
	{
		return new EntityManager();
	}
	*/
	/*
	//@Bean(name="AppProperties")
	@Bean
	public AppProperties getAppProperties()
	{
		return new AppProperties();
	}
	*/
	/*
	public static String reverseHello(String str)
	{
		StringBuilder hello = new StringBuilder(str);
		hello.reverse();
		return hello.toString();
	}
	
	public static String manualReverseHello(String str)
	{
		String reverseStr = "";
		for(int i = str.length()-1; i >=0; i--)
		{
			reverseStr = reverseStr + str.charAt(i);
		}
		return reverseStr;
	}
	*/

	@Override
	public void run(String... args) throws Exception {
		//logger.info(String.valueOf(userController.getHqlUsers()));
		//logger.info(userController.hello());
		
	}
}
