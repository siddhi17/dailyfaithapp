package com.example.dailyfaithapp.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;

import com.example.dailyfaithapp.Database.DatabaseHandler;
import com.example.dailyfaithapp.Model.Quotes;

import java.util.ArrayList;
import java.util.List;

public class GetAllQuotesAsyncTask extends
                                   AsyncTask<Void, Integer, List<Quotes>> {

    DatabaseHandler dbConnector;
    List<Quotes> quotesArrayList = new ArrayList<>();
    GetAllQuotesCallBack getAllQuotesCallBack;
    private Context mContext;

    public GetAllQuotesAsyncTask(
            Context context, GetAllQuotesCallBack getAllQuotesCallBack
    ) {

        this.mContext = context;
        this.getAllQuotesCallBack = getAllQuotesCallBack;
        dbConnector = new DatabaseHandler(context);

    }

    @Override
    protected List<Quotes> doInBackground(Void... params) {

        if (dbConnector.getAllQuotes() != null) {

            quotesArrayList.addAll(dbConnector.getAllQuotes());

            return quotesArrayList;
        }
        else {
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<Quotes> favouritesList) {
        if (favouritesList != null) {
            dbConnector.close();

            getAllQuotesCallBack.onPostExecute(quotesArrayList);
            return;
        }
    }

    public interface GetAllQuotesCallBack {
        void onPostExecute(List<Quotes> quotesList);
    }
}
