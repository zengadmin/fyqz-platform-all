package com.fyqz.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.fyqz.dto.UserDto;
import com.fyqz.dto.UserPageDto;
import com.fyqz.model.User;
import com.fyqz.result.Result;
import com.fyqz.result.ResultUtil;
import com.fyqz.service.impl.UserServiceImpl;
import com.fyqz.util.DataUtil;
import com.fyqz.util.LogUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 服务调用者
 */
@Slf4j
@RestController
@Api("用户API管理")
public class UserController {
    @Autowired
    private UserServiceImpl userService;

    @ApiOperation("添加用户")
    @ApiImplicitParam(name = "user", value = "用户信息", required = true, dataType = "UserDto")
    @PostMapping("/addUser")
    public Result addUser(@RequestBody UserDto user) {
        LogUtil.debug(log, "用户信息={}", user);
        User userModel = new User();
        BeanUtils.copyProperties(user, userModel);
        userService.update(userModel);
        return ResultUtil.success(userModel);
    }

    @ApiOperation("查询用户列表")
    @ApiImplicitParam(name = "userPage", value = "用户列表", required = true, dataType = "UserPageDto")
    @PostMapping("/queryUserList")
    public Result queryUserList(@RequestBody UserPageDto userPage) {
        PageHelper.startPage(userPage.getPageNum(), userPage.getPageSize());
        Wrapper wrapper=new EntityWrapper<User>();
        if(DataUtil.isNotEmpty(userPage.getName())){
            wrapper.like("name",userPage.getName());
        }
        List<User> list=userService.selectList(wrapper);
        return ResultUtil.success(new PageInfo<>(list));
    }


    @ApiOperation(value = "根据ID获取用户", notes = "根据ID获取用户")
    @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "String")
    @RequestMapping(value = "/queryUser/{userId}", method = RequestMethod.GET)
    public Result queryUser(HttpServletRequest request, @PathVariable("userId") String userId) {
        LogUtil.debug(log, "根据ID获取用户,ID=【{}】", userId);
        User user = userService.queryById(userId);
        user.setMsg(request.getRequestURL().toString());
        return ResultUtil.success(user);
    }

    @ApiOperation(value = "根据ID删除用户", notes = "根据ID删除用户")
    @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "String")
    @RequestMapping(value = "/deleteUser/{userId}", method = RequestMethod.POST)
    public void deleteUser(HttpServletRequest request, @PathVariable("userId") String userId) {
        LogUtil.debug(log, "根据ID删除用户,ID=【{}】", userId);
        userService.delete(userId);
    }
}
