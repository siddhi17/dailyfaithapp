package com.example.dailyfaithapp.Database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.dailyfaithapp.Model.Quotes;

@Database(entities = {Quotes.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract QuotesDAO quotesDAO();
}