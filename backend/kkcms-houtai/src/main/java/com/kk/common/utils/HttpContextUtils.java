package com.kk.common.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

public class HttpContextUtils {

	public static HttpServletRequest getHttpServletRequest() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	}

	public static String getDomain(){
		HttpServletRequest request = getHttpServletRequest();
		StringBuffer url = request.getRequestURL();
		return url.delete(url.length() - request.getRequestURI().length(), url.length()).toString();
	}

	public static String getOrigin(){
		HttpServletRequest request = getHttpServletRequest();
		return request.getHeader("Origin");
	}

	public static String getUser_name() throws UnsupportedEncodingException {
		HttpServletRequest httpServletRequest = getHttpServletRequest();
		String header = httpServletRequest.getHeader("Authorization");
		String token = StringUtils.substringAfter(header, "bearer ");
		Claims claims = Jwts.parser().setSigningKey("hutwanghui".getBytes("UTF-8")).parseClaimsJws(token).getBody();
		return (String) claims.get("user_name");

	}
}
