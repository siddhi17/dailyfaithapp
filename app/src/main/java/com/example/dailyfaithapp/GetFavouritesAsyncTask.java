/*
package com.example.dailyfaithapp;

import android.app.Activity;
import android.os.AsyncTask;

import androidx.room.Room;

import com.example.dailyfaithapp.Database.QuotesDAO;
import com.example.dailyfaithapp.Database.QuotesDatabase;

import java.lang.ref.WeakReference;

public class GetFavouritesAsyncTask extends AsyncTask<Void, Void, Integer> {

    //Prevent leak
    private WeakReference<Activity> weakActivity;
    private String email;
    private String phone;
    private String license;
    private QuotesDatabase mDatabase;

    public GetFavouritesAsyncTask(Activity activity, String email, String phone, String license) {
        weakActivity = new WeakReference<>(activity);
        this.email = email;
        this.phone = phone;
        this.license = license;

    }

    @Override
    protected Integer doInBackground(Void... params) {
   */
/*     QuotesDAO favouritesDAO = MyApp.DatabaseSetup.getDatabase().agentDao();*//*


        return agentDao.agentsCount(email, phone, license);
    }

    @Override
    protected void onPostExecute(Integer agentsCount) {
        Activity activity = weakActivity.get();
        if(activity == null) {
            return;
        }

        if (agentsCount > 0) {
            //2: If it already exists then prompt user
            Toast.makeText(activity, "Agent already exists!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(activity, "Agent does not exist! Hurray :)", Toast.LENGTH_LONG).show();
            activity.onBackPressed();
        }
    }
}
*/
