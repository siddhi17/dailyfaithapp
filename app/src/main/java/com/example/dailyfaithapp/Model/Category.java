package com.example.dailyfaithapp.Model;

import java.util.ArrayList;

public class Category {


    int id;
    String category;
    int color;
    ArrayList<Category> categoryArrayList;


    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public ArrayList<Category> getCategoryArrayList() {
        return categoryArrayList;
    }

    public void setCategoryArrayList(
            ArrayList<Category> categoryArrayList
    ) {
        this.categoryArrayList = categoryArrayList;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
