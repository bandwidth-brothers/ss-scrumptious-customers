// package com.ss.scrumptious_customers.client;

// import java.util.UUID;

// import com.ss.scrumptious_customers.client.authentication.AuthenticationRequest;
// import com.ss.scrumptious_customers.controller.EndpointConstants;

// import org.springframework.cloud.openfeign.FeignClient;
// import org.springframework.http.MediaType;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.PutMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestHeader;

// @FeignClient("scrumptious-auth-service")
// public interface AccountsClient {

//   @PostMapping(value = "/login")
//   ResponseEntity<String> login(@RequestBody AuthenticationRequest authenticationRequest);

// //   @PutMapping(value = EndpointConstants.API_V_0_1_ACCOUNTS + "/customer/{customerId}",
// //       consumes = MediaType.TEXT_PLAIN_VALUE)
// //   ResponseEntity<Void> updateCustomerEmail(@RequestHeader(value = "Authorization")
// //                                         String authorizationHeader,
// //                                         @PathVariable UUID customerId,
// //                                         @RequestBody String newEmail);

// }
