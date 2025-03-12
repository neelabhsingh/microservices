package com.spring.cloud;
import feign.Capability;
import feign.micrometer.MicrometerCapability;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class CurrencyConversionServiceApplication {
    @Bean public Capability capability(final MeterRegistry registry) {
        return new MicrometerCapability(registry);
    }
    public static void main(String[] args) {
        SpringApplication.run(CurrencyConversionServiceApplication.class, args);
        System.out.println("Currency Conversion Service is Running....");
    }
}
