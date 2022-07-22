package com.vectorx.smartcampus.pojo;

import lombok.Data;

@Data
public class LoginForm
{

    private String username;
    private String password;
    private String verifyCode;
    private Integer userType;

}
