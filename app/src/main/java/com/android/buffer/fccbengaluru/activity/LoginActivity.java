package com.android.buffer.fccbengaluru.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;

import com.android.buffer.fccbengaluru.R;
import com.android.buffer.fccbengaluru.fragment.EmailSignupFragment;
import com.android.buffer.fccbengaluru.fragment.LoginFragment;
import com.android.buffer.fccbengaluru.fragment.SignupFragment;
import com.android.buffer.fccbengaluru.util.Utils;
import com.google.android.gms.common.ConnectionResult;

import static com.android.buffer.fccbengaluru.util.Constants.FRAGMENT_EMAIL_SIGNUP;
import static com.android.buffer.fccbengaluru.util.Constants.FRAGMENT_LOGIN;
import static com.android.buffer.fccbengaluru.util.Constants.FRAGMENT_SIGN_UP;

/**
 * Created by incred on 5/12/17.
 */

public class LoginActivity extends BaseActivity {

    private int REQUEST_CODE = 101;

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

    @Override
    protected void onResume() {
        super.onResume();
        checkGooglePlayServicesAvailability();
    }

    private void checkGooglePlayServicesAvailability() {
        //check if the google play serevices are available or not
        switch (Utils.checkGooglePlayServices(this)) {
            case ConnectionResult.SUCCESS:
                //success no action required
                break;
            case ConnectionResult.SERVICE_MISSING:
                //device not supported
                showGooglePlayMissingDialog();
                break;
            case ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED:
                //update the google play services
                Utils.showGooglePlayUpdateDialog(this, REQUEST_CODE);
                break;
        }
    }

    private void showGooglePlayMissingDialog() {
        //create a dialog and show to the user about missing google play services
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle(R.string.dialog_google_play_services_title)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialogInterface, final int i) {
                        dialogInterface.dismiss();
                        finish();
                    }
                })
                .setMessage(R.string.dialog_google_play_services_missing)
                .setCancelable(false);
        builder.create().show();
    }
}
