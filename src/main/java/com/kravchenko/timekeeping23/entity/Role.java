package com.kravchenko.timekeeping23.entity;

public enum Role {

    ADMIN("1"),
    USER("2"),
    NEW("3");
    private String value;

    Role(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
