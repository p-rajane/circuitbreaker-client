package com.circuitbreaker.client.java;

import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreakerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Service
public class CircuitBreaker {
	
	private final ReactiveCircuitBreaker reactiveCircuitBreaker;
	private final WebClient webClient;
	
	public CircuitBreaker(ReactiveCircuitBreakerFactory circuitBreakerFactory) {
		this.reactiveCircuitBreaker = circuitBreakerFactory.create("getName");
		this.webClient = null;
	}
	
	public Mono<String> getNameList() {
		return reactiveCircuitBreaker.run(webClient.get().uri("http://localhost:8081/getName").retrieve().bodyToMono(String.class),
				throwable  -> {
					return Mono.just("Server down, alternative response has been sent by circuit breaker");
				});
	}
}
