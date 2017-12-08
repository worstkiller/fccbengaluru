package com.android.buffer.fccbengaluru.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatButton;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.android.buffer.fccbengaluru.R;
import com.android.buffer.fccbengaluru.activity.MainActivity;
import com.android.buffer.fccbengaluru.repository.SharedPreference;
import com.android.buffer.fccbengaluru.util.Utils;
import com.android.buffer.fccbengaluru.util.Validate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by incred on 6/12/17.
 */

public class EmailSignupFragment extends BaseFragment {

    private static final String TAG = EmailSignupFragment.class.getCanonicalName();

    @BindView(R.id.etEmailInput)
    EditText mEtEmailInput;
    @BindView(R.id.tilEmailInput)
    TextInputLayout mTilEmailInput;
    @BindView(R.id.etEmailPassword)
    EditText mEtEmailPassword;
    @BindView(R.id.tilEmailPassword)
    TextInputLayout mTilEmailPassword;
    @BindView(R.id.btEmailSignup)
    AppCompatButton mBtEmailSignup;
    Unbinder unbinder;
    private FirebaseAuth mAuth;

    /**
     * returns a new @EmailSignup fragment instance
     *
     * @return
     */
    public static EmailSignupFragment getInstance() {
        return new EmailSignupFragment();
    }

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_email_signup, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setTextWatchers();
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
                if (editable == mEtEmailInput.getEditableText()) {
                    Utils.removeErrorFromTextInputLayout(mTilEmailInput);
                }
                if (editable == mEtEmailPassword.getEditableText()) {
                    Utils.removeErrorFromTextInputLayout(mTilEmailPassword);
                }
            }
        };
        //add the text watcher
        mEtEmailInput.addTextChangedListener(textWatcher);
        mEtEmailPassword.addTextChangedListener(textWatcher);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btEmailSignup)
    public void onViewClicked() {
        //validate the input
        if (validate()) {
            signupFirebaseCall(mEtEmailInput.getText().toString(), mEtEmailPassword.getText().toString());
        }
    }

    private void signupFirebaseCall(final String email, final String password) {
        //make firebase call to singup
        showProgressDialog();
        mAuth.createUserWithEmailAndPassword(email, password).
                addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull final Task<AuthResult> task) {
                        if (isAdded()) {
                            if (task.isSuccessful()) {
                                sendEmailVerification();
                            } else {
                                showInfoToUser(task);
                            }
                        }
                    }
                });
    }

    private void showInfoToUser(Task<AuthResult> task) {
        //here manage the exceptions and show relevant information to user
        hideProgressDialog();
        if (task.getException() instanceof FirebaseAuthUserCollisionException) {
            showSnackBar(getString(R.string.user_already_exist_msg));
        } else if (task.getException() instanceof FirebaseAuthWeakPasswordException) {
            showSnackBar(task.getException().getMessage());
        } else {
            showSnackBar(getString(R.string.error_common));
        }
    }


    private void updateUI(final FirebaseUser user) {
        //here update the ui and store the info
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

    private void sendEmailVerification() {
        //send email verification
        try {
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            FirebaseUser user = firebaseAuth.getCurrentUser();
            user.sendEmailVerification()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "Email sent.");
                                Toast.makeText(getContext(), getString(R.string.email_verification_sent), Toast.LENGTH_LONG).show();
                                updateUI(mAuth.getCurrentUser());
                            } else {
                                updateUI(mAuth.getCurrentUser());
                            }
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean validate() {
        //validate the user input
        if (!Validate.isValidEmail(mEtEmailInput)) {
            mTilEmailInput.setError(getString(R.string.splash_error_email));
            mEtEmailInput.requestFocus();
            return false;
        }
        if (!Validate.isValidPassword(mEtEmailPassword)) {
            mTilEmailPassword.setError(getString(R.string.splash_error_password));
            mEtEmailPassword.requestFocus();
            return false;
        }
        return true;
    }
}
