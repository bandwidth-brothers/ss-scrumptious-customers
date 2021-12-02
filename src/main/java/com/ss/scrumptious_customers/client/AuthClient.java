package com.ss.scrumptious_customers.client;

import java.util.UUID;

import javax.validation.Valid;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.ss.scrumptious_customers.dto.AuthDto;

 @FeignClient(name = "auth-service", url = "${base.url}")
 public interface AuthClient {

	 @PostMapping(value = "/auth/customer/register")
	 ResponseEntity<UUID> createNewAccountCustomer(@Valid @RequestBody AuthDto authDto);

 }
