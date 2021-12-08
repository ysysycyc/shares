package com.example.shares.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.shares.model.Account;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

/**
 * @author lijiawei
 */
@Mapper
public interface AccountMapper extends BaseMapper<Account> {

    int increase(@Param("accountId") Integer accountId, @Param("amount") BigDecimal amount);

    int reduce(@Param("accountId") Integer accountId, @Param("amount") BigDecimal amount);

}