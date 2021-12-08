package com.example.shares.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.shares.dto.CurrentStockDto;
import com.example.shares.enums.SellReasonEnum;
import com.example.shares.enums.StockFlowOperationEnum;
import com.example.shares.model.Stock;
import com.example.shares.model.StockHold;
import com.example.shares.utils.UserInfoContext;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.shares.mapper.StockFlowMapper;
import com.example.shares.model.StockFlow;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author lijiawei
 */
@Service
public class StockFlowService extends ServiceImpl<StockFlowMapper, StockFlow> {

    public void buy(Stock stock, BigDecimal amount, int count) {
        this.saveFlow(stock.getId(), amount, null, count, StockFlowOperationEnum.BUY, null);
    }

    private void saveFlow(int stockId, BigDecimal amount, BigDecimal buyAmount, int count, StockFlowOperationEnum stockFlowOperationEnum, SellReasonEnum sellReasonEnum) {
        StockFlow stockFlow = new StockFlow();
        stockFlow.setStockId(stockId);
        stockFlow.setAccountId(UserInfoContext.getAccountId());
        stockFlow.setCount(count);
        stockFlow.setAmount(amount.multiply(BigDecimal.valueOf(count)));
        if (StockFlowOperationEnum.SELL.equals(stockFlowOperationEnum)) {
            stockFlow.setBuyAmount(buyAmount);
            stockFlow.setSellReason(sellReasonEnum.getCode());
        }
        stockFlow.setOperation(stockFlowOperationEnum.getCode());
        stockFlow.setTax(null);
        stockFlow.setDealTime(LocalDateTime.now());
        this.save(stockFlow);
    }

    public List<StockFlow> getTodaySellFlow() {
        LocalDate today = LocalDate.now();
        QueryWrapper<StockFlow> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(StockFlow.COL_OPERATION, StockFlowOperationEnum.SELL.getCode())
                .eq(StockFlow.COL_ACCOUNT_ID, UserInfoContext.getAccountId())
                .gt(StockFlow.COL_DEAL_TIME, today);
        return this.list(queryWrapper);
    }

    public void sell(StockHold stockHold, CurrentStockDto currentStockDto, SellReasonEnum sellReasonEnum) {
        this.saveFlow(stockHold.getStockId(), currentStockDto.getCurrentPrice(), stockHold.getBuyAmount(), stockHold.getBuyCount(), StockFlowOperationEnum.SELL, sellReasonEnum);
    }
}


