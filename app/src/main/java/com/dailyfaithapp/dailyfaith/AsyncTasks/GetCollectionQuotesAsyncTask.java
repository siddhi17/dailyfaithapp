package com.dailyfaithapp.dailyfaith.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;

import com.dailyfaithapp.dailyfaith.Database.DatabaseHandler;

import java.util.ArrayList;

public class GetCollectionQuotesAsyncTask extends
                                          AsyncTask<String, Integer,
                                                  ArrayList<String>> {

    DatabaseHandler dbConnector;
    ArrayList<String> quotes = new ArrayList<>();
    GetCollectionQuotesCallBack
            getCollectionQuotesCallBack;
    private Context mContext;

    public GetCollectionQuotesAsyncTask(
            Context context,
            GetCollectionQuotesCallBack getCollectionQuotesCallBack
    ) {

        this.mContext = context;
        this.getCollectionQuotesCallBack = getCollectionQuotesCallBack;
        dbConnector = new DatabaseHandler(context);

    }

    @Override
    protected ArrayList<String> doInBackground(String... params) {

        if (dbConnector != null) {

            quotes.addAll(dbConnector
                    .getCollectionsQuotes(Integer.parseInt(params[0])));

            return quotes;
        }
        else {
            return null;
        }
    }

    @Override
    protected void onPostExecute(ArrayList<String> quotes) {
        if (quotes != null) {
            dbConnector.close();

            getCollectionQuotesCallBack.onPostExecute(quotes);
            return;
        }
    }

    public interface GetCollectionQuotesCallBack {
        void onPostExecute(ArrayList<String> quotesList);
    }
}
