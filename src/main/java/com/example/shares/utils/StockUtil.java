package com.example.shares.utils;

import com.example.shares.constants.ResourceConstants;
import com.example.shares.constants.RuntimeDataConstants;
import com.example.shares.converter.GrabStockConverter;
import com.example.shares.converter.HolidayConverter;
import com.example.shares.dto.CurrentStockDto;
import com.example.shares.dto.HolidayInfoDto;
import com.example.shares.dto.StockDto;
import com.example.shares.model.Stock;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author lijiawei
 */
@Slf4j
public class StockUtil {

    public static boolean equals(Stock s1, Stock s2) {
        if (s1 == null || s2 == null) {
            return false;
        }

        return s1.getCode().equals(s2.getCode());
    }

    /**
     * grab all stock from the internet
     *
     * @return stock model list
     */
    public static List<Stock> grabAll() {
        String result = HttpClientUtil.get(ResourceConstants.GRAB_ALL_STOCK_URL);
        return GrabStockConverter.convertGrabAll(result);
    }

    public static List<StockDto> grabProfit() {
        String result = HttpClientUtil.get(ResourceConstants.GRAB_PROFIT_STOCK_URL);
        return GrabStockConverter.convertGrabProfit(result);
    }

    public static CurrentStockDto grabCurrent(String code) {
        return grabCurrent(Lists.newArrayList(code)).get(0);
    }

    public static List<CurrentStockDto> grabCurrent(List<String> stockCodeList) {
        String result = HttpClientUtil.get(ResourceConstants.GRAB_APPOINT_STOCK_PREFIX_URL + StringUtils.join(stockCodeList, ","));
        return GrabStockConverter.convertGrabAppoint(result);
    }

    public static Map<Integer, CurrentStockDto> grabCurrentMap(List<String> stockCodeList) {
        List<CurrentStockDto> currentStockDtoList = grabCurrent(stockCodeList);
        return currentStockDtoList.stream().collect(Collectors.toMap(s -> s.getStock().getId(), Function.identity()));
    }

    public static List<String> convertTypeCodeStock(List<Integer> stockIdList) {
        return stockIdList.stream().map(stockId -> {
            Stock stock = RuntimeDataConstants.STOCK_CONTAINER.get(stockId);
            return stock.getType() + stock.getCode();
        }).collect(Collectors.toList());
    }

    public static void setStockOpen() {
        int retry = 3;
        while (--retry > 0) {
            String result = HttpClientUtil.get(ResourceConstants.GET_HOLIDAY_URL);
            HolidayInfoDto holidayInfoDto = HolidayConverter.convert(result);
            if (holidayInfoDto == null || holidayInfoDto.getCode() != 0) {
                continue;
            }
            RuntimeDataConstants.STOCK_OPEN = holidayInfoDto.isWeekday();
            log.info("finish init stock open value [{}]", RuntimeDataConstants.STOCK_OPEN);
            break;
        }
    }

    public static void main(String[] args) {
        String result = HttpClientUtil.get(ResourceConstants.GET_HOLIDAY_URL + "2021-12-08");
        System.out.println(result);
    }

}
