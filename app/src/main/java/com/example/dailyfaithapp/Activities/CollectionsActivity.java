package com.example.dailyfaithapp.Activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dailyfaithapp.Adapters.CollectionsAdapter;
import com.example.dailyfaithapp.Model.Collections;
import com.example.dailyfaithapp.R;
import com.example.dailyfaithapp.Views.AddNewCollectionPopUp;

import java.util.ArrayList;

public class CollectionsActivity extends AppCompatActivity implements
                                                           View.OnClickListener {

    ArrayList<Collections> collectionsArrayList = new ArrayList<>();
    private RecyclerView recyclerView;
    private CollectionsAdapter collectionsAdapter;
    private ConstraintLayout constraintLayoutEmpty;
    private FrameLayout constraintLayoutContainer;
    private ImageView imageViewBack;
    private Button buttonAddCollection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collections);

        setUpUI();
        setUpRecyclerView();
        addCollectionsData();
    }

    public void setUpUI() {

        recyclerView = findViewById(R.id.recycler_view_collections);
        constraintLayoutEmpty = findViewById(R.id.constraint_empty);
        constraintLayoutContainer = findViewById(R.id.container_collection);
        imageViewBack = findViewById(R.id.imageView_back);
        buttonAddCollection = findViewById(R.id.button_add_collection);

        imageViewBack.setOnClickListener(this);
        buttonAddCollection.setOnClickListener(this);
    }

    public void setUpRecyclerView() {

        collectionsAdapter = new CollectionsAdapter(collectionsArrayList, this);
        RecyclerView.LayoutManager mLayoutManager =
                new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setAdapter(collectionsAdapter);

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.imageView_back:

                openMotivationActivity();

                break;

            case R.id.button_add_collection:

                findViewById(R.id.button_add_collection).post(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    public void run() {

                        AddNewCollectionPopUp popUpClass
                                = new AddNewCollectionPopUp();
                        popUpClass.showPopupWindow(
                                constraintLayoutContainer,
                                CollectionsActivity.this
                        );

                    }
                });
        }
    }

    public void openMotivationActivity() {

        startActivity(new Intent(this, MotivationActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK));
        CollectionsActivity.this.finish();

    }


    public void addCollectionsData() {

  /*      Collections collections = new Collections();
        collections = new Collections();
        collections.setCollection_msg("Butterfly lives only few days,\n" +
                "but still it flies joyfully capturing many hearts.\n" +
                "Each moment is precious in life.\n" +
                "Live it fully and win many hearts.");

        collectionsArrayList.add(collections);
        collections = new Collections();
        collections.setCollection_msg("Always have a unique character like " +
                "SALT!\n" +
                "Its presence is not felt but absence makes all things tasteless!!\n" +
                "Gud day! :-)");
        collectionsArrayList.add(collections);
        collections = new Collections();
        collections.setCollection_msg("A stronger and positive attitude\n" +
                "creates more miracles than any other thing.\n" +
                "Because\n" +
                "Life is 10 % how you make it..\n" +
                "and\n" +
                "90 % How you take it..!");
        collectionsArrayList.add(collections);
        collections = new Collections();
        collections.setCollection_msg("All people have fears,\n" +
                "but the brave one's put down their fears and go forward,\n" +
                "sometimes to death, but always to victory.");
        collectionsArrayList.add(collections);
        collections = new Collections();
        collections.setCollection_msg("A boy cried wen he had no shoes..\n" +
                "Suddenly he stopped crying wen he saw a man vdout legs..\n" +
                "Life is full of blessings!\n" +
                "Sometimes we dont understand it..\n" +
                "Gud mrng!! :-)");
        collectionsArrayList.add(collections);

        collectionsAdapter.notifyDataSetChanged();*/

        if (collectionsArrayList.size() > 0) {
            constraintLayoutEmpty.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
        else {
            constraintLayoutEmpty.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
    }
}
