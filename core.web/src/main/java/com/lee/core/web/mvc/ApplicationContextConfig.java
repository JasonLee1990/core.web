package com.lee.core.web.mvc;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Configuration
@MapperScan(basePackages = { "domain" }, annotationClass = Repository.class)
@ComponentScan(basePackages = { "domain" }, useDefaultFilters = false, includeFilters = { @ComponentScan.Filter(value = Service.class, type = FilterType.ANNOTATION), @ComponentScan.Filter(value = Repository.class, type = FilterType.ANNOTATION) })
@ImportResource({ 
	"applicationContext.xml", 
	"permanent.xml"
	})
	public class ApplicationContextConfig {
		
	}