package com.spring.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
@EnableDiscoveryClient
public class SpringCloudConfigServerApplication {

    public static void main(String[] args) {

        SpringApplication.run(SpringCloudConfigServerApplication.class, args);
        System.out.println("Spring Cloud Config Server Started");
    }

}
