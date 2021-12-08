package com.example.shares.bootstrap;

import com.example.shares.constants.RuntimeDataConstants;
import com.example.shares.model.Account;
import com.example.shares.model.StockHold;
import com.example.shares.service.AccountService;
import com.example.shares.service.StockHoldService;
import com.example.shares.utils.SpringUtil;
import com.example.shares.utils.UserInfoContext;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * @author lijiawei
 */
@Component
public class RefreshBootstrap implements ApplicationRunner {

    private static AccountService accountService;
    private static StockHoldService stockHoldService;

    public static void refresh() {
        refreshAccountContainer();
        refreshAccountHoldStockContainer();
    }

    /**
     * refresh current user account data
     */
    public static void refreshAccountContainer() {
        Account account = accountService.getById(UserInfoContext.getAccountId());
        // modify reference
        Optional<Account> accountOptional = RuntimeDataConstants.ACCOUNT_CONTAINER.stream().filter(a -> a.getId().equals(account.getId())).findFirst();
        accountOptional.ifPresent(value -> BeanUtils.copyProperties(account, value));
    }

    /**
     * refresh hold stock by accountId
     */
    public static void refreshAccountHoldStockContainer() {
        int accountId = UserInfoContext.getAccountId();
        List<StockHold> stockHoldList = stockHoldService.getByAccountId(accountId);
        RuntimeDataConstants.ACCOUNT_HOLD_STOCK_CONTAINER.put(accountId, stockHoldList);
    }

    @Override
    public void run(ApplicationArguments args) {
        RefreshBootstrap.accountService = SpringUtil.getBean(AccountService.class);
        RefreshBootstrap.stockHoldService = SpringUtil.getBean(StockHoldService.class);
    }

}
