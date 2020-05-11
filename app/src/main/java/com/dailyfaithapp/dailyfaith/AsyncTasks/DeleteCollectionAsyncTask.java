package com.dailyfaithapp.dailyfaith.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;

import com.dailyfaithapp.dailyfaith.Database.DatabaseHandler;


public class DeleteCollectionAsyncTask extends
                                       AsyncTask<String, Integer, Boolean> {

    DatabaseHandler dbConnector;
    DeleteCollectionCallBack deleteCollectionCallBack;
    int position;
    private Context mContext;

    public DeleteCollectionAsyncTask(
            Context context, DeleteCollectionCallBack deleteCollectionCallBack
    ) {

        this.mContext = context;
        this.deleteCollectionCallBack = deleteCollectionCallBack;
        dbConnector = new DatabaseHandler(context);
    }

    @Override
    protected Boolean doInBackground(String... params) {

        if (dbConnector != null) {

            dbConnector.deleteCollection(params[0]);
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
            deleteCollectionCallBack.onPostExecute(b, position);
            return;
        }
    }

    public interface DeleteCollectionCallBack {
        void onPostExecute(Boolean b, int position);
    }
}
