package com.vectorx.smartcampus.pojo;

public enum UserType
{
    ADMIN(1),

    STUDENT(2),

    TEACHER(3);

    private Integer userType;

    private UserType(Integer userType) {
        this.userType = userType;
    }

    public Integer getUserType() {
        return userType;
    }
}
