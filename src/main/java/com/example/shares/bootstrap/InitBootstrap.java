package com.example.shares.bootstrap;

import com.example.shares.service.AccountService;
import com.example.shares.service.StockHoldService;
import com.example.shares.service.StockService;
import com.example.shares.utils.StockUtil;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author lijiawei
 */
@Component
public class InitBootstrap implements InitializingBean {

    @Autowired
    private AccountService accountService;
    @Autowired
    private StockService stockService;
    @Autowired
    private StockHoldService stockHoldService;

    @Override
    public void afterPropertiesSet() {
        stockService.init();
        accountService.init();
        stockHoldService.init();
        StockUtil.setStockOpen();
    }

}
