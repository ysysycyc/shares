package com.example.shares.dto;

import lombok.Data;

import java.util.List;

/**
 * @author lijiawei
 */
@Data
public class GrabAllStockDto {

    private String rc;

    private String rt;

    private String svr;

    private String lt;

    private String full;

    private Child data;

    @Data
    public static class Child {

        private Integer total;

        private List<Item> diff;

        @Data
        public static class Item {

            /**
             * 当前价格
             */
            private String f2;

            /**
             * 涨跌幅
             */
            private String f3;

            /**
             * 换手率
             */
            private String f8;

            /**
             * 量比
             */
            private String f10;

            /**
             * 代码
             */
            private String f12;

            /**
             * 名称
             */
            private String f14;

            /**
             * 流通市值
             */
            private String f21;

        }

    }

}
