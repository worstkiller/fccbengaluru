package com.android.buffer.fccbengaluru.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.buffer.fccbengaluru.R;
import com.android.buffer.fccbengaluru.adapter.ViewPagerAdapter;
import com.android.buffer.fccbengaluru.model.UserModel;
import com.android.buffer.fccbengaluru.repository.SharedPreference;
import com.android.buffer.fccbengaluru.util.Constants;
import com.android.buffer.fccbengaluru.util.Utils;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    private static final String TAG = MainActivity.class.getCanonicalName();
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tabMain)
    TabLayout mTabMain;
    @BindView(R.id.vpMain)
    ViewPager mVpMain;
    @BindView(R.id.btTryAgain)
    AppCompatButton mBtTryAgain;
    @BindView(R.id.llErrorConnectivity)
    LinearLayout mLlErrorConnectivity;
    @BindView(R.id.flContainerMain)
    RelativeLayout mFlContainerMain;

    private Boolean isUserTypeAdmin = false;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        mAuth = FirebaseAuth.getInstance();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_REFERENCE_USER);
        setViewPagerWithTab();
    }

    private void setViewPagerWithTab() {
        //setting tab layout with viewpager
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this, getSupportFragmentManager());
        mVpMain.setAdapter(viewPagerAdapter);
        mVpMain.setOffscreenPageLimit(2);
        mTabMain.setupWithViewPager(mVpMain);
        viewPagerAdapter.setIcons(mTabMain);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        if (mAuth.getCurrentUser() == null) {
            Toast.makeText(this, R.string.login_expired, Toast.LENGTH_SHORT).show();
            Utils.startIntent(MainActivity.this, LoginActivity.class);
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        manageConnection();
        checkUserType();
    }

    private void checkUserType() {
        //checks for user type in firebase database
        final String email = mAuth.getCurrentUser().getEmail();
        Query query = mDatabaseReference.orderByChild("email").equalTo(email);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                try {
                    if (dataSnapshot != null) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            UserModel userModel = snapshot.getValue(UserModel.class);
                            if (userModel.getEmail() == null) {
                                return;
                            }
                            if (userModel.getEmail().equals(email) && userModel.getUserType() == 0) {
                                Log.d(TAG, userModel.getEmail());
                                isUserTypeAdmin = true;
                                invalidateOptionsMenu();
                            }
                        }
                    } else {
                        Log.d(TAG, "user check failed");
                    }
                } catch (NullPointerException exp) {
                    exp.printStackTrace();
                }
            }

            @Override
            public void onCancelled(final DatabaseError databaseError) {
                Log.d(TAG, "user check failed");
            }
        });
    }

    private void manageConnection() {
        //this manages the connections
        if (!Utils.isConnectedToInternet(this)) {
            mLlErrorConnectivity.setVisibility(View.VISIBLE);
            mTabMain.setVisibility(View.GONE);
        } else {
            if (mLlErrorConnectivity.getVisibility() == View.VISIBLE) {
                mLlErrorConnectivity.setVisibility(View.GONE);
                mTabMain.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_signout) {
            signOutUser();
            return true;
        }
        if (id == R.id.action_admin) {
            openAdminPanel();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(final Menu menu) {
        if (isUserTypeAdmin) {
            menu.findItem(R.id.action_admin).setVisible(true);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    private void openAdminPanel() {
        //here open the admin panel
        Utils.startIntent(MainActivity.this, EventEntryActivity.class);
    }

    private void signOutUser() {
        //sign out user
        SharedPreference sharedPreference = getSharedPreference();
        if (sharedPreference != null) {
            sharedPreference.clearLogin();
        }
        FirebaseAuth.getInstance().signOut();
        //start again the Login Activity
        Utils.startIntent(MainActivity.this, LoginActivity.class);
        finish();
        //sign out from google
        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder().build();
        GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(MainActivity.this, signInOptions);
        googleSignInClient.signOut();
    }

    @OnClick(R.id.btTryAgain)
    public void onViewClicked() {
        Intent intent = new Intent(MainActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }
}
