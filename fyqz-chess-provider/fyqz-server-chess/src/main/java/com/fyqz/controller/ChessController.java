package com.fyqz.controller;


import com.fyqz.dto.UserDto;
import com.fyqz.result.Result;
import com.fyqz.rpc.UserServiceFeign;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * chess 服务
 */
@RestController
public class ChessController {

    @Autowired
    private UserServiceFeign userServiceFeign;

    /**
     * 向前端提供一个访问地址；通过userServiceFeign调用服务并返回结果
     * @return
     */

    @ApiOperation(value = "棋子管理根据ID获取用户", notes = "棋子管理根据ID获取用户")
    @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "String")
    @RequestMapping(value = "/queryChessUser/{userId}", method = RequestMethod.GET)
    public Result queryUser(@PathVariable("userId") String userId) {
        return userServiceFeign.queryUser(userId);
    }


    @ApiOperation("棋子管理添加用户")
    @ApiImplicitParam(name = "user", value = "用户信息", required = true, dataType = "UserDto")
    @PostMapping("/addChessUser")
    public Result addUser(@RequestBody UserDto user) {
        return  userServiceFeign.addUser(user);
    }
}
