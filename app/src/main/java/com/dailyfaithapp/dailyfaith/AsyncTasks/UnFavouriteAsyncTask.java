package com.dailyfaithapp.dailyfaith.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;

import com.dailyfaithapp.dailyfaith.Database.DatabaseHandler;
import com.dailyfaithapp.dailyfaith.Model.Favourites;
import com.dailyfaithapp.dailyfaith.Model.Quotes;

import java.util.List;

public class UnFavouriteAsyncTask extends
                                  AsyncTask<String, Integer, Boolean> {

    DatabaseHandler dbConnector;
    List<Favourites> mFavouriteList;
    UnFavouriteCallBack unFavouriteCallBack;
    private Context mContext;

    public UnFavouriteAsyncTask(
            Context context, UnFavouriteCallBack unFavouriteCallBack
    ) {

        this.mContext = context;
        this.unFavouriteCallBack = unFavouriteCallBack;
        dbConnector = new DatabaseHandler(context);

    }

    @Override
    protected Boolean doInBackground(String... params) {

        if (dbConnector != null) {

            Quotes favourites = new Quotes();
            favourites.setId(Integer.parseInt(params[0]));
            favourites.setFavourite(params[1]);

            dbConnector.unFavourite(favourites);

            return true;
        }
        else {
            return null;
        }
    }

    @Override
    protected void onPostExecute(Boolean b) {
        if (b != null) {
            dbConnector.close();
            unFavouriteCallBack.onPostExecute(b);
            return;
        }
    }

    public interface UnFavouriteCallBack {
        void onPostExecute(Boolean b);
    }

}
