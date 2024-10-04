package ru.javacode.mvcobjecmapper.customer.service;

import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.javacode.mvcobjecmapper.customer.dto.CreateCustomerDto;
import ru.javacode.mvcobjecmapper.customer.dto.CustomerDto;
import ru.javacode.mvcobjecmapper.customer.dto.UpdateCustomerDto;
import ru.javacode.mvcobjecmapper.customer.mapper.CustomerMapper;
import ru.javacode.mvcobjecmapper.customer.model.Customer;
import ru.javacode.mvcobjecmapper.customer.repository.CustomerRepository;
import ru.javacode.mvcobjecmapper.exception.ResourceNotFoundException;
import ru.javacode.mvcobjecmapper.util.JsonUtils;
import ru.javacode.mvcobjecmapper.util.ValidationUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final Validator validator;

    @Override
    public String createCustomer(String createCustomerJson) {
        CreateCustomerDto createCustomerDto = JsonUtils.fromJson(createCustomerJson, CreateCustomerDto.class);
        ValidationUtils.validate(validator, createCustomerDto);
        Customer customer = customerMapper.CreateCustomerDtoToCustomer(createCustomerDto);
        Customer savedCustomer = customerRepository.save(customer);
        CustomerDto customerDto = customerMapper.customerToCustomerDto(savedCustomer);
        return JsonUtils.toJson(customerDto);
    }

    @Override
    public String updateCustomer(Long customerId, String updateCustomerJson) {
        UpdateCustomerDto updateCustomerDto = JsonUtils.fromJson(updateCustomerJson, UpdateCustomerDto.class);
        ValidationUtils.validate(validator, updateCustomerDto);
        Customer customerToUpdate = customerRepository.findById(customerId).orElseThrow(() ->
                new ResourceNotFoundException("Покупатель с id " + customerId + " не найден"));
        if (updateCustomerDto.getFirstName() != null) {
            customerToUpdate.setFirstName(updateCustomerDto.getFirstName());
        }
        if (updateCustomerDto.getLastName() != null) {
            customerToUpdate.setLastName(updateCustomerDto.getLastName());
        }
        if (updateCustomerDto.getEmail() != null) {
            customerToUpdate.setEmail(updateCustomerDto.getEmail());
        }
        if (updateCustomerDto.getContactNumber() != null) {
            customerToUpdate.setContactNumber(updateCustomerDto.getContactNumber());
        }
        Customer savedCustomer = customerRepository.save(customerToUpdate);
        CustomerDto customerDto = customerMapper.customerToCustomerDto(savedCustomer);
        return JsonUtils.toJson(customerDto);
    }

    @Transactional(readOnly = true)
    @Override
    public String getCustomerById(Long customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new ResourceNotFoundException(
                "Покупатель с id " + customerId + " не найден"));
        CustomerDto customerDto = customerMapper.customerToCustomerDto(customer);
        return JsonUtils.toJson(customerDto);
    }

    @Override
    public void deleteCustomerById(Long customerId) {
        customerRepository.deleteById(customerId);
    }

    @Transactional(readOnly = true)
    @Override
    public String getAllCustomers() {
        List<CustomerDto> customers = customerRepository.findAll().stream()
                .map(customerMapper::customerToCustomerDto)
                .toList();
        return JsonUtils.toJson(customers);
    }
}
