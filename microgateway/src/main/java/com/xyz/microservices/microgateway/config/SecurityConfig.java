package com.xyz.microservices.microgateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.header.ReferrerPolicyServerHttpHeadersWriter.ReferrerPolicy;
import org.springframework.security.web.server.header.XFrameOptionsServerHttpHeadersWriter.Mode;

@EnableWebFluxSecurity
@Configuration(proxyBeanMethods = false)
public class SecurityConfig {

	@Bean
	public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http,
			ServerCodecConfigurer serverCodecConfigurer) {

		return http.headers(headers -> {
		            headers
		                .frameOptions().mode(Mode.DENY)
		                .referrerPolicy(referrerPolicy -> referrerPolicy.policy(ReferrerPolicy.SAME_ORIGIN)
		                );
		     		}
		           )
				.csrf().disable()
				.cors()
				.and()
				.authorizeExchange()
				.pathMatchers("/","/favicon.ico","/images/**","/css/**","/js/**","/webjars/**").permitAll()
				.pathMatchers("/v1/**","/v2/**","/actuator/**","/instances").permitAll()
				.pathMatchers("/user/**","/mock-server/**","/deposit/**","/withdrawal/**").permitAll()
				.anyExchange().authenticated()
				.and()
				.build();
	}
}