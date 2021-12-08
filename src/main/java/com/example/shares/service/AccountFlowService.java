package com.example.shares.service;

import com.example.shares.enums.AccountOperationEnum;
import com.example.shares.model.Account;
import com.example.shares.utils.UserInfoContext;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.shares.mapper.AccountFlowMapper;
import com.example.shares.model.AccountFlow;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author lijiawei
 */
@Service
public class AccountFlowService extends ServiceImpl<AccountFlowMapper, AccountFlow> {

    public void buy(BigDecimal amount, LocalDateTime dealTime) {
        this.saveFlow(amount, dealTime, AccountOperationEnum.BUY);
    }

    public void sell(BigDecimal amount, LocalDateTime dealTime) {
        this.saveFlow(amount, dealTime, AccountOperationEnum.SELL);
    }

    public void recharge(BigDecimal amount, LocalDateTime dealTime) {
        this.saveFlow(amount, dealTime, AccountOperationEnum.RECHARGE);
    }

    public void withdraw(BigDecimal amount, LocalDateTime dealTime) {
        this.saveFlow(amount, dealTime, AccountOperationEnum.WITHDRAW);
    }

    public void saveFlow(BigDecimal amount, LocalDateTime dealTime, AccountOperationEnum accountOperationEnum) {
        AccountFlow accountFlow = new AccountFlow();
        accountFlow.setAccountId(UserInfoContext.getAccountId());
        accountFlow.setAmount(amount);
        accountFlow.setDealTime(dealTime);
        accountFlow.setOperation(accountOperationEnum.getCode());
        this.save(accountFlow);
    }

}
