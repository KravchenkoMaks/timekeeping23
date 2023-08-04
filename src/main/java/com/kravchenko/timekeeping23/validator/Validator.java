package com.kravchenko.timekeeping23.validator;

public interface Validator<T> {

    ValidationResult isValid(T object);
}
