package com.kravchenko.timekeeping23.entity;

import com.kravchenko.timekeeping23.dto.ReadUserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Activity {
    private Integer id;
    private String activityName;
    private ActivityCategory category;
    private LocalDate createDate;
    private LocalDate doneDate;
    private Integer effortHrs;
    private ReadUserDto user;
    private ActivityState state;
    private String description;


}
