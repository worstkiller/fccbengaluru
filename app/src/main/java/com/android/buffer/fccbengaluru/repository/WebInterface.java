package com.android.buffer.fccbengaluru.repository;

import com.android.buffer.fccbengaluru.model.FeedModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by incred on 8/12/17.
 */

public interface WebInterface {

    @GET("{group_id}/feed?")
    Call<FeedModel> getFeeds(@Path("group_id") String group_id, @Query("access_token") String access_token);

}
