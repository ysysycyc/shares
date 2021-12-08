package com.example.shares.schedule;

import com.example.shares.bootstrap.DealBootstrap;
import com.example.shares.constants.DefinedConstants;
import com.example.shares.constants.RuntimeDataConstants;
import com.example.shares.dto.CurrentStockDto;
import com.example.shares.dto.StockDto;
import com.example.shares.enums.SellReasonEnum;
import com.example.shares.model.Account;
import com.example.shares.model.StockFlow;
import com.example.shares.model.StockHold;
import com.example.shares.service.StockDailyFlowService;
import com.example.shares.service.StockFlowService;
import com.example.shares.utils.AccountUtil;
import com.example.shares.utils.StockUtil;
import com.example.shares.utils.UserInfoContext;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author lijiawei
 */
@Slf4j
@Component
public class StockSchedule {

    @Autowired
    private StockDailyFlowService stockDailyFlowService;
    @Autowired
    private StockFlowService stockFlowService;

    /**
     * 每天开盘前初始化开盘状态信息
     * 每天9点执行
     */
    @Scheduled(cron = "0 0 9 * * ?")
    public void setStockOpenSchedule() {
        StockUtil.setStockOpen();
    }

    /**
     * 每天收盘前半小时轮询符合买入条件的股票
     * 每天14:30每5分钟执行一次
     */
    @Scheduled(cron = "0 30/5 14 * * ?")
    public void loopPotentialShareSchedule() {
        if (RuntimeDataConstants.STOCK_OPEN) {
            AccountUtil.autoContextForeach(this::loopPotentialShare);
        }
    }

    /**
     * 每天9:30-10:55每5分钟执行一次
     */
    @Scheduled(cron = "0 30/5 9-10 * * ?")
    public void loopProfitShareMorning1Schedule() {
        log.info("loopProfitShareMorning1Schedule start running...");
        this.loopProfitShareSchedule();
        log.info("loopProfitShareMorning1Schedule finish running...");
    }

    /**
     * 每天11：00-11:25每5分钟执行一次
     */
    @Scheduled(cron = "0 0-25/5 11 * * ?")
    public void loopProfitShareMorning2Schedule() {
        log.info("loopProfitShareMorning2Schedule start running...");
        this.loopProfitShareSchedule();
        log.info("loopProfitShareMorning2Schedule finish running...");
    }

    private void loopProfitShareSchedule() {
        if (RuntimeDataConstants.STOCK_OPEN) {
            AccountUtil.autoContextForeach(this::loopProfitShare);
        }
    }

    /**
     * 每天13:00-14:55每5分钟执行一次
     */
    @Scheduled(cron = "0 0/5 13-14 * * ?")
    public void loopProfitShareAfternoonSchedule() {
        log.info("loopProfitShareAfternoonSchedule start running...");
        this.loopProfitShareSchedule();
        log.info("loopProfitShareAfternoonSchedule finish running...");
    }

    /**
     * 查看持股中，抛售满足一下条件的持股
     * 1.如果有亏损15%以上的
     * 2.如果有盈利20%以上的
     * 3.如果持股超过1个月
     *
     * @see com.example.shares.enums.SellReasonEnum
     */
    private synchronized void loopProfitShare() {
        Account account = UserInfoContext.get();
        List<StockHold> stockHoldList = RuntimeDataConstants.ACCOUNT_HOLD_STOCK_CONTAINER.get(account.getId());
        if (CollectionUtils.isEmpty(stockHoldList)) {
            log.info("account {} had no hold stock", account.getName());
            return;
        }

        // 过滤今天买的，当天买的不能卖
        List<StockHold> stockHoldListBefore = stockHoldList.stream().filter(stockHold -> {
            LocalDate now = LocalDate.now();
            return stockHold.getBuyTime().toLocalDate().compareTo(now) < 0;
        }).collect(Collectors.toList());

        if (CollectionUtils.isEmpty(stockHoldListBefore)) {
            log.info("account {} only had today hold stock", account.getName());
            return;
        }

        List<Integer> stockIdList = stockHoldListBefore.stream().map(StockHold::getStockId).collect(Collectors.toList());
        Map<Integer, CurrentStockDto> currentStockDtoMap = StockUtil.grabCurrentMap(StockUtil.convertTypeCodeStock(stockIdList));

        stockHoldListBefore.forEach(stockHold -> {
            CurrentStockDto currentStockDto = currentStockDtoMap.get(stockHold.getStockId());
            if (currentStockDto == null) {
                log.error("account {} grab no current stockId {}", account.getName(), stockHold.getStockId());
                return;
            }
            BigDecimal profitPercent = currentStockDto.getCurrentPrice().subtract(stockHold.getBuyAmount()).divide(stockHold.getBuyAmount(), RoundingMode.HALF_DOWN);

            if (profitPercent.compareTo(DefinedConstants.FULL_PROFIT) >= 0) {
                DealBootstrap.sell(stockHold, currentStockDto, SellReasonEnum.FULL_PROFIT);
                return;
            } else if (profitPercent.add(DefinedConstants.LOW_PROFIT).compareTo(BigDecimal.ZERO) <= 0) {
                DealBootstrap.sell(stockHold, currentStockDto, SellReasonEnum.LOW_PROFIT);
                return;
            }

            // 判断持股时间
            Period between = Period.between(stockHold.getBuyTime().toLocalDate(), LocalDate.now());
            if (between.getMonths() >= 1) {
                DealBootstrap.sell(stockHold, currentStockDto, SellReasonEnum.FULL_TIME);
            }
        });
    }

    /**
     * 每天收盘计算当天收益
     * 每天15点执行
     */
    @Scheduled(cron = "30 0 15 * * ?")
    public void calcDailyFlowSchedule() {
        if (RuntimeDataConstants.STOCK_OPEN) {
            AccountUtil.autoContextForeach(this::calcDailyFlow);
        }
    }

    /**
     * 计算当前持有股票的收益和当日卖出股票的收益
     */
    private void calcDailyFlow() {
        this.calcHoldDailyFlow();
        this.calcSellDailyFlow();
    }

    private void calcHoldDailyFlow() {
        int accountId = UserInfoContext.getAccountId();
        List<StockHold> stockHoldList = RuntimeDataConstants.ACCOUNT_HOLD_STOCK_CONTAINER.get(accountId);
        if (CollectionUtils.isEmpty(stockHoldList)) {
            log.info("calcHoldDailyFlow accountId {} has no data", accountId);
            return;
        }

        // 询价
        List<Integer> stockIdList = stockHoldList.stream().map(StockHold::getStockId).collect(Collectors.toList());
        Map<Integer, CurrentStockDto> currentStockDtoMap = StockUtil.grabCurrentMap(StockUtil.convertTypeCodeStock(stockIdList));

        stockHoldList.forEach(stockHold -> {
            CurrentStockDto currentStockDto = currentStockDtoMap.get(stockHold.getStockId());
            if (currentStockDto == null) {
                log.error("stockId {} not found current stock info, no daily flow recorded", stockHold.getStockId());
                return;
            }

            BigDecimal currentPrice = currentStockDto.getCurrentPrice();
            stockDailyFlowService.dailyRecord(stockHold, currentPrice);
        });
    }

    private void calcSellDailyFlow() {
        // 查询当天的交易
        int accountId = UserInfoContext.getAccountId();
        List<StockFlow> stockFlowList = stockFlowService.getTodaySellFlow();
        if (CollectionUtils.isEmpty(stockFlowList)) {
            log.info("calcSellDailyFlow accountId {} has no data", accountId);
        }

        stockFlowList.forEach(stockFlow -> stockDailyFlowService.dailyRecord(stockFlow));
    }

    /**
     * 每天14:30点开始，每5分钟执行一次
     *
     * 每天2点半开始每隔5分钟查询一次潜力股
     * 潜力股定义：
     * 1.涨幅在3~5之间
     * 2.量比大于1
     * 3.换手率5-10之间
     * 4.流通市值50-200亿
     * 5.
     * 6.
     */
    private synchronized void loopPotentialShare() {
        Account account = UserInfoContext.get();
        log.info("loopPotentialShare start running account {}...", account.getName());
        List<StockDto> profitStockList = StockUtil.grabProfit();
        if (CollectionUtils.isEmpty(profitStockList)) {
            log.info("no data grab by profit account {}", account.getName());
            return;
        }

        List<StockDto> filterProfitStockList = profitStockList.stream().filter(p -> {
            boolean profitGt3, profitLt5, quantityRelativeRatioGt1, turnoverRateGt5, turnoverRateLt10,
                    circulationMarketValueGt50e, circulationMarketValueLt200e;
            return (profitGt3 = p.getProfit().compareTo(BigDecimal.valueOf(3)) > 0)
                    && (profitLt5 = p.getProfit().compareTo(BigDecimal.valueOf(5)) < 0)
                    && (quantityRelativeRatioGt1 = p.getQuantityRelativeRatio().compareTo(BigDecimal.ONE) > 0)
                    && (turnoverRateGt5 = p.getTurnoverRate().compareTo(BigDecimal.valueOf(5)) > 0)
                    && (turnoverRateLt10 = p.getTurnoverRate().compareTo(BigDecimal.valueOf(10)) < 0)
                    && (circulationMarketValueGt50e = p.getCirculationMarketValue().compareTo(BigDecimal.valueOf(50_0000_0000L)) > 0)
                    && (circulationMarketValueLt200e = p.getCirculationMarketValue().compareTo(BigDecimal.valueOf(200_0000_0000L)) < 0)
                    ;
        }).collect(Collectors.toList());

        // todo expression 5.6

        if (CollectionUtils.isEmpty(filterProfitStockList)) {
            log.info("no data grab by profit after filter account {}", account.getName());
            return;
        }

        // 如果不在已购中，买入潜力股
        List<StockHold> stockHoldList = Optional.ofNullable(RuntimeDataConstants.ACCOUNT_HOLD_STOCK_CONTAINER.get(UserInfoContext.getAccountId())).orElse(Lists.newArrayList());
        List<Integer> stockIdHoldList = stockHoldList.stream().map(StockHold::getStockId).collect(Collectors.toList());
        filterProfitStockList = filterProfitStockList.stream().filter(f -> !stockIdHoldList.contains(f.getStock().getId())).collect(Collectors.toList());

        if (CollectionUtils.isEmpty(filterProfitStockList)) {
            log.info("account {} all profit stock is hold", account.getName());
            return;
        }

        log.info("account {} filter profit stock -> {}", account.getName(), filterProfitStockList);

        // 购买
        filterProfitStockList.forEach(stock -> {
            int buyCount = 100;
            // 判断余额是否足够
            BigDecimal totalAmount = stock.getCurrentPrice().multiply(BigDecimal.valueOf(buyCount));
            if (account.getBalanceAmount().compareTo(totalAmount) < 0) {
                log.info("account {} not had enough amount", account.getName());
                return;
            }
            boolean success = DealBootstrap.buy(stock, buyCount);
            if (!success) {
                log.error("buy error stock code {} account {}", stock.getStock().getCode(), account.getName());
            }
        });

        log.info("loopPotentialShare finish running account {}...", account.getName());
    }


}
