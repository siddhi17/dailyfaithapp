package com.example.dailyfaithapp.Model;

import java.util.ArrayList;

public class Collections {

    int id;
    String collection_msg;
    int color;
    ArrayList<Collections> collectionsArrayList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCollection_msg() {
        return collection_msg;
    }

    public void setCollection_msg(String collection_msg) {
        this.collection_msg = collection_msg;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public ArrayList<Collections> getCollectionsArrayList() {
        return collectionsArrayList;
    }

    public void setCollectionsArrayList(
            ArrayList<Collections> collectionsArrayList
    ) {
        this.collectionsArrayList = collectionsArrayList;
    }
}
