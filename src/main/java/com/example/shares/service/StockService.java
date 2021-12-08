package com.example.shares.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.shares.constants.RuntimeDataConstants;
import com.example.shares.mapper.StockMapper;
import com.example.shares.model.Stock;
import com.example.shares.utils.StockUtil;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lijiawei
 */
@Service
public class StockService extends ServiceImpl<StockMapper, Stock> {

    public void init() {
        List<Stock> stockList = this.findValidList();
        if (CollectionUtils.isEmpty(stockList)) {
            stockList = StockUtil.grabAll();
            this.saveBatch(stockList, 800);
        }

        RuntimeDataConstants.STOCK_CONTAINER.clear();
        RuntimeDataConstants.STOCK_CONTAINER.putAll(stockList, Stock::getId, Stock::getCode);
    }

    private List<Stock> findValidList() {
        QueryWrapper<Stock> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(Stock.COL_IS_VALID, 1);
        return this.list(queryWrapper);
    }

    public void refresh() {
        List<Stock> stocks = StockUtil.grabAll();
        this.updateByGrab(stocks);
    }

    private void updateByGrab(List<Stock> updateStockList) {
        List<Stock> stockList = Lists.newArrayList(RuntimeDataConstants.STOCK_CONTAINER.values());
        if (CollectionUtils.isEmpty(stockList)) {
            this.saveBatch(updateStockList);
            return;
        }

        // 如果有新的，插入
        List<String> stockCodeList = stockList.stream().map(Stock::getCode).collect(Collectors.toList());
        List<Stock> newStockList = stockList.stream().filter(stock -> !stockCodeList.contains(stock.getCode())).collect(Collectors.toList());
        this.saveBatch(newStockList);
    }

}

