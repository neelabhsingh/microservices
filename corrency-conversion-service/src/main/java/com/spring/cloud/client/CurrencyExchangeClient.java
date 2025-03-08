package com.spring.cloud.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.spring.cloud.bean.CurrencyConversion;

//@FeignClient(name = "currency-exchange", url = "localhost:8000")1
//@FeignClient(name = "currency-exchange")
@FeignClient(name = "currency-exchange", url = "http://localhost:8000")
//@FeignClient(name = "currency-exchange")
public interface CurrencyExchangeClient {
    @GetMapping(value = "/currency-exchange/from/{from}/to/{to}")
    public CurrencyConversion getCurrencyExchange(@PathVariable String from, @PathVariable String to);
}
