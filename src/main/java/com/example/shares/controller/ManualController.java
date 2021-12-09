package com.example.shares.controller;

import com.example.shares.bootstrap.DealBootstrap;
import com.example.shares.constants.RuntimeDataConstants;
import com.example.shares.dto.CurrentStockDto;
import com.example.shares.dto.req.ManualBuyReq;
import com.example.shares.model.Account;
import com.example.shares.model.Stock;
import com.example.shares.utils.StockUtil;
import com.example.shares.utils.UserInfoContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * @author lijiawei
 */
@RestController
@RequestMapping("manual")
public class ManualController {

    @RequestMapping("buy")
    public void buy(@Validated ManualBuyReq req) {
        Stock stock = RuntimeDataConstants.STOCK_CONTAINER.get(req.getStockCode());
        Assert.notNull(stock, "stock code is not exist");

        Account account;
        if (StringUtils.isBlank(req.getAccountName())) {
            account = RuntimeDataConstants.ACCOUNT_CONTAINER.get(0);
        } else {
            Optional<Account> accountOptional = RuntimeDataConstants.ACCOUNT_CONTAINER.stream().filter(a -> a.getName().equals(req.getAccountName())).findFirst();
            Assert.isTrue(accountOptional.isPresent(), "account name is not exist");
            account = accountOptional.get();
        }

        UserInfoContext.set(account);

        CurrentStockDto currentStockDto = StockUtil.grabCurrent(StockUtil.convertTypeCodeStock(stock));
        BigDecimal maxAmount = req.getMaxAmount();
        BigDecimal currentPrice = currentStockDto.getCurrentPrice();
        if (maxAmount != null) {
            Assert.isTrue(maxAmount.compareTo(currentPrice) >= 0, String.format("current price %s grater than max amount %s", currentPrice, maxAmount));
        }

        DealBootstrap.buy(stock, currentPrice, 100);
    }

}
