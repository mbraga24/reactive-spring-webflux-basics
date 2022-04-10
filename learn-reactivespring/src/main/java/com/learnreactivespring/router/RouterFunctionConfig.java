package com.learnreactivespring.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.learnreactivespring.handler.SampleHandlerFunction;

//**********************************
//        Router Function 
//**********************************

/*
 * The router function will be responsible for mapping the incoming request
 * to the appropriate handler. It checks if the request has appropriate mapping.
 * 
 */

@Configuration
public class RouterFunctionConfig {
	
	@Bean
	public RouterFunction<ServerResponse> route(SampleHandlerFunction handlerFunction) {
		
		return RouterFunctions.route(
				RequestPredicates.GET("/functional/flux").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), handlerFunction::flux)
				.andRoute(RequestPredicates.GET("/functional/mono").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), handlerFunction::mono);
		
	}
	
}

