package com.fyqz.rpc.hystrix;


import com.fyqz.dto.UserDto;
import com.fyqz.result.Result;
import com.fyqz.result.ResultUtil;
import com.fyqz.rpc.UserServiceFeign;
import org.springframework.stereotype.Component;

/**
 * 需要实现 UserServiceFeign 接口
 *
 * 【注意点】feign是自带断路器hystrix的，只是没有默认打开hystrix。需要在配置文件中配置打开，将feign.hystrix.enabled设置为true，则hystrix断路器方可生效，如果不配置或者为false，则页面会报错
 *
 * @Component 将UserServiceHystrix类注入到Ioc容器中
 */
@Component
public class UserServiceHystrix implements UserServiceFeign {

    /**
     * 当 UserServiceFeign 服务不可用的时候，会回调这个方法
     * @return
     */
    @Override
    public Result queryUser(String userId) {
       return  ResultUtil.error(104,"查询用户出错");
    }


    @Override
    public Result addUser(UserDto user) {
        return  ResultUtil.error(104,"添加用户出错");
    }
}
