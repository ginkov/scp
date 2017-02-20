package com.jiayun.scp.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jboss.logging.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

@Service
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler{
	
	private static final Logger log = Logger.getLogger(LoginSuccessHandler.class);
	
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
            HttpServletResponse response, Authentication authentication)
            throws ServletException, IOException {
    	
        User user = (User) authentication.getPrincipal();
        log.info("User "+user.getUsername()+" logged in.");
        response.sendRedirect("index");
    }
}
