package com.android.buffer.fccbengaluru.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatButton;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.buffer.fccbengaluru.R;
import com.android.buffer.fccbengaluru.util.Constants;

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
        setSpannableSignupString();
    }

    private void setSpannableSignupString() {
        //set a spannable string on sign up line
        int color = ContextCompat.getColor(getActivity(),R.color.colorAccent);
        SpannableStringBuilder stringBuilder = new SpannableStringBuilder(mTvSignupSignin.getText());
        //color
        stringBuilder.setSpan(new ForegroundColorSpan(color),
                mTvSignupSignin.getText().length() - 6,
                mTvSignupSignin.getText().length(),
                SpannableStringBuilder.SPAN_EXCLUSIVE_INCLUSIVE);
        //underline
        stringBuilder.setSpan(new UnderlineSpan(),
                mTvSignupSignin.getText().length() - 6,
                mTvSignupSignin.getText().length(),
                SpannableStringBuilder.SPAN_EXCLUSIVE_INCLUSIVE);
        //click
        stringBuilder.setSpan(new ClickableSpan() {
                                  @Override
                                  public void onClick(final View view) {
                                      //handle click event here on sign up
                                      openSignUpFragment();
                                  }
                              },
                mTvSignupSignin.getText().length() - 6,
                mTvSignupSignin.getText().length(),
                SpannableStringBuilder.SPAN_EXCLUSIVE_INCLUSIVE);
        mTvSignupSignin.setText(stringBuilder);
        mTvSignupSignin.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void openSignUpFragment() {
        //get back to sign in
        getFragmentManager().popBackStack();
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

    }

    private void openGoogleSignup() {
        //do google login
        showSnackBar("Work under progress");
    }

    private void openEmailSignup() {
        //do email signup here
        changeFragment(Constants.FRAGMENT_EMAIL_SIGNUP);
    }
}
