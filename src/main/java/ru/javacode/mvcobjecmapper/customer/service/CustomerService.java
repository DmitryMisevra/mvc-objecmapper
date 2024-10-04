package ru.javacode.mvcobjecmapper.customer.service;

public interface CustomerService {

    String createCustomer(String createCustomerJson);

    String updateCustomer(Long customerId, String updateCustomerJson);

    String getCustomerById(Long customerId);

    void deleteCustomerById(Long customerId);

    String getAllCustomers();
}
