package com.ss.scrumptious_customers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
@EntityScan(basePackages = {"com.ss.scrumptious.common_entities.entity"})
public class ScrumptiousCustomersServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScrumptiousCustomersServiceApplication.class, args);
	}

}
