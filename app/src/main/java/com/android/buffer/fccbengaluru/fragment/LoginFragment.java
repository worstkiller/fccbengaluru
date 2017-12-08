package com.android.buffer.fccbengaluru.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
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

import com.android.buffer.fccbengaluru.R;
import com.android.buffer.fccbengaluru.activity.MainActivity;
import com.android.buffer.fccbengaluru.repository.SharedPreference;
import com.android.buffer.fccbengaluru.util.Constants;
import com.android.buffer.fccbengaluru.util.Utils;
import com.android.buffer.fccbengaluru.util.Validate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by incred on 5/12/17.
 */

public class LoginFragment extends BaseFragment {

    private static final String TAG = LoginFragment.class.getCanonicalName();
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

    private FirebaseAuth mFirebaseAuth;

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
        mFirebaseAuth = FirebaseAuth.getInstance();
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
        int color = ContextCompat.getColor(getActivity(), R.color.colorAccent);
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
        changeFragment(Constants.FRAGMENT_SIGN_UP);
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
            makeSigninFireBaseCall(mEtSplashEmail.getText().toString(), mEtSplashPassword.getText().toString());
        }
    }

    private void makeSigninFireBaseCall(String email, String password) {
        //here make call to firebase for signin process
        showProgressDialog();
        mFirebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull final Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            updateUI(mFirebaseAuth.getCurrentUser());
                        } else {
                            showInfoToUser(task);
                        }
                    }
                });
    }

    private void showInfoToUser(Task<AuthResult> task) {
        //here manage the exceptions and show relevant information to user
        hideProgressDialog();
        if (task.getException() instanceof FirebaseAuthInvalidUserException) {
            showSnackBar(getString(R.string.user_not_found_msg));
        } else if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
            showSnackBar(getString(R.string.wrong_password_msg));
        } else {
            showSnackBar(getString(R.string.error_common));
        }
    }

    private void updateUI(final FirebaseUser user) {
        hideProgressDialog();
        if (user != null) {
            SharedPreference sharedPreference = getSharedPreference();
            if (sharedPreference != null) {
                sharedPreference.saveEmailLogin(user.getEmail(), user.getUid());
                Utils.startIntent(getActivity(), MainActivity.class);
                getActivity().finish();
            }
        } else {
            showSnackBar(getString(R.string.error_common));
        }
    }

    private boolean validate() {
        //validate the user input
        if (!Validate.isValidEmail(mEtSplashEmail)) {
            mTilSplashEmail.setError(getString(R.string.splash_error_email));
            mEtSplashEmail.requestFocus();
            return false;
        }
        if (!Validate.isValidPassword(mEtSplashPassword)) {
            mTilSplashPassword.setError(getString(R.string.splash_error_password));
            mEtSplashPassword.requestFocus();
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
