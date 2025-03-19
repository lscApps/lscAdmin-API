package com.api.lscAdmim.configs;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.api.lscAdmim.filters.CorsFilter;
import com.api.lscAdmim.filters.JwtValidationFilter;

import jakarta.servlet.Filter;

@Configuration
public class WebConfigs {

	@Bean
    public FilterRegistrationBean<Filter> customCorsFilter() {
        FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new CorsFilter());
        registrationBean.addUrlPatterns("/lscAdmin/api/*");
        registrationBean.setOrder(1);
        return registrationBean;
    }
	
	@Bean
    public FilterRegistrationBean<Filter> jwtFilter() {
        FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new JwtValidationFilter());
        registrationBean.addUrlPatterns("/lscAdmin/api/*");
        registrationBean.setOrder(2);
        return registrationBean;
    }

}
