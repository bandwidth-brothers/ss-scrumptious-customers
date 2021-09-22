package com.ss.scrumptious_customers.client;

import java.util.UUID;

import javax.validation.Valid;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.ss.scrumptious_customers.dto.AuthDto;

 @FeignClient("scrumptious-auth-service")
 public interface AuthClient {

	 @PostMapping(value = "/auth/customer/register")
	 ResponseEntity<UUID> createNewAccountCustomer(@Valid @RequestBody AuthDto authDto);

//   @PutMapping(value = EndpointConstants.API_V_0_1_ACCOUNTS + "/customer/{customerId}",
//       consumes = MediaType.TEXT_PLAIN_VALUE)
//   ResponseEntity<Void> updateCustomerEmail(@RequestHeader(value = "Authorization")
//                                         String authorizationHeader,
//                                         @PathVariable UUID customerId,
//                                         @RequestBody String newEmail);

 }
