package com.example.dailyfaithapp;

import android.content.Context;

import androidx.room.Room;

import com.example.dailyfaithapp.Database.QuotesDatabase;

public class FavouritesRepository {

    private String DB_NAME = "favourites_database";

    private QuotesDatabase favouritesDatabase;

    public FavouritesRepository(Context context) {
        favouritesDatabase = Room
                .databaseBuilder(context, QuotesDatabase.class, DB_NAME)
                .build();
    }

    public void insertTask(String title) {

        insertTask(title);
    }

       /* public void insertTask(String title,
                String description,
                boolean encrypt,
                String password) {

            Note note = new Note();
            note.setTitle(title);
            note.setDescription(description);
            note.setCreatedAt(AppUtils.getCurrentDateTime());
            note.setModifiedAt(AppUtils.getCurrentDateTime());
            note.setEncrypt(encrypt);


            if(encrypt) {
                note.setPassword(AppUtils.generateHash(password));
            } else note.setPassword(null);

            insertTask(note);
        }

        public void insertTask(final Note note) {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    noteDatabase.daoAccess().insertTask(note);
                    return null;
                }
            }.execute();
        }

        public void updateTask(final Note note) {
            note.setModifiedAt(AppUtils.getCurrentDateTime());

            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    noteDatabase.daoAccess().updateTask(note);
                    return null;
                }
            }.execute();
        }

        public void deleteTask(final int id) {
            final LiveData<Note> task = getTask(id);
            if(task != null) {
                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... voids) {
                        noteDatabase.daoAccess().deleteTask(task.getValue());
                        return null;
                    }
                }.execute();
            }
        }

        public void deleteTask(final Note note) {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    noteDatabase.daoAccess().deleteTask(note);
                    return null;
                }
            }.execute();
        }
*/
      /*  public LiveData<Favourites> getTask(int id) {
            return favouritesDatabase.favouritesDAO().f(id);
        }
*/
       /* public LiveData<List<Favourites>> getFavourites() {
            return favouritesDatabase.quotesDAO().getAllFavourites();
        }
*/
}
