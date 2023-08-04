package com.kravchenko.timekeeping23.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ReadActivityDto {
    String id;
    String name;
    String category;
    String create;
    String  done;
    String effort;
    ReadUserDto user;
    String state;
    String description;
}
