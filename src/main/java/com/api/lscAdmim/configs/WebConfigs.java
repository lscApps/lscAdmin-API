package com.api.lscAdmim.configs;

import com.api.lscAdmim.filters.CorsFilter;
import com.api.lscAdmim.filters.JwtValidationFilter;
import jakarta.servlet.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfigs {

    @Autowired
    private JwtValidationFilter jwtValidationFilter;

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
        registrationBean.setFilter(jwtValidationFilter);
        registrationBean.addUrlPatterns("/lscAdmin/api/*");
        registrationBean.setOrder(2);
        return registrationBean;
    }



}
