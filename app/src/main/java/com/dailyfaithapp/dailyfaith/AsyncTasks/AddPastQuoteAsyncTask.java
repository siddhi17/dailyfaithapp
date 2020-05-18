package com.dailyfaithapp.dailyfaith.AsyncTasks;

import android.os.AsyncTask;

import androidx.appcompat.app.AppCompatActivity;

import com.dailyfaithapp.dailyfaith.Database.DatabaseHandler;
import com.dailyfaithapp.dailyfaith.Model.Quotes;

public class AddPastQuoteAsyncTask extends
                                   AsyncTask<String, Integer, Boolean> {

    DatabaseHandler dbConnector;
    AddPastQuoteCallBack addPastQuoteCallBack;
    private AppCompatActivity mContext;
    int position;

    public AddPastQuoteAsyncTask(
            AppCompatActivity context,
            AddPastQuoteCallBack addPastQuoteCallBack
    ) {

        this.mContext = context;
        this.addPastQuoteCallBack = addPastQuoteCallBack;
        dbConnector = new DatabaseHandler(context);

    }

    @Override
    protected Boolean doInBackground(String... params) {

        if (dbConnector != null) {

            Quotes quotes = new Quotes();

            quotes.setQuote(params[0]);
            quotes.setDocId(params[1]);
            quotes.setIsPastQuoteSaved("1");
            quotes.setIsFavourite("0");

            this.position = Integer.parseInt(params[2]);
            dbConnector.addPastQuote(quotes);

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
            addPastQuoteCallBack.onPostExecute(b, position);
            return;
        }
    }

    public interface AddPastQuoteCallBack {
        void onPostExecute(Boolean b, int position);
    }
}
