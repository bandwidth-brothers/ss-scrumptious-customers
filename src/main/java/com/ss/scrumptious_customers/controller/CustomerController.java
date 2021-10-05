package com.ss.scrumptious_customers.controller;

import java.net.URI;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ss.scrumptious_customers.dto.CreateCustomerDto;
import com.ss.scrumptious_customers.dto.UpdateCustomerDto;
import com.ss.scrumptious_customers.entity.Customer;
import com.ss.scrumptious_customers.security.permissions.AdminOnlyPermission;
import com.ss.scrumptious_customers.security.permissions.GetCustomerByIdPermission;
import com.ss.scrumptious_customers.service.CustomerService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor 
public class CustomerController {

    //TODO: add orders relation to delete all orders when deleting customer
    //TODO: make api call to delete user when customer is deleted
    //TODO: make api call to update user email when customer email is updated
    //TODO: add try catch to update and delete

    private final CustomerService customerService;

    
    @PostMapping
	public ResponseEntity<Void> createCustomer(
			@Valid @RequestBody CreateCustomerDto createCustomerDto) {
		System.out.println(createCustomerDto);
		Customer customer = customerService.createNewCustomer(createCustomerDto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{customerId}")
                .buildAndExpand(customer.getId()).toUri();
        return ResponseEntity.created(location).build();
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
    @GetMapping(value = "/{customerId}",
        produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Customer> getCustomerById(@PathVariable UUID customerId) {
        log.info("GET Customer id=" + customerId);
        return ResponseEntity.of(Optional.ofNullable(customerService.getCustomerById(customerId)));
    }

   @GetCustomerByIdPermission
   @PutMapping(value = "/{customerId}",
       consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
   public ResponseEntity<Customer> updateExistingCustomer(@PathVariable UUID customerId,
                                                      @Valid @RequestBody
                                                          UpdateCustomerDto updateCustomerDto) {
     log.info("PUT Customer id=" + customerId);
     return ResponseEntity.noContent().build();
   }
  
    @AdminOnlyPermission
    @DeleteMapping("/{customerId}")
    public ResponseEntity<String> deleteCustomer(@PathVariable UUID customerId) {
      log.info("DELETE id=" + customerId);
      customerService.removeCustomerById(customerId);
      return ResponseEntity.noContent().build();
    }

}
