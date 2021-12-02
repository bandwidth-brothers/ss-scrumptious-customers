package com.ss.scrumptious_customers.dao;

import com.ss.scrumptious.common_entities.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

}
