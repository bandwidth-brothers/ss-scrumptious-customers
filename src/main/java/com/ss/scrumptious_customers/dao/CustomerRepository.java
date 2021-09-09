package com.ss.scrumptious_customers.dao;

import java.util.UUID;

import com.ss.scrumptious_customers.entity.Customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, UUID> {

}