package com.ss.scrumptious_customers.controller;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import com.ss.scrumptious_customers.dto.CreateAddressDto;
import com.ss.scrumptious_customers.dto.CreateCustomerDto;
import com.ss.scrumptious_customers.dto.UpdateAddressDto;
import com.ss.scrumptious_customers.dto.UpdateCustomerDto;
import com.ss.scrumptious_customers.entity.Address;
import com.ss.scrumptious_customers.entity.Customer;
import com.ss.scrumptious_customers.exception.NoSuchCustomerException;
import com.ss.scrumptious_customers.service.CustomerService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CustomerControllerTests {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @MockBean
    CustomerService customerService;

    private Customer validCustomer;
    private CreateCustomerDto validCreateCustomerDto;
    private UpdateCustomerDto validUpdateCustomerDto;

    @BeforeEach
    void beforeEach() {
        Mockito.reset(customerService);

        // setup Customer objs
        validCustomer = new Customer();

        validCustomer.setId(UUID.randomUUID());
        validCustomer.setFirstName("John");

        validCustomer.setLastName("Doe");
        validCustomer.setEmail("test@test.com");
        validCustomer.setPhone("999-999-9999");

        validCustomer.setLoyaltyPoints(3);

        // setup Address
        Address validAddress = new Address();
        validAddress.setId(1L);
        validAddress.setLine1("123 Main St.");
        validAddress.setLine2("Apt #1");
        validAddress.setCity("Las Vegas");
        validAddress.setState("NV");
        validAddress.setZip("12345");

        validCustomer.setAddress(validAddress);
  
      // setup DTOs
        validUpdateCustomerDto = UpdateCustomerDto.builder()
            .firstName(validCustomer.getFirstName())
            .lastName(validCustomer.getLastName())
            .email(validCustomer.getEmail())
            .address(UpdateAddressDto.builder()
                .line1(validAddress.getLine1())
                .line2(validAddress.getLine2())
                .city(validAddress.getCity())
                .state(validAddress.getState())
                .zip(validAddress.getZip())
                .build())
            .build();
  
  
        validCreateCustomerDto = CreateCustomerDto.builder()
            .firstName(validCustomer.getFirstName())
            .lastName(validCustomer.getLastName())
            .email(validCustomer.getEmail())
            .password("abCD1234!@")
            .phone(validCustomer.getPhone())
            .address(CreateAddressDto.builder()
                .line1(validAddress.getLine1())
                .line2(validAddress.getLine2())
                .city(validAddress.getCity())
                .state(validAddress.getState())
                .zip(validAddress.getZip())
                .build())
            .build();
    }

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
          .webAppContextSetup(context)
          .apply(springSecurity())
          .build();
    }

    @WithMockUser(roles = "ADMIN")
    @Test
    public void getAllCustomersValid() throws Exception {
        List<Customer> customers = new ArrayList<>();
        customers.add(validCustomer);
        when(customerService.getAllCustomers()).thenReturn(customers);
        
        mvc.perform(get("/customers"))
        .andExpect(status().is(200))
        .andExpect(jsonPath("$.[0]firstName").value(validCustomer.getFirstName()));
    }

    @WithMockUser(roles = "CUSTOMER")
    @Test
    public void getAllCustomersNotAuthorized() throws Exception {
        mvc.perform(get("/customers"))
        .andExpect(status().is(403));
    }

    @WithMockUser(roles = "ADMIN")
    @Test
    public void getAllCustomersEmpty() throws Exception {
        when(customerService.getAllCustomers()).thenReturn(Collections.emptyList());
        
        mvc.perform(get("/customers"))
        .andExpect(status().is(204));
    }

    @WithMockUser(roles = "ADMIN")
    @Test
    void getCustomerByIdValid() throws Exception {
        when(customerService.getCustomerById(validCustomer.getId())).thenReturn(validCustomer);

        mvc.perform(get("/customers/" + validCustomer.getId()))
        .andExpect(status().is(200))
        .andExpect(jsonPath("$.firstName").value(validCustomer.getFirstName()));
    }
    
    @WithMockUser(roles = "ADMIN")
    @Test
    void getCustomerByIdNotExist() throws Exception {
        UUID randomId = UUID.randomUUID();

        mvc
            .perform(
                get("/customers/" + randomId))
            .andExpect(status().is(404));
    }

    @Test
    void getCustomerByIdNotAuthorized() throws Exception {

        mvc
            .perform(
                get("/customers/" + validCustomer.getId()))
            .andExpect(status().is(403));
    }

}
