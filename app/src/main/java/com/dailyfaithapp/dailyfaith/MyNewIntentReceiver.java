package com.dailyfaithapp.dailyfaith;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.media.RingtoneManager;
import android.os.PowerManager;
import android.util.Log;

import com.dailyfaithapp.dailyfaith.Activities.MainActivity;

public class MyNewIntentReceiver extends BroadcastReceiver {

    public MyNewIntentReceiver() {
    }


    @Override
    public void onReceive(Context context, Intent intent) {

        PowerManager powerManager = (PowerManager) context.getSystemService(
                Context.POWER_SERVICE);
        PowerManager.WakeLock wakeLock =
                powerManager.newWakeLock(
                        PowerManager.PARTIAL_WAKE_LOCK,
                        "dailyfaith:wakelog"
                );
        wakeLock.acquire();

        // get id, titleText and bigText from intent
        int NOTIFY_ID = intent.getIntExtra("notify_id", 0);
        String titleText = intent.getStringExtra("title");
        String bigText = intent.getStringExtra("quote");

        // Create intent.
        Intent notificationIntent = new Intent(context, MainActivity.class);

        // use NOTIFY_ID as requestCode
        PendingIntent contentIntent = PendingIntent.getActivity(context,
                NOTIFY_ID, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT
        );

        // get res.
        Resources res = context.getResources();

        // build notification.
        Notification.Builder builder = new Notification.Builder(context)
                .setContentIntent(contentIntent)
                .setSmallIcon(R.drawable.ic_daily_faith_icon)
                .setAutoCancel(true)
                .setContentTitle(titleText)
                .setSound(RingtoneManager
                        .getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentText(bigText);

        Log.d("notificationBuild", "Notification Builder set");

    /*    // check vibration.
        if (mPrefs.getBoolean("vibration", true)) {
            builder.setVibrate(new long[]{0, 50});
        }*/

        // create default title if empty.
        if (titleText.length() == 0) {
            builder.setContentTitle(
                    context.getString(R.string.app_name));
        }

        // show notification. check for delay.
        builder.setWhen(System.currentTimeMillis());
        Log.d("notificationSetWhen", "Notification set when triggered");

        Notification notification = new Notification.BigTextStyle(builder)
                .bigText(bigText).build();

        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFY_ID, notification);

        wakeLock.release();
    }
}