package com.weasel.springboot;

import javax.servlet.Filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CharacterEncodingFilter;


@Configuration
public class FilterConfig {
	
	@Bean
	public FilterRegistrationBean getFilterRegistrationBean() {
		FilterRegistrationBean registration = new FilterRegistrationBean();
		Filter filter = new CharacterEncodingFilter("UTF-8");
		registration.setFilter(filter);
		registration.addUrlPatterns("/*");
		
		return registration;
	}
}
