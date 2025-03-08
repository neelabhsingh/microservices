package com.spring.cloud.controller;

import java.math.BigDecimal;
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

    @GetMapping("/currency-conversion-feign/from/{from}/to/{to}/qty/{qty}")
    public CurrencyConversion calculateTotalConversionAmount(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal qty) {
        CurrencyConversion responseResult= client.getCurrencyExchange(from, to);
        System.out.println("Total : ="+responseResult.getConversionMultiple());
        CurrencyConversion totalConversionAmount =  new CurrencyConversion(responseResult.getId(), from, to, qty,
                responseResult.getConversionMultiple(), qty.multiply(responseResult.getConversionMultiple()), responseResult.getEnv());
        return totalConversionAmount;

    }

}
