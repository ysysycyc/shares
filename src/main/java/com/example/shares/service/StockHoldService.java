package com.example.shares.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.shares.constants.RuntimeDataConstants;
import com.example.shares.mapper.StockHoldMapper;
import com.example.shares.model.Stock;
import com.example.shares.model.StockHold;
import com.example.shares.utils.UserInfoContext;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author lijiawei
 */
@Service
public class StockHoldService extends ServiceImpl<StockHoldMapper, StockHold> {

    public void init() {
        List<StockHold> stockHoldList = this.list();
        Map<Integer, List<StockHold>> stockHoldMap = stockHoldList.stream().collect(Collectors.groupingBy(StockHold::getAccountId));
        RuntimeDataConstants.ACCOUNT_HOLD_STOCK_CONTAINER.clear();
        RuntimeDataConstants.ACCOUNT_HOLD_STOCK_CONTAINER.putAll(stockHoldMap);
    }

    public List<StockHold> getByAccountId(int accountId) {
        QueryWrapper<StockHold> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(StockHold.COL_ACCOUNT_ID, accountId);
        return this.list(queryWrapper);
    }

    public void buy(Stock stock, BigDecimal currentPrice, int buyCount) {
        StockHold stockHold = new StockHold();
        stockHold.setStockId(stock.getId());
        stockHold.setAccountId(UserInfoContext.getAccountId());
        stockHold.setBuyAmount(currentPrice);
        stockHold.setBuyCount(buyCount);
        stockHold.setBuyTax(null);
        stockHold.setBuyTime(LocalDateTime.now());
        this.save(stockHold);
    }

    public void sell(StockHold stockHold) {
        this.removeById(stockHold.getId());
    }
}
