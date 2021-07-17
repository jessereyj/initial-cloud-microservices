package com.xyz.microservices.user.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class ApiConfig {

	@Value("${other-company.api.base-url}")
	private String baseUrl;

	@Value("${other-company.api.get.retrieve-user-path}")
	private String retrieveUserPath;

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public String getRetrieveUserPath() {
		return retrieveUserPath;
	}

	public void setRetrieveUserPath(String retrieveUserPath) {
		this.retrieveUserPath = retrieveUserPath;
	}
}
