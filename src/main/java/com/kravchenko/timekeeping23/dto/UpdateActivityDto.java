package com.kravchenko.timekeeping23.dto;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
public class UpdateActivityDto {
    String id;
    String activityName;
    String category;
    LocalDate createDate;
    LocalDate doneDate;
    Integer effortHrs;
    String state;
    String description;
}
