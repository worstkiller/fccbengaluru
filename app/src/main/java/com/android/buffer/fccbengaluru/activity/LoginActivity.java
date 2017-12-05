package com.android.buffer.fccbengaluru.activity;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.android.buffer.fccbengaluru.fragment.LoginFragment;

/**
 * Created by incred on 5/12/17.
 */

public class LoginActivity extends BaseActivity {

    public final static int FRAGMENT_LOGIN = 101, FRAGMENT_SIGN_UP = 102, FRAGMENT_FORGOT_PASSWORD = 103;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        changeFragment(FRAGMENT_LOGIN);
    }

    /**
     * change fragment according to input code
     *
     * @param code
     */
    public void changeFragment(final int code) {
        Fragment fragment = null;
        switch (code) {
            case FRAGMENT_LOGIN:
                fragment = LoginFragment.getInstance();
                break;
            case FRAGMENT_SIGN_UP:
                fragment = LoginFragment.getInstance();
                break;
            case FRAGMENT_FORGOT_PASSWORD:
                fragment = LoginFragment.getInstance();
                break;
        }
        addFragment(fragment);
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() == 1) {
            super.onBackPressed();
        } else {
            getFragmentManager().popBackStack();
        }
    }
}
