package com.advance.salaryadvance.Repository;


import com.advance.salaryadvance.Entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;


public interface CustomerRepository extends JpaRepository<Customer, UUID> {
    Optional<Customer> findByMobile(String mobile);
}

