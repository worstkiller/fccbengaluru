package com.android.buffer.fccbengaluru.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.android.buffer.fccbengaluru.R;
import com.android.buffer.fccbengaluru.activity.MainActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by incred on 14/12/17.
 */

public class FirebaseNotificationService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(final RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        if (remoteMessage.getNotification() != null) {
            sendNotification(remoteMessage.getNotification());
        }
    }

    private void sendNotification(RemoteMessage.Notification notification) {
        //create a notification and notify
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        String channelId = getString(R.string.default_notification_channel_id);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationCompat = new NotificationCompat.Builder(this, channelId);
        notificationCompat.setSmallIcon(R.drawable.ic_stat_notification);
        notificationCompat.setContentTitle(notification.getTitle());
        notificationCompat.setContentText(notification.getBody());
        notificationCompat.setAutoCancel(true);
        notificationCompat.setSound(defaultSoundUri);
        notificationCompat.setDeleteIntent(pendingIntent);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, notificationCompat.build());
    }

}
