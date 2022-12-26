package com.gom.mago.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration
public class MessageConfig {
	
    @Bean
    public ResourceBundleMessageSource messageSource() {
    	ResourceBundleMessageSource source = new ResourceBundleMessageSource();
    	/* message properties 위치 설정 */
    	source.setBasenames("messages");
    	/* encoding 룰 설정 */
    	source.setDefaultEncoding("UTF-8");
    	/* 5초간 케싱*/
    	source.setCacheSeconds(5);
    	return source;
    }
}