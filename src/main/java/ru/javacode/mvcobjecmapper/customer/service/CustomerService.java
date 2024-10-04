package ru.javacode.mvcobjecmapper.customer.service;

import ru.javacode.mvcobjecmapper.customer.dto.CreateCustomerDto;
import ru.javacode.mvcobjecmapper.customer.dto.CustomerDto;
import ru.javacode.mvcobjecmapper.customer.dto.UpdateCustomerDto;

import java.util.List;

public interface CustomerService {

    CustomerDto createCustomer(CreateCustomerDto createCustomerDto);

    CustomerDto updateCustomer(Long customerId, UpdateCustomerDto updateCustomerDto);

    CustomerDto getCustomerById(Long customerId);

    void deleteCustomerById(Long customerId);

    List<CustomerDto> getAllCustomers();
}
