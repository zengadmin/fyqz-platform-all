package com.fyqz.model;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fyqz.base.BaseModel;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@TableName("user")
public class User extends BaseModel{

    @TableId("name")
    private String name;

    @TableId("msg")
    private String msg;

    @TableId("password")
    private String password;

    @TableId("token")
    private String token;
}