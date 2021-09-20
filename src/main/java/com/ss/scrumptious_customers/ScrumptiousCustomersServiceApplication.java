package com.ss.scrumptious_customers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
//import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class ScrumptiousCustomersServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScrumptiousCustomersServiceApplication.class, args);
	}

}
