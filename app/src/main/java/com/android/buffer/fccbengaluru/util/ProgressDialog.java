package com.android.buffer.fccbengaluru.util;

import android.app.Activity;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.android.buffer.fccbengaluru.AppController;
import com.android.buffer.fccbengaluru.R;
import com.victor.loading.rotate.RotateLoading;

/**
 * Created by incred on 14/9/17.
 */

public class ProgressDialog {

    public static AlertDialog getSimpleProgressDialog(Activity activity, int message, boolean cancellable) {
        return getSimpleProgressDialog(activity, activity.getString(message), cancellable);
    }

    public static AlertDialog getSimpleProgressDialog(Activity activity, String message, boolean cancellable) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.widget_progress_dialog, null);

        ((TextView) view.findViewById(R.id.textViewMessage)).setText(message);
        ((RotateLoading) view.findViewById(R.id.rotateloading)).start();
        builder.setView(view)
                .setCancelable(cancellable);

        return builder.create();
    }

    public static void setMessage(AlertDialog dialog, int message) {
        setMessage(dialog, AppController.getAppController().getApplicationContext().getString(message));
    }

    public static void setMessage(AlertDialog dialog, String message) {
        if (dialog == null) {
            return;
        }
        if (dialog.findViewById(R.id.textViewMessage) != null && dialog.findViewById(R.id.textViewMessage) instanceof TextView) {
            ((TextView) dialog.findViewById(R.id.textViewMessage)).setText(message);
        }
    }

}
