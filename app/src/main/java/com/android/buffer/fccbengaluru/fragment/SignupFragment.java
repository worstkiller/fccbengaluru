package com.android.buffer.fccbengaluru.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.buffer.fccbengaluru.R;
import com.android.buffer.fccbengaluru.activity.LoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by incred on 6/12/17.
 */

public class SignupFragment extends BaseFragment {

    @BindView(R.id.btSignupEmail)
    AppCompatButton mBtSignupEmail;
    @BindView(R.id.btSignupGoogle)
    AppCompatButton mBtSignupGoogle;
    @BindView(R.id.btSignupFacebook)
    AppCompatButton mBtSignupFacebook;
    @BindView(R.id.tvSignupSignin)
    TextView mTvSignupSignin;
    Unbinder unbinder;

    /**
     * returns a signup instance
     *
     * @return
     */
    public static SignupFragment getInstance() {
        return new SignupFragment();
    }

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btSignupEmail, R.id.btSignupGoogle, R.id.btSignupFacebook})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btSignupEmail:
                openEmailSignup();
                break;
            case R.id.btSignupGoogle:
                openGoogleSignup();
                break;
            case R.id.btSignupFacebook:
                openFacebookSignup();
                break;
        }
    }

    private void openFacebookSignup() {
        //do facebook signup
        ((LoginActivity)getActivity()).changeFragment(LoginActivity.FRAGMENT_EMAIL_SIGNUP);
    }

    private void openGoogleSignup() {
        //do google login
    }

    private void openEmailSignup() {
        //do email signup here
    }
}
