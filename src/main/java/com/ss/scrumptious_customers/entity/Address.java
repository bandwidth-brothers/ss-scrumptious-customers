package com.ss.scrumptious_customers.entity;

import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "ADDRESS")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "customers"})
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id", updatable = false)
    private Long addressId;

    @NotBlank
    @Column(name="lineOne")
	private String lineOne;
	
	@Nullable
	@Column(name="lineTwo")
	private String lineTwo;

    private String city;

    private String state;

    private String zip;

    @ManyToMany(mappedBy = "addresses", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<Customer> customers;
}
