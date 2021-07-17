package com.xyz.microservices.withdrawal.constant;

public final class ApiConstant {

	private ApiConstant() {
		throw new IllegalStateException("Constant class");
	}
	
	public static final String AUTHORITIES_KEY = "auth";
	public static final String AUTHORIZATION_HEADER = "Authorization";
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String COMPANY_JWT = "XYZ-JWT";	

	public static final String API_V1="/v1";
	public static final String API_TERMS_OF_SERVICE = "";
	public static final String API_LOGIN = API_V1.concat("/auth");
	public static final String API_LOGOUT = API_V1.concat("/logout");
	public static final String API_ACTUATOR = "/actuator/**";
	public static final String API_ROLE_USER= "ROLE_USER";
	public static final String API_HOME = "/";
	public static final String API_INSTANCES = "/instances";
	public static final String API_NAME = "/withdrawal";
	
	public static final String SWAGGER_V2 = "/v2/api-docs";
	public static final String SWAGGER_RESOURCES = "/swagger-resources/**";
	public static final String SWAGGER_UI = "/swagger-ui.html/**";
	public static final String SWAGGER_UI_PAGE = "/swagger-ui.html";
	public static final String HOME_PAGE = "/index.html";
	public static final String ASSETS = "/assets/**";
	public static final String WEB_JARS = "/webjars/**";
	public static final String STATIC_RESOURCES = "/static/**";
	public static final String DATA_RESOURCES = "/data/**";
	public static final String CSS_RESOURCES = "/**.css";
	public static final String JS_RESOURCES = "/**.js";
	
	public static final String EMPTY = "";
	public static final String COOKIES = "JSESSIONID, Authorization";
	public static final String LOCAL_ENV = "local";
	public static final String DEV_ENV = "dev";
}
