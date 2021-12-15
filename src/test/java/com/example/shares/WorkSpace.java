package com.example.shares;

import com.example.shares.model.Account;
import com.example.shares.service.AccountService;
import com.example.shares.service.TransactionService;
import com.example.shares.utils.SpringUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;

/**
 * 用于各种验证的测试类
 *
 * @author lijiawei
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class WorkSpace {

    @Autowired
    private AccountService accountService;
    @Autowired
    private TransactionService transactionService;

    @Test
    public void getAccount() {
        Account account = accountService.getById(1);
        System.out.println(account);
    }

    @Test
    public void transactionTemplateTest() {
        doTransaction();
    }

    private static void doTransaction() {
        TransactionService bean = SpringUtil.getBean(TransactionService.class);
        AccountService a = SpringUtil.getBean(AccountService.class);
        bean.newTransaction(Propagation.REQUIRED, () -> {
            Account a1 = new Account();
            a1.setName("a1");
            a.save(a1);
            Account a2 = new Account();
            a2.setName("a2");
            a.save(a2);
            return null;
        });
    }

    public static void main(String[] args) {
        firstSynchronized();
    }

    /**
     * 验证synchronized锁id是否有可重入的问题
     */
    public static void firstSynchronized() {
        synchronized ("test".intern()) {
            System.out.println("first");
            duplicateSynchronized();
        }
    }

    public static void duplicateSynchronized() {
        synchronized ("test".intern()) {
            System.out.println("duplicate");
        }
    }



}
