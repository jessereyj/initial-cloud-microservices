package com.xyz.microservices.user.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xyz.microservices.user.constant.ApiConstant;
import com.xyz.microservices.user.model.LoginRequest;
import com.xyz.microservices.user.model.LoginResponse;
import com.xyz.microservices.user.service.LoginService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(ApiConstant.API_V1)
public class LoginApi {

	@Autowired LoginService loginService;
	
	@ApiOperation(value = "User LDAP Auth API")
	@PostMapping(value = "/auth", 
	consumes= {MediaType.APPLICATION_JSON_VALUE}, 
	produces= {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<LoginResponse> auth(HttpServletRequest request, HttpServletResponse response, @RequestBody @Valid LoginRequest loginRequest)  {
		return loginService.ldapAuth(request, response, loginRequest);
	}
	
	@ApiOperation(value = "User LDAP Logout API")
	@PostMapping(value = "/logout", 
	consumes= {MediaType.APPLICATION_JSON_VALUE}, 
	produces= {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<Object> logout()  {
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}
}
