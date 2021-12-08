package com.example.shares.enums;

/**
 * @author lijiawei
 */
public enum AccountOperationEnum {

    BUY(1, false),
    SELL(2, true),
    RECHARGE(3, true),
    WITHDRAW(4, false);

    private final int code;
    private final boolean increase;

    public byte getCode() {
        return (byte) code;
    }

    public boolean isIncrease() {
        return increase;
    }

    AccountOperationEnum(int code, boolean increase) {
        this.code = code;
        this.increase = increase;
    }

}
