package com.ss.scrumptious_customers.service;

import java.util.List;
import java.util.UUID;

import com.ss.scrumptious_customers.dto.CreateCustomerDto;
import com.ss.scrumptious_customers.dto.UpdateCustomerDto;
import com.ss.scrumptious_customers.entity.Customer;

public interface CustomerService {

	UUID createNewCustomer(CreateCustomerDto customerDto);
	
	List<Customer> getAllCustomers();

	Customer getCustomerById(UUID id);

	Customer updateCustomer(UUID customerId, UpdateCustomerDto updateCustomerDto);

	void removeCustomerById(UUID id);
}
