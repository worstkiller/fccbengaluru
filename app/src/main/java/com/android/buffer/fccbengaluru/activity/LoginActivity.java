package com.android.buffer.fccbengaluru.activity;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.android.buffer.fccbengaluru.fragment.EmailSignupFragment;
import com.android.buffer.fccbengaluru.fragment.LoginFragment;
import com.android.buffer.fccbengaluru.fragment.SignupFragment;

/**
 * Created by incred on 5/12/17.
 */

public class LoginActivity extends BaseActivity {

    public final static int FRAGMENT_LOGIN = 101, FRAGMENT_SIGN_UP = 102, FRAGMENT_FORGOT_PASSWORD = 103,FRAGMENT_EMAIL_SIGNUP=104;

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
                fragment = SignupFragment.getInstance();
                break;
            case FRAGMENT_EMAIL_SIGNUP:
                fragment = EmailSignupFragment.getInstance();
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
