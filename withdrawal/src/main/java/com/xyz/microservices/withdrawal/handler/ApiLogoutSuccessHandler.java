package com.xyz.microservices.withdrawal.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import com.xyz.microservices.withdrawal.constant.ApiConstant;
import com.xyz.microservices.withdrawal.util.CookieUtil;

public class ApiLogoutSuccessHandler implements LogoutSuccessHandler {

	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		
		CookieUtil.clear(response, ApiConstant.COMPANY_JWT);
		SecurityContextHolder.clearContext();
		//perform other required operation		
		response.setStatus(HttpStatus.OK.value());
		response.getWriter().flush();

	}

}
