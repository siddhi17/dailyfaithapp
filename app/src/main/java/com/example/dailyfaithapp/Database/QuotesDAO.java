package com.example.dailyfaithapp.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.dailyfaithapp.Model.Quotes;

import java.util.List;

@Dao
public interface QuotesDAO {

    @Query("SELECT * FROM Quotes")
    LiveData<List<Quotes>> getAllFavourites();

    @Query("SELECT * FROM Quotes")
    List<Quotes> loadAllFavorites();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllFavourites(Quotes... quotes);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertFavourite(Quotes quotes);

    @Delete
    void delete(Quotes quotes);
}
