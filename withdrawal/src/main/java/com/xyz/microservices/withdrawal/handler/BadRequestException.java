package com.xyz.microservices.withdrawal.handler;

public class BadRequestException extends Exception {

	private static final long serialVersionUID = 2259323060028534605L;

	public BadRequestException(String msg){
		super(msg);
	}

}
