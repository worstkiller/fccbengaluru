package com.android.buffer.fccbengaluru.repository;

import com.android.buffer.fccbengaluru.callback.RequestApiListener;
import com.android.buffer.fccbengaluru.model.FeedModel;
import com.android.buffer.fccbengaluru.util.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by incred on 8/12/17.
 */

public class RequestProvider {

    private RequestApiListener mRequestApiListener;

    public RequestProvider(RequestApiListener apiListener) {
        //empty constructor
        mRequestApiListener = apiListener;
    }

    public void getFeeds(String groupID, String token) {
        //get the feeds from the api
        RetrofitInstance
                .getInterface(Constants.ENDPOINT)
                .getFeeds(groupID, token)
                .enqueue(new Callback<FeedModel>() {
                    @Override
                    public void onResponse(final Call<FeedModel> call, final Response<FeedModel> response) {
                        //handle the response here
                        if (response.code() == 200) {
                            mRequestApiListener.onFeedsReceived(response.body());
                        } else {
                            mRequestApiListener.onApiFailure();
                        }
                    }

                    @Override
                    public void onFailure(final Call<FeedModel> call, final Throwable t) {
                        //handle the failure
                        mRequestApiListener.onApiFailure();
                    }
                });
    }
}
