package com.xyz.microservices.user.handler;

import org.springframework.security.core.AuthenticationException;

public class LoginException extends AuthenticationException {

	private static final long serialVersionUID = 2259323060028534605L;

	public LoginException(String msg){
		super(msg);
	}
}
