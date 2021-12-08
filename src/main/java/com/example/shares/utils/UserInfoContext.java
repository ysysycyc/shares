package com.example.shares.utils;

import com.example.shares.model.Account;

/**
 * @author lijiawei
 */
public class UserInfoContext {

    private static final ThreadLocal<Account> threadLocal = new ThreadLocal<>();

    private UserInfoContext() {

    }

    public static void set(Account account) {
        threadLocal.set(account);
    }

    public static Account get() {
        return threadLocal.get();
    }

    public static int getAccountId() {
        return get().getId();
    }

    public static void clear() {
        threadLocal.remove();
    }

}
