package com.fyqz.result;

/**
 * @author zengchao
 * @date
 */
public class ResultUtil {
    /**
     * 成功且带数据
     **/
    public static Result success(Object object) {
        Result result = new Result();
        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setMsg(ResultEnum.SUCCESS.getMsg());
        result.setData(object);
        return result;
    }

    /**
     * 成功 自定义code，msg
     **/
    public static Result success(Integer code, String msg) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

    /**
     * 成功且带数据 自定义code，msg
     **/
    public static Result success(Integer code, String msg, Object object) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(object);
        return result;
    }

    /**
     * 成功返回默认code, msg
     **/
    public static Result success() {
        Result result = new Result();
        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setMsg(ResultEnum.SUCCESS.getMsg());
        return result;
    }

    /**
     * 错误且带数据
     **/
    public static Result error(Object object) {
        Result result = new Result();
        result.setCode(ResultEnum.UNKNOWN_ERROR.getCode());
        result.setMsg(ResultEnum.UNKNOWN_ERROR.getMsg());
        result.setData(object);
        return result;
    }

    /**
     * 错误 自定义code，msg
     **/
    public static Result error(Integer code, String msg) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

    /**
     * 错误返回默认code, msg
     **/
    public static Result error() {
        Result result = new Result();
        result.setCode(ResultEnum.ERROR.getCode());
        result.setMsg(ResultEnum.ERROR.getMsg());
        return result;
    }
}