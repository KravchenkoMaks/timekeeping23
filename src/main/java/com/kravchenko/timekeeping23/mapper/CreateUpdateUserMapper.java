package com.kravchenko.timekeeping23.mapper;

import com.kravchenko.timekeeping23.dto.UpdateUserDto;
import com.kravchenko.timekeeping23.entity.User;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class CreateUpdateUserMapper implements Mapper<UpdateUserDto, User> {

    private static final CreateUpdateUserMapper INSTANCE = new CreateUpdateUserMapper();
    @Override
    public User mapFrom(UpdateUserDto object) {
        return User.builder()
                .id(Integer.valueOf(object.getId()))
                .firstName(object.getFirstName())
                .lastName(object.getLastName())
                .email(object.getEmail())
                .role(object.getRole())
                .build();
    }

    public static CreateUpdateUserMapper getInstance(){
        return INSTANCE;
    }
}
