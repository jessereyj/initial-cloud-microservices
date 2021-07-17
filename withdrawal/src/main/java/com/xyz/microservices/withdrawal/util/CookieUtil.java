package com.xyz.microservices.withdrawal.util;

import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.util.WebUtils;

public final class CookieUtil {
	
	private static final Charset UTF_8 = StandardCharsets.UTF_8;
	
	private CookieUtil() {
		 throw new IllegalStateException("Utility class");
	}
	
	public static void clear(HttpServletResponse httpServletResponse, String name)  {
    	String safeCookieName = URLEncoder.encode(name, UTF_8);
		Cookie cookie = new Cookie(safeCookieName, null);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        cookie.setSecure(true);
        httpServletResponse.addCookie(cookie);
    }

    public static String getValue(HttpServletRequest httpServletRequest, String name) {
    	String safeCookieName = URLEncoder.encode(name, UTF_8);
        Cookie cookie = WebUtils.getCookie(httpServletRequest, safeCookieName);
        return cookie != null ? cookie.getValue() : null;
    }
}