package com.android.buffer.fccbengaluru.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.View;

import com.android.buffer.fccbengaluru.activity.BaseActivity;
import com.android.buffer.fccbengaluru.activity.LoginActivity;
import com.android.buffer.fccbengaluru.repository.SharedPreference;

/**
 * Created by incred on 5/12/17.
 */

public class BaseFragment extends Fragment {

    private View mView;

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mView = view;
    }

    public void showProgressDialog() {
        if (isAdded()) {
            if (getActivity() instanceof BaseActivity) {
                ((BaseActivity) getActivity()).showProgressDialog();
            }
        }
    }

    public void hideProgressDialog() {
        if (isAdded()) {
            if (getActivity() instanceof BaseActivity) {
                ((BaseActivity) getActivity()).hideProgressDialog();
            }
        }
    }

    /**
     * retuns a shared preference
     * @return
     */
    public SharedPreference getSharedPreference() {
        if (isAdded()) {
            if (getActivity() instanceof BaseActivity) {
                return ((BaseActivity) getActivity()).getSharedPreference();
            }
        }
        return null;
    }

    public void showSnackBar(String msg){
        Snackbar.make(mView,msg,Snackbar.LENGTH_SHORT).show();
    }

    /*
    change fragment based on code from login acitivty
     */
    public void changeFragment(int code){
        if (isAdded()){
            if (getActivity() instanceof BaseActivity) {
                ((LoginActivity)getActivity()).changeFragment(code);
            }
        }
    }
}
