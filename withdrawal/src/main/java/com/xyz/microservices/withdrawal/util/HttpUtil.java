package com.xyz.microservices.withdrawal.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import com.xyz.microservices.withdrawal.constant.ApiConstant;

public final class HttpUtil {
	
	private HttpUtil() {
		 throw new IllegalStateException("Utility class");
	}
	
	public static String getClientIp(HttpServletRequest request) {
        String remoteAddr = "";
        if (request != null) {
            remoteAddr = request.getHeader("X-FORWARDED-FOR");
            if (remoteAddr == null || "".equals(remoteAddr)) {
                remoteAddr = request.getRemoteAddr();
            }
        }
        return remoteAddr;
	}
	
	public static String getJWTToken(HttpServletRequest request, String defaultToken) {
		
		String token = CookieUtil.getValue(request, ApiConstant.COMPANY_JWT);
		if(StringUtils.isEmpty(token)) 
			token = getJWTTokenFromHeader(request);
		if(StringUtils.isEmpty(token))
			token = StringUtils.defaultIfEmpty(defaultToken, ApiConstant.EMPTY);
		return token;
	}
	
	public static String getJWTTokenFromHeader(HttpServletRequest request) {
		String token = request.getHeader(ApiConstant.AUTHORIZATION_HEADER);
		if (StringUtils.isNotBlank(token)) {
			token = token.replaceFirst(ApiConstant.TOKEN_PREFIX, "");
		}
		return token;
	}
}
