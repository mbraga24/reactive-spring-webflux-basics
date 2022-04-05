package com.learnreactivespring.fluxandmonoplayground;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;
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
//				.concatWith(Flux.error(new RuntimeException("An Exception Occurred")))
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
				.log();
		
	
	}
}
