package com.example.shares.converter;

import com.example.shares.dto.GrabAllStockDto;
import com.example.shares.dto.StockDto;
import com.example.shares.model.Stock;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author lijiawei
 */
@Mapper
public interface StockConverter {

    StockConverter INSTANTS = Mappers.getMapper(StockConverter.class);

    @Mapping(target = "code", source = "f12")
    @Mapping(target = "name", source = "f14")
    Stock convertStock(GrabAllStockDto.Child.Item item);

    @Mapping(target = "currentPrice", source = "f2")
    @Mapping(target = "profit", source = "f3")
    @Mapping(target = "turnoverRate", source = "f8")
    @Mapping(target = "quantityRelativeRatio", source = "f10")
    @Mapping(target = "circulationMarketValue", source = "f21")
    StockDto convertStockDto(GrabAllStockDto.Child.Item item);

}
