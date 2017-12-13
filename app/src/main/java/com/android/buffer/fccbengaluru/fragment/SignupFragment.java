package com.android.buffer.fccbengaluru.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatButton;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.buffer.fccbengaluru.R;
import com.android.buffer.fccbengaluru.activity.MainActivity;
import com.android.buffer.fccbengaluru.model.UserModel;
import com.android.buffer.fccbengaluru.repository.SharedPreference;
import com.android.buffer.fccbengaluru.util.Constants;
import com.android.buffer.fccbengaluru.util.Utils;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.android.buffer.fccbengaluru.util.Constants.DATABASE_REFERENCE_USER;
import static com.android.buffer.fccbengaluru.util.Constants.USER_NORMAL;

/**
 * Created by incred on 6/12/17.
 */

public class SignupFragment extends BaseFragment {

    private static final String TAG = SignupFragment.class.getCanonicalName();
    private static final int RC_SIGN_IN = 101;
    @BindView(R.id.btSignupEmail)
    AppCompatButton mBtSignupEmail;
    @BindView(R.id.btSignupGoogle)
    AppCompatButton mBtSignupGoogle;
    @BindView(R.id.btSignupFacebook)
    AppCompatButton mBtSignupFacebook;
    @BindView(R.id.tvSignupSignin)
    TextView mTvSignupSignin;
    Unbinder unbinder;
    private CallbackManager mCallbackManager;
    private FirebaseAuth mFirebaseAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private DatabaseReference mDatabaseReference;

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
        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference(DATABASE_REFERENCE_USER);
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup, container, false);
        unbinder = ButterKnife.bind(this, view);
        mCallbackManager = CallbackManager.Factory.create();
        facebookLoginManager();
        googleLoginManager();
        return view;
    }

    private void googleLoginManager() {
        //login using google
        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), signInOptions);
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setSpannableSignupString();
    }

    private void facebookLoginManager() {
        //login facebook using login manager
        LoginManager.getInstance().registerCallback(mCallbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                        Log.d(TAG, "facebook:onSuccess:" + loginResult);
                        handleFacebookAccessToken(loginResult.getAccessToken());
                    }

                    @Override
                    public void onCancel() {
                        // App codeu
                        if (isAdded()) {
                            showSnackBar(getString(R.string.last_login_info));
                        }
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                        updateUI(null);
                    }
                });
    }

    private void handleFacebookAccessToken(final AccessToken accessToken) {
        //handle the success response here from the facebook
        showProgressDialog();
        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        mFirebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull final Task<AuthResult> task) {
                        if (task != null) {
                            insertUser(mFirebaseAuth.getCurrentUser());
                            updateUI(mFirebaseAuth.getCurrentUser());
                        } else {
                            updateUI(null);
                        }
                    }
                });
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

    private void setSpannableSignupString() {
        //set a spannable string on sign up line
        int color = ContextCompat.getColor(getActivity(), R.color.colorAccent);
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
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email", "public_profile"));
    }

    private void openGoogleSignup() {
        //check if user is already logged in
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void openEmailSignup() {
        //do email signup here
        changeFragment(Constants.FRAGMENT_EMAIL_SIGNUP);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                showSnackBar(getString(R.string.last_login_info));
            }
        }
    }

    private void firebaseAuthWithGoogle(final GoogleSignInAccount account) {
        //firebase login here finally
        showProgressDialog();
        AuthCredential authCredential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mFirebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull final Task<AuthResult> task) {
                if (task != null) {
                    Log.d(TAG, "signInWithCredential:success");
                    insertUser(mFirebaseAuth.getCurrentUser());
                    updateUI(mFirebaseAuth.getCurrentUser());
                } else {
                    Log.w(TAG, "signInWithCredential:failure", task.getException());
                    updateUI(null);
                }
            }
        });
    }

    private void insertUser(final FirebaseUser currentUser) {
        //here insert a user entry into database for future reference
        mDatabaseReference
                .push()
                .setValue(getUserModel(currentUser), new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(final DatabaseError databaseError, final DatabaseReference databaseReference) {
                        //handle the response here
                        if (databaseError == null) {
                            Log.d(TAG, "user is inserted into DB");
                        } else {
                            Log.d(TAG, "user is not inserted into DB");
                        }
                    }
                });
    }

    private UserModel getUserModel(final FirebaseUser currentUser) {
        //construct a user model and return
        UserModel userModel = new UserModel();
        userModel.setEmail(currentUser.getEmail());
        userModel.setName(currentUser.getDisplayName());
        userModel.setUserType(USER_NORMAL);
        userModel.setFirebaseToken(FirebaseInstanceId.getInstance().getToken());
        return userModel;
    }


}
