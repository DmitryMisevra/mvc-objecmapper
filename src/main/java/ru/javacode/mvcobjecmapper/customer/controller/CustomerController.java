package ru.javacode.mvcobjecmapper.customer.controller;


import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.javacode.mvcobjecmapper.customer.service.CustomerService;

@RestController
@RequestMapping(path = "/api/v1/customers")
@AllArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping(produces = "application/json")
    ResponseEntity<String> createCustomer(@RequestBody String createCustomerJson) {
        return new ResponseEntity<>(customerService.createCustomer(createCustomerJson), HttpStatus.CREATED);
    }

    @PutMapping(path = "/{customerId}", produces = "application/json")
    ResponseEntity<String> updateCustomer(@PathVariable @Min(1) Long customerId,
                                          @RequestBody String updateCustomerJson) {
        return ResponseEntity.ok(customerService.updateCustomer(customerId, updateCustomerJson));
    }

    @GetMapping(path = "/{customerId}", produces = "application/json")
    ResponseEntity<String> getCustomerById(@PathVariable @Min(1) Long customerId) {
        return ResponseEntity.ok(customerService.getCustomerById(customerId));
    }

    @DeleteMapping(path = "/{customerId}", produces = "application/json")
    ResponseEntity<Void> deleteCustomerById(@PathVariable @Min(1) Long customerId) {
        customerService.deleteCustomerById(customerId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(produces = "application/json")
    ResponseEntity<String> getAllCustomers() {
        return ResponseEntity.ok(customerService.getAllCustomers());
    }
}
