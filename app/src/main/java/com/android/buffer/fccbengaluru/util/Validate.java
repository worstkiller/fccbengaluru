package com.android.buffer.fccbengaluru.util;

import android.util.Patterns;
import android.widget.EditText;

/**
 * Created by incred on 5/12/17.
 */

public class Validate {

    /**
     * <p>returns true for valid email</p>
     *
     * @param editText
     * @return
     */
    public static boolean isValidEmail(EditText editText) {
        if (Patterns.EMAIL_ADDRESS.matcher(editText.getText().toString()).matches()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * <p>checks for password length of 8 and greater for true</p>
     * @param editText
     * @return
     */
    public static boolean isValidPassword(EditText editText) {
        if (editText.length() < 8) {
            return false;
        } else {
            if (editText.getText().toString().trim().length() > 8) {
                return false;
            }
            return true;
        }
    }
}
