package com.example.shares.enums;

/**
 * @author lijiawei
 */
public enum StockFlowOperationEnum {

    BUY(1),
    SELL(2);

    private final int code;

    public byte getCode() {
        return (byte) code;
    }

    StockFlowOperationEnum(int code) {
        this.code = code;
    }

}
