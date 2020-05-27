package com.dailyfaithapp.dailyfaith;

import android.app.Application;
import android.graphics.Bitmap;

import com.dailyfaithapp.dailyfaith.Model.Quotes;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.File;
import java.util.ArrayList;

public class MyApplication extends Application {
    public FirebaseFirestore db;
    public ArrayList<Quotes> myGlobalArray = null;
    public ArrayList<String> themeFontsList = new ArrayList<>();
    public File mImageFile;
    public Bitmap mBitmap = null;

    public MyApplication() {
        myGlobalArray = new ArrayList();
        mImageFile = new File("");
    }
}