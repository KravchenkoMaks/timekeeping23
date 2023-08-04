package com.kravchenko.timekeeping23.mapper;

import com.kravchenko.timekeeping23.dto.CreateActivityDto;
import com.kravchenko.timekeeping23.entity.Activity;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class CreateActivityMapper implements Mapper<CreateActivityDto, Activity> {
    private static final CreateActivityMapper INSTANCE = new CreateActivityMapper();

    @Override
    public Activity mapFrom(CreateActivityDto object) {
        LocalDate date = LocalDate.now();
        return Activity.builder()
                .activityName(object.getActivityName())
                .category(object.getCategory())
                .createDate(date)
                .doneDate(date)
                .effortHrs(0)
                .user(object.getUser())
                .state(object.getState())
                .description(object.getDescription())
                .build();
    }

    public static CreateActivityMapper getInstance(){
        return INSTANCE;
    }
}

