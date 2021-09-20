package com.ss.scrumptious_customers.service;

import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
//import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.ss.scrumptious_customers.client.AuthClient;
//import com.ss.scrumptious_customers.client.authentication.ServiceAuthenticationProvider;
import com.ss.scrumptious_customers.dao.CustomerRepository;
import com.ss.scrumptious_customers.dto.AuthDto;
import com.ss.scrumptious_customers.dto.CreateCustomerDto;
import com.ss.scrumptious_customers.dto.UpdateCustomerDto;
import com.ss.scrumptious_customers.entity.Customer;
import com.ss.scrumptious_customers.exception.NoSuchCustomerException;

import lombok.RequiredArgsConstructor;

//@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final AuthClient authClient;

	private final CustomerRepository customerRepository;

	@Transactional
	public UUID createNewCustomer(CreateCustomerDto customerDto) {
        AuthDto authDto = AuthDto.builder().email(customerDto.getEmail())
                .password(customerDto.getPassword()).build();
        ResponseEntity<UUID> resp = authClient.createNewAccountCustomer(authDto);
        if (resp.getBody() == null){
            throw new IllegalStateException("Email is already in use");
        }

        System.out.println("cline id: "  + resp.getBody());

        Customer customer = Customer.builder()
        		.customerId(resp.getBody())
                .firstName(customerDto.getFirstName())
                .lastName(customerDto.getLastName())
                .email(customerDto.getEmail())
                .phone(customerDto.getPhone())
                .build();
        
        Customer customerRet = customerRepository.save(customer);

        System.out.println("owner id: " + customerRet.getCustomerId());

        return customerRet.getCustomerId();
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
	public Customer getCustomerById(UUID id) {
		notNull(id);
		return customerRepository.findById(id).orElseThrow(() -> new NoSuchCustomerException(id));
	}

	@Override
	public Customer updateCustomer(UUID customerId, @Valid UpdateCustomerDto updateCustomerDto) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Removes a {@link Customer} record given an ID.
	 *
	 * @param id the ID of the customer to remove.
	 */
	@Override
	public void removeCustomerById(UUID id) {
		notNull(id);

		customerRepository.findById(id).ifPresent(customerRepository::delete);
	}

//   @Override
//   public Customer getCustomerByEmail(String email) {
//     notNull(email);
//     return customerRepository.findByEmail(email)
//         .orElseThrow(() -> new NoSuchCustomerException(email));
//   }

//   /**
//    * Updates an existing {@link Customer} account.
//    *
//    * @param updateCustomerDto The {@link Customer} account to update.
//    * @return the updated {@link Customer} from saving changes.
//    * @throws NoSuchCustomerException if no Customer found with the ID.
//    * @throws DuplicateEmailException if a different record exists with the same email as the update
//    *                                 information.
//    */
//   @Override
//   public Customer updateCustomer(UUID customerId, @Valid UpdateCustomerDto updateCustomerDto) {
//     notNull(customerId);

//     var duplicateEmail = customerRepository.findByEmail(updateCustomerDto.getEmail())
//         .stream()
//         .anyMatch(customer -> !customer.getId().equals(customerId));

//     if (duplicateEmail) {
//       throw new DuplicateEmailException(updateCustomerDto.getEmail());
//     }

//     var oldValue = getCustomerById(customerId);
//     var newValue = CustomerDtoMapper.map(updateCustomerDto);

//     if (!oldValue.getEmail().equals(newValue.getEmail())) {
//       var header = serviceAuthenticationProvider.getAuthorizationHeader();
//       accountsClient.updateCustomerEmail(header, oldValue.getId(), newValue.getEmail());
//     }

//     // set from old payment methods or it'll be erased
//     newValue.setPaymentMethods(oldValue.getPaymentMethods());
//     newValue.setId(customerId);
//     return customerRepository.save(newValue);
//   }

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
}