package com.api.lscAdmim.filters;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import com.api.lscAdmim.configs.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtValidationFilter extends GenericFilterBean {

    private static final String LOGIN_URI = "/lscAdmin/api/user/login";
    private static final String BEARER = "Bearer ";

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        
        
        if (httpRequest.getRequestURI().equals(LOGIN_URI)){
            chain.doFilter(request, response);
            return;
        }
        
        String authHeader = httpRequest.getHeader("Authorization");
        System.out.printf("authHeader: %s\n", authHeader);
        if(authHeader == null || !authHeader.startsWith(BEARER)){
        	httpRequest.getHeaderNames();
        	
            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing or invalid Authorization header");
            return;
        }

        String cleanToken = authHeader.substring(BEARER.length());
        String username = jwtUtil.validateToken(cleanToken);

        if(username == null){
            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
        }else {
        	httpResponse.setStatus(HttpServletResponse.SC_OK);
        }
        	
        chain.doFilter(request, response);

    }
}
