package com.xyz.microservices.withdrawal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(proxyBeanMethods=false)
@EnableDiscoveryClient
public class WithdrawalApplication {

	public static void main(String[] args) {
		SpringApplication.run(WithdrawalApplication.class, args);
	}
}
