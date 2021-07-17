package com.xyz.microservices.withdrawal.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods=false)
public class ApiConfig {

	@Value("${xyz.api.base-url}")
	private String baseUrl;
	
	@Value("${xyz.api.post.cash-withdrawal-path}")
	private String cashWithdrawalPath;
	
	@Value("${xyz.api.post.check-withdrawal-path}")
	private String checkWithdrawalPath;

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public String getCashWithdrawalPath() {
		return cashWithdrawalPath;
	}

	public void setCashWithdrawalPath(String cashDepositPath) {
		this.cashWithdrawalPath = cashDepositPath;
	}

	public String getCheckWithdrawalPath() {
		return checkWithdrawalPath;
	}

	public void setCheckWithdrawalPath(String checkWithdrawalPath) {
		this.checkWithdrawalPath = checkWithdrawalPath;
	}
	
	
}
