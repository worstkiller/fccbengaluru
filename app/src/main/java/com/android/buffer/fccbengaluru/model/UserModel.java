package com.android.buffer.fccbengaluru.model;

/**
 * Created by incred on 14/12/17.
 */

public class UserModel {
    private String name;
    private String email;
    private Integer userType;
    private String firebaseToken;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(final Integer userType) {
        this.userType = userType;
    }

    public String getFirebaseToken() {
        return firebaseToken;
    }

    public void setFirebaseToken(final String firebaseToken) {
        this.firebaseToken = firebaseToken;
    }
}
