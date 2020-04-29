package com.example.dailyfaithapp.Repositories;


import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.dailyfaithapp.Database.QuotesDAO;
import com.example.dailyfaithapp.Database.QuotesDatabase;
import com.example.dailyfaithapp.Model.Quotes;

import java.util.List;

public class QuotesRepository {

    private LiveData<List<Quotes>> allFavourites;
    private QuotesDAO favouritesDAO;

    public QuotesRepository(Application application) {
        QuotesDatabase db;
        db = QuotesDatabase.getQuotesDatabase(application);
        favouritesDAO = db.quotesDAO();
        allFavourites = favouritesDAO.getAllFavourites();
    }

    public LiveData<List<Quotes>> getAllFavourites() {
        return getAllFavourites();
    }

    private static class getFavouritesAsyncTask extends
                                                AsyncTask<String, Void, List<Quotes>> {

        private QuotesDAO favouritesDAO;
        private QuotesRepository delegate = null;

        getFavouritesAsyncTask(QuotesDAO dao) {
            favouritesDAO = dao;
        }

        @Override
        protected List<Quotes> doInBackground(final String... params) {
            return favouritesDAO.loadAllFavorites();
        }
    }
}
