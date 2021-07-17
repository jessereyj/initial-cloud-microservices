package com.xyz.microservices.deposit.api;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.xyz.microservices.deposit.constant.ApiConstant;
import com.xyz.microservices.deposit.model.ApiResponse;
import com.xyz.microservices.deposit.model.CashDepositRequest;
import com.xyz.microservices.deposit.service.DepositService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

@RestController
@RequestMapping(ApiConstant.API_V1)
public class DepositApi {
	
	@Autowired DepositService service;
	
	private CircuitBreakerFactory circuitBreakerFactory;
	
	public DepositApi(CircuitBreakerFactory circuitBreakerFactory) {
		this.circuitBreakerFactory = circuitBreakerFactory;
	}
	
	@ApiOperation(value = "Cash Deposit Submission API", authorizations = {@Authorization(value="apiKey") })
	@PostMapping(value = "/cash", consumes={MediaType.APPLICATION_JSON_VALUE}, produces={MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<ApiResponse> cashDeposit(HttpServletRequest request, @RequestBody @Valid CashDepositRequest cashDepositRequest)  throws JsonProcessingException {
		return service.cashDeposit(request, cashDepositRequest);
	}
	
	@GetMapping("/delay/{seconds}")
	public Map delay(@PathVariable int seconds) {
		return circuitBreakerFactory.create("delay").run(service.delaySuppplier(seconds), t -> {
			Map<String, String> fallback = new HashMap<>();
			fallback.put("hello", "world");
			return fallback;
		});
	}

}
