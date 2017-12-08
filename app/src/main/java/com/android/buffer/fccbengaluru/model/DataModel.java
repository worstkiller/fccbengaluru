package com.android.buffer.fccbengaluru.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by incred on 8/12/17.
 */

public class DataModel {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("updated_time")
    @Expose
    private String updatedTime;
    @SerializedName("story")
    @Expose
    private String story;

    public String getStory() {
        return story;
    }

    public void setStory(final String story) {
        this.story = story;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public String getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(final String updatedTime) {
        this.updatedTime = updatedTime;
    }
}
