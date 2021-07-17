package com.xyz.microservices.user.model;

public class LoginResponse extends ApiResponse {

	private String jwt;

	public String getJwt() {
		return jwt;
	}

	public void setJwt(String jwt) {
		this.jwt = jwt;
	}
}
