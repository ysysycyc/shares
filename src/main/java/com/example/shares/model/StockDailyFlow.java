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
 * 股票每日盈亏流水
 */
@Data
@TableName(value = "stock_daily_flow")
public class StockDailyFlow {
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
     * 持有金额
     */
    @TableField(value = "hold_amount")
    private BigDecimal holdAmount;

    /**
     * 收盘价格
     */
    @TableField(value = "close_amount")
    private BigDecimal closeAmount;

    /**
     * 盈亏
     */
    @TableField(value = "profit_amount")
    private BigDecimal profitAmount;

    /**
     * 持有股数
     */
    @TableField(value = "hold_count")
    private Integer holdCount;

    /**
     * 交易账号
     */
    @TableField(value = "account_id")
    private Integer accountId;

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

    public static final String COL_HOLD_AMOUNT = "hold_amount";

    public static final String COL_CLOSE_AMOUNT = "close_amount";

    public static final String COL_PROFIT_AMOUNT = "profit_amount";

    public static final String COL_HOLD_COUNT = "hold_count";

    public static final String COL_ACCOUNT_ID = "account_id";

    public static final String COL_CREATE_TIME = "create_time";

    public static final String COL_UPDATE_TIME = "update_time";
}