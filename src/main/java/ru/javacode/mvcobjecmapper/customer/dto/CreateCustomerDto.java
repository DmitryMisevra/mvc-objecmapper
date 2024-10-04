package ru.javacode.mvcobjecmapper.customer.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.javacode.mvcobjecmapper.customer.validator.ValidPhoneNumber;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CreateCustomerDto {

    @NotBlank(message = "Не указано имя покупателя")
    private String firstName;
    @NotBlank(message = "Не указана фамилия покупателя")
    private String lastName;
    @NotNull(message = "не указан email пользователя")
    @Email(message = "некорректный формат email")
    private String email;
    @ValidPhoneNumber
    private String contactNumber;
}
