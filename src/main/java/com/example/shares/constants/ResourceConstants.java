package com.example.shares.constants;

/**
 * @author lijiawei
 */
public class ResourceConstants {

    /**
     * 抓取所有股票
     */
    public static final String GRAB_ALL_STOCK_URL = "https://89.push2.eastmoney.com/api/qt/clist/get?pn=1&pz=5000" +
            "&po=1&np=1&ut=bd1d9ddb04089700cf9c27f6f7426281&fltt=2&invt=2&fid=f3&fs=m:0+t:6,m:0+t:80,m:1+t:2,m:1+t:23" +
            "&fields=f12,f14";

    public static final String GRAB_PROFIT_STOCK_URL = "https://89.push2.eastmoney.com/api/qt/clist/get?pn=1&pz=500" +
            "&po=1&np=1&ut=bd1d9ddb04089700cf9c27f6f7426281&fltt=2&invt=2&fid=f3&fs=m:0+t:6,m:0+t:80,m:1+t:2,m:1+t:23" +
            "&fields=f2,f3,f8,f10,f12,f14,f21";

    /**
     * 抓取指定股票的当前交易价
     */
    public static final String GRAB_APPOINT_STOCK_PREFIX_URL = "https://qt.gtimg.cn/q=";

    /**
     * 获取是否工作日的接口
     */
    public static final String GET_HOLIDAY_URL = "https://timor.tech/api/holiday/info/";

}
