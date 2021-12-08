package com.example.shares.constants;

import java.math.BigDecimal;

/**
 * @author lijiawei
 */
public class DefinedConstants {

    /**
     * 沪深股票代码前缀
     */
    public static final String[] HU_SHEN_STOCKS_PREFIX = {"000", "002", "600", "601", "603"};

    /**
     * 跌破15%
     */
    public static final BigDecimal LOW_PROFIT = BigDecimal.valueOf(0.15);

    /**
     * 涨幅20%
     */
    public static final BigDecimal FULL_PROFIT = BigDecimal.valueOf(0.2);

}
