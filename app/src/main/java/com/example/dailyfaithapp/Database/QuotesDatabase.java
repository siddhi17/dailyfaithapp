package com.example.dailyfaithapp.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.dailyfaithapp.Model.Quotes;

@Database(entities = {Quotes.class}, version = 1, exportSchema = false)
public abstract class QuotesDatabase extends RoomDatabase {

    private static QuotesDatabase INSTANCE;

    public static QuotesDatabase getQuotesDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (QuotesDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE =
                            Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    QuotesDatabase.class,
                                    "quotes_database"
                            ).build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract QuotesDAO quotesDAO();
}
