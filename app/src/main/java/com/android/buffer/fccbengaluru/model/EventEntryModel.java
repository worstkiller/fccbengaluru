package com.android.buffer.fccbengaluru.model;

/**
 * Created by incred on 7/12/17.
 */

public class EventEntryModel {
    private String title;
    private String date;
    private String pinCode;
    private String address;
    private String shortDetails;
    private String imageUrl;
    private String startTime;
    private String website;

    public String getWebsite() {
        return website;
    }

    public void setWebsite(final String website) {
        this.website = website;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(final String startTime) {
        this.startTime = startTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(final String date) {
        this.date = date;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(final String pinCode) {
        this.pinCode = pinCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(final String address) {
        this.address = address;
    }

    public String getShortDetails() {
        return shortDetails;
    }

    public void setShortDetails(final String shortDetails) {
        this.shortDetails = shortDetails;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(final String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
