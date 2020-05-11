package com.dailyfaithapp.dailyfaith;

import android.app.Application;

import com.dailyfaithapp.dailyfaith.Model.Quotes;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class MyApplication extends Application {
    public FirebaseFirestore db;
    public ArrayList<Quotes> myGlobalArray = null;

    public MyApplication() {
        myGlobalArray = new ArrayList();

    }
}