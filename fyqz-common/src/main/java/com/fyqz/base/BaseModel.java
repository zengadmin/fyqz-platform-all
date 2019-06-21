package com.fyqz.base;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author zengchao
 * @date
 */
@Data
public abstract class BaseModel extends Model implements Serializable {
    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.ID_WORKER)
    private String id;

    /**
     * 创建人ID
     */
    @TableId(value = "create_id")
    private String createId;

    /**
     * 修改人ID
     */
    @TableId(value = "update_id")
    private String updateId;

    /**
     * 创建时间
     */
    @TableId(value = "create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 更新时间
     */
    @TableId(value = "update_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}