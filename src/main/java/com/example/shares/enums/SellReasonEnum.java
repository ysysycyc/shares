package com.example.shares.enums;

import com.example.shares.constants.DefinedConstants;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author lijiawei
 */
public enum SellReasonEnum {

    FULL_PROFIT(1, "达到" + DefinedConstants.FULL_PROFIT.multiply(BigDecimal.valueOf(100)).setScale(0, RoundingMode.DOWN) + "%盈利"),
    LOW_PROFIT(2, "达到" + DefinedConstants.LOW_PROFIT.multiply(BigDecimal.valueOf(100)).setScale(0, RoundingMode.DOWN) + "%亏损"),
    FULL_TIME(3, "达到1个月的持股时间"),
    ;

    private final int code;
    private final String desc;

    public byte getCode() {
        return (byte) code;
    }

    public String getDesc() {
        return desc;
    }

    SellReasonEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
