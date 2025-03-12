package com.spring.cloud.controller;

import java.math.BigDecimal;


import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import com.spring.cloud.bean.CurrencyConversion;
import com.spring.cloud.client.CurrencyExchangeClient;

@RestController
public class CurrencyConversionController {
    @Autowired
    private CurrencyExchangeClient client;
    private static Logger logger = LoggerFactory.getLogger(CurrencyConversionController.class);
    @GetMapping("/currency-conversion-feign/from/{from}/to/{to}/qty/{qty}")
//    @RateLimiter(name = "getMessageRateLimit", fallbackMethod = "getMessageFallBack")
    //@Retry(name="currencyExchangeService", fallbackMethod = "serviceNotAvailable")
    @CircuitBreaker(name="currencyExchangeService", fallbackMethod = "serviceNotAvailable")
    public CurrencyConversion calculateTotalConversionAmount(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal qty) {
        CurrencyConversion responseResult= client.getCurrencyExchange(from, to);
        System.out.println("Total : ="+responseResult.getConversionMultiple());
        CurrencyConversion totalConversionAmount =  new CurrencyConversion(responseResult.getId(), from, to, qty,
                responseResult.getConversionMultiple(), qty.multiply(responseResult.getConversionMultiple()), responseResult.getEnv());
        return totalConversionAmount;

    }
    public CurrencyConversion getMessageFallBack(String from, String to, BigDecimal qty, Throwable e) {
        logger.info("Rate limit has applied, so no further call are getting accepted");
        return new CurrencyConversion(0L, "N/A", "N/A", BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, "Too Many Request : No Further request will be accepted please try after sometime");
    }
    public CurrencyConversion serviceNotAvailable(String from, String to, BigDecimal qty, Throwable e) {
        //logger.info("Retry has applied, so no further call are getting accepted");
        logger.info("Circuit Breaker has applied, so no further call are getting accepted");
        return new CurrencyConversion(0L, "N/A", "N/A", BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, "Service is not available, please try after sometime");
    }
}
