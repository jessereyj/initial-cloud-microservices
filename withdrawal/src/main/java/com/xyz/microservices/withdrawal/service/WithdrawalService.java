package com.xyz.microservices.withdrawal.service;

import java.util.Collections;
import java.util.Map;
import java.util.function.Supplier;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xyz.microservices.withdrawal.config.ApiConfig;
import com.xyz.microservices.withdrawal.constant.ApiConstant;
import com.xyz.microservices.withdrawal.constant.GenericMessageConstant;
import com.xyz.microservices.withdrawal.model.ApiResponse;
import com.xyz.microservices.withdrawal.model.CashWithdrawalRequest;
import com.xyz.microservices.withdrawal.util.HttpUtil;

@Service
public class WithdrawalService {

	private static final Logger log = LoggerFactory.getLogger(WithdrawalService.class);

	@Autowired RestTemplate restTemplate;
	@Autowired ApiConfig apiConfig;
    
	private HttpHeaders httpHeaders;
	private CircuitBreakerFactory  cbFactory;
	private CircuitBreaker circuitBreaker;

	public WithdrawalService( CircuitBreakerFactory  cbFactory) {
		this.cbFactory = cbFactory;
		httpHeaders = new HttpHeaders();
		httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		circuitBreaker = this.cbFactory.create("delay");
	}

	public ResponseEntity<ApiResponse> cashWithdrawal(HttpServletRequest request, CashWithdrawalRequest cashWithdrawalRequest) {
		String token = HttpUtil.getJWTToken(request, cashWithdrawalRequest.getJwt()); 
		httpHeaders.set(ApiConstant.AUTHORIZATION_HEADER, ApiConstant.TOKEN_PREFIX.concat(token));
		
		ApiResponse fallback = new ApiResponse("Retry-After","Please wait for a while");
		return circuitBreaker.run(() -> 
		  	  this.submitWithdrawal(apiConfig.getBaseUrl().concat(apiConfig.getCashWithdrawalPath()), new HttpEntity<>(cashWithdrawalRequest, httpHeaders)),
			  throwable -> new ResponseEntity<>(fallback, HttpStatus.SERVICE_UNAVAILABLE));
	}
	
	public Map delay(int seconds) {
		return restTemplate.getForObject("https://httpbin.org/delay/" + seconds, Map.class);
	}

	public Supplier<Map> delaySuppplier(int seconds) {
		return () -> this.delay(seconds);
	}

	public ResponseEntity<ApiResponse> submitWithdrawal(String apiUrl, HttpEntity<?> httpEntity)  {

		ResponseEntity<ApiResponse> response = new ResponseEntity<>(HttpStatus.OK);

		try {

			response = restTemplate.exchange(apiUrl, HttpMethod.POST, httpEntity, ApiResponse.class);

		} catch(HttpClientErrorException|HttpServerErrorException e) {

			log.error("HttpClientErrorException : {} {} {}", e.getStatusCode(), e.getStatusText(), e.getResponseBodyAsString());
			log.error(e.getMessage(), e);

			ApiResponse ApiResponse = new ApiResponse();

			if (!StringUtils.isEmpty(e.getResponseBodyAsString())) {// Validation Error
				ObjectMapper objectMapper = new ObjectMapper();
				try {
					ApiResponse = objectMapper.readValue(e.getResponseBodyAsString(), ApiResponse.class);
				} catch (JsonProcessingException ex) {
					log.error(e.getMessage(), ex);
				}
			} else { // System Error
				ApiResponse.setMessage(StringUtils.defaultIfEmpty(e.getStatusText(),ApiConstant.EMPTY).concat(ApiConstant.EMPTY).concat(StringUtils.defaultIfEmpty(e.getMessage(),ApiConstant.EMPTY)));
				ApiResponse.setStatus(GenericMessageConstant.SYSTEM_ERROR);
			}
			response = new ResponseEntity<>(ApiResponse, e.getStatusCode());
		}

		return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
	}

}
