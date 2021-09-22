package com.ss.scrumptious_customers.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ss.scrumptious_customers.dto.CreateCustomerDto;
import com.ss.scrumptious_customers.entity.Customer;
import com.ss.scrumptious_customers.security.permissions.AdminOnlyPermission;
import com.ss.scrumptious_customers.security.permissions.GetCustomerByIdPermission;
import com.ss.scrumptious_customers.service.CustomerService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor 
public class CustomerController {

    private final CustomerService customerService;

    
    @PostMapping("/register")
	public ResponseEntity<UUID> createCustomer(
			@Valid @RequestBody CreateCustomerDto createCustomerDto) {
		System.out.println(createCustomerDto);
		UUID uid = customerService.createNewCustomer(createCustomerDto);
		return ResponseEntity.of(Optional.ofNullable(uid));
	}
    
    
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<Customer>> getAllCustomers() {
        log.info("GET Customer all");
        List<Customer> customers = customerService.getAllCustomers();
        if (customers.isEmpty()) {
        return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(customers);
    }

    @GetCustomerByIdPermission
    @GetMapping(value = "/me/{customerId}",
        produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Customer> getCustomerById(@PathVariable UUID customerId) {
        log.info("GET Customer id=" + customerId);
        return ResponseEntity.of(Optional.ofNullable(customerService.getCustomerById(customerId)));
    }

//    @GetCustomerByIdPermission
//    @PutMapping(value = "/{customerId}",
//        consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
//    public ResponseEntity<Void> updateExistingCustomer(@PathVariable UUID customerId,
//                                                       @Valid @RequestBody
//                                                           UpdateCustomerDto updateCustomerDto) {
//      log.info("PUT Customer id=" + customerId);
//      customerService.updateCustomer(customerId, updateCustomerDto);
//      return ResponseEntity.noContent().build();
//    }
  
    @AdminOnlyPermission
    @DeleteMapping("/me/{customerId}")
    public ResponseEntity<String> deleteCustomer(@PathVariable UUID customerId) {
      log.info("DELETE id=" + customerId);
      customerService.removeCustomerById(customerId);
      return ResponseEntity.noContent().build();
    }

    //   @GetCustomerByEmailPermission
    //   @GetMapping(value = "/email/{email}",
    //       produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    //   public ResponseEntity<Customer> getCustomerByEmail(@PathVariable String email) {
    //     log.info("GET Customer email=" + email);
    //     return ResponseEntity.of(Optional.ofNullable(customerService.getCustomerByEmail(email)));
    //   }






}
