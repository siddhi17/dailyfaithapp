package com.dailyfaithapp.dailyfaith.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;

import com.dailyfaithapp.dailyfaith.Database.DatabaseHandler;
import com.dailyfaithapp.dailyfaith.Model.Quotes;

import java.util.ArrayList;

public class GetAllPastQuotesAsyncTask extends
                                       AsyncTask<Void, Integer,
                                               ArrayList<Quotes>> {

    DatabaseHandler dbConnector;
    ArrayList<Quotes> quotes = new ArrayList<>();
    GetAllPastCollectionQuotesCallBack getAllPastCollectionQuotesCallBack;
    private Context mContext;

    public GetAllPastQuotesAsyncTask(
            Context context,
            GetAllPastCollectionQuotesCallBack getAllPastCollectionQuotesCallBack
    ) {

        this.mContext = context;
        this.getAllPastCollectionQuotesCallBack
                = getAllPastCollectionQuotesCallBack;
        dbConnector = new DatabaseHandler(context);

    }

    @Override
    protected ArrayList<Quotes> doInBackground(Void... params) {

        if (dbConnector != null) {

            quotes.addAll(dbConnector
                    .getPastCollectionsQuotes());

            return quotes;
        }
        else {
            return null;
        }
    }

    @Override
    protected void onPostExecute(ArrayList<Quotes> quotes) {
        if (quotes != null) {
            dbConnector.close();

            getAllPastCollectionQuotesCallBack.onPostExecute(quotes);
            return;
        }
    }

    public interface GetAllPastCollectionQuotesCallBack {
        void onPostExecute(ArrayList<Quotes> quotesList);
    }
}
