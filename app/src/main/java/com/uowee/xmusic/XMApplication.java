package com.uowee.xmusic;

import android.app.Application;

import com.google.gson.Gson;

/**
 * Created by GuoWee on 2018/2/28.
 */

public class XMApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
    }

    private static Gson gson;

    public static Gson gsonInstance() {
        if (gson == null) {
            gson = new Gson();
        }
        return gson;
    }
}
