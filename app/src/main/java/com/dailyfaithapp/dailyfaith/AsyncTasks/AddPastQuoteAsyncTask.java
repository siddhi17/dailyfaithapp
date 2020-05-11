package com.dailyfaithapp.dailyfaith.AsyncTasks;

import android.os.AsyncTask;

import androidx.appcompat.app.AppCompatActivity;

import com.dailyfaithapp.dailyfaith.Database.DatabaseHandler;
import com.dailyfaithapp.dailyfaith.Model.Quotes;

public class AddPastQuoteAsyncTask extends
                                   AsyncTask<Quotes, Integer, Boolean> {

    DatabaseHandler dbConnector;
    AddPastQuoteCallBack addPastQuoteCallBack;
    private AppCompatActivity mContext;

    public AddPastQuoteAsyncTask(
            AppCompatActivity context,
            AddPastQuoteCallBack addPastQuoteCallBack
    ) {

        this.mContext = context;
        this.addPastQuoteCallBack = addPastQuoteCallBack;
        dbConnector = new DatabaseHandler(context);

    }

    @Override
    protected Boolean doInBackground(Quotes... params) {

        if (dbConnector != null) {

            Quotes quotes = new Quotes();
            quotes.setQuote(params[0].getQuote());
            quotes.setId(params[0].getId());
            quotes.setIsPastQuoteSaved("1");

            dbConnector.updatePastQuote(quotes);

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
            addPastQuoteCallBack.onPostExecute(b);
            return;
        }
    }

    public interface AddPastQuoteCallBack {
        void onPostExecute(Boolean b);
    }
}
