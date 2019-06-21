package com.fyqz.dto;

import lombok.Data;

import java.io.Serializable;
@Data
public class LoginUserDto implements Serializable {
    /**
     * 用户id
     */
    private String userId;
}