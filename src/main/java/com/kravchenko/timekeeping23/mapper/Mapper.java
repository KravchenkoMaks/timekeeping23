package com.kravchenko.timekeeping23.mapper;

public interface Mapper<F, T> {

    T mapFrom(F object);
}
