package com.ss.scrumptious_customers.service;

import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
//import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.ss.scrumptious_customers.client.AuthClient;
import com.ss.scrumptious_customers.dao.AddressRepository;
//import com.ss.scrumptious_customers.client.authentication.ServiceAuthenticationProvider;
import com.ss.scrumptious_customers.dao.CustomerRepository;
import com.ss.scrumptious_customers.dto.AuthDto;
import com.ss.scrumptious_customers.dto.CreateAddressDto;
import com.ss.scrumptious_customers.dto.CreateCustomerDto;
import com.ss.scrumptious_customers.dto.UpdateAddressDto;
import com.ss.scrumptious_customers.dto.UpdateCustomerDto;
import com.ss.scrumptious_customers.entity.Address;
import com.ss.scrumptious_customers.entity.Customer;
import com.ss.scrumptious_customers.exception.NoSuchAddressException;
import com.ss.scrumptious_customers.exception.NoSuchCustomerException;

import lombok.RequiredArgsConstructor;

//@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final AuthClient authClient;

	private final CustomerRepository customerRepository;
	private final AddressRepository addressRepository;

	@Transactional
	public Customer createNewCustomer(CreateCustomerDto createCustomerDto) {
        AuthDto authDto = AuthDto.builder().email(createCustomerDto.getEmail())
                .password(createCustomerDto.getPassword()).build();
        ResponseEntity<UUID> resp = authClient.createNewAccountCustomer(authDto);
        if (resp.getBody() == null){
            throw new IllegalStateException("Email is already in use");
        }

        System.out.println("cline id: "  + resp.getBody());

        Customer customer = Customer.builder()
        		.id(resp.getBody())
                .firstName(createCustomerDto.getFirstName())
                .lastName(createCustomerDto.getLastName())
                .email(createCustomerDto.getEmail())
                .phone(createCustomerDto.getPhone())
				.address(createNewAddress(createCustomerDto.getAddress()))
                .build();

		if (createCustomerDto.getPicture() != null) {
			customer.setPicture(createCustomerDto.getPicture());
		}
		if (createCustomerDto.getVeteranaryStatus() != null) {
			customer.setVeteranaryStatus(createCustomerDto.getVeteranaryStatus());
		}

        return customerRepository.save(customer);
    }
	
	
	/**
	 * Gets all {@link Customer} records.
	 *
	 * @return a list of all customers.
	 */
	@Override
	public List<Customer> getAllCustomers() {
		return customerRepository.findAll();
	}

	/**
	 * Gets a {@link Customer} record given an ID.
	 *
	 * @param id the ID of the customer.
	 * @return the record with the given ID.
	 * @throws IllegalArgumentException if id is null.
	 * @throws NoSuchCustomerException  if a customer with the ID cannot be found.
	 */
	@Override
	public Customer getCustomerById(UUID customerId) {
		notNull(customerId);
		return customerRepository.findById(customerId).orElseThrow(() -> new NoSuchCustomerException(customerId));
	}

	@Override
	public Customer updateCustomer(UUID customerId, @Valid UpdateCustomerDto updateCustomerDto) {
		Customer customer = getCustomerById(customerId);
		if (updateCustomerDto.getFirstName() != null) {
			customer.setFirstName(updateCustomerDto.getFirstName());
		}
		if (updateCustomerDto.getLastName() != null) {
			customer.setLastName(updateCustomerDto.getLastName());
		}
		if (updateCustomerDto.getEmail() != null) {
			customer.setEmail(updateCustomerDto.getEmail());
		}
		if (updateCustomerDto.getPhone() != null) {
			customer.setPhone(updateCustomerDto.getPhone());
		}
		if (updateCustomerDto.getPicture() != null) {
			customer.setPicture(updateCustomerDto.getPicture());
		}
		if (updateCustomerDto.getVeteranaryStatus() != null) {
			customer.setVeteranaryStatus(updateCustomerDto.getVeteranaryStatus());
		}
		if (updateCustomerDto.getAddress() != null) {
			customer.setAddress(updateAddress(customer.getAddress().getId(), updateCustomerDto.getAddress()));
		}
		return customerRepository.save(customer);
	}

	/**
	 * Removes a {@link Customer} record given an ID.
	 *
	 * @param id the ID of the customer to remove.
	 */
	@Override
	public void removeCustomerById(UUID customerId) {
		notNull(customerId);

		customerRepository.findById(customerId).ifPresent(customerRepository::delete);
	}

	/**
	 * Util method to check for null ID values.
	 *
	 * @param ids vararg ids to check.
	 */
	private void notNull(Object... ids) {
		for (Object i : ids) {
			if (i == null) {
				throw new IllegalArgumentException("Expected value but received null.");
			}
		}
	}


	@Override
	public Address createNewAddress(CreateAddressDto createAddressDto) {
		Address address = Address.builder()
				.line1(createAddressDto.getLine1())
				.city(createAddressDto.getCity())
				.state(createAddressDto.getState())
				.zip(createAddressDto.getZip())
				.build();
		
		if (createAddressDto.getLine2() != null) {
			address.setLine2(createAddressDto.getLine2());
		}

		return addressRepository.save(address);
	}

	@Override
	public Address getAddressById(Long addressId) {
		notNull(addressId);
		return addressRepository.findById(addressId).orElseThrow(() -> new NoSuchAddressException(addressId));
	}


	@Override
	public Address updateAddress(Long addressId, UpdateAddressDto updateAddressDto) {
		Address address = getAddressById(addressId);
		// every field is optional so you have to check for null
		if (updateAddressDto.getLine1() != null) {
			address.setLine1(updateAddressDto.getLine1());
		}
		if (updateAddressDto.getLine2() != null) {
			address.setLine2(updateAddressDto.getLine2());
		}
		if (updateAddressDto.getCity() != null) {
			address.setCity(updateAddressDto.getCity());
		}
		if (updateAddressDto.getState() != null) {
			address.setState(updateAddressDto.getState());
		}
		if (updateAddressDto.getZip() != null) {
			address.setZip(updateAddressDto.getZip());
		}
		return addressRepository.save(address);
	}



}