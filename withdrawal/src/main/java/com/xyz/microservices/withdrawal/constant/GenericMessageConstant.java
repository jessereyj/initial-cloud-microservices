package com.xyz.microservices.withdrawal.constant;

public final class GenericMessageConstant {
	
	private GenericMessageConstant() {
		throw new IllegalStateException("Constant class");
	}
	
	public static final String VALIDATION_ERROR = "VALIDATION ERROR";
	public static final String JWT_EXPIRED = "JWT EXPIRED";
	public static final String ACCESS_DENIED = "ACCESS DENIED";
	public static final String JWT_SIGNATURE_INVALID = "JWT SIGNATURE INVALID";
	public static final String SUCCESS_STATUS = "SUCCESS";
	public static final String LOCKED = "LOCKED";
	public static final String SYSTEM_ERROR = "SYSTEM ERROR";
}
