package com.kravchenko.timekeeping23.validator;

import lombok.Value;

@Value(staticConstructor = "of")
public class Error {
    String code;
    String massage;
}
