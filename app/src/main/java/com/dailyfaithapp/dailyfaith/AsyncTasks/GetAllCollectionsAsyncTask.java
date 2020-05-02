package com.dailyfaithapp.dailyfaith.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;

import com.dailyfaithapp.dailyfaith.Database.DatabaseHandler;
import com.dailyfaithapp.dailyfaith.Model.Collections;

import java.util.ArrayList;
import java.util.List;

public class GetAllCollectionsAsyncTask extends
                                        AsyncTask<Void, Integer, List<Collections>> {

    DatabaseHandler dbConnector;
    List<Collections> collectionsArrayList = new ArrayList<>();
    GetAllCollectionsCallBack getAllCollectionsCallBack;
    private Context mContext;

    public GetAllCollectionsAsyncTask(
            Context context, GetAllCollectionsCallBack getAllCollectionsCallBack
    ) {

        this.mContext = context;
        this.getAllCollectionsCallBack = getAllCollectionsCallBack;
        dbConnector = new DatabaseHandler(context);

    }

    @Override
    protected List<Collections> doInBackground(Void... params) {

        if (dbConnector.getAllCollections() != null) {

            collectionsArrayList.addAll(dbConnector.getAllCollections());

            return collectionsArrayList;
        }
        else {
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<Collections> collectionsArrayList) {
        if (collectionsArrayList != null) {
            dbConnector.close();

            getAllCollectionsCallBack.onPostExecute(collectionsArrayList);
            return;
        }
    }

    public interface GetAllCollectionsCallBack {
        void onPostExecute(List<Collections> quotesList);
    }

}
