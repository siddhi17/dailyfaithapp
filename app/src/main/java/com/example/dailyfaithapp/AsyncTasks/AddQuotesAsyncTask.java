package com.example.dailyfaithapp.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;

import com.example.dailyfaithapp.Database.DatabaseHandler;
import com.example.dailyfaithapp.Model.Quotes;

import java.util.List;

public class AddQuotesAsyncTask extends
                                AsyncTask<List<Quotes>, Integer, Boolean> {

    DatabaseHandler dbConnector;
    List<Quotes> mFavouriteList;
    AddQuotesCallBack addQuotesCallBack;
    private Context mContext;

    public AddQuotesAsyncTask(
            Context context,
            AddQuotesCallBack addQuotesCallBack
    ) {

        this.mContext = context;
        this.addQuotesCallBack = addQuotesCallBack;
        dbConnector = new DatabaseHandler(context);

    }

    @Override
    protected Boolean doInBackground(List<Quotes>... params) {

        if (dbConnector != null) {

            mFavouriteList = params[0];
            dbConnector.addQuotesList(mFavouriteList);

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
            addQuotesCallBack.onPostExecute(b);
            return;
        }
    }

    public interface AddQuotesCallBack {
        void onPostExecute(Boolean b);
    }
}
