package com.fyqz.controller;


import com.fyqz.rpc.UserServiceFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;


/**
 * chess 服务
 */
@RestController
public class ScenarioController {

    @Autowired
    private UserServiceFeign userServiceFeign;

   /* *
     * 向前端提供一个访问地址；通过userServiceFeign调用服务并返回结果
     * @return
    @ApiOperation(value = "想定测试获取用户",notes = "想定测试获取用户")
    @RequestMapping(value = "/personScenario",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public String person(){
        String result = userServiceFeign.getUser();
        return result;
    }

    @ApiOperation(value = "测试获取想定用户",notes = "测试获想定取用户")
    @RequestMapping(value = "/userScenario",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public String user(){
        User user = new User();
        user.setName("I am  From userScenario");
        String result = userServiceFeign.hello(user);
        return result;
    }*/
}
