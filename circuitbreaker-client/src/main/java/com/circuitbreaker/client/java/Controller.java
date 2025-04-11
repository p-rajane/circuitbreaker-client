package com.circuitbreaker.client.java;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import reactor.core.publisher.Mono;

@RestController
public class Controller {

	@Autowired
	RestTemplate restTemplate;

	@GetMapping("/getNameFromServer")
	public Mono<String> getNameFromServer() {
		ResponseEntity<String> responseEntity = restTemplate.getForEntity("http://localhost:8081/getNameList",
				String.class);
		return Mono.just("Response from server = " + responseEntity.getBody());
	}
}
