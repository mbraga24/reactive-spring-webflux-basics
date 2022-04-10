package com.learnreactivespring.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
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
	
	@Test
	public void flux_approach3() {
		
		List<Integer> expectedIntegerList = Arrays.asList(1,2,3,4);
		
		EntityExchangeResult<List<Integer>> entityExchangeResult = webTestClient.get().uri("/flux")
		.accept(MediaType.APPLICATION_JSON_UTF8)
		.exchange()
		.expectStatus().isOk()
		.expectBodyList(Integer.class) // this call will convert Flux to a list
		.returnResult();
		
		// entityExchangeResult instance has the value and we're getting the 
		// response body from it, which will have the Flux value as a list
		
		// expectedIntegerList will hace the value we expect to receive
		
		assertEquals(expectedIntegerList, entityExchangeResult.getResponseBody());
	}
	
	@Test 
	public void flux_approach4() {
		
		List<Integer> expectedIntegerList = Arrays.asList(1,2,3,4);
		
		webTestClient.get().uri("/flux")
		.accept(MediaType.APPLICATION_JSON_UTF8)
		.exchange()
		.expectStatus().isOk()
		.expectBodyList(Integer.class)
		.consumeWith(response -> {
			assertEquals(expectedIntegerList, response.getResponseBody());
		});
	}
	
	@Test
	public void fluxStream() {

		// !*need to change from APPLICATION_JSON_UTF8 to APPLICATION_STREAM_JSON 
		// else error 406 NOT_ACCEPTABLE will be thrown*!
		
		Flux<Long> longStreamFlux = webTestClient.get().uri("/fluxstream")
				.accept(MediaType.APPLICATION_STREAM_JSON) 
				.exchange()
				.expectStatus().isOk()
				.returnResult(Long.class)
				.getResponseBody();
		
		
		StepVerifier.create(longStreamFlux)
			.expectNext(0l)
			.expectNext(1l)
			.expectNext(2l)
			.thenCancel()
			.verify();
	}
	
	@Test
	public void mono( ) {
		
		Integer expectedValue = Integer.valueOf(1);
		
		webTestClient.get().uri("/mono")
			.accept(MediaType.APPLICATION_JSON_UTF8)
			.exchange()
			.expectStatus().isOk()
			.expectBody(Integer.class)
			.consumeWith(response -> {
				assertEquals(expectedValue, response.getResponseBody());
			});	
	}
	
}
