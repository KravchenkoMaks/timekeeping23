package com.kravchenko.timekeeping23.util;

import com.kravchenko.timekeeping23.exception.DBException;
import com.kravchenko.timekeeping23.service.UserService;
import com.kravchenko.timekeeping23.validator.Error;
import com.kravchenko.timekeeping23.validator.ValidationResult;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;


@UtilityClass
public class CheckValidation {

    private final UserService userService = UserService.getInstance();

    private static final String NAME_REGEX = "^(?!.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$)(?!.*[@#$%^&-+=()]).{2,20}$";
    private static final String EMAIL_REGEX = "[a-z0-9]+@[a-z]+\\.[a-z]{2,3}";
    private static final String PASSWORD_REGEX = "^(?=.*[0-9])(?!.*[a-z])(?!.*[A-Z])(?=\\S+$)(?!.*[@#$%^&-+=()]).{3,10}$";


    public static void checkFirstAndLastName(ValidationResult validationResult, String firstName, String lastName) {

        if (firstName == null || !firstName.matches(NAME_REGEX)) {
//            validationResult.add(Error.of("invalid.firstName", "firstName is invalid"));
        }
        if (lastName == null || !lastName.matches(NAME_REGEX)) {
//            validationResult.add(Error.of("invalid.lastName", "lastName is invalid"));
        }
    }

    public static void checkCreateEmail(ValidationResult validationResult, String email) {
        if (email == null || !email.matches(EMAIL_REGEX)) {
            validationResult.add(Error.of("invalid.email", "the user already exists"));
        } else if (isUser(email)) {
            validationResult.add(Error.of("invalid.email", "the user already exists"));
        }
    }

    public static void checkExistEmail(ValidationResult validationResult, String email) {
        if (email == null || !email.matches(EMAIL_REGEX)) {
            validationResult.add(Error.of("invalid.email", "email is invalid"));
        } else if (!isUser(email)) {
            validationResult.add(Error.of("invalid.email", "the user already exists"));
        }
    }

    private boolean isUser(String email) {

        try {
            if (userService.findByEmail(email).isPresent()) {
                return true;
            }
        } catch (DBException ignored) {
        }
        return false;
    }

    public static void checkPassword(ValidationResult validationResult, String password) {
        if (password == null || !password.matches(PASSWORD_REGEX)) {
            validationResult.add(Error.of("invalid.password", "password is invalid"));
        }
    }

    public static void checkActivityName(ValidationResult validationResult, String activityName) {
        if (activityName == null || activityName.trim().isEmpty()) {
            validationResult.add(Error.of("invalid.activity", "activity is invalid"));
        }
    }

    public static void checkDoneDate(ValidationResult validationResult, LocalDate createDate, LocalDate doneDate) {
        if (!createDate.isEqual(doneDate)) {
            if (createDate.isAfter(doneDate)) {
                validationResult.add(Error.of("invalid.date", "date is invalid"));
            }
        }
    }

    public static void checkEffortHrs(ValidationResult validationResult, Integer effortHrs) {
        if(effortHrs < 0){
            validationResult.add(Error.of("invalid.effort", "effort is invalid"));
        }
    }
}

