package com.sean.ws;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.sean.ws.security.AppProperties;

@SpringBootApplication
public class MobileAppWsApplication extends SpringBootServletInitializer{
	
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
}
