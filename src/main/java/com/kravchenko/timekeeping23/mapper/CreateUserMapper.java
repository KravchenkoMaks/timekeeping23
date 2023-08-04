package com.kravchenko.timekeeping23.mapper;

import com.kravchenko.timekeeping23.dto.CreateUserDto;
import com.kravchenko.timekeeping23.entity.User;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class CreateUserMapper implements Mapper<CreateUserDto, User> {

    private static final CreateUserMapper INSTANCE = new CreateUserMapper();
    private static final String IMAGE_FOLDER = "users/";
    private static final String DEFAULT_IMAGE = "defaultUserPhoto.jpg";

    @Override
    public User mapFrom(CreateUserDto object) {
        var imageName = object.getImage().getSubmittedFileName();

        return User.builder()
                .firstName(object.getFirstName())
                .lastName(object.getLastName())
                .email(object.getEmail())
                .password(object.getPassword())
                .image(IMAGE_FOLDER + (imageName.equals("") ? DEFAULT_IMAGE : imageName))
                .role(object.getRole())
                .build();
    }

    public static CreateUserMapper getInstance(){
        return INSTANCE;
    }
}
