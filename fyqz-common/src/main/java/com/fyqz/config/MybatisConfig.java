package com.fyqz.config;

import com.baomidou.mybatisplus.mapper.MetaObjectHandler;
import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.fyqz.constants.Constants;
import com.fyqz.dto.LoginUserDto;
import com.fyqz.util.DataUtil;
import com.fyqz.util.ThreadLocalMap;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author zengchao
 */
@Component
public class MybatisConfig extends MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        Object createTime = getFieldValByName("createTime", metaObject);
        Object id = getFieldValByName("id", metaObject);
        Object createId = getFieldValByName("createId", metaObject);
        if (id == null) {
            setFieldValByName("id", IdWorker.getIdStr(), metaObject);
        }
        if (createTime == null) {
            setFieldValByName("createTime", new Date(), metaObject);
        }
        if (createId == null) {
            LoginUserDto dto=(LoginUserDto) ThreadLocalMap.get(Constants.TOKEN_USER);
            if(DataUtil.isNotEmpty(dto)){
                setFieldValByName("createId", dto.getUserId(), metaObject);
            }
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        Object updateTime = getFieldValByName("updateTime", metaObject);
        Object updateId = getFieldValByName("updateId", metaObject);
        if (updateTime == null) {
            setFieldValByName("updateTime", new Date(), metaObject);
        }
        if (updateId == null) {
            LoginUserDto dto=(LoginUserDto) ThreadLocalMap.get(Constants.TOKEN_USER);
            if(DataUtil.isNotEmpty(dto)){
                setFieldValByName("updateId", dto.getUserId(), metaObject);
            }
        }
    }
}