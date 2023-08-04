package com.kravchenko.timekeeping23.validator;

import com.kravchenko.timekeeping23.dto.UpdateActivityDto;
import com.kravchenko.timekeeping23.util.CheckValidation;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class UpdateActivityValidator implements Validator<UpdateActivityDto> {
    private static final UpdateActivityValidator INSTANCE = new UpdateActivityValidator();

    @Override
    public ValidationResult isValid(UpdateActivityDto object) {
        var validationResult = new ValidationResult();
        CheckValidation.checkActivityName(validationResult, object.getActivityName());
        CheckValidation.checkDoneDate(validationResult, object.getCreateDate(), object.getDoneDate());
        CheckValidation.checkEffortHrs(validationResult, object.getEffortHrs());
        return validationResult;
    }

    public static UpdateActivityValidator getInstance() {
        return INSTANCE;
    }
}
