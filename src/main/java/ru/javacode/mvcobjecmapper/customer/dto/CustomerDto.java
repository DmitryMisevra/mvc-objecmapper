package ru.javacode.mvcobjecmapper.customer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CustomerDto {

    private Long customerId;
    private String firstName;
    private String lastName;
    private String email;
    private String contactNumber;
}
