package com.dailyfaithapp.dailyfaith.Utils;

import android.Manifest;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import com.dailyfaithapp.dailyfaith.MyReceiver;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class Utils {

    private static final int NOTIFICATION_REMINDER = 100;

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(
                Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static String getURLForResource(int resourceId, Context context) {
        //use BuildConfig.APPLICATION_ID instead of R.class.getPackage().getName() if both are not same
    /*    return Uri
                .parse("android.resource://"+ BuildConfig.APPLICATION_ID  +
                        "/" +resourceId).toString();*/

        Resources resources = context.getResources();

        return Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://"
                + resources.getResourcePackageName(resourceId) + '/'
                + resources.getResourceTypeName(resourceId) + '/'
                + resources.getResourceEntryName(resourceId)).toString();

    }

    public static int getColor(Context context, int color) {
        return ResourcesCompat.getColor(context.getResources(), color,
                null
        );
    }

    public static Drawable getDrawable(Context context, int drawable) {
        return ResourcesCompat.getDrawable(context.getResources(), drawable,
                null
        );
    }

    public static int calculateBrightness(
            android.graphics.Bitmap bitmap,
            int skipPixel
    ) {
        int R = 0;
        int G = 0;
        int B = 0;
        int height = bitmap.getHeight();
        int width = bitmap.getWidth();
        int n = 0;
        int[] pixels = new int[width * height];
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
        for (int i = 0; i < pixels.length; i += skipPixel) {
            int color = pixels[i];
            R += Color.red(color);
            G += Color.green(color);
            B += Color.blue(color);
            n++;
        }
        return (R + B + G) / (n * 3);
    }

    public static int getDominantColor(Bitmap bitmap) {
        Bitmap newBitmap = Bitmap.createScaledBitmap(bitmap, 1, 1, true);
        final int color = newBitmap.getPixel(0, 0);
        newBitmap.recycle();
        return color;
    }

    public static boolean isColorDark(int color) {
        double darkness = 1 -
                (0.299 * Color.red(color) + 0.587 * Color.green(color) +
                        0.114 * Color.blue(color)) / 255;
        if (darkness < 0.5) {
            return false; // It's a light color
        }
        else {
            return true; // It's a dark color
        }
    }

    public static boolean isStoragePermissionGranted(
            AppCompatActivity context
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
                    != PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG, "Permission is granted");
            }
            return true;
        }
        else {

            Log.v(TAG, "Permission is revoked");
            ActivityCompat.requestPermissions(context,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    200
            );
            return false;
        }
    }

    public static String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver()
                    .query(contentUri, proj, null, null, null);
            assert cursor != null;
            int column_index =
                    cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public static File getOutputMediaFile(Context context) {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.
        File mediaStorageDir = new File(
                Environment.getExternalStorageDirectory()
                        + "/Android/data/"
                        + context.getApplicationContext().getPackageName()
                        + "/Files");

        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            mediaStorageDir.mkdirs();
        }
        else {
            return null;
        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmm")
                .format(new Date());
        File mediaFile;
        String mImageName = "MI_" + timeStamp + ".png";
        mediaFile = new File(
                mediaStorageDir.getPath() + File.separator + mImageName);
        return mediaFile;
    }

    public static void storeImage(Bitmap image, Context context) {
        File pictureFile = getOutputMediaFile(context);
        if (pictureFile == null) {
            Log.d(
                    TAG,
                    "Error creating media file, check storage permissions: "
            );// e.getMessage());
            return;
        }
        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            image.compress(Bitmap.CompressFormat.PNG, 90, fos);
            fos.close();
        } catch (FileNotFoundException e) {
            Log.d(TAG, "File not found: " + e.getMessage());
        } catch (IOException e) {
            Log.d(TAG, "Error accessing file: " + e.getMessage());
        }
    }

    public String BitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    public Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory
                    .decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    public void showNotification(Context context, String quote) {


        Intent notifyIntent = new Intent(context, MyReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast
                (
                        context, NOTIFICATION_REMINDER, notifyIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        AlarmManager alarmManager = (AlarmManager) context
                .getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                System.currentTimeMillis(),
                1000 * 60 * 60 * 24, pendingIntent
        );

    }

    public void stopAlarm(Context context) {
        Intent intent = new Intent(context, MyReceiver.class);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 1,
                intent, 0
        );
        AlarmManager alarmManager = (AlarmManager) context
                .getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(alarmIntent);
    }

    public Drawable getResource(Context context, int id) {
        Drawable d =
                context.getResources().getDrawable(id);
        return d;
    }
}
