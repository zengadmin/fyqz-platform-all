package com.fyqz.rpc;

import com.fyqz.dto.UserDto;
import com.fyqz.result.Result;
import com.fyqz.rpc.hystrix.UserServiceHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 这里采用的是feign处理负载均衡的。
 *
 *
 */
@FeignClient(value = "fyqz-server-user",fallback = UserServiceHystrix.class)
public interface UserServiceFeign {

    @RequestMapping(value = "/queryUser/{userId}",method = RequestMethod.GET)
    Result queryUser(@PathVariable("userId") String userId);

    @RequestMapping(value = "/addUser", method=RequestMethod.POST)
    Result addUser(UserDto user);
}
