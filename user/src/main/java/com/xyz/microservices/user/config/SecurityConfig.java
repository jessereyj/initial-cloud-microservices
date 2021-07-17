package com.xyz.microservices.user.config;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.ldap.userdetails.LdapAuthoritiesPopulator;

import com.xyz.microservices.user.constant.ApiConstant;
import com.xyz.microservices.user.handler.ApiLogoutSuccessHandler;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, proxyTargetClass = true)
@Configuration(proxyBeanMethods = false)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Value("${ldap.user-dn-patterns}")
	private String userDnPatterns;
	@Value("${ldap.group-search-base}")
	private String groupSearchBase;
	@Value("${ldap.server-url}")
	private String ldapServerUrl;
	@Value("${ldap.password-attribute}")
	private String passwordAttribute;

	public SecurityConfig(){
		super();
		SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/favicon.ico",ApiConstant.SWAGGER_V2,
				ApiConstant.SWAGGER_RESOURCES, 
				ApiConstant.WEB_JARS,
				ApiConstant.ASSETS,
				ApiConstant.STATIC_RESOURCES,
				ApiConstant.DATA_RESOURCES,
				ApiConstant.CSS_RESOURCES,
				ApiConstant.JS_RESOURCES);
	}


	@Override
	protected void configure(HttpSecurity http) throws Exception {

		//security off
		//http.authorizeRequests().anyRequest().permitAll().and().csrf().disable();

		http
		.headers().frameOptions().deny()
		.and()
		.csrf().disable()
		.cors()
		.and()
		.authorizeRequests()
		.antMatchers(ApiConstant.API_HOME,ApiConstant.API_NAME,ApiConstant.SWAGGER_UI).permitAll()
		.antMatchers(ApiConstant.API_LOGIN,ApiConstant.API_LOGOUT,ApiConstant.API_ACTUATOR,ApiConstant.API_INSTANCES).permitAll()
		.anyRequest().authenticated()
		.and()
		.logout().deleteCookies(ApiConstant.COMPANY_JWT)
		.logoutUrl(ApiConstant.API_LOGOUT).permitAll().logoutSuccessHandler(new ApiLogoutSuccessHandler())
		.and()
		// 	this disables session creation on Spring Security
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
		.ldapAuthentication()
		//.userDetailsContextMapper(userDetailsContextMapper())
		.ldapAuthoritiesPopulator(ldapAuthoritiesPopulator())
		.userDnPatterns(userDnPatterns)
		.groupSearchBase(groupSearchBase)
		.contextSource()
		.url(ldapServerUrl)
		.and()
		.passwordCompare()
		.passwordEncoder(new BCryptPasswordEncoder())
		.passwordAttribute(passwordAttribute);
	}
	
	public LdapAuthoritiesPopulator ldapAuthoritiesPopulator() {
		return new LdapAuthoritiesPopulator() {
			@Override
			public Collection<? extends GrantedAuthority> getGrantedAuthorities(DirContextOperations userData, 
					String username) {
				return Arrays.asList(new SimpleGrantedAuthority(ApiConstant.API_ROLE_USER));
			}
		};
	}
}