package com.android.buffer.fccbengaluru.util;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;

import com.android.buffer.fccbengaluru.activity.MainActivity;
import com.android.buffer.fccbengaluru.activity.SplashActivity;

/**
 * Created by incred on 4/12/17.
 */

public class Utils {

    /**
     * <p>Starts a intent with normal flag</p>
     * @param context
     * @param aClass
     */
    public static void startIntent(Context context,final Class aClass){
        Intent intent = new Intent(context,aClass);
        context.startActivity(intent);
    }

    /**
     * <p>remove the error text from text input layout</p>
     * @param inputLayout
     */
    public static void removeErrorFromTextInputLayout(TextInputLayout inputLayout){
        inputLayout.setError(null);
        inputLayout.setErrorEnabled(false);
    }

}
