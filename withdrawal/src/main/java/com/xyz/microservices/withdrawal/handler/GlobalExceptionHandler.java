package com.xyz.microservices.withdrawal.handler;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.xyz.microservices.withdrawal.constant.GenericMessageConstant;
import com.xyz.microservices.withdrawal.model.ApiResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(value = {AuthenticationException.class})
	public ResponseEntity<Object> handleAuthenticationException(AuthenticationException ex,HttpServletRequest request) {
		ApiResponse errorDetails = new ApiResponse(HttpStatus.UNAUTHORIZED.getReasonPhrase(),ex.getMessage(), request.getServletPath());
		return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<Object> handleBadRequestException(BadRequestException ex, HttpServletRequest request){
		ApiResponse errorDetails = new ApiResponse(HttpStatus.BAD_REQUEST.getReasonPhrase(),ex.getMessage(), request.getServletPath());
		return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(BadJwtException.class)
	public ResponseEntity<Object> handleBadJwtException(BadJwtException ex, HttpServletRequest request){
		ApiResponse errorDetails = new ApiResponse(GenericMessageConstant.JWT_EXPIRED, ex.getMessage(), request.getServletPath());
		return new ResponseEntity<>(errorDetails, HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex, HttpServletRequest request){
		ApiResponse errorDetails = new ApiResponse(GenericMessageConstant.ACCESS_DENIED,ex.getMessage(), request.getServletPath());
		return new ResponseEntity<>(errorDetails, HttpStatus.FORBIDDEN);
	}
}
