package com.dailyfaithapp.dailyfaith.Utils;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.core.content.res.ResourcesCompat;
import androidx.exifinterface.media.ExifInterface;

import com.dailyfaithapp.dailyfaith.MyNewIntentReceiver;
import com.dailyfaithapp.dailyfaith.MyReceiver;
import com.dailyfaithapp.dailyfaith.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        // Create a media file name
        String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmm")
                .format(new Date());
        File mediaFile;
        String mImageName = "MI_" + timeStamp + ".jpg";
        mediaFile = new File(
                mediaStorageDir.getPath() + File.separator + mImageName);
        return mediaFile;
    }

    public static File storeImage(Bitmap image, Context context) {
        File pictureFile = getOutputMediaFile(context);
        if (pictureFile == null) {
            Log.d(
                    TAG,
                    "Error creating media file, check storage permissions: "
            );// e.getMessage());
        }
        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
/*            image =
                    compressImage(new File(getFileDir(context),
                            pictureFile.getName()).getAbsolutePath());*/
            image.compress(Bitmap.CompressFormat.JPEG, 100, fos);

            fos.close();
        } catch (FileNotFoundException e) {
            Log.d(TAG, "File not found: " + e.getMessage());
        } catch (IOException e) {
            Log.d(TAG, "Error accessing file: " + e.getMessage());
        }
        return pictureFile;
    }

    public static int getDominantColor1(Bitmap bitmap) {

        if (bitmap == null) {
            throw new NullPointerException();
        }

        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int size = width * height;
        int pixels[] = new int[size];

        Bitmap bitmap2 = bitmap.copy(Bitmap.Config.ARGB_4444, false);

        bitmap2.getPixels(pixels, 0, width, 0, 0, width, height);

        final List<HashMap<Integer, Integer>> colorMap
                = new ArrayList<HashMap<Integer, Integer>>();
        colorMap.add(new HashMap<Integer, Integer>());
        colorMap.add(new HashMap<Integer, Integer>());
        colorMap.add(new HashMap<Integer, Integer>());

        int color = 0;
        int r = 0;
        int g = 0;
        int b = 0;
        Integer rC, gC, bC;
        for (int i = 0; i < pixels.length; i++) {
            color = pixels[i];

            r = Color.red(color);
            g = Color.green(color);
            b = Color.blue(color);

            rC = colorMap.get(0).get(r);
            if (rC == null) {
                rC = 0;
            }
            colorMap.get(0).put(r, ++rC);

            gC = colorMap.get(1).get(g);
            if (gC == null) {
                gC = 0;
            }
            colorMap.get(1).put(g, ++gC);

            bC = colorMap.get(2).get(b);
            if (bC == null) {
                bC = 0;
            }
            colorMap.get(2).put(b, ++bC);
        }

        int[] rgb = new int[3];
        for (int i = 0; i < 3; i++) {
            int max = 0;
            int val = 0;
            for (Map.Entry<Integer, Integer> entry : colorMap.get(i)
                    .entrySet()) {
                if (entry.getValue() > max) {
                    max = entry.getValue();
                    val = entry.getKey();
                }
            }
            rgb[i] = val;
        }

        int dominantColor = Color.rgb(rgb[0], rgb[1], rgb[2]);

        return dominantColor;
    }

    public static List<String> setAlarmTimeList(
            String startTime, String endTime,
            int howMany
    ) {

        List<String> times = new ArrayList<>();
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
            Date start = dateFormat.parse(startTime);
            Date end = dateFormat.parse(endTime);
            long minutes = ((end.getTime() - start.getTime()) / 1000 / 60) /
                    howMany;
            for (int i = 0; i < howMany; i++) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(start);
                cal.add(Calendar.MINUTE, (int) (i * minutes));
                String time = dateFormat.format(cal.getTime());
                times.add(time);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        Log.d("timesList", times.toString());
        return times;
    }

    public static void showNotification(
            List<String> timeList, Context context,
            String quote
    ) {

        Intent notifyIntent = new Intent(context, MyNewIntentReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0,
                notifyIntent, PendingIntent.FLAG_ONE_SHOT
        );

        notifyIntent.putExtra("title", context.getString(R.string.app_name));

        AlarmManager alarmManager = (AlarmManager) context
                .getSystemService(Context.ALARM_SERVICE);

        for (String time : timeList) {
            notifyIntent.putExtra("notify_id", timeList.get(time.length()));

            notifyIntent.putExtra(
                    "quote",
                    quote
            );
            alarmManager
                    .setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                            Long.parseLong(timeList.get(0)),
                            Long.parseLong(time),
                            pendingIntent
                    );
        }

        Log.d("notificationIntentSet", "Utils, pending intent set");
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

    public static int calculateInSampleSize(
            BitmapFactory.Options options,
            int reqWidth,
            int reqHeight,
            String src
    ) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio =
                    Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        final float totalPixels = (float) width * height;
        final float totalReqPixelsCap = (float) reqWidth * reqHeight * 2;
        while (totalPixels / (inSampleSize * inSampleSize) >
                totalReqPixelsCap) {
            inSampleSize++;
        }

        Bitmap bitmap = null;
        BitmapFactory.Options options1 = new BitmapFactory.Options();
        options1.inJustDecodeBounds = false;
        options1.inSampleSize = inSampleSize;

        while (bitmap == null) {
            Log.d("bitmap", "bitmap: null");
            try {
                bitmap = BitmapFactory.decodeFile(src, options);
                break;
            } catch (OutOfMemoryError exception) {
                Log.e(TAG, "exception", exception);
                inSampleSize++;

            }
        }
        return inSampleSize;
    }

    public static Bitmap compressImage(String filePath) {
        Bitmap scaledBitmap = null;

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        Bitmap bmp = BitmapFactory.decodeFile(filePath, options);
        int actualHeight = options.outHeight;
        int actualWidth = options.outWidth;


        options.inSampleSize =
                calculateInSampleSize(
                        options, actualWidth, actualHeight, filePath);

        options.inJustDecodeBounds = false;

        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inTempStorage = new byte[16 * 1024];

        try {
            bmp = BitmapFactory.decodeFile(filePath, options);
        } catch (OutOfMemoryError exception) {
            Log.e(TAG, "exception", exception);

        }
        try {
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight,
                    Bitmap.Config.ARGB_8888
            );
        } catch (OutOfMemoryError exception) {
            Log.e(TAG, "exception", exception);
        }

        float ratioX = actualWidth / (float) options.outWidth;
        float ratioY = actualHeight / (float) options.outHeight;
        float middleX = actualWidth / 2.0f;
        float middleY = actualHeight / 2.0f;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bmp, middleX - (float) bmp.getWidth() / 2,
                middleY - (float) bmp.getHeight() / 2
                , new Paint(Paint.FILTER_BITMAP_FLAG)
        );

        ExifInterface exif;
        try {
            exif = new ExifInterface(filePath);

            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION, 0);
            Log.d("EXIF", "Exif: " + orientation);
            Matrix matrix = new Matrix();
            if (orientation == 6) {
                matrix.postRotate(90);
                Log.d("EXIF", "Exif: " + orientation);
            }
            else if (orientation == 3) {
                matrix.postRotate(180);
                Log.d("EXIF", "Exif: " + orientation);
            }
            else if (orientation == 8) {
                matrix.postRotate(270);
                Log.d("EXIF", "Exif: " + orientation);
            }
            if (null != scaledBitmap) {
                scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0,
                        scaledBitmap.getWidth(), scaledBitmap.getHeight(),
                        matrix,
                        true
                );
            }
        } catch (IOException e) {
            Log.e(TAG, "exception", e);
        }

        return scaledBitmap;

    }

}
