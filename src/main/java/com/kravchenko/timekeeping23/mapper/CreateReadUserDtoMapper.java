package com.kravchenko.timekeeping23.mapper;

import com.kravchenko.timekeeping23.dto.ReadUserDto;
import com.kravchenko.timekeeping23.entity.User;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class CreateReadUserDtoMapper implements Mapper<User, ReadUserDto> {

    private static final CreateReadUserDtoMapper INSTANCE = new CreateReadUserDtoMapper();

    @Override
    public ReadUserDto mapFrom(User object) {
        return ReadUserDto.builder()
                .id(String.valueOf(object.getId()))
                .firstName(object.getFirstName())
                .lastName(object.getLastName())
                .email(object.getEmail())
                .role(object.getRole().name())
                .image(object.getImage())
                .build();
    }

    public static CreateReadUserDtoMapper getInstance(){
        return INSTANCE;
    }
}
