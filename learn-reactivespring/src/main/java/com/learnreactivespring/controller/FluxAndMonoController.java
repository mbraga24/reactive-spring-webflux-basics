package com.learnreactivespring.controller;

import java.time.Duration;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;

@RestController
public class FluxAndMonoController {
	
	@GetMapping("/flux")
	public Flux<Integer> returnFlux() {
		return Flux.just(1,2,3,4)
				.delayElements(Duration.ofSeconds(1))
				.log();
	}
	
	/*
	 * returnFlux
	 * The return type value of this endpoint was specified as APPLICATION_STREAM_JSON_VALUE.
	 * This specification will inform the client (browser) that the returned value will be 
	 * a Stream value. 
	 * 
	 * The result data is displayed as a Stream. In another words, the data is displayed as 
	 * it is received from the Publiser (data producer). 
	 * 
	 */
	@GetMapping(value = "/fluxstream", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
	public Flux<String> returnFluxStream() {
		
		return Flux.just("STAY ", "STRONG ", "AND ", "LEARN ", "WEBFLUX", ". ", " PEOPLE ", "ARE ", "COUNTING ", "ON ", "YOU ", "!")
				.delayElements(Duration.ofSeconds(1))
				.log();
	}

}
