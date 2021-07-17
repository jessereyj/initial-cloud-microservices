package com.xyz.microservices.withdrawal.api;

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
import com.xyz.microservices.withdrawal.constant.ApiConstant;
import com.xyz.microservices.withdrawal.model.ApiResponse;
import com.xyz.microservices.withdrawal.model.CashWithdrawalRequest;
import com.xyz.microservices.withdrawal.service.WithdrawalService;

@RestController
@RequestMapping(ApiConstant.API_V1)
public class WithdrawalApi {
	
	@Autowired WithdrawalService service;
	
	private CircuitBreakerFactory circuitBreakerFactory;
	
	public WithdrawalApi(CircuitBreakerFactory circuitBreakerFactory) {
		this.circuitBreakerFactory = circuitBreakerFactory;
	}
	
	@PostMapping(value = "/cash", consumes={MediaType.APPLICATION_JSON_VALUE}, produces={MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<ApiResponse> cashWithdrawal(HttpServletRequest request, @RequestBody @Valid CashWithdrawalRequest cashWithdrawalRequest)  throws JsonProcessingException {
		return service.cashWithdrawal(request, cashWithdrawalRequest);
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
