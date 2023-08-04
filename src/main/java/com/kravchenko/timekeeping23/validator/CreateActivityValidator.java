package com.kravchenko.timekeeping23.validator;

import com.kravchenko.timekeeping23.dto.CreateActivityDto;
import lombok.NoArgsConstructor;

import static com.kravchenko.timekeeping23.util.CheckValidation.*;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class CreateActivityValidator implements Validator<CreateActivityDto> {

    private static final CreateActivityValidator INSTANCE = new CreateActivityValidator();

    @Override
    public ValidationResult isValid(CreateActivityDto object) {
        var validationResult = new ValidationResult();
        checkExistEmail(validationResult, object.getUser().getEmail());
        checkActivityName(validationResult, object.getActivityName());

        return validationResult;
    }

    public static CreateActivityValidator getInstance() {
        return INSTANCE;
    }
}
