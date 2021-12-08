package com.example.shares.converter;

import com.example.shares.dto.GrabAllStockDto.Child.Item;
import com.example.shares.dto.StockDto;
import com.example.shares.model.Stock;
import java.math.BigDecimal;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-12-07T14:06:42+0800",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.13 (Azul Systems, Inc.)"
)
public class StockConverterImpl implements StockConverter {

    @Override
    public Stock convertStock(Item item) {
        if ( item == null ) {
            return null;
        }

        Stock stock = new Stock();

        stock.setCode( item.getF12() );
        stock.setName( item.getF14() );

        return stock;
    }

    @Override
    public StockDto convertStockDto(Item item) {
        if ( item == null ) {
            return null;
        }

        StockDto stockDto = new StockDto();

        if ( item.getF2() != null ) {
            stockDto.setCurrentPrice( new BigDecimal( item.getF2() ) );
        }
        if ( item.getF3() != null ) {
            stockDto.setProfit( new BigDecimal( item.getF3() ) );
        }
        if ( item.getF8() != null ) {
            stockDto.setTurnoverRate( new BigDecimal( item.getF8() ) );
        }
        if ( item.getF10() != null ) {
            stockDto.setQuantityRelativeRatio( new BigDecimal( item.getF10() ) );
        }
        if ( item.getF21() != null ) {
            stockDto.setCirculationMarketValue( new BigDecimal( item.getF21() ) );
        }

        return stockDto;
    }
}
