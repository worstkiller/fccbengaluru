package com.android.buffer.fccbengaluru.activity;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.android.buffer.fccbengaluru.AppController;
import com.android.buffer.fccbengaluru.R;
import com.android.buffer.fccbengaluru.repository.SharedPreference;

/**
 * Created by incred on 03/12/17.
 */

public class BaseActivity extends AppCompatActivity {

    private SharedPreference mSharedPreference;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        mSharedPreference = new SharedPreference(this);
    }

    /**
     * <p>set fragment to base activity</p>
     *
     * @param fragment
     */
    public void addFragment(Fragment fragment) {
        getFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.animator.slide_left, R.animator.fade_in)
                .replace(R.id.flContainerLogin, fragment)
                .commit();
    }

    /*
    return a shared preference
     */
    public SharedPreference getSharedPreference() {
        return mSharedPreference;
    }
}
