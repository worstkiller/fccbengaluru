package com.android.buffer.fccbengaluru;

import android.app.Application;
import android.support.multidex.MultiDex;

import com.android.buffer.fccbengaluru.repository.SharedPreference;
import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

/**
 * Created by incred on 3/12/17.
 */

public class AppController extends Application {

    private static AppController appController;

    /**
     * retuns a Appcontroller instance
     * @return
     */
    public static AppController getAppController(){
        return appController;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
        Fabric.with(this, new Crashlytics());
        appController = this;
    }
}
