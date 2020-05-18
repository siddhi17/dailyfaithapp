package com.dailyfaithapp.dailyfaith.AsyncTasks;

import android.os.AsyncTask;

import androidx.appcompat.app.AppCompatActivity;

import com.dailyfaithapp.dailyfaith.Database.DatabaseHandler;
import com.dailyfaithapp.dailyfaith.Model.Quotes;

public class GetQuoteAsyncTask extends
                               AsyncTask<String, Integer, Quotes> {

    DatabaseHandler dbConnector;
    GetQuoteCallBack getQuoteCallBack;
    int position;
    private AppCompatActivity mContext;
    private Boolean pastQuotes;

    public GetQuoteAsyncTask(
            AppCompatActivity context, GetQuoteCallBack getQuoteCallBack
    ) {

        this.mContext = context;
        this.getQuoteCallBack = getQuoteCallBack;
        dbConnector = new DatabaseHandler(context);
        this.pastQuotes = pastQuotes;
    }

    @Override
    protected Quotes doInBackground(String... params) {

        if (dbConnector != null) {

            Quotes quotes = dbConnector.getQuote(params[0]);
            this.position = Integer.parseInt(params[1]);

            return quotes;
        }
        else {
            return null;
        }
    }

    @Override
    protected void onPostExecute(Quotes quotes) {

        dbConnector.close();
        getQuoteCallBack.onPostExecute(quotes, position);
        return;

    }

    public interface GetQuoteCallBack {
        void onPostExecute(Quotes quotes, int position);
    }
}
