package com.example.dailyfaithapp.Model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Quotes {

    @NonNull
    @ColumnInfo(name = "favourite")
    public String isFavourite;
    @PrimaryKey
    int id;
    @NonNull
    @ColumnInfo(name = "quotes")
    String quote;

    public Quotes() {
    }

    public Quotes(String favourites) {
    }

    public String isFavourite() {
        return isFavourite;
    }

    public void setFavourite(String favourite) {
        isFavourite = favourite;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }
}