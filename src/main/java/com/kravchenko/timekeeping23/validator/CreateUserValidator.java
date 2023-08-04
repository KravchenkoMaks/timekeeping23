package com.kravchenko.timekeeping23.validator;


import com.kravchenko.timekeeping23.dto.CreateUserDto;
import lombok.NoArgsConstructor;

import static com.kravchenko.timekeeping23.util.CheckValidation.*;
import static lombok.AccessLevel.PRIVATE;


@NoArgsConstructor(access = PRIVATE)
public class CreateUserValidator implements Validator<CreateUserDto> {
    private static final CreateUserValidator INSTANCE = new CreateUserValidator();

    @Override
    public ValidationResult isValid(CreateUserDto object) {
        var validationResult = new ValidationResult();
        checkFirstAndLastName(validationResult, object.getFirstName(), object.getLastName());
        checkCreateEmail(validationResult, object.getEmail());
        checkPassword(validationResult, object.getPassword());

        return validationResult;
    }

    public static CreateUserValidator getInstance() {
        return INSTANCE;
    }
}
