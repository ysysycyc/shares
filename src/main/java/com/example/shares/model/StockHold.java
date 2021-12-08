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
    * 持有股票
    */
@Data
@TableName(value = "stock_hold")
public class StockHold {
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
     * 买入价格
     */
    @TableField(value = "buy_amount")
    private BigDecimal buyAmount;

    /**
     * 买入股数
     */
    @TableField(value = "buy_count")
    private Integer buyCount;

    /**
     * 买入时间
     */
    @TableField(value = "buy_time")
    private LocalDateTime buyTime;

    /**
     * 交易税
     */
    @TableField(value = "buy_tax")
    private BigDecimal buyTax;

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

    public static final String COL_BUY_AMOUNT = "buy_amount";

    public static final String COL_BUY_COUNT = "buy_count";

    public static final String COL_BUY_TIME = "buy_time";

    public static final String COL_BUY_TAX = "buy_tax";

    public static final String COL_ACCOUNT_ID = "account_id";

    public static final String COL_CREATE_TIME = "create_time";

    public static final String COL_UPDATE_TIME = "update_time";
}