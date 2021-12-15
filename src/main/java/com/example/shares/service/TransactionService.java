package com.example.shares.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.concurrent.Callable;

/**
 * @author lijiawei
 */
@Service
public class TransactionService {

    @Autowired
    private DataSourceTransactionManager dataSourceTransactionManager;

    public <T> T newTransaction(Callable<T> runnable) {
        return this.newTransaction(Propagation.REQUIRED.value(), Isolation.DEFAULT.value(), runnable);
    }

    public <T> T newTransaction(Propagation propagation, Callable<T> runnable) {
        return this.newTransaction(propagation.value(), Isolation.DEFAULT.value(), runnable);
    }

    private <T> T newTransaction(int level, int isoLevel, Callable<T> runnable) {
        TransactionTemplate transactionTemplate = new TransactionTemplate(dataSourceTransactionManager);
        transactionTemplate.setPropagationBehavior(level);
        transactionTemplate.setIsolationLevel(isoLevel);
        return transactionTemplate.execute(status -> {
            try {
                return runnable.call();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

}
