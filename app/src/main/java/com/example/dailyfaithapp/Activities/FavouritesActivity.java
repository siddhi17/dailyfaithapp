package com.example.dailyfaithapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dailyfaithapp.Adapters.FavouritesAdapter;
import com.example.dailyfaithapp.AsyncTasks.GetFavouritesAsyncTask;
import com.example.dailyfaithapp.Database.QuotesDatabase;
import com.example.dailyfaithapp.FavouritesRepository;
import com.example.dailyfaithapp.FavouritesViewModelFactory;
import com.example.dailyfaithapp.Model.Quotes;
import com.example.dailyfaithapp.R;
import com.example.dailyfaithapp.ViewModels.FavouritesViewModel;

import java.util.ArrayList;
import java.util.List;


public class FavouritesActivity extends AppCompatActivity implements
                                                          View.OnClickListener,
                                                          GetFavouritesAsyncTask.GetFavouritesCallBack {

    public FavouritesViewModel favouritesViewModel;
    List<Quotes> favouritesArrayList = new ArrayList<>();
    private RecyclerView recyclerView;
    private FavouritesAdapter favouritesAdapter;
    private ConstraintLayout constraintLayoutEmpty;
    private ImageView imageViewBack;
    private QuotesDatabase mDatabase;
    private FavouritesRepository favouriteRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);

        setUpUI();
        setUpRecyclerView();
        // addFavouritesData();
        //   observerSetup();
    }


    public void setUpUI() {

        recyclerView = findViewById(R.id.recycler_view_favourites);
        constraintLayoutEmpty = findViewById(R.id.constraint_empty);
        imageViewBack = findViewById(R.id.imageView_back);

        imageViewBack.setOnClickListener(this);


        favouritesViewModel = new ViewModelProvider(
                this,
                new FavouritesViewModelFactory(getApplication())
        ).get(FavouritesViewModel.class);

/*
        favouritesViewModel.getmAllFavourites().observe(this,
                new Observer<List<Q>>() {
            @Override
            public void onChanged(@Nullable final List<Favourites> products) {

                favouritesArrayList = products;
            }
        });*/
  /*      favouritesViewModel =
                new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(FavouritesViewModel.class);*/

      /*  favouritesViewModel = ViewModelProviders.of(this,
                new FavouritesViewModelFactory(this.getApplication())).get(FavouritesViewModel.class);*/
    }


    public void setUpRecyclerView() {

        favouritesAdapter = new FavouritesAdapter(favouritesArrayList, this);
        RecyclerView.LayoutManager mLayoutManager =
                new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setAdapter(favouritesAdapter);

        favouritesViewModel.getmAllFavourites().observe(
                this,
                new Observer<List<Quotes>>() {
                    @Override
                    public void onChanged(
                            @Nullable final List<Quotes> favourites
                    ) {
                        // Update the cached copy of the words in the adapter.
                        if (favouritesArrayList.size() == 0) {
                            favouritesArrayList = favourites;
                            favouritesAdapter.updateList(favourites);
                        }
                    }
                }
        );

        // addFavouritesData();
        //  getLocalFavourites();

    }

    @Override
    public void onResume() {
        super.onResume();

        /*       */

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.imageView_back:

                openMotivationActivity();
        }
    }

    public void openMotivationActivity() {

        startActivity(new Intent(this, MotivationActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK));
        FavouritesActivity.this.finish();

    }

    public void getLocalFavourites() {
        GetFavouritesAsyncTask getFavouritesAsyncTask =
                new GetFavouritesAsyncTask(this, this);
        getFavouritesAsyncTask.execute();

    }

    @Override
    public void onPostExecute(List<Quotes> favourites) {
        favouritesArrayList.clear();
        favouritesArrayList.addAll(favourites);
        favouritesAdapter.updateList(favouritesArrayList);

        if (favouritesArrayList.size() > 0) {
            constraintLayoutEmpty.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
        else {
            constraintLayoutEmpty.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
    }

    public void addFavouritesData() {

        Quotes favourites = new Quotes();
        favourites = new Quotes();
        favourites.setQuote("Butterfly lives only few days,\n" +
                "but still it flies joyfully capturing many hearts.\n" +
                "Each moment is precious in life.\n" +
                "Live it fully and win many hearts.");

        favouritesArrayList.add(favourites);
        favourites = new Quotes();
        favourites.setQuote("Always have a unique character like " +
                "SALT!\n" +
                "Its presence is not felt but absence makes all things tasteless!!\n" +
                "Gud day! :-)");

        favouritesArrayList.add(favourites);
        favourites = new Quotes();
        favourites.setQuote("A stronger and positive attitude\n" +
                "creates more miracles than any other thing.\n" +
                "Because\n" +
                "Life is 10 % how you make it..\n" +
                "and\n" +
                "90 % How you take it..!");

        favouritesArrayList.add(favourites);
        favourites = new Quotes();
        favourites.setQuote("All people have fears,\n" +
                "but the brave one's put down their fears and go forward,\n" +
                "sometimes to death, but always to victory.");

        favouritesArrayList.add(favourites);
        favourites = new Quotes();
        favourites.setQuote(("A boy cried wen he had no shoes..\n" +
                "Suddenly he stopped crying wen he saw a man vdout legs..\n" +
                "Life is full of blessings!\n" +
                "Sometimes we dont understand it..\n" +
                "Gud mrng!! :-)"));
        favouritesArrayList.add(favourites);

        favouritesAdapter.notifyDataSetChanged();

        if (favouritesArrayList.size() > 0) {
            constraintLayoutEmpty.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
        else {
            constraintLayoutEmpty.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }

//insertAndGetUser();

/*
        favouriteRepository = new FavouritesRepository(getApplicationContext());

        favouriteRepository.getFavourites().observe(this,
                new Observer<List<Favourites>>() {
            @Override
            public void onChanged(@Nullable List<Favourites> notes) {
                for(Favourites note : notes) {
                    System.out.println("-----------------------");
                    System.out.println(note.getId());
                    System.out.println(note.getFavourites());

                }
            }
        });
*/


    }

    //    private void observerSetup() {
//
//        favouritesViewModel.getmAllFavourites().observe(this,
//                new Observer<List<Favourites>>() {
//            @Override
//            public void onChanged(@Nullable final List<Favourites> products) {
//                favouritesAdapter.updateList(products);
//            }
//        });
//
//        favouritesViewModel.getmAllFavourites().observe(this,
//                new Observer<List<Favourites>>() {
//                    @Override
//                    public void onChanged(@Nullable final List<Favourites> products) {
//
//                        Log.d("FavList",products.toString());
//
//                    }
//                });
//    }
    public void insertAndGetUser() {
        favouriteRepository = new FavouritesRepository(this);

        String description = "This is the description of the third task";
        favouriteRepository.insertTask(description);

    }

}
