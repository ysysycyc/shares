package com.example.shares.dto.req;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author lijiawei
 */
@Data
public class ManualBuyReq {

    /**
     * 买入股票code
     */
    @NotBlank
    private String stockCode;

    /**
     * 买入数量
     */
    @NotNull
    private Integer buyCount;

    /**
     * 买入用户
     * default:第一个用户
     */
    private String accountName;

    /**
     * 支持的最高买入价
     */
    private BigDecimal maxAmount;

}
