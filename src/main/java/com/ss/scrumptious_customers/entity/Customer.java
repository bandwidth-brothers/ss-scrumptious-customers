package com.ss.scrumptious_customers.entity;

import java.sql.Date;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.springframework.lang.Nullable;

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

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "address_id", referencedColumnName = "id")
    Address address;
	
	@NotBlank
	@Column(name = "first_name", nullable = false)
	private String firstName;
	
	@NotBlank
	@Column(name = "last_name", nullable = false)
	private String lastName;
	
	@NotBlank
	@Column(name = "phone", nullable = false)
	private String phone;
	
	@Nullable
	@Column(name = "picture")
	private String picture;

	// without columnDefinition = "TINYINT" mysql will default to bit(1)
	@Builder.Default
	@Column(name = "veteranary_status", columnDefinition = "TINYINT")
	private Boolean veteranaryStatus = false;

	@NotBlank
	@Column(name = "email", nullable = false)
	private String email;
	
	//@NotBlank
	@Column(name = "dob")//, nullable = false)
	private Date dob;
	
	@Builder.Default
	@Column(name = "loyalty_points", nullable = false)
	private Integer loyaltyPoints = 0;


}
