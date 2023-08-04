package com.kravchenko.timekeeping23.mapper;

import com.kravchenko.timekeeping23.dto.UpdateActivityDto;
import com.kravchenko.timekeeping23.entity.Activity;
import com.kravchenko.timekeeping23.entity.ActivityCategory;
import com.kravchenko.timekeeping23.entity.ActivityState;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class CreateUpdateActivityMapper implements Mapper<UpdateActivityDto, Activity> {
    private static final CreateUpdateActivityMapper INSTANCE =  new CreateUpdateActivityMapper();

    public static CreateUpdateActivityMapper getInstance(){
        return INSTANCE;
    }

    @Override
    public Activity mapFrom(UpdateActivityDto object) {
        return Activity.builder()
                .id(Integer.valueOf(object.getId()))
                .activityName(object.getActivityName())
                .description(object.getDescription())
                .doneDate(object.getDoneDate())
                .effortHrs(object.getEffortHrs())
                .category(ActivityCategory.valueOf(object.getCategory()))
                .state(ActivityState.valueOf(object.getState()))
                .build();
    }
}
