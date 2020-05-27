package com.dailyfaithapp.dailyfaith;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.dailyfaithapp.dailyfaith.Activities.HomeScreenActivity;

public class MyNewIntentReceiver extends BroadcastReceiver {

    public MyNewIntentReceiver() {
    }


    public static void sendNotification(
            Context mcontext, String messageBody,
            int notify_id, int requestCode, String notifyQuoteId
    ) {
        Intent intent = new Intent(mcontext, HomeScreenActivity.class);
        intent.putExtra("notificationQuote", messageBody);
        intent.putExtra("notifyQuoteId", notifyQuoteId);
        PendingIntent pendingIntent = PendingIntent
                .getActivity(mcontext, requestCode /* Request code */, intent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        NotificationManager notificationManager = (NotificationManager) mcontext
                .getSystemService(Context.NOTIFICATION_SERVICE);

        Uri defaultSoundUri = RingtoneManager
                .getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel
                    (
                            mcontext.getString(
                                    R.string.default_notification_channel_id),
                            "Rewards Notifications",
                            NotificationManager.IMPORTANCE_HIGH
                    );

            // Configure the notification channel.
            notificationChannel.setDescription("Channel description");
            notificationChannel.enableLights(true);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        NotificationCompat.Builder notificationBuilder = new NotificationCompat
                .Builder(
                mcontext,
                mcontext.getString(R.string.default_notification_channel_id)
        )
                .setSmallIcon(R.drawable.ic_daily_faith_icon)
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);


        notificationManager.notify(notify_id /* ID of notification */,
                notificationBuilder.build());
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        /*
         *//*    PowerManager powerManager = (PowerManager) context.getSystemService(
                Context.POWER_SERVICE);
        PowerManager.WakeLock wakeLock =
                powerManager.newWakeLock(
                        PowerManager.PARTIAL_WAKE_LOCK,
                        "dailyfaith:wakelog"
                );
        wakeLock.acquire();*//*

        // get id, titleText and bigText from intent
        int NOTIFY_ID = intent.getIntExtra("notify_id", 0);
        String titleText = intent.getStringExtra("title");
        String bigText = intent.getStringExtra("quote");

        // Create intent.
        Intent notificationIntent = new Intent(context, MainActivity.class);

        // use NOTIFY_ID as requestCode
        PendingIntent contentIntent = PendingIntent.getActivity(context,
                0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT
        );

        // get res.
        Resources res = context.getResources();

        // build notification.
*//*        Notification.Builder builder = new Notification.Builder(context)
                .setContentIntent(contentIntent)
                .setSmallIcon(R.drawable.ic_daily_faith_icon)
                .setAutoCancel(true)
                .setContentTitle(titleText)
                .setSound(RingtoneManager
                        .getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentText(bigText);*//*

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context,
                        String.valueOf(NOTIFY_ID))
                .setContentIntent(contentIntent)
                .setSmallIcon(R.drawable.ic_daily_faith_icon)
                .setContentTitle(titleText)
                .setContentText(bigText)
                .setSound(RingtoneManager
                        .getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = String.valueOf(NOTIFY_ID);
         //   String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel =
                    new NotificationChannel(String.valueOf(NOTIFY_ID),
                    name,
                    importance);
          //  channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager =
                    context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

//            NotificationManagerCompat notificationManager = NotificationManagerCompat
//                    .from(context);

// notificationId is a unique int for each notification that you must define
            notificationManager.notify(NOTIFY_ID, builder.build());

        }
        else {
            Notification.Builder builder1 = new Notification.Builder(context)
                    .setContentIntent(contentIntent)
                    .setSmallIcon(R.drawable.ic_daily_faith_icon)
                    .setAutoCancel(true)
                    .setContentTitle(titleText)
                    .setSound(RingtoneManager
                            .getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                    .setContentText(bigText);


            Notification notification = new Notification.BigTextStyle(builder1)
                    .bigText(bigText).build();
            NotificationManager notificationManager = (NotificationManager) context
                    .getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(NOTIFY_ID, notification);

        }

        Log.d("notificationBuild", "Notification Builder set");


    *//*    // check vibration.
        if (mPrefs.getBoolean("vibration", true)) {
            builder.setVibrate(new long[]{0, 50});
        }*//*

         *//*     // create default title if empty.
        if (titleText.equals("")) {
            builder.setContentTitle(
                    context.getString(R.string.app_name));
        }*//*

        // show notification. check for delay.
      *//*  builder.setWhen(System.currentTimeMillis());
        Log.d("notificationSetWhen", "Notification set when triggered");

        Notification notification = new Notification.BigTextStyle(builder)
                .bigText(bigText).build();

        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFY_ID, notification);
*//*
    //    wakeLock.release();*/
        int NOTIFY_ID = intent.getIntExtra("notify_id", 0);
        String titleText = intent.getStringExtra("title");
        String bigText = intent.getStringExtra("quote");
        int requestCode = intent.getIntExtra("requestCode", 0);
        String notifyQuoteId = intent.getStringExtra("notifyQuoteId");
        sendNotification(context, bigText, NOTIFY_ID, requestCode,
                notifyQuoteId);
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library

    }
}