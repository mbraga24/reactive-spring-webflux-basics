package com.learnreactivespring.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@RunWith(SpringRunner.class)
@WebFluxTest // will scan for all the classes annotated as @Controller and @RestController
public class FluxAndMonoControllerTest {
	
	//	WebFluxTest annotation is resposible for creating the 
	//	instance of WebTestClient.
	@Autowired
	WebTestClient webTestClient; // webTestClient will act as a Subscriber
	
	@Test
	public void flux_approach1() {
		Flux<Integer> integerFlux = webTestClient.get().uri("/flux")
				.accept(MediaType.APPLICATION_JSON_UTF8) // accept the media type. 
				.exchange()                              // call that will invoke the endpoint
				.expectStatus().isOk()                   // expecting the status to be Ok.
				.returnResult(Integer.class)             // expecting the return result to be Integer
				.getResponseBody();                      // get ResponseBody where the actual flux will be
		
		StepVerifier.create(integerFlux)                  // verify the values from Flux
			.expectSubscription()                         // expect the Subscription to be sent to us from the given endpoint
			.expectNext(1)                                // next steps we will check for each value
			.expectNext(2)
			.expectNext(3)
			.expectNext(4)
			.verifyComplete();						       // verify complete event			
	}
	
	@Test
	public void flux_approach2() {
		
		webTestClient.get().uri("/flux")
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.exchange()
				.expectStatus().isOk()
				.expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
				.expectBodyList(Integer.class)
				.hasSize(4);
			
	}
	
}
