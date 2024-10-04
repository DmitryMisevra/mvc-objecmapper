package ru.javacode.mvcobjecmapper.customer.mapper;

import org.springframework.stereotype.Component;
import ru.javacode.mvcobjecmapper.customer.dto.CreateCustomerDto;
import ru.javacode.mvcobjecmapper.customer.dto.CustomerDto;
import ru.javacode.mvcobjecmapper.customer.model.Customer;

@Component
public class CustomerMapper {

    public Customer CreateCustomerDtoToCustomer(CreateCustomerDto createCustomerDto) {
        return Customer.builder()
                .firstName(createCustomerDto.getFirstName())
                .lastName(createCustomerDto.getLastName())
                .email(createCustomerDto.getEmail())
                .contactNumber(createCustomerDto.getContactNumber())
                .build();
    }

    public CustomerDto customerToCustomerDto(Customer customer) {
        return CustomerDto.builder()
                .customerId(customer.getCustomerId())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .email(customer.getEmail())
                .contactNumber(customer.getContactNumber())
                .build();
    }

}
