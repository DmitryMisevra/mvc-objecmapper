package ru.javacode.mvcobjecmapper.customer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.javacode.mvcobjecmapper.customer.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
