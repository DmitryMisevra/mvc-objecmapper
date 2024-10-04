package ru.javacode.mvcobjecmapper.customer.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.javacode.mvcobjecmapper.customer.dto.CreateCustomerDto;
import ru.javacode.mvcobjecmapper.customer.dto.CustomerDto;
import ru.javacode.mvcobjecmapper.customer.dto.UpdateCustomerDto;
import ru.javacode.mvcobjecmapper.customer.model.Customer;
import ru.javacode.mvcobjecmapper.customer.repository.CustomerRepository;
import ru.javacode.mvcobjecmapper.exception.ResourceNotFoundException;
import ru.javacode.mvcobjecmapper.util.JsonUtils;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
public class CustomerServiceIntegrationTest {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void testCreateCustomer() {
        // Arrange
        CreateCustomerDto createCustomerDto = CreateCustomerDto.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .contactNumber("+79123456789")
                .build();
        String createCustomerJson = JsonUtils.toJson(createCustomerDto);

        // Act
        String responseJson = customerService.createCustomer(createCustomerJson);
        CustomerDto customerDto = JsonUtils.fromJson(responseJson, CustomerDto.class);

        // Assert
        Optional<Customer> savedCustomer = customerRepository.findById(customerDto.getCustomerId());
        assertThat(savedCustomer).isPresent();
        assertThat(savedCustomer.get().getFirstName()).isEqualTo("John");
        assertThat(savedCustomer.get().getLastName()).isEqualTo("Doe");
        assertThat(savedCustomer.get().getEmail()).isEqualTo("john.doe@example.com");
        assertThat(savedCustomer.get().getContactNumber()).isEqualTo("+79123456789");
    }

    @Test
    void testGetCustomerById() {
        // Arrange
        Customer customer = Customer.builder()
                .firstName("Alice")
                .lastName("Smith")
                .email("alice.smith@example.com")
                .contactNumber("+79112233445")
                .build();
        customer = customerRepository.save(customer);

        // Act
        String responseJson = customerService.getCustomerById(customer.getCustomerId());
        CustomerDto customerDto = JsonUtils.fromJson(responseJson, CustomerDto.class);

        // Assert
        assertThat(customerDto.getCustomerId()).isEqualTo(customer.getCustomerId());
        assertThat(customerDto.getFirstName()).isEqualTo("Alice");
        assertThat(customerDto.getLastName()).isEqualTo("Smith");
        assertThat(customerDto.getEmail()).isEqualTo("alice.smith@example.com");
        assertThat(customerDto.getContactNumber()).isEqualTo("+79112233445");
    }

    @Test
    void testDeleteCustomerById() {
        // Arrange
        Customer customer = Customer.builder()
                .firstName("Bob")
                .lastName("Johnson")
                .email("bob.johnson@example.com")
                .contactNumber("+79223344556")
                .build();
        customer = customerRepository.save(customer);

        // Act
        customerService.deleteCustomerById(customer.getCustomerId());

        // Assert
        Optional<Customer> deletedCustomer = customerRepository.findById(customer.getCustomerId());
        assertThat(deletedCustomer).isNotPresent();
    }

    @Test
    void testGetAllCustomers() {
        // Arrange
        Customer customer1 = Customer.builder()
                .firstName("Charlie")
                .lastName("Brown")
                .email("charlie.brown@example.com")
                .contactNumber("+79334455667")
                .build();
        Customer customer2 = Customer.builder()
                .firstName("David")
                .lastName("Wilson")
                .email("david.wilson@example.com")
                .contactNumber("+79445566778")
                .build();
        customerRepository.save(customer1);
        customerRepository.save(customer2);

        // Act
        String responseJson = customerService.getAllCustomers();
        List<CustomerDto> customers = JsonUtils.fromJson(responseJson, List.class);

        // Assert
        assertThat(customers).hasSize(2);
    }

    @Test
    void testGetCustomerById_NotFound() {
        // Act & Assert
        assertThatThrownBy(() -> customerService.getCustomerById(999L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Покупатель с id 999 не найден");
    }
}