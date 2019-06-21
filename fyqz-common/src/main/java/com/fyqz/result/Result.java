package com.fyqz.result;


import lombok.Data;
/**
 * @author zengchao
 * @date
 */
@Data
public class Result<T>  {
    private Integer code;
    private String msg;
    private T data;
}