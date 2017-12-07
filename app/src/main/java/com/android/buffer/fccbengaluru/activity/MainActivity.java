package com.android.buffer.fccbengaluru.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.buffer.fccbengaluru.R;
import com.android.buffer.fccbengaluru.repository.SharedPreference;
import com.android.buffer.fccbengaluru.util.Utils;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends BaseActivity {

    private FirebaseAuth mAuth;
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mTextView = findViewById(R.id.tvName);
        setSupportActionBar(toolbar);
        mAuth = FirebaseAuth.getInstance();
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
        } else {
            mTextView.setText(mAuth.getCurrentUser().getDisplayName() + " && " + mAuth.getCurrentUser().getEmail());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            signOutUser();
            return true;
        }
        if (id == R.id.action_admin) {
            openAdminPanel();
            return true;
        }

        return super.onOptionsItemSelected(item);
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
}
