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
 * 股票交易记录
 */
@Data
@TableName(value = "stock_flow")
public class StockFlow {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 股票id
     */
    @TableField(value = "stock_id")
    private Integer stockId;

    /**
     * 操作 1.买入 2.卖出
     */
    @TableField(value = "`operation`")
    private Byte operation;

    /**
     * 交易价格
     */
    @TableField(value = "amount")
    private BigDecimal amount;

    /**
     * 买入价格，卖出时有值
     */
    @TableField(value = "buy_amount")
    private BigDecimal buyAmount;

    /**
     * 交易股数
     */
    @TableField(value = "`count`")
    private Integer count;

    /**
     * 交易税
     */
    @TableField(value = "tax")
    private BigDecimal tax;

    /**
     * 交易账号
     */
    @TableField(value = "account_id")
    private Integer accountId;

    /**
     * 卖出原因，卖出时有值
     */
    @TableField(value = "sell_reason")
    private Byte sellReason;

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

    public static final String COL_STOCK_ID = "stock_id";

    public static final String COL_OPERATION = "operation";

    public static final String COL_AMOUNT = "amount";

    public static final String COL_BUY_AMOUNT = "buy_amount";

    public static final String COL_COUNT = "count";

    public static final String COL_TAX = "tax";

    public static final String COL_ACCOUNT_ID = "account_id";

    public static final String COL_SELL_REASON = "sell_reason";

    public static final String COL_DEAL_TIME = "deal_time";

    public static final String COL_CREATE_TIME = "create_time";

    public static final String COL_UPDATE_TIME = "update_time";
}