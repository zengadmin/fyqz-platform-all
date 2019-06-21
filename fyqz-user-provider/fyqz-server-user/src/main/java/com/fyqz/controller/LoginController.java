package com.fyqz.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.fyqz.dto.LoginDto;
import com.fyqz.model.User;
import com.fyqz.result.Result;
import com.fyqz.result.ResultUtil;
import com.fyqz.service.impl.UserServiceImpl;
import com.fyqz.userenum.UserErrorEnum;
import com.fyqz.util.DataUtil;
import com.fyqz.util.JwtUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@Api("用户登录管理")
public class LoginController {
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private UserServiceImpl userService;

    @ApiOperation("用户登录")
    @ApiImplicitParam(name = "loginDto", value = "用户信息", required = true, dataType = "LoginDto")
    @PostMapping("/userLogin")
    public Result userLogin(@RequestBody LoginDto loginDto) {
        Wrapper<User> userWrapper = new EntityWrapper<>();
        userWrapper.eq("name", loginDto.getUserName());
        userWrapper.eq("password", loginDto.getPassWord());
        if (DataUtil.isNotEmpty(userService.selectList(userWrapper))) {
            User user = userService.selectList(userWrapper).get(0);
            String token = jwtUtils.generateToken(user.getId());
            user.setToken(token);
            userService.update(user);
            return ResultUtil.success(user);
        }
        return ResultUtil.error(UserErrorEnum.USER_NOT_EXIT.getCode(), UserErrorEnum.USER_NOT_EXIT.getMsg());
    }
}