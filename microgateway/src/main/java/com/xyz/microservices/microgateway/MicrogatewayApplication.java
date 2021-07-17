package com.xyz.microservices.microgateway;

import java.util.ArrayList;
import java.util.List;

import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.SwaggerUiConfigParameters;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication(proxyBeanMethods=false)
@EnableDiscoveryClient
@EnableWebFlux
public class MicrogatewayApplication {

	@Value("${info.app.name}")
	private String infoAppName;
	@Value("${info.app.version}")
	private String infoAppVersion;
	
	@Bean
	@Lazy(false)
	public List<GroupedOpenApi> apis(SwaggerUiConfigParameters swaggerUiConfigParameters, RouteDefinitionLocator locator) {
		List<GroupedOpenApi> groups = new ArrayList<>();
		List<RouteDefinition> definitions = locator.getRouteDefinitions().collectList().block();
		if(definitions != null) {
			definitions.stream().filter(routeDefinition -> routeDefinition.getId().matches(".*")).filter(route -> !"openapi".equalsIgnoreCase(route.getId())).forEach(routeDefinition -> {
				String name = routeDefinition.getId();
				swaggerUiConfigParameters.addGroup(name);
				GroupedOpenApi.builder().pathsToMatch("/" + name + "/**").group(name).build();
			});
		}
		return groups;
	}
	
	public static void main(String[] args) {
		SpringApplication.run(MicrogatewayApplication.class, args);
	}

}
