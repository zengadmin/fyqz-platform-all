package com.fyqz.dto;

import lombok.Data;
import lombok.NonNull;

@Data
public class LoginDto {
    /**
     * 用户姓名
     */
    @NonNull
    private String userName;
    /**
     * 用户密码
     */
    @NonNull
    private String passWord;

    public LoginDto(@NonNull String userName, @NonNull String passWord) {
        this.userName = userName;
        this.passWord = passWord;
    }

    public LoginDto() {
    }
}