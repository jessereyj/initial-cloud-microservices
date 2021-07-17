package com.xyz.microservices.user.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.xyz.microservices.user.constant.ApiConstant;
import com.xyz.microservices.user.constant.GenericMessageConstant;
import com.xyz.microservices.user.model.LoginRequest;
import com.xyz.microservices.user.model.LoginResponse;

@Service
public class LoginService {
	
	private static final Logger log = LoggerFactory.getLogger(LoginService.class);
    
    @Autowired AuthenticationManager authenticationManager;
    
	public ResponseEntity<LoginResponse> ldapAuth(HttpServletRequest request, HttpServletResponse response, LoginRequest loginRequest) {
		
		Authentication authentication = null;
		LoginResponse loginResponse = new LoginResponse();
		
		try{
			 authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(
							loginRequest.getUsername(),
							loginRequest.getPassword(),
							AuthorityUtils.createAuthorityList(ApiConstant.API_ROLE_USER)
							)
					);
		} catch(BadCredentialsException e) {
			log.error(e.getMessage(), e);
			//Recommended: Instead of hard-coding this, use the internalization approach in the messages_en.properties 
			loginResponse.setStatus(GenericMessageConstant.ACCESS_DENIED);
			loginResponse.setMessage("Access Denied");
			return new ResponseEntity<>(loginResponse, HttpStatus.UNAUTHORIZED);
		}

		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		//Recommended: Instead of hard-coding this, use the internalization approach in the messages_en.properties 
		loginResponse.setStatus(GenericMessageConstant.SUCCESS_STATUS);
		loginResponse.setMessage("Hi "+loginRequest.getUsername()+", Welcome to Cloud Certified Microservices Professionals");

		return new ResponseEntity<>(loginResponse, HttpStatus.OK);
	}

}
