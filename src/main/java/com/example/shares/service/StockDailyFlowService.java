package com.example.shares.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.shares.mapper.StockDailyFlowMapper;
import com.example.shares.model.StockDailyFlow;
import com.example.shares.model.StockFlow;
import com.example.shares.model.StockHold;
import com.example.shares.utils.UserInfoContext;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @author lijiawei
 */
@Service
public class StockDailyFlowService extends ServiceImpl<StockDailyFlowMapper, StockDailyFlow> {

    public void dailyRecord(StockHold stockHold, BigDecimal currentPrice) {
        StockDailyFlow stockDailyFlow = new StockDailyFlow();
        stockDailyFlow.setStockId(stockHold.getStockId());
        stockDailyFlow.setAccountId(UserInfoContext.getAccountId());
        stockDailyFlow.setHoldAmount(stockHold.getBuyAmount());
        stockDailyFlow.setCloseAmount(currentPrice);
        stockDailyFlow.setHoldCount(stockHold.getBuyCount());
        BigDecimal profitAmount = currentPrice.subtract(stockHold.getBuyAmount()).multiply(BigDecimal.valueOf(stockHold.getBuyCount()));
        stockDailyFlow.setProfitAmount(profitAmount);
        this.save(stockDailyFlow);
    }

    public void dailyRecord(StockFlow stockFlow) {
        StockDailyFlow stockDailyFlow = new StockDailyFlow();
        stockDailyFlow.setStockId(stockFlow.getStockId());
        stockDailyFlow.setAccountId(UserInfoContext.getAccountId());
        stockDailyFlow.setHoldAmount(stockFlow.getBuyAmount());
        stockDailyFlow.setHoldCount(stockFlow.getCount());
        BigDecimal profitAmount = stockFlow.getAmount().subtract(stockFlow.getBuyAmount()).multiply(BigDecimal.valueOf(stockFlow.getCount()));
        stockDailyFlow.setProfitAmount(profitAmount);
        this.save(stockDailyFlow);
    }

}

