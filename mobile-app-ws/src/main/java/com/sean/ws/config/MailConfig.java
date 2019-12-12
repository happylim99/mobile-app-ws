package com.sean.ws.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
@PropertySource("classpath:mail.properties")
public class MailConfig {
	@Value("${mail.smtp.auth}")
	private boolean auth;
	
	@Value("${mail.smtp.starttls.enable}")
	private boolean tlsEnable;
	
	@Value("${mail.smtp.host}")
	private String host;
	
	@Value("${mail.smtp.port}")
	private int port;
	
	@Value("${mail.smtp.ssl.trust}")
	private String sslTrust;
	
	@Value("${mail.username}")
	private String username;
	
	@Value("${mail.password}")
	private String password;
	
	@Bean
	public JavaMailSender getJavaMailSender() {
		
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		Properties props = new Properties();
		
		props.put("mail.smtp.auth", auth);
		props.put("mail.smtp.starttls.enable", tlsEnable);
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", port);
		props.put("mail.smtp.ssl.trust", sslTrust);
		
		mailSender.setJavaMailProperties(props);
		mailSender.setUsername(username);
		mailSender.setPassword(password);
		
		return mailSender;
	}
}
