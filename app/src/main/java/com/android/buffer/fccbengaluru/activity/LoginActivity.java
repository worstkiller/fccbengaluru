package com.android.buffer.fccbengaluru.activity;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.android.buffer.fccbengaluru.fragment.EmailSignupFragment;
import com.android.buffer.fccbengaluru.fragment.LoginFragment;
import com.android.buffer.fccbengaluru.fragment.SignupFragment;

import static com.android.buffer.fccbengaluru.util.Constants.FRAGMENT_EMAIL_SIGNUP;
import static com.android.buffer.fccbengaluru.util.Constants.FRAGMENT_LOGIN;
import static com.android.buffer.fccbengaluru.util.Constants.FRAGMENT_SIGN_UP;

/**
 * Created by incred on 5/12/17.
 */

public class LoginActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        changeFragment(FRAGMENT_LOGIN, false);
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
                fragment = SignupFragment.getInstance();
                break;
            case FRAGMENT_EMAIL_SIGNUP:
                fragment = EmailSignupFragment.getInstance();
                break;
        }
        addFragment(fragment, true);
    }

    /**
     * change fragment according to input code with back stack enabled
     *
     * @param code
     */
    public void changeFragment(final int code, boolean isBackStack) {
        Fragment fragment = null;
        switch (code) {
            case FRAGMENT_LOGIN:
                fragment = LoginFragment.getInstance();
                break;
            case FRAGMENT_SIGN_UP:
                fragment = SignupFragment.getInstance();
                break;
            case FRAGMENT_EMAIL_SIGNUP:
                fragment = EmailSignupFragment.getInstance();
                break;
        }
        addFragment(fragment, isBackStack);
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }
}
