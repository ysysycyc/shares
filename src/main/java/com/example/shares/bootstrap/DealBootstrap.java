package com.example.shares.bootstrap;

import com.example.shares.dto.CurrentStockDto;
import com.example.shares.enums.SellReasonEnum;
import com.example.shares.model.Stock;
import com.example.shares.model.StockHold;
import com.example.shares.service.AccountService;
import com.example.shares.service.StockFlowService;
import com.example.shares.service.StockHoldService;
import com.example.shares.service.TransactionService;
import com.example.shares.utils.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * @author lijiawei
 */
@Slf4j
@Component
public class DealBootstrap implements ApplicationRunner {

    private static AccountService accountService;
    private static StockFlowService stockFlowService;
    private static StockHoldService stockHoldService;
    private static TransactionService transactionService;

    public static boolean buy(Stock stock, BigDecimal currentPrice, int buyCount) {
        log.info("buy stock {} {} current amount {} buy count {}",
                stock.getCode(), stock.getName(), currentPrice, buyCount);
        return transactionService.newTransaction(() -> {
            boolean success = accountService.buy(currentPrice, buyCount);
            if (success) {
                stockFlowService.buy(stock, currentPrice, buyCount);
                // 写入购买数据
                stockHoldService.buy(stock, currentPrice, buyCount);
                // 成功后修改账户和持股属性
                RefreshBootstrap.refresh();
                return true;
            }
            return false;
        });
    }

    public static boolean sell(StockHold stockHold, CurrentStockDto currentStockDto, SellReasonEnum sellReasonEnum) {
        log.info("sell stock {} sell amount {} sell count {} buy amount {}",
                currentStockDto.getStock().getCode(),
                currentStockDto.getCurrentPrice(),
                stockHold.getBuyCount(),
                stockHold.getBuyAmount());
        return transactionService.newTransaction(() -> {
            boolean success = accountService.sell(currentStockDto.getCurrentPrice(), stockHold.getBuyCount());
            if (success) {
                stockFlowService.sell(stockHold, currentStockDto, sellReasonEnum);
                stockHoldService.sell(stockHold);
                RefreshBootstrap.refresh();
                return true;
            }
            return false;
        });
    }

    @Override
    public void run(ApplicationArguments args) {
        DealBootstrap.accountService = SpringUtil.getBean(AccountService.class);
        DealBootstrap.stockFlowService = SpringUtil.getBean(StockFlowService.class);
        DealBootstrap.stockHoldService = SpringUtil.getBean(StockHoldService.class);
        DealBootstrap.transactionService = SpringUtil.getBean(TransactionService.class);
    }

}
