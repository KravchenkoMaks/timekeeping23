package com.kravchenko.timekeeping23.dto;

import com.kravchenko.timekeeping23.entity.ActivityCategory;
import com.kravchenko.timekeeping23.entity.ActivityState;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
public class CreateActivityDto {
    String activityName;
    ActivityCategory category;
    LocalDate createDate;
    LocalDate doneDate;
    Integer effortHrs;
    ReadUserDto user;
    ActivityState state;
    String description;

}
