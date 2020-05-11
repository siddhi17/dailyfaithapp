package com.dailyfaithapp.dailyfaith.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;

import com.dailyfaithapp.dailyfaith.Database.DatabaseHandler;
import com.dailyfaithapp.dailyfaith.Model.Quotes;

import java.util.ArrayList;

public class GetQuotesByCategoryAsyncTask extends
                                          AsyncTask<String, Integer,
                                                  ArrayList<Quotes>> {

    DatabaseHandler dbConnector;
    ArrayList<Quotes> quotesArrayList = new ArrayList<>();
    GetQuotesByCategoryCallBack getQuotesByCategoryCallBack;
    private Context mContext;

    public GetQuotesByCategoryAsyncTask(
            Context context,
            GetQuotesByCategoryCallBack getQuotesByCategoryCallBack
    ) {

        this.mContext = context;
        this.getQuotesByCategoryCallBack = getQuotesByCategoryCallBack;
        dbConnector = new DatabaseHandler(context);

    }

    @Override
    protected ArrayList<Quotes> doInBackground(String... params) {

        if (dbConnector != null) {

            quotesArrayList.addAll(dbConnector.getQuotesByCategory(
                    params[0],
                    params[1]
            ));

            return quotesArrayList;
        }
        else {
            return null;
        }
    }

    @Override
    protected void onPostExecute(ArrayList<Quotes> favouritesList) {
        if (favouritesList != null) {

            dbConnector.close();
            getQuotesByCategoryCallBack.onPostExecute(quotesArrayList);

            return;
        }
    }

    public interface GetQuotesByCategoryCallBack {
        void onPostExecute(ArrayList<Quotes> quotesList);
    }
}
