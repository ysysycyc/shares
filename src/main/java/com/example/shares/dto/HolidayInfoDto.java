package com.example.shares.dto;

import lombok.Data;

/**
 * @author lijiawei
 */
@Data
public class HolidayInfoDto {

    private Integer code;

    private Type type;

    private Holiday holiday;

    @Data
    public static class Type {

        /**
         * 节假日类型，分别表示 工作日(0)、周末(1)、节日(2)、调休(3)
         */
        private Integer type;

        private String name;

        private Integer week;

    }

    @Data
    public static class Holiday {

        /**
         * 是否是节假日
         */
        private Boolean holiday;

        /**
         * 节假日的中文名
         */
        private String name;

        /**
         * 薪资倍数，1表示是1倍工资
         */
        private Integer wage;

        /**
         * 只在调休下有该字段。true表示放完假后调休，false表示先调休再放假
         */
        private Boolean after;

        /**
         * 调休的节假日
         */
        private String target;

    }

    public boolean isWeekday() {
        return type != null && type.getType() == 0;
    }

}
