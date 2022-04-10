package com.learnreactivespring.handler;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

//**********************************
//       Handler Function 
//**********************************

/*
 * The handler function is the one that is going to do the complete process
 * of reading the request, processing the request and sending the response
 * back to the server.
 * 
 * @RestController is normally used in the old way of coding a rest end point. 
 * When coding functional web end points, we use @Component instead. 
 * 
 * The handler function has two new classes:
 * -> ServerRequest: ServerRequest represents the HttpRequest
 * -> ServerResponse: ServerResponse represents the HttpResponse
 * 
 */

@Component
public class SampleHandlerFunction {

	public Mono<ServerResponse> flux(ServerRequest serverRequest) {
		
		return ServerResponse.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(Flux.just(1, 2, 3, 4).log(), Integer.class);
		
	}
	
	public Mono<ServerResponse> mono(ServerRequest serverRequest) {
		
		return ServerResponse.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(Mono.just(1).log(), Integer.class);
	}
	
}
