package com.example.dailyfaithapp.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;

import com.example.dailyfaithapp.Database.DatabaseHandler;
import com.example.dailyfaithapp.Model.Quotes;

import java.util.ArrayList;
import java.util.List;

public class GetFavouritesAsyncTask
        extends AsyncTask<Void, Integer, List<Quotes>> {

    DatabaseHandler dbConnector;
    List<Quotes> mFavouriteList = new ArrayList<>();
    GetFavouritesCallBack getFavouritesCallBack;
    private Context mContext;

    public GetFavouritesAsyncTask(
            Context context, GetFavouritesCallBack getFavouritesCallBack
    ) {

        this.mContext = context;
        this.getFavouritesCallBack = getFavouritesCallBack;
        dbConnector = new DatabaseHandler(context);

    }

    @Override
    protected List<Quotes> doInBackground(Void... params) {

        if (dbConnector.getAllFavourites() != null) {

            mFavouriteList.addAll(dbConnector.getAllFavourites());

            return mFavouriteList;
        }
        else {
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<Quotes> favouritesList) {
        if (favouritesList != null) {
            dbConnector.close();

            getFavouritesCallBack.onPostExecute(mFavouriteList);
            return;
        }
    }

    public interface GetFavouritesCallBack {
        void onPostExecute(List<Quotes> mFavouriteList);
    }
}
