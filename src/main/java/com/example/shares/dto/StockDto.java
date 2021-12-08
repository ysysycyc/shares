package com.example.shares.dto;

import com.example.shares.model.Stock;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author lijiawei
 */
@Data
public class StockDto {

    private Stock stock;

    /**
     * 当前价格
     */
    private BigDecimal currentPrice;

    /**
     * 涨跌
     */
    private BigDecimal profit;

    /**
     * 量比
     */
    private BigDecimal quantityRelativeRatio;

    /**
     * 换手率
     */
    private BigDecimal turnoverRate;

    /**
     * 流通市值
     */
    private BigDecimal circulationMarketValue;

}
