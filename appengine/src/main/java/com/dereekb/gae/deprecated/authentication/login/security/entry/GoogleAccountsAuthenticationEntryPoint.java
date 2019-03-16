package com.thevisitcompany.gae.deprecated.authentication.login.security.entry;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class GoogleAccountsAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {
    	
    		Logger.getAnonymousLogger().info("Google Account Entry Point.");
    	
        UserService userService = UserServiceFactory.getUserService();
        
        String requestURI = request.getRequestURI();
        String loginUrl = userService.createLoginURL(requestURI);
        
        response.sendRedirect(loginUrl);
    }
}
