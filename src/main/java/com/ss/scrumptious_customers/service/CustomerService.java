package com.ss.scrumptious_customers.service;

import java.util.List;
import java.util.UUID;

import com.ss.scrumptious.common_entities.entity.Address;
import com.ss.scrumptious.common_entities.entity.Customer;
import com.ss.scrumptious_customers.dto.CreateAddressDto;
import com.ss.scrumptious_customers.dto.CreateCustomerDto;
import com.ss.scrumptious_customers.dto.UpdateAddressDto;
import com.ss.scrumptious_customers.dto.UpdateCustomerDto;


public interface CustomerService {

	Customer createNewCustomer(CreateCustomerDto customerDto);

	List<Customer> getAllCustomers();

	Customer getCustomerById(UUID customerId);

	void updateCustomer(UUID customerId, UpdateCustomerDto updateCustomerDto);

	void removeCustomerById(UUID customerId);

	Address createNewAddress(CreateAddressDto createAddressDto);

	Address getAddressById(Long addressId);

	Address updateAddress(Long addressId, UpdateAddressDto updateAddressDto);
}
