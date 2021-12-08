package com.example.shares.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * @author lijiawei
 */
@Data
@TableName(value = "stock")
public class Stock {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 股票代码
     */
    @TableField(value = "code")
    private String code;

    /**
     * 股票名称
     */
    @TableField(value = "`name`")
    private String name;

    /**
     * 股票类型 sz.深股 sh.沪股
     */
    @TableField(value = "`type`")
    private String type;

    /**
     * 是否有效
     */
    @TableField(value = "is_valid")
    private Byte isValid;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    private LocalDateTime updateTime;

    public static final String COL_ID = "id";

    public static final String COL_CODE = "code";

    public static final String COL_NAME = "name";

    public static final String COL_TYPE = "type";

    public static final String COL_IS_VALID = "is_valid";

    public static final String COL_CREATE_TIME = "create_time";

    public static final String COL_UPDATE_TIME = "update_time";
}