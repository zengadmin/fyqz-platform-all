package com.fyqz.base;

import lombok.Data;

import java.io.Serializable;

@Data
public class BasePage implements Serializable{

    private Integer pageNum;

    private Integer pageSize;

    private String orderBy;
}