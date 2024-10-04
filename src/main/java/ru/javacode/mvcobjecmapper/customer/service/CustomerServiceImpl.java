package ru.javacode.mvcobjecmapper.customer.service;

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

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public CustomerDto createCustomer(CreateCustomerDto createCustomerDto) {
        Customer customer = customerMapper.CreateCustomerDtoToCustomer(createCustomerDto);
        Customer savedCustomer = customerRepository.save(customer);
        return customerMapper.customerToCustomerDto(savedCustomer);
    }

    @Override
    public CustomerDto updateCustomer(Long customerId, UpdateCustomerDto updateCustomerDto) {
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
        return customerMapper.customerToCustomerDto(savedCustomer);
    }

    @Transactional(readOnly = true)
    @Override
    public CustomerDto getCustomerById(Long customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new ResourceNotFoundException(
                "Покупатель с id " + customerId + " не найден"));
        return customerMapper.customerToCustomerDto(customer);
    }

    @Override
    public void deleteCustomerById(Long customerId) {
        customerRepository.deleteById(customerId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<CustomerDto> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(customerMapper::customerToCustomerDto)
                .toList();
    }
}
