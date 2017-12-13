package com.android.buffer.fccbengaluru.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;

import com.android.buffer.fccbengaluru.model.EventEntryModel;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.android.buffer.fccbengaluru.util.Constants.PACKAGE_CHROME;

/**
 * Created by incred on 4/12/17.
 */

public class Utils {

    /**
     * <p>Starts a intent with normal flag</p>
     *
     * @param context
     * @param aClass
     */
    public static void startIntent(Context context, final Class aClass) {
        Intent intent = new Intent(context, aClass);
        context.startActivity(intent);
    }

    /**
     * <p>remove the error text from text input layout</p>
     *
     * @param inputLayout
     */
    public static void removeErrorFromTextInputLayout(TextInputLayout inputLayout) {
        inputLayout.setError(null);
        inputLayout.setErrorEnabled(false);
    }

    public static void openLinkToWeb(Context activity, String url) {
        if (TextUtils.isEmpty(url)) {
            return;
        }

        //here open the link
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        intent.setPackage(PACKAGE_CHROME);
        if (intent.resolveActivity(activity.getPackageManager()) != null) {
            activity.startActivity(intent);
        } else {
            intent.setPackage(null);
            activity.startActivity(intent);
        }
    }

    /**
     * set the event in android calendar
     *
     * @param context
     * @param eventEntryModel
     */
    public static void addToCalendar(Context context, EventEntryModel eventEntryModel) {
        Intent intent = new Intent(Intent.ACTION_EDIT);
        try {
            intent.setType("vnd.android.cursor.item/event");
            intent.putExtra(CalendarContract.Events.TITLE, eventEntryModel.getTitle());
            intent.putExtra(CalendarContract.Events.ALL_DAY, false);
            intent.putExtra(CalendarContract.Events.DESCRIPTION, eventEntryModel.getShortDetails());
            intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, getTimeInMillis(eventEntryModel.getDate()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        context.startActivity(intent);
    }

    /**
     * insert input in dd/MM/yyyy format and get long
     *
     * @param date
     * @return
     * @throws ParseException
     */
    public static long getTimeInMillis(String date) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        Date date1 = simpleDateFormat.parse(date);
        return date1.getTime();
    }


    /**
     * returns date in month date and year JAN 09 2017
     *
     * @param date
     * @return
     * @throws ParseException
     */
    public static String getParsedDateFormat(String date) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        SimpleDateFormat simpleDateOutput = new SimpleDateFormat("MMM d yyyy", Locale.getDefault());
        Date date1 = simpleDateFormat.parse(date);
        String dateFormat = simpleDateOutput.format(date1);
        return dateFormat;
    }

    /**
     * returns if the device is connected or connecting
     *
     * @param context
     * @return
     */
    public static boolean isConnectedToInternet(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        boolean result = networkInfo != null && networkInfo.isConnectedOrConnecting();
        return result;
    }

    /**
     * return @Integer value for each status, check </br> on calling side to match and take action
     *
     * @param context
     * @return
     */
    public static int checkGooglePlayServices(Context context) {
        GoogleApiAvailability availability = GoogleApiAvailability.getInstance();
        int code = availability.isGooglePlayServicesAvailable(context);
        if (code == ConnectionResult.SUCCESS) {
            //google api is supported
            return ConnectionResult.SUCCESS;
        } else {
            if (availability.isUserResolvableError(code)) {
                //out of date google play services
                return ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED;
            } else {
                //device is not supported
                return ConnectionResult.SERVICE_MISSING;
            }
        }
    }

    /**
     * shows dialog for google play services enable when context provided
     *
     * @param context
     */
    public static void showGooglePlayUpdateDialog(Context context, int requestCode) {
        GoogleApiAvailability availability = GoogleApiAvailability.getInstance();
        int code = availability.isGooglePlayServicesAvailable(context);
        availability.showErrorDialogFragment((Activity) context, code, requestCode);
    }
}
