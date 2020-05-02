package com.dailyfaithapp.dailyfaith.AsyncTasks;

import android.os.AsyncTask;

import androidx.appcompat.app.AppCompatActivity;

import com.dailyfaithapp.dailyfaith.Database.DatabaseHandler;
import com.dailyfaithapp.dailyfaith.Model.Collections;

public class AddCollectionAsyncTask extends
                                    AsyncTask<String, Integer, Boolean> {

    DatabaseHandler dbConnector;
    AddCollectionCallBack addCollectionCallBack;
    private AppCompatActivity mContext;

    public AddCollectionAsyncTask(
            AppCompatActivity context,
            AddCollectionCallBack addCollectionCallBack
    ) {

        this.mContext = context;
        this.addCollectionCallBack = addCollectionCallBack;
        dbConnector = new DatabaseHandler(context);

    }

    @Override
    protected Boolean doInBackground(String... params) {

        if (dbConnector != null) {

            Collections collections = new Collections();
            collections.setTitle(params[0]);

            dbConnector.addCollection(collections);

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
            addCollectionCallBack.onPostExecute(b);
            return;
        }
    }

    public interface AddCollectionCallBack {
        void onPostExecute(Boolean b);
    }

}
