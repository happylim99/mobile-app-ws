package com.sean.ws.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
//@Profile("dev")
// any version of java
/*
@PropertySources({
	//@PropertySource("classpath:application-dev.properties"),
	//@PropertySource("classpath:application-test.properties"),
	@PropertySource(value="${external.config.path}", ignoreResourceNotFound=false)
})
*/
@PropertySource(value="${external.config.file}", ignoreResourceNotFound=false)

//@EnableTransactionManagement
//@Import({ EmailConfig.class })
public class AppConfig {
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

}
