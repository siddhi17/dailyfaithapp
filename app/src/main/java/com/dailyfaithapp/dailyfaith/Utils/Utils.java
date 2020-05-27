package com.dailyfaithapp.dailyfaith.Utils;

import android.annotation.SuppressLint;
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
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.core.content.FileProvider;
import androidx.core.content.res.ResourcesCompat;
import androidx.exifinterface.media.ExifInterface;
import androidx.multidex.BuildConfig;

import com.dailyfaithapp.dailyfaith.Model.Quotes;
import com.dailyfaithapp.dailyfaith.MyNewIntentReceiver;
import com.dailyfaithapp.dailyfaith.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class Utils {

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

    public static List<Date> setAlarmTimeList(
            String startTime, String endTime, int howMany
    ) {
        List<Date> times = new ArrayList<>();
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(
                    "HH:mm", Locale.ENGLISH);
            Date start = dateFormat.parse(startTime);
            Date end = dateFormat.parse(endTime);
            long minutes = ((end.getTime() - start.getTime()) / 1000 / 60) /
                    (howMany - 1);
            Calendar calobj;
            for (int i = 0; i < howMany; i++) {

                calobj = Calendar.getInstance();
                calobj.set(
                        Calendar.HOUR_OF_DAY,
                        Integer.valueOf(dateFormat.format(start).split(":")[0])
                );
                calobj.set(
                        Calendar.MINUTE,
                        Integer.valueOf(dateFormat.format(start).split(":")[1])
                );
                calobj.add(Calendar.MINUTE, (int) (i * minutes));
                calobj.set(Calendar.SECOND, 0);
                times.add(calobj.getTime());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.d("timesList", times.toString());
        return times;
    }


    public static void showNotification(
            List<Date> timeList, Context context,
            List<Quotes> quotesList
    ) {

        for (Date date : timeList) {
            Intent notifyIntent = new Intent(
                    context, MyNewIntentReceiver.class);

            notifyIntent
                    .putExtra("title", context.getString(R.string.app_name));

            final int random = new Random().nextInt();
            notifyIntent.putExtra("notify_id", random);

            if (quotesList.size() > 0) {

                final int randomQuote = new Random().nextInt(quotesList.size());

                Quotes quotes = new Quotes();
                quotes = quotesList.get(randomQuote);

                notifyIntent.putExtra(
                        "quote",
                        quotes.getQuote()
                );


                notifyIntent.putExtra("notifyQuoteId", quotes.getDocId());

            }

            int randomInt = new Random().nextInt(1000);

            notifyIntent.putExtra("requestCode", randomInt);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                    randomInt,
                    notifyIntent, PendingIntent.FLAG_ONE_SHOT

            );

            AlarmManager alarmManager = (AlarmManager) context
                    .getSystemService(Context.ALARM_SERVICE);


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setInexactRepeating
                        (AlarmManager.RTC_WAKEUP,
                                date.getTime(), AlarmManager.INTERVAL_DAY,
                                pendingIntent
                        );
            }
            else {
                alarmManager.setRepeating
                        (AlarmManager.RTC_WAKEUP,
                                date.getTime(), AlarmManager.INTERVAL_DAY,
                                pendingIntent
                        );
            }

        }

        Toast.makeText(context, "Notification is set.", Toast.LENGTH_LONG)
                .show();

        Log.d("notificationIntentSet", "Utils, pending intent set");
    }

    public static Bitmap loadBitmapFromView(View v) {

        Bitmap b = Bitmap.createBitmap(v.getWidth(), v.getHeight(),
                Bitmap.Config.ARGB_8888
        );

        View namebar = v.findViewById(R.id.include2);
        namebar.setVisibility(View.GONE);

        Bitmap mutableBitmap = b.copy(Bitmap.Config.ARGB_8888, true);

        Canvas c = new Canvas(b);
        v.layout(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());
        v.draw(c);
        namebar.setVisibility(View.VISIBLE);
        return b;
    }

    @SuppressLint("LongLogTag")
    public static File createShareImage(View relativeLayout, Context context) {

        Bitmap bitmap = loadBitmapFromView(relativeLayout);
        //   File dir = new File("/sdcard/saved_images");

        File dir = new File(
                "/sdcard/DailyFaith");
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmm")
                .format(new Date());
        String mImageName = "MI_" + timeStamp + ".jpg";

        File file = new File(dir, mImageName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);

        } catch (Exception e) {
            Log.e("ExpressionEditImageActivity", "Error, " + e);
        }

        return file;
    }

    public static boolean checkDate(String startTime, String endTime) {
        Date startDate, endDate;
        boolean isGreater = false;
        try {

            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm",
                    Locale.ENGLISH);

            startDate = sdf.parse(startTime);
            endDate = sdf.parse(endTime);

            Calendar calobjStartDate, calobjectEndDate;

            calobjStartDate = Calendar.getInstance();
            calobjStartDate.set(
                    Calendar.HOUR_OF_DAY,
                    Integer.valueOf(sdf.format(startDate).split(":")[0])
            );
            calobjStartDate.set(
                    Calendar.MINUTE,
                    Integer.valueOf(sdf.format(startDate).split(":")[1])
            );
            calobjStartDate.set(Calendar.SECOND, 0);

            calobjectEndDate = Calendar.getInstance();
            calobjectEndDate.set(
                    Calendar.HOUR_OF_DAY,
                    Integer.valueOf(sdf.format(endDate).split(":")[0])
            );
            calobjectEndDate.set(
                    Calendar.MINUTE,
                    Integer.valueOf(sdf.format(endDate).split(":")[1])
            );
            calobjectEndDate.set(Calendar.SECOND, 0);

            if (System.currentTimeMillis() <
                    calobjStartDate.getTimeInMillis()) {

                if (calobjectEndDate.before(calobjStartDate)) {
                    // method also.
                    isGreater = false;
                }
                else {
                    isGreater = true;
                }
            }
            else {
                isGreater = false;
            }
        } catch (ParseException e) {
            e.toString();

        }
        ;

        return isGreater;
    }

    public static void messageText(String quote, Context context) {

        /*Create an ACTION_SEND Intent*/
        Intent intent = new Intent(android.content.Intent.ACTION_SEND);
        /*This will be the actual content you wish you share.*/
        String shareBody = quote;
        /*The type of the content is text, obviously.*/
        intent.setType("text/plain");
        /*Applying information Subject and Body.*/
        intent.putExtra(
                android.content.Intent.EXTRA_SUBJECT,
                context.getString(R.string.app_name)
        );
        intent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        /*Fire!*/
        context.startActivity(Intent.createChooser(
                intent,
                context.getString(R.string.share_using)
        ));
    }

    public static void openUrl(String url, Context context) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        context.startActivity(browserIntent);
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


    public static void createInstagramIntent(
            String type, String mediaPath,
            Context context
    ) {

        // Create the new Intent using the 'Send' action.
        Intent share = new Intent(Intent.ACTION_SEND);

        // Set the MIME type
        share.setType(type);

        // Create the URI from the media
        File media = new File(mediaPath);
        Uri uri;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

            Log.d("FilePath", media.getPath());
            uri = FileProvider.getUriForFile(
                    context,
                    BuildConfig.APPLICATION_ID + ".provider",
                    media
            );
            share.setPackage("com.instagram.android");
            share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            // Add the URI to the Intent.
            share.putExtra(Intent.EXTRA_STREAM, uri);

        }
        else {

            uri = Uri.fromFile(media);

            share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            share.setPackage("com.instagram.android");
            // Add the URI to the Intent.
            share.putExtra(Intent.EXTRA_STREAM, uri);
        }
        // Broadcast the Intent.
        context.startActivity(share);
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context
                        .getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


}
