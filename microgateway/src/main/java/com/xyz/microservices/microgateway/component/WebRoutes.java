package com.xyz.microservices.microgateway.component;

import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;

@Component
public class WebRoutes {//Optional

	@Bean
	RouterFunction<?> iconResources() {
		return RouterFunctions
				.resources("/favicon.**", new ClassPathResource("images/favicon.ico"));//Optional
	}
	
	@Bean
	RouterFunction<?> cssResources() {
		return RouterFunctions
				.resources("/css/**", new ClassPathResource("/static/css/"));//Optional
	}
	
	@Bean
	RouterFunction<?> jsResources() {
		return RouterFunctions
				.resources("/js/**", new ClassPathResource("/static/js/"));//Optional
	}
}
