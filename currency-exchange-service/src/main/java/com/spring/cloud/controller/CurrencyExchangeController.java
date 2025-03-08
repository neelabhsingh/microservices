package com.spring.cloud.controller;

import com.spring.cloud.repository.CurrencyExchangeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.spring.cloud.bean.CurrencyExchange;

@RestController
public class CurrencyExchangeController {
    @Autowired
    private CurrencyExchangeRepository repository;

    @Autowired
    Environment environment;

    @GetMapping(value = "/currency-exchange/from/{from}/to/{to}")
    public CurrencyExchange getCurrencyExchange(@PathVariable String from, @PathVariable String to ) {
        CurrencyExchange currencyExchange= repository.findByFromAndTo(from, to);
        String serverPort= environment.getProperty("local.server.port");
        currencyExchange.setEnv(serverPort);
        return currencyExchange;
    }
}
