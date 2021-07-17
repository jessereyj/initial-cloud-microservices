package com.xyz.microservices.deposit.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods=false)
public class ApiConfig {

	@Value("${xyz.api.base-url}")
	private String baseUrl;
	
	@Value("${xyz.api.post.cash-deposit-path}")
	private String cashDepositPath;
	
	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public String getCashDepositPath() {
		return cashDepositPath;
	}

	public void setCashDepositPath(String cashDepositPath) {
		this.cashDepositPath = cashDepositPath;
	}

}
