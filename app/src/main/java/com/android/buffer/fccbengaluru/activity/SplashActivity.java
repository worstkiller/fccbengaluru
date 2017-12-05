package com.android.buffer.fccbengaluru.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.android.buffer.fccbengaluru.R;
import com.android.buffer.fccbengaluru.repository.SharedPreference;
import com.android.buffer.fccbengaluru.util.Utils;

/**
 * Created by incred on 03/12/17.
 */

public class SplashActivity extends BaseActivity {

    private final int SPLASH_DISPLAY_LENGTH = 2000;

    private SharedPreference mSharedPreference;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        mSharedPreference = getSharedPreference();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //perform login validation and decide user navigation flow
                if (mSharedPreference.isLoggedIn()) {
                    Utils.startIntent(SplashActivity.this, MainActivity.class);
                } else {
                    Utils.startIntent(SplashActivity.this, LoginActivity.class);
                }
                finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
