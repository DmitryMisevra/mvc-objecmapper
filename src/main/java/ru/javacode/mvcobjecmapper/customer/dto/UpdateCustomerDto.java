package ru.javacode.mvcobjecmapper.customer.dto;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.javacode.mvcobjecmapper.customer.validator.ValidPhoneNumber;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UpdateCustomerDto {

    private String firstName;
    private String lastName;
    @Email(message = "некорректный формат email")
    private String email;
    @ValidPhoneNumber
    private String contactNumber;
}
