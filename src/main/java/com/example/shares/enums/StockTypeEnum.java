package com.example.shares.enums;

import com.example.shares.utils.DataConvertUtil;

/**
 * @author lijiawei
 */
public enum StockTypeEnum {

    SZ(0, "sz"),
    SH(6, "sh");

    private final int code;
    private final String type;

    public int getCode() {
        return code;
    }

    public String getType() {
        return type;
    }

    StockTypeEnum(int code, String type) {
        this.code = code;
        this.type = type;
    }

    public static String getTypeByStockCode(String code) {
        StockTypeEnum[] values = StockTypeEnum.values();
        int c = DataConvertUtil.convertInteger(code.substring(0, 1));
        for (StockTypeEnum value : values) {
            if (value.getCode() == c) {
                return value.type;
            }
        }
        return null;
    }

}
