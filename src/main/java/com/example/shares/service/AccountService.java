package com.example.shares.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.shares.constants.RuntimeDataConstants;
import com.example.shares.enums.AccountOperationEnum;
import com.example.shares.mapper.AccountMapper;
import com.example.shares.model.Account;
import com.example.shares.utils.UserInfoContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author lijiawei
 */
@Slf4j
@Service
public class AccountService extends ServiceImpl<AccountMapper, Account> {

    @Autowired
    private AccountFlowService accountFlowService;
    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private TransactionService transactionService;

    public void init() {
        List<Account> accountList = this.list();
        RuntimeDataConstants.ACCOUNT_CONTAINER.clear();
        RuntimeDataConstants.ACCOUNT_CONTAINER.addAll(accountList);
    }

    public boolean buy(BigDecimal buyAmount, int buyCount) {
        return this.deal(buyAmount, buyCount, AccountOperationEnum.BUY);
    }

    public boolean sell(BigDecimal sellPrice, Integer sellCount) {
        return this.deal(sellPrice, sellCount, AccountOperationEnum.SELL);
    }

    public boolean deal(BigDecimal buyAmount, int buyCount, AccountOperationEnum accountOperationEnum) {
        int accountId = UserInfoContext.getAccountId();
        LocalDateTime dealTime = LocalDateTime.now();
        BigDecimal amount = buyAmount.multiply(BigDecimal.valueOf(buyCount));

        return transactionService.newTransaction(() -> {
            int count;
            if (accountOperationEnum.isIncrease()) {
                count = accountMapper.increase(accountId, amount);
            } else {
                count = accountMapper.reduce(accountId, amount);
            }
            if (count == 0) {
                log.error("accountId {} deal error for buyAmount {} operation {}", accountId, amount, accountOperationEnum.name());
                return false;
            }
            accountFlowService.saveFlow(amount, dealTime, accountOperationEnum);
            return true;
        });
    }

}
