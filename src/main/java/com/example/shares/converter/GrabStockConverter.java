package com.example.shares.converter;

import com.alibaba.fastjson.JSONObject;
import com.example.shares.constants.DefinedConstants;
import com.example.shares.constants.RuntimeDataConstants;
import com.example.shares.dto.CurrentStockDto;
import com.example.shares.dto.GrabAllStockDto;
import com.example.shares.dto.StockDto;
import com.example.shares.enums.StockTypeEnum;
import com.example.shares.model.Stock;
import com.example.shares.utils.DataConvertUtil;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lijiawei
 */
@Slf4j
public class GrabStockConverter {

    public static List<Stock> convertGrabAll(String result) {
        GrabAllStockDto grabAllStockDto = JSONObject.parseObject(result, GrabAllStockDto.class);
        List<GrabAllStockDto.Child.Item> diff = grabAllStockDto.getData().getDiff();
        log.info("totally grab stocks {} count", diff.size());

        diff = filterHuShen(diff);

        return diff.stream().map(d -> {
            Stock stock = StockConverter.INSTANTS.convertStock(d);
            stock.setType(StockTypeEnum.getTypeByStockCode(stock.getCode()));
            return stock;
        }).collect(Collectors.toList());
    }

    public static List<StockDto> convertGrabProfit(String result) {
        GrabAllStockDto grabAllStockDto = JSONObject.parseObject(result, GrabAllStockDto.class);
        List<GrabAllStockDto.Child.Item> diff = grabAllStockDto.getData().getDiff();

        diff = filterHuShen(diff);

        return diff.stream().map(d -> {
            StockDto stock = StockConverter.INSTANTS.convertStockDto(d);
            stock.setStock(RuntimeDataConstants.STOCK_CONTAINER.get(d.getF12()));
            return stock;
        }).collect(Collectors.toList());
    }

    private static List<GrabAllStockDto.Child.Item> filterHuShen(List<GrabAllStockDto.Child.Item> diff) {
        // 只要沪深两股
        List<String> huShenStocksPrefixList = Arrays.asList(DefinedConstants.HU_SHEN_STOCKS_PREFIX);
        diff = diff.stream().filter(d -> {
            String prefix = d.getF12().substring(0, 3);
            return huShenStocksPrefixList.contains(prefix);
        }).collect(Collectors.toList());
        log.info("hu shen stocks after filter {} count", diff.size());
        return diff;
    }

    public static List<CurrentStockDto> convertGrabAppoint(String result) {
        if (StringUtils.isBlank(result)) {
            log.info("convertGrabAppoint param result is null");
            return Lists.newArrayList();
        }
        result = result.replaceAll("\n", "");
        String[] stockArr = result.split(";");

        return Arrays.stream(stockArr).map(s -> {
            String[] stockSplit = s.split("=");
            String varRef = stockSplit[0];
            String dataRef = stockSplit[1];
            String data = dataRef.substring(1, dataRef.length() - 1);
            String[] n = data.split("~");

            return CurrentStockDto.builder()
                    .stock(RuntimeDataConstants.STOCK_CONTAINER.get(n[2]))
                    .name(n[1])
                    .code(n[2])
                    .currentPrice(DataConvertUtil.convertBigDecimal(n[3]))
                    .yesterdayClosePrice(DataConvertUtil.convertBigDecimal(n[4]))
                    .todayOpenPrice(DataConvertUtil.convertBigDecimal(n[5]))
                    .dealCount(DataConvertUtil.convertBigDecimal(n[6]))
                    .outDisk(DataConvertUtil.convertBigDecimal(n[7]))
                    .innerDisk(DataConvertUtil.convertBigDecimal(n[8]))
                    .buy1Price(DataConvertUtil.convertBigDecimal(n[9]))
                    .buy1Count(DataConvertUtil.convertBigDecimal(n[10]))
                    .buy2Price(DataConvertUtil.convertBigDecimal(n[11]))
                    .buy2Count(DataConvertUtil.convertBigDecimal(n[12]))
                    .buy3Price(DataConvertUtil.convertBigDecimal(n[13]))
                    .buy3Count(DataConvertUtil.convertBigDecimal(n[14]))
                    .buy4Price(DataConvertUtil.convertBigDecimal(n[15]))
                    .buy4Count(DataConvertUtil.convertBigDecimal(n[16]))
                    .buy5Price(DataConvertUtil.convertBigDecimal(n[17]))
                    .buy5Count(DataConvertUtil.convertBigDecimal(n[18]))
                    .sell1Price(DataConvertUtil.convertBigDecimal(n[19]))
                    .sell1Count(DataConvertUtil.convertBigDecimal(n[20]))
                    .sell2Price(DataConvertUtil.convertBigDecimal(n[21]))
                    .sell2Count(DataConvertUtil.convertBigDecimal(n[22]))
                    .sell3Price(DataConvertUtil.convertBigDecimal(n[23]))
                    .sell3Count(DataConvertUtil.convertBigDecimal(n[24]))
                    .sell4Price(DataConvertUtil.convertBigDecimal(n[25]))
                    .sell4Count(DataConvertUtil.convertBigDecimal(n[26]))
                    .sell5Price(DataConvertUtil.convertBigDecimal(n[27]))
                    .sell5Count(DataConvertUtil.convertBigDecimal(n[28]))
                    .profitAmount(DataConvertUtil.convertBigDecimal(n[31]))
                    .profitPercent(DataConvertUtil.convertBigDecimal(n[32]))
                    .todayMaxPrice(DataConvertUtil.convertBigDecimal(n[33]))
                    .todayMinPrice(DataConvertUtil.convertBigDecimal(n[34]))
                    .dealAmount(DataConvertUtil.convertBigDecimal(n[37]))
                    .turnoverRate(DataConvertUtil.convertBigDecimal(n[38]))
                    .marketProfit(DataConvertUtil.convertBigDecimal(n[39]))
                    .circulationMarketValue(DataConvertUtil.convertBigDecimal(n[44]))
                    .totalMarketValue(DataConvertUtil.convertBigDecimal(n[45]))
                    .priceToBookRatio(DataConvertUtil.convertBigDecimal(n[46]))
                    .treadLimit(DataConvertUtil.convertBigDecimal(n[47]))
                    .dropLimit(DataConvertUtil.convertBigDecimal(n[48]))
                    .build();
        }).collect(Collectors.toList());

    }

}
