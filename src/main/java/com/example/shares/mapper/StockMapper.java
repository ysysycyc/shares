package com.example.shares.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.shares.model.Stock;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author lijiawei
 */
@Mapper
public interface StockMapper extends BaseMapper<Stock> {
}