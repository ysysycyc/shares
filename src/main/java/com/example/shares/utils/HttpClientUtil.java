package com.example.shares.utils;

import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * @author lijiawei
 */
@Slf4j
public class HttpClientUtil {

    public static String get(String url) {
        try {
            OkHttpClient client = new OkHttpClient().newBuilder().build();
            Request request = new Request.Builder().url(url).get().build();
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                ResponseBody responseBody = response.body();
                return responseBody.string();
            }
        } catch (Exception e) {
            log.error(String.format("http client get error url [%s]", url), e);
        }

        return null;
    }

}
