package com.fyqz.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserDto implements Serializable {
    /**
     * 用户姓名
     */
    private String name;
    /**
     * 用户描述
     */
    private String msg;
}