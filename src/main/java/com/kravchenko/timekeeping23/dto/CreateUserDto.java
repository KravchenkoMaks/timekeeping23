package com.kravchenko.timekeeping23.dto;

import com.kravchenko.timekeeping23.entity.Role;
import jakarta.servlet.http.Part;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateUserDto {
    String email;
    String firstName;
    String lastName;
    String password;
    Part image;
    Role role;
}
