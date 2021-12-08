package com.example.shares.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * @author lijiawei
 */
/**
    * 资金账户
    */
@Data
@TableName(value = "account")
public class Account {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 账户名
     */
    @TableField(value = "`name`")
    private String name;

    /**
     * 可用金额
     */
    @TableField(value = "balance_amount")
    private BigDecimal balanceAmount;

    /**
     * 冻结金额
     */
    @TableField(value = "frozen_amount")
    private BigDecimal frozenAmount;

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

    public static final String COL_NAME = "name";

    public static final String COL_BALANCE_AMOUNT = "balance_amount";

    public static final String COL_FROZEN_AMOUNT = "frozen_amount";

    public static final String COL_CREATE_TIME = "create_time";

    public static final String COL_UPDATE_TIME = "update_time";
}