package com.kravchenko.timekeeping23.dto;

import com.kravchenko.timekeeping23.entity.Role;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UpdateUserDto {
    String id;
    String email;
    String firstName;
    String lastName;
    Role role;
}
