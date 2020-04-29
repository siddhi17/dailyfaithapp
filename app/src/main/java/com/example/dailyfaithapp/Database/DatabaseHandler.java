package com.example.dailyfaithapp.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.dailyfaithapp.Model.Favourites;
import com.example.dailyfaithapp.Model.Quotes;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "DailyFaithDatabase";
    private static final String TABLE_QUOTES = "quotes";
    private static final String KEY_ID = "id";
    private static final String KEY_QUOTE = "quote";
    private static final String KEY_FAVOURITE = "isFavourite";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_QUOTES + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                KEY_QUOTE +
                " TEXT," + KEY_FAVOURITE + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUOTES);

        // Create tables again
        onCreate(db);
    }

 /*   // code to add the new contact
    public void addFavourite(Quotes favourites) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_QUOTE, favourites.getQuote());
        values.put(KEY_FAVOURITE, favourites.isFavourite());// Contact Name

        // Inserting Row
        db.insert(TABLE_QUOTES, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }*/

    public void addQuotesList(List<Quotes> listItem) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        for (int i = 0; i < listItem.size(); i++) {

            Log.e("vlaue inserting==", "" + listItem.get(i));
            values.put(KEY_QUOTE, listItem.get(i).getQuote());
            values.put(KEY_FAVOURITE, listItem.get(i).isFavourite());
            db.insert(TABLE_QUOTES, null, values);
        }

        db.close(); // Closing database connection
    }

    // code to add the new contact
   /* public void addFavourite(Quotes favourites) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_QUOTE, favourites.getQuote()); // Contact Name

        // Inserting Row
        db.insert(TABLE_QUOTES, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }*/

   /* // code to get the single contact
    Contact getContact(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CONTACTS, new String[] { KEY_ID,
                        KEY_NAME, KEY_PH_NO }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Contact contact = new Contact(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));
        // return contact
        return contact;
    }
*/

    // code to get all contacts in a list view
    public List<Quotes> getAllQuotes() {
        List<Quotes> quotesArrayList = new ArrayList<Quotes>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_QUOTES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Quotes quotes = new Quotes();

                quotes.setId(Integer.parseInt(cursor.getString(0)));
                quotes.setQuote(cursor.getString(1));
                quotes.setFavourite(cursor.getString(2));
                // Adding contact to list
                quotesArrayList.add(quotes);

            }
            while (cursor.moveToNext());
        }

        // return contact list
        return quotesArrayList;
    }

    // code to get all contacts in a list view
    public List<Quotes> getAllFavourites() {
        List<Quotes> favouritesList = new ArrayList<Quotes>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_QUOTES + " WHERE " +
                " isFavourite == 1";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Quotes quotes = new Quotes();

                quotes.setId(Integer.parseInt(cursor.getString(0)));
                quotes.setQuote(cursor.getString(1));
                quotes.setFavourite(cursor.getString(2));
                // Adding contact to list
                favouritesList.add(quotes);

            }
            while (cursor.moveToNext());
        }

        // return contact list
        return favouritesList;
    }

    // code to update the single contact
    public int addFavourite(Quotes quotes) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_FAVOURITE, quotes.isFavourite());

        // updating row
        return db.update(TABLE_QUOTES, values, KEY_ID + " = ?",
                new String[]{String.valueOf(quotes.getId())}
        );
    }

    // Deleting single contact
    public void unFavourite(Favourites favourites) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_QUOTES, KEY_ID + " = ?",
                new String[]{String.valueOf(favourites.getId())}
        );
        db.close();
    }

    // Getting contacts Count
    public int getFavouritesCount() {
        String countQuery = "SELECT  * FROM " + TABLE_QUOTES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }
}