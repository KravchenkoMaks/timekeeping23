package com.kravchenko.timekeeping23.mapper;


import com.kravchenko.timekeeping23.dto.ReadActivityDto;
import com.kravchenko.timekeeping23.entity.Activity;

public class CreateReadActivityDtoMapper implements Mapper<Activity, ReadActivityDto> {

    private static final CreateReadActivityDtoMapper INSTANCE = new CreateReadActivityDtoMapper();

    private CreateReadActivityDtoMapper() {
    }

    @Override
    public ReadActivityDto mapFrom(Activity activity) {
        return ReadActivityDto.builder()
                .id(String.valueOf(activity.getId()))
                .name(activity.getActivityName())
                .category(activity.getCategory().name())
                .create(activity.getCreateDate().toString())
                .done(activity.getDoneDate().equals(activity.getCreateDate()) ? "" : activity.getDoneDate().toString())
                .effort(activity.getEffortHrs() == 0 ? "" : activity.getEffortHrs().toString())
                .user(activity.getUser())
                .state(activity.getState().name())
                .description(activity.getDescription())
                .build();
    }

    public static CreateReadActivityDtoMapper getInstance(){
        return INSTANCE;
    }
}
