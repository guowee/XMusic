package com.uowee.xmusic.net;

import android.content.Context;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.util.concurrent.TimeUnit;

/**
 * Created by GuoWee on 2018/3/8.
 */

public class HttpUtils {
    public static final OkHttpClient mOkHttpClient = new OkHttpClient();


    public static JsonObject getResponseFromJson(Context context, String url, boolean forceCache) {
        mOkHttpClient.setConnectTimeout(1000, TimeUnit.MINUTES);
        mOkHttpClient.setReadTimeout(1000, TimeUnit.MINUTES);
        Request.Builder builder = new Request.Builder()
                .header("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:0.9.4)")
                .url(url);
        Request request = builder.build();
        Response response = null;
        try {
            response = mOkHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                String c = response.body().string();
                JsonParser parser = new JsonParser();
                JsonElement el = parser.parse(c);
                return el.getAsJsonObject();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }


}
