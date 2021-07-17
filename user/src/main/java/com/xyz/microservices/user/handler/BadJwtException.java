package com.xyz.microservices.user.handler;

public class BadJwtException extends Exception {
	
	private static final long serialVersionUID = -6928468434281536043L;

	public BadJwtException(String msg){
		super(msg);
	}
	
	public BadJwtException(String msg, Throwable t){
		super(msg, t);
	}
}
