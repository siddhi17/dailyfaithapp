package com.example.dailyfaithapp.ViewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.dailyfaithapp.Model.Quotes;
import com.example.dailyfaithapp.Repositories.QuotesRepository;

import java.util.List;

public class FavouritesViewModel extends AndroidViewModel {

    public QuotesRepository mRepository;

    public LiveData<List<Quotes>> mAllFavourites;

    public FavouritesViewModel(Application application) {
        super(application);
        mRepository = new QuotesRepository(application);
        mAllFavourites = mRepository.getAllFavourites();
    }

    public LiveData<List<Quotes>> getmAllFavourites() {
        return
                mAllFavourites;
    }

}
