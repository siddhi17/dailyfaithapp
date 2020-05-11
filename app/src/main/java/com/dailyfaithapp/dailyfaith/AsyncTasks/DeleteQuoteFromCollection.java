package com.dailyfaithapp.dailyfaith.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;

import com.dailyfaithapp.dailyfaith.Database.DatabaseHandler;

public class DeleteQuoteFromCollection extends
                                       AsyncTask<String, Integer, Boolean> {

    DatabaseHandler dbConnector;
    DeleteQuoteFromCollectionCallBack deleteQuoteFromCollectionCallBack;
    int position;
    private Context mContext;

    public DeleteQuoteFromCollection(
            Context context,
            DeleteQuoteFromCollectionCallBack deleteQuoteFromCollectionCallBack
    ) {

        this.mContext = context;
        this.deleteQuoteFromCollectionCallBack
                = deleteQuoteFromCollectionCallBack;
        dbConnector = new DatabaseHandler(context);
    }

    @Override
    protected Boolean doInBackground(String... params) {

        if (dbConnector != null) {

            dbConnector.deleteQuoteFromCollection(params[0]);
            this.position = Integer.parseInt(params[1]);

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
            deleteQuoteFromCollectionCallBack.onPostExecute(b, position);
            return;
        }
    }

    public interface DeleteQuoteFromCollectionCallBack {
        void onPostExecute(Boolean b, int position);
    }

}
