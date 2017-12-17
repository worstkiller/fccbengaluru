package com.android.buffer.fccbengaluru.service;

import android.util.Log;

import com.android.buffer.fccbengaluru.util.Constants;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.iid.zzi;
import com.google.firebase.messaging.FirebaseMessaging;

/**
 * Created by incred on 14/12/17.
 */

public class FirebaseTokenService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIDService";

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        //handle the token here
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        FirebaseMessaging.getInstance().subscribeToTopic(Constants.USER_NORMAL_GROUP);
        Log.d(TAG, "Refreshed token: " + refreshedToken);
    }
}
