package com.api.lscAdmim.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class WebConfigs {

	@Value("${lscAdmin.allowed.origins}")
	private String allowedOrigins;

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		
		log.info(String.format("AllowedOrigins = %s", allowedOrigins));
		
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins(allowedOrigins).allowedMethods("GET", "POST", "PUT", "DELETE");
			}
		};
	}

}
