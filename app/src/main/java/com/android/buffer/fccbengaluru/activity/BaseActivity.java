package com.android.buffer.fccbengaluru.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.android.buffer.fccbengaluru.R;
import com.android.buffer.fccbengaluru.repository.SharedPreference;
import com.android.buffer.fccbengaluru.util.ProgressDialog;

/**
 * Created by incred on 03/12/17.
 */

public class BaseActivity extends AppCompatActivity {

    private SharedPreference mSharedPreference;
    private AlertDialog mProgressDialog;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        mSharedPreference = new SharedPreference(this);
        mProgressDialog = ProgressDialog.getSimpleProgressDialog(this, getString(R.string.progress_dialog_msg), false);
    }

    /**
     * <p>set fragment to base activity</p>
     *
     * @param fragment
     */
    public void addFragment(Fragment fragment, boolean backStackEnabled) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (backStackEnabled) {
            fragmentTransaction.replace(R.id.flContainerLogin, fragment).addToBackStack(fragment.getClass().getCanonicalName());
        } else {
            fragmentTransaction.replace(R.id.flContainerLogin, fragment);
        }
        fragmentTransaction.commit();
    }

    /*
    return a shared preference
     */
    public SharedPreference getSharedPreference() {
        return mSharedPreference;
    }

    /*
    shows progress dialog
     */
    public void showProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.show();
        }
    }

    /*
    hides the progress dialog
     */
    public void hideProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.hide();
        }
    }

    /*
    shows a snackbar
     */
    public void showSnackBar(String msg) {
        Snackbar.make(getCurrentFocus(), msg, Snackbar.LENGTH_SHORT).show();
    }
}
