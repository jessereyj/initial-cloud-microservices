package com.xyz.microservices.user.model;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.xyz.microservices.user.util.GeneratorUtil;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class ApiResponse {
	
	private Date timestamp = new Date();
	private String status;
	private String message;
	private List<String> errors;
	private String error;
	private String path;
	private String refId = GeneratorUtil.getUUID();
	
	public ApiResponse(){}
	public ApiResponse(String status, String message) {
		super();
		this.status = status;
		this.message = message;
	}
	public ApiResponse(String status, String message, String path) {
		super();
		this.status = status;
		this.message = message;
		this.path = path;
	}
	
	public ApiResponse(String status, List<String> errors) {
		super();
		this.status = status;
		this.errors = errors;
	}
	
	public ApiResponse(String status, String message, List<String> errors) {
		super();
		this.status = status;
		this.message = message;
		this.errors = errors;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<String> getErrors() {
		return errors;
	}
	public void setErrors(List<String> errors) {
		this.errors = errors;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getRefId() {
		return refId;
	}
	public void setRefId(String refId) {
		this.refId = refId;
	}
	
}
