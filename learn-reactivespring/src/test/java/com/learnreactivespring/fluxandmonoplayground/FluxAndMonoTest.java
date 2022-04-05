package com.learnreactivespring.fluxandmonoplayground;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class FluxAndMonoTest {
	
	/*
	 *  
	 * public final Disposable subscribe(@Nullable
     *                         Consumer<? super T> consumer,
     *                         @Nullable
     *                         Consumer<? super Throwable> errorConsumer,
     *                         @Nullable
     *                         Runnable completeConsumer)
	 * 
	 * Parameters:
     *  consumer - the consumer to invoke on each value
     *  errorConsumer - the consumer to invoke on error signal
     *  completeConsumer - the consumer to invoke on complete signal
     * Returns:
     *  a new Disposable that can be used to cancel the underlying Subscription
	 * 
	 * https://projectreactor.io/docs/core/release/api/reactor/core/publisher/Flux.html#subscribe--
	 * 
	 */
	
	@Test
	public void fluxTest() {
		
		Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Reactive Spring")
//				.concatWith(Flux.error(new RuntimeException("An Exception Occurred in fluxTest")))
				.concatWith(Flux.just("After Error"))
				.log(); // will log all events happening behind the scenes
		
		stringFlux.subscribe(System.out::println, 
				(e) -> System.err.println("The exception is: " + e),
				() -> System.out.println("==> Completed(v)"));
		
	}
	
	@Test
	public void fluxTestElements_WithoutError() {
		Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Reactive Spring")
				.log();
		
		StepVerifier.create(stringFlux)
			.expectNext("Spring")
			.expectNext("Spring Boot")
			.expectNext("Reactive Spring")
			.verifyComplete();
	}
	
	@Test
	public void fluxTestElements_WithError() {
		Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Reactive Spring")
				.concatWith(Flux.error(new RuntimeException("An Exception Occurred in fluxTestElements_WithError")))
				.log();
		
		StepVerifier.create(stringFlux)
			.expectNext("Spring")
			.expectNext("Spring Boot")
			.expectNext("Reactive Spring")
//			.expectError(RuntimeException.class)
			.expectErrorMessage("An Exception Occurred")
			.verify();
	}
	
	@Test
	public void fluxTestElementsCount_WithError() {
		Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Reactive Spring")
				.concatWith(Flux.error(new RuntimeException("An Exception Occurred in fluxTestElementsCount_WithError")))
				.log();
		
		StepVerifier.create(stringFlux)
			.expectNextCount(3)
			.expectErrorMessage("An Exception Occurred")
			.verify();
			
	}
	
	@Test
	public void fluxTestElements_WithErrorExpectNextCombine() {
		Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Reactive Spring")
				.concatWith(Flux.error(new RuntimeException("An Error Occurred in MonoTest")))
				.log();
		
		StepVerifier.create(stringFlux)
			.expectNext("Spring", "Spring Boot", "Reactive Spring")
			.expectErrorMessage("An Error Occurred")
			.verify();
	}
	
	@Test
	public void monoTest() {
		
		Mono<String> stringMono = Mono.just("Spring");
		
		StepVerifier.create(stringMono.log())
				.expectNext("Spring")
				.verifyComplete();
	}
	
	@Test
	public void monoTest_Error() {
		
		StepVerifier.create(Mono.error(new RuntimeException("An Error Occurred in MonoTest_Error")).log())
			.expectError(RuntimeException.class)
			.verify();
		
	}
}
