package com.android.buffer.fccbengaluru.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by incred on 8/12/17.
 */

public class FeedModel {
    @SerializedName("data")
    @Expose
    private List<DataModel> mModelList;

    public List<DataModel> getModelList() {
        return mModelList;
    }

    public void setModelList(final List<DataModel> modelList) {
        mModelList = modelList;
    }
}
