	package com.spring.cloud;

	import feign.Capability;
	import feign.micrometer.MicrometerCapability;
	import io.micrometer.core.instrument.MeterRegistry;
	import org.springframework.boot.SpringApplication;
	import org.springframework.boot.autoconfigure.SpringBootApplication;
	import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
	import org.springframework.context.annotation.Bean;

	@SpringBootApplication
	@EnableDiscoveryClient
	public class CurrencyExchangeServiceApplication {
		@Bean
		public Capability capability(final MeterRegistry registry) {
			return new MicrometerCapability(registry);
		}

		public static void main(String[] args) {
			SpringApplication.run(CurrencyExchangeServiceApplication.class, args);
			System.out.println("Currency Exchange Service is Running....");
		}

	}
