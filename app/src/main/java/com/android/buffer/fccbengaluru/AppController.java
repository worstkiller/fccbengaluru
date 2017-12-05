package com.android.buffer.fccbengaluru;

import android.app.Application;

import com.android.buffer.fccbengaluru.repository.SharedPreference;
import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

/**
 * Created by incred on 3/12/17.
 */

public class AppController extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
    }
}
