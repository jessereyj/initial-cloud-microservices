package com.xyz.microservices.microgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication(proxyBeanMethods=false)
@EnableDiscoveryClient
@EnableWebFlux
public class MicrogatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicrogatewayApplication.class, args);
	}

}
