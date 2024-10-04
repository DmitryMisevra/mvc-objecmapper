package ru.javacode.mvcobjecmapper.util;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

import java.util.Set;

public class ValidationUtils {

    public static <T> void validate(Validator validator, T object) {
        Set<ConstraintViolation<T>> violations = validator.validate(object);
        if (!violations.isEmpty()) {
            StringBuilder errorMessage = new StringBuilder("Ошибка валидации: ");
            for (ConstraintViolation<T> violation : violations) {
                errorMessage.append(violation.getPropertyPath()).append(" ").append(violation.getMessage()).append("; ");
            }
            throw new RuntimeException(errorMessage.toString());
        }
    }
}
