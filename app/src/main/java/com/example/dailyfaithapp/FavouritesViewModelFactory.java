package com.example.dailyfaithapp;


import android.app.Application;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.dailyfaithapp.ViewModels.FavouritesViewModel;

public class FavouritesViewModelFactory implements ViewModelProvider.Factory {
    private Application mApplication;
    private String mParam;


    public FavouritesViewModelFactory(Application application) {
        mApplication = application;
    }


    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new FavouritesViewModel(mApplication);
    }
}
