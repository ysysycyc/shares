package com.example.shares.controller;

import com.example.shares.constants.RuntimeDataConstants;
import com.example.shares.dto.CurrentStockDto;
import com.example.shares.model.StockHold;
import com.example.shares.schedule.StockSchedule;
import com.example.shares.utils.StockUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Builder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author lijiawei
 */
@RestController
@RequestMapping("stock")
public class StockController {

    @Autowired
    private StockSchedule stockSchedule;

    @RequestMapping("manualrun")
    public void manualrun() {
        stockSchedule.loopPotentialShareSchedule();
    }

    @RequestMapping("info")
    public Object info() {
        Map<String, Object> map = Maps.newHashMap();
        map.put("stockopen", RuntimeDataConstants.STOCK_OPEN);
        map.put("account", RuntimeDataConstants.ACCOUNT_CONTAINER);
        map.put("stockhold", RuntimeDataConstants.ACCOUNT_HOLD_STOCK_CONTAINER);
        return map;
    }

    @RequestMapping("balance")
    public List<AccountBalance> balance() {
        List<AccountProfitStock> accountProfitStockList = this.profit();
        Map<Integer, AccountProfitStock> accountProfitStockMap = accountProfitStockList.stream().collect(Collectors.toMap(AccountProfitStock::getAccountId, Function.identity()));
        return RuntimeDataConstants.ACCOUNT_CONTAINER.stream().map(account -> {
            BigDecimal accountBalance = account.getBalanceAmount().add(account.getFrozenAmount());
            AccountProfitStock accountProfitStock = accountProfitStockMap.get(account.getId());
            BigDecimal totalAmount = accountProfitStock.getProfitStockList().stream().map(ps -> ps.getCurrentAmount().multiply(BigDecimal.valueOf(ps.getHoldCount()))).reduce(accountBalance, BigDecimal::add);
            return AccountBalance.builder().accountName(account.getName()).balanceAmount(totalAmount).build();
        }).collect(Collectors.toList());
    }

    @RequestMapping("profit")
    public List<AccountProfitStock> profit() {
        return RuntimeDataConstants.ACCOUNT_CONTAINER.stream().map(account -> {
            List<StockHold> stockHoldList = Optional.ofNullable(RuntimeDataConstants.ACCOUNT_HOLD_STOCK_CONTAINER.get(account.getId())).orElse(Lists.newArrayList());
            List<Integer> stockIdList = stockHoldList.stream().map(StockHold::getStockId).collect(Collectors.toList());
            Map<Integer, CurrentStockDto> currentStockDtoMap = StockUtil.grabCurrentMap(StockUtil.convertTypeCodeStock(stockIdList));

            List<ProfitStock> profitStockList = stockHoldList.stream().map(stockHold -> {
                CurrentStockDto currentStockDto = currentStockDtoMap.get(stockHold.getStockId());
                if (currentStockDto == null) {
                    return null;
                }
                return ProfitStock.builder()
                        .code(currentStockDto.getStock().getCode())
                        .name(currentStockDto.getStock().getName())
                        .currentAmount(currentStockDto.getCurrentPrice())
                        .buyAmount(stockHold.getBuyAmount())
                        .holdCount(stockHold.getBuyCount())
                        .profit(currentStockDto.getCurrentPrice().subtract(stockHold.getBuyAmount()).multiply(BigDecimal.valueOf(stockHold.getBuyCount())))
                        .build();
            }).filter(Objects::nonNull).collect(Collectors.toList());

            return AccountProfitStock.builder().accountId(account.getId()).accountName(account.getName()).profitStockList(profitStockList).build();
        }).collect(Collectors.toList());
    }

    @Data
    @Builder
    public static class AccountBalance {

        private String accountName;

        private BigDecimal balanceAmount;

    }

    @Data
    @Builder
    public static class AccountProfitStock {

        private int accountId;

        private String accountName;

        private List<ProfitStock> profitStockList;

    }

    @Data
    @Builder
    public static class ProfitStock {

        private String code;

        private String name;

        private BigDecimal buyAmount;

        private int holdCount;

        private BigDecimal currentAmount;

        private BigDecimal profit;

    }

}
