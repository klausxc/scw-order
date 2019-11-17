package com.atguigu.scw.project.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.atguigu.scw.project.utils.OSSTemplate;

@Configuration
public class OSSTemplateConfig {
	
	@Bean
	@ConfigurationProperties(prefix="oss")
	public OSSTemplate getOSSTemplate() {
		return new OSSTemplate();
	}
	
}
