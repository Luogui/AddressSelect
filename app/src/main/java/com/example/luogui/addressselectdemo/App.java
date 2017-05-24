package com.example.luogui.addressselectdemo;

import android.app.Application;
import android.content.Context;

/**
 * Created by  luogui on 2017/5/23.
 */

public class App extends Application {


    public static Context application;

    @Override
    public void onCreate() {
        super.onCreate();
        application = getApplicationContext();
        AddressInit.getInstance(this);
    }
}
