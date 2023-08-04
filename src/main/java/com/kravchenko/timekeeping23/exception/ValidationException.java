package com.kravchenko.timekeeping23.exception;

import com.kravchenko.timekeeping23.validator.Error;
import lombok.Getter;

import java.util.List;

public class ValidationException extends RuntimeException{

    @Getter
    private final List<Error> errors;

    public ValidationException(List<Error> errors){

        this.errors = errors;
    }
}
