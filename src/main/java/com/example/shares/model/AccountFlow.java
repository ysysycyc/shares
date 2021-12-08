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
    * 资金账户流水记录
    */
@Data
@TableName(value = "account_flow")
public class AccountFlow {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 账户id
     */
    @TableField(value = "account_id")
    private Integer accountId;

    /**
     * 操作 1.买入 2.卖出 3.充值 4.提现
     */
    @TableField(value = "`operation`")
    private Byte operation;

    /**
     * 金额
     */
    @TableField(value = "amount")
    private BigDecimal amount;

    /**
     * 交易时间
     */
    @TableField(value = "deal_time")
    private LocalDateTime dealTime;

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

    public static final String COL_ACCOUNT_ID = "account_id";

    public static final String COL_OPERATION = "operation";

    public static final String COL_AMOUNT = "amount";

    public static final String COL_DEAL_TIME = "deal_time";

    public static final String COL_CREATE_TIME = "create_time";

    public static final String COL_UPDATE_TIME = "update_time";
}