package com.example.shares.converter;

import com.alibaba.fastjson.JSONObject;
import com.example.shares.dto.HolidayInfoDto;

/**
 * @author lijiawei
 */
public class HolidayConverter {

    public static HolidayInfoDto convert(String result) {
        return JSONObject.parseObject(result, HolidayInfoDto.class);
    }

}
