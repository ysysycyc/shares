package com.example.shares.dto;

import com.example.shares.model.Stock;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author lijiawei
 */
@Data
@Builder
public class CurrentStockDto {

    private Stock stock;

    /**
     * 名称 - 1
     */
    private String name;

    /**
     * 代码 - 2
     */
    private String code;

    /**
     * 当前价格 - 3
     */
    private BigDecimal currentPrice;

    /**
     * 昨日收盘价 - 4
     */
    private BigDecimal yesterdayClosePrice;

    /**
     * 今日开盘价 - 5
     */
    private BigDecimal todayOpenPrice;

    /**
     * 成交量（手） - 6
     */
    private BigDecimal dealCount;

    /**
     * 外盘 - 7
     */
    private BigDecimal outDisk;

    /**
     * 内盘 - 8
     */
    private BigDecimal innerDisk;

    /**
     * 买一价格 - 9
     */
    private BigDecimal buy1Price;

    /**
     * 买一量（手）- 10
     */
    private BigDecimal buy1Count;

    /**
     * 买二价格 - 11
     */
    private BigDecimal buy2Price;

    /**
     * 买二量（手）- 12
     */
    private BigDecimal buy2Count;

    /**
     * 买三价格 - 13
     */
    private BigDecimal buy3Price;

    /**
     * 买三量（手）- 14
     */
    private BigDecimal buy3Count;

    /**
     * 买四价格 - 15
     */
    private BigDecimal buy4Price;

    /**
     * 买四量（手）- 16
     */
    private BigDecimal buy4Count;

    /**
     * 买五价格 - 17
     */
    private BigDecimal buy5Price;

    /**
     * 买五量（手）- 18
     */
    private BigDecimal buy5Count;

    /**
     * 卖一价格 - 19
     */
    private BigDecimal sell1Price;

    /**
     * 卖一量（手）- 20
     */
    private BigDecimal sell1Count;

    /**
     * 卖二价格 - 21
     */
    private BigDecimal sell2Price;

    /**
     * 卖二量（手）- 22
     */
    private BigDecimal sell2Count;

    /**
     * 卖三价格 - 23
     */
    private BigDecimal sell3Price;

    /**
     * 卖三量（手）- 24
     */
    private BigDecimal sell3Count;

    /**
     * 卖四价格 - 25
     */
    private BigDecimal sell4Price;

    /**
     * 卖四量（手）- 26
     */
    private BigDecimal sell4Count;

    /**
     * 卖五价格 - 27
     */
    private BigDecimal sell5Price;

    /**
     * 卖五量（手）- 28
     */
    private BigDecimal sell5Count;

    /**
     * 日期 - 30
     * 原始yyyyMMddHHmmss
     */
    private LocalDateTime time;

    /**
     * 涨跌金额 - 31
     */
    private BigDecimal profitAmount;

    /**
     * 涨跌百分比 - 32
     */
    private BigDecimal profitPercent;

    /**
     * 今日最高价 - 33
     */
    private BigDecimal todayMaxPrice;

    /**
     * 今日最低价 - 34
     */
    private BigDecimal todayMinPrice;

    /**
     * 成交量（手）- 36
     */
//    private BigDecimal dealCount;

    /**
     * 成交金额（万）- 37
     */
    private BigDecimal dealAmount;

    /**
     * 换手率 - 38
     */
    private BigDecimal turnoverRate;

    /**
     * 市盈率 - 39
     */
    private BigDecimal marketProfit;

    /**
     * 流通市值 - 44
     */
    private BigDecimal circulationMarketValue;

    /**
     * 总市值 - 45
     */
    private BigDecimal totalMarketValue;

    /**
     * 市净率 - 46
     */
    private BigDecimal priceToBookRatio;

    /**
     * 涨停价 - 47
     */
    private BigDecimal treadLimit;

    /**
     * 跌停价 - 48
     */
    private BigDecimal dropLimit;

}
