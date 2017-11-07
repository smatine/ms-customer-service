package com.sg.microservices.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;


@SpringBootApplication
@EnableDiscoveryClient
@EnableCircuitBreaker
@EnableHystrixDashboard
@Component
@ComponentScan("com.sg.microservices.customer")

public class CustomerService {
	public static void main(String[] args) {
		SpringApplication.run(CustomerService.class, args);
		 final Logger LOGGER = LoggerFactory.getLogger(CustomerService.class);
		   String customlogger ="ADMS Loger::::";
		   LOGGER.info(customlogger+"CustomerService Started");
	}
}