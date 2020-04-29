package com.example.dailyfaithapp.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;

import com.example.dailyfaithapp.Database.DatabaseHandler;
import com.example.dailyfaithapp.Model.Favourites;
import com.example.dailyfaithapp.Model.Quotes;

import java.util.List;

public class AddFavouriteAsyncTask extends
                                   AsyncTask<String, Integer, Boolean> {

    DatabaseHandler dbConnector;
    List<Favourites> mFavouriteList;
    AddFavouriteCallBack addFavouriteCallBack;
    private Context mContext;

    public AddFavouriteAsyncTask(
            Context context, AddFavouriteCallBack addFavouriteCallBack
    ) {

        this.mContext = context;
        this.addFavouriteCallBack = addFavouriteCallBack;
        dbConnector = new DatabaseHandler(context);

    }

    @Override
    protected Boolean doInBackground(String... params) {

        if (dbConnector != null) {

            Quotes favourites = new Quotes();
            favourites.setId(Integer.parseInt(params[0]));
            favourites.setFavourite(params[1]);

            dbConnector.addFavourite(favourites);

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
            addFavouriteCallBack.onPostExecute(b);
            return;
        }
    }

    public interface AddFavouriteCallBack {
        void onPostExecute(Boolean b);
    }
}
