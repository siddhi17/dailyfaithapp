package com.dailyfaithapp.dailyfaith.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;

import com.dailyfaithapp.dailyfaith.Database.DatabaseHandler;
import com.dailyfaithapp.dailyfaith.Model.Collections;
import com.dailyfaithapp.dailyfaith.Model.Quotes;

import java.util.List;

public class AddQuoteInCollectionAsyncTask extends
                                           AsyncTask<String, Integer,
                                                   Boolean> {

    DatabaseHandler dbConnector;
    List<Quotes> mFavouriteList;
    AddQuoteInCollectionCallBack addQuoteInCollectionCallBack;
    private Context mContext;

    public AddQuoteInCollectionAsyncTask(
            Context context,
            AddQuoteInCollectionCallBack addQuoteInCollectionCallBack
    ) {

        this.mContext = context;
        this.addQuoteInCollectionCallBack = addQuoteInCollectionCallBack;
        dbConnector = new DatabaseHandler(context);

    }

    @Override
    protected Boolean doInBackground(String... params) {

        if (dbConnector != null) {

            Collections collections = new Collections();
            collections.setId(Integer.parseInt(params[0]));
            collections.setCollection_msg(params[1]);

            dbConnector.addIntoCollection(collections);

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
            addQuoteInCollectionCallBack.onPostExecute(b);
            return;
        }
    }

    public interface AddQuoteInCollectionCallBack {
        void onPostExecute(Boolean b);
    }
}