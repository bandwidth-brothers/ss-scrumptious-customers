package com.ss.scrumptious_customers.entity;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "CUSTOMER")
@Builder
public class Customer {


	@Id
	@Column(columnDefinition = "BINARY(16)", name = "id")
	private UUID id;
	
	@NotBlank
	@Column(name = "first_name", nullable = false)
	private String firstName;
	
	@NotBlank
	@Column(name = "last_name", nullable = false)
	private String lastName;
	
	@NotBlank
	@Column(name = "phone", nullable = false)
	private String phone;
	
	@NotBlank
	@Column(name = "email", nullable = false)
	private String email;
	
	//@NotBlank
	@Column(name = "dob")//, nullable = false)
	private Date dob;
	
	@Builder.Default
	@Column(name = "loyalty_points", nullable = false)
	private Integer loyaltyPoints = 0;


    @ManyToMany
    @JoinTable(
        name = "customer_address",
        joinColumns = @JoinColumn(name = "customer_id"),
        inverseJoinColumns = @JoinColumn(name = "address_id"))
    List<Address> addresses;
	
}
