package com.tec.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;

@PropertySource("classpath:com/tec/config/application.properties")
public class BaseController {
	
	protected Integer pageSize=null;
	protected Integer pageNumber=null;
	protected String sortIndex=null;
	protected String sortOrder=null;
	
	@Autowired
	protected Environment env;
	protected String getProperty(String key) {
		return env.getProperty(key);
	}

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}
}