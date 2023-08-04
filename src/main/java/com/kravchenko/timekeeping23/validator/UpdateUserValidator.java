package com.kravchenko.timekeeping23.validator;

import com.kravchenko.timekeeping23.dto.UpdateUserDto;
import com.kravchenko.timekeeping23.util.CheckValidation;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class UpdateUserValidator implements Validator<UpdateUserDto> {
    private static final UpdateUserValidator INSTANCE = new UpdateUserValidator();

    @Override
    public ValidationResult isValid(UpdateUserDto object) {
        var validationResult = new ValidationResult();
        CheckValidation.checkFirstAndLastName(validationResult, object.getFirstName(), object.getLastName());

        return validationResult;
    }

    public static UpdateUserValidator getInstance() {
        return INSTANCE;
    }
}
