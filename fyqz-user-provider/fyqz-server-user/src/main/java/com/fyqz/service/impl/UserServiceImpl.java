package com.fyqz.service.impl;

import com.fyqz.base.BaseService;
import com.fyqz.mapper.UserMapper;
import com.fyqz.model.User;
import com.fyqz.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@CacheConfig(cacheNames = "userService")
public class UserServiceImpl extends BaseService<User,UserMapper> implements IUserService {

}