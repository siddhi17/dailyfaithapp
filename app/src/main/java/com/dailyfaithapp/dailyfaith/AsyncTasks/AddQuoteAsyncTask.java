package com.dailyfaithapp.dailyfaith.AsyncTasks;

import android.os.AsyncTask;

import androidx.appcompat.app.AppCompatActivity;

import com.dailyfaithapp.dailyfaith.Database.DatabaseHandler;
import com.dailyfaithapp.dailyfaith.Model.Quotes;

public class AddQuoteAsyncTask extends
                               AsyncTask<Quotes, Integer, Boolean> {

    DatabaseHandler dbConnector;
    AddQuoteCallBack addQuoteCallBack;
    private AppCompatActivity mContext;
    private Boolean pastQuotes;

    public AddQuoteAsyncTask(
            AppCompatActivity context, AddQuoteCallBack addQuoteCallBack,
            Boolean pastQuotes
    ) {

        this.mContext = context;
        this.addQuoteCallBack = addQuoteCallBack;
        dbConnector = new DatabaseHandler(context);
        this.pastQuotes = pastQuotes;
    }

    @Override
    protected Boolean doInBackground(Quotes... params) {

        if (dbConnector != null) {

            Quotes quotes = new Quotes();

            quotes.setQuote(params[0].getQuote());
            quotes.setIsPastQuoteSaved("0");
            quotes.setFavourite("0");
            quotes.setCategory(params[0].getCategory());
            quotes.setQuoteType(params[0].getQuoteType());
            dbConnector.addQuote(quotes);

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
            addQuoteCallBack.onPostExecute(b, pastQuotes);
            return;
        }
    }

    public interface AddQuoteCallBack {
        void onPostExecute(Boolean b, Boolean pastQuotes);
    }

}
