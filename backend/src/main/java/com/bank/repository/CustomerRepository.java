package com.bank.repository;

import com.bank.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByCustomerNumber(String customerNumber);
    Optional<Customer> findByIdCardNumber(String idCardNumber);
    Optional<Customer> findByPhone(String phone);
}