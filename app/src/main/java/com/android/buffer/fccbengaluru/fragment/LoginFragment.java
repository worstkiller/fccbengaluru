package com.android.buffer.fccbengaluru.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatButton;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.buffer.fccbengaluru.R;
import com.android.buffer.fccbengaluru.util.Utils;
import com.android.buffer.fccbengaluru.util.Validate;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by incred on 5/12/17.
 */

public class LoginFragment extends BaseFragment {

    @BindView(R.id.etSplashEmail)
    EditText mEtSplashEmail;

    @BindView(R.id.tilSplashEmail)
    TextInputLayout mTilSplashEmail;

    @BindView(R.id.etSplashPassword)
    EditText mEtSplashPassword;

    @BindView(R.id.tilSplashPassword)
    TextInputLayout mTilSplashPassword;

    @BindView(R.id.btSplashLogin)
    AppCompatButton mBtSplashLogin;

    @BindView(R.id.tvSplashSignup)
    TextView mTvSplashSignup;

    Unbinder unbinder;

    /**
     * <p>returns a login fragment</p>
     *
     * @return
     */
    public static LoginFragment getInstance() {
        return new LoginFragment();
    }

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setTextWatchers();
        setSpannableSignupString();
    }

    private void setSpannableSignupString() {
        //set a spannable string on sign up line
        int color = ContextCompat.getColor(getContext(),R.color.colorAccent);
        SpannableStringBuilder stringBuilder = new SpannableStringBuilder(mTvSplashSignup.getText());
        //color
        stringBuilder.setSpan(new ForegroundColorSpan(color),
                mTvSplashSignup.getText().length() - 6,
                mTvSplashSignup.getText().length(),
                SpannableStringBuilder.SPAN_EXCLUSIVE_INCLUSIVE);
        //underline
        stringBuilder.setSpan(new UnderlineSpan(),
                mTvSplashSignup.getText().length() - 6,
                mTvSplashSignup.getText().length(),
                SpannableStringBuilder.SPAN_EXCLUSIVE_INCLUSIVE);
        //click
        stringBuilder.setSpan(new ClickableSpan() {
                                  @Override
                                  public void onClick(final View view) {
                                      //handle click event here on sign up
                                      openSignUpFragment();
                                  }
                              },
                mTvSplashSignup.getText().length() - 6,
                mTvSplashSignup.getText().length(),
                SpannableStringBuilder.SPAN_EXCLUSIVE_INCLUSIVE);
        mTvSplashSignup.setText(stringBuilder);
        mTvSplashSignup.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void openSignUpFragment() {
        //open sign up fragment
        Toast.makeText(getContext(),"Work under progress",Toast.LENGTH_SHORT).show();
    }

    private void setTextWatchers() {
        //declare the text watcher
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(final CharSequence charSequence, final int i, final int i1, final int i2) {
                //do nothing for now
            }

            @Override
            public void onTextChanged(final CharSequence charSequence, final int i, final int i1, final int i2) {
                //do nothing for now
            }

            @Override
            public void afterTextChanged(final Editable editable) {
                //remove the error if any
                if (editable == mEtSplashEmail.getEditableText()) {
                    Utils.removeErrorFromTextInputLayout(mTilSplashEmail);
                }
                if (editable == mEtSplashPassword.getEditableText()) {
                    Utils.removeErrorFromTextInputLayout(mTilSplashEmail);
                }
            }
        };
        //add the text watcher
        mEtSplashEmail.addTextChangedListener(textWatcher);
        mEtSplashPassword.addTextChangedListener(textWatcher);
    }

    @OnClick(R.id.btSplashLogin)
    public void onViewClicked() {
        if (validate()) {
            Toast.makeText(getContext(), "Login successfully", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validate() {
        //validate the user input
        if (!Validate.isValidEmail(mEtSplashEmail)) {
            mTilSplashEmail.setError(getString(R.string.splash_error_email));
            return false;
        }
        if (!Validate.isValidPassword(mEtSplashPassword)) {
            mTilSplashPassword.setError(getString(R.string.splash_error_password));
            return false;
        }
        return true;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
