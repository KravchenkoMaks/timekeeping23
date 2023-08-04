package com.kravchenko.timekeeping23.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ReadUserDto {
    String id;
    String email;
    String firstName;
    String lastName;
    String role;
    String image;
}
