package com.example.shares.constants;

import com.example.shares.model.Account;
import com.example.shares.model.Stock;
import com.example.shares.model.StockHold;
import com.example.shares.utils.DoubleKeyMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

/**
 * @author lijiawei
 */
public class RuntimeDataConstants {

    /**
     * 是否开盘
     */
    public static boolean STOCK_OPEN = false;

    /**
     * key1 - stock id
     * key2 - stock code
     */
    public static final DoubleKeyMap<Integer, String, Stock> STOCK_CONTAINER = new DoubleKeyMap<>();

    public static final List<Account> ACCOUNT_CONTAINER = Lists.newArrayList();

    /**
     * key - accountId
     */
    public static final Map<Integer, List<StockHold>> ACCOUNT_HOLD_STOCK_CONTAINER = Maps.newHashMap();

}
