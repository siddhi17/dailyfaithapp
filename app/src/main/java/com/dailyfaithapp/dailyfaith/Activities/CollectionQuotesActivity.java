package com.dailyfaithapp.dailyfaith.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dailyfaithapp.dailyfaith.Adapters.CollectionQuotesAdapter;
import com.dailyfaithapp.dailyfaith.AsyncTasks.GetCollectionQuotesAsyncTask;
import com.dailyfaithapp.dailyfaith.Model.Collections;
import com.dailyfaithapp.dailyfaith.R;

import java.util.ArrayList;

public class CollectionQuotesActivity extends AppCompatActivity implements
                                                                View.OnClickListener,
                                                                GetCollectionQuotesAsyncTask.GetCollectionQuotesCallBack {


    ArrayList<Collections> quotesList = new ArrayList<>();
    private RecyclerView recyclerView;
    private CollectionQuotesAdapter collectionQuotesAdapter;
    private ConstraintLayout constraintLayoutEmpty;
    private FrameLayout constraintLayoutContainer;
    private ImageView imageViewBack;
    private Button buttonAddCollection;
    private TextView toolbarTitle;
    private Intent intent;
    private Collections collections;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_quotes);

        setUpUI();

        setUpRecyclerView();

        getCollections();
    }


    public void setUpUI() {

        intent = getIntent();

        recyclerView = findViewById(R.id.recycler_view_collection_quotes);
        constraintLayoutEmpty = findViewById(R.id.constraint_empty);
        constraintLayoutContainer = findViewById(R.id.container_collection);
        imageViewBack = findViewById(R.id.imageView_back);
        toolbarTitle = findViewById(R.id.textView_toolbar_title);


    /*    quotesList.clear();
        quotesList = intent.getParcelableArrayListExtra("CollectionQuotesList");
*/
        toolbarTitle.setText(intent.getStringExtra("title"));

        imageViewBack.setOnClickListener(this);

    }

    public void setUpRecyclerView() {

        collectionQuotesAdapter = new CollectionQuotesAdapter(
                quotesList,
                this
        );
        RecyclerView.LayoutManager mLayoutManager =
                new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setAdapter(collectionQuotesAdapter);

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.imageView_back:

                onBackPressed();

                break;
        }
    }

   /* public void openMotivationActivity() {

        startActivity(new Intent(this, MotivationActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK));
        CollectionsActivity.this.finish();

    }*/

    public void getCollections() {

     /*   collectionQuotesAdapter.updateList(quotesList);

        if (quotesList.size() > 0) {
            constraintLayoutEmpty.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
        else {
            constraintLayoutEmpty.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }*/
        GetCollectionQuotesAsyncTask
                getCollectionQuotesAsyncTask = new
                GetCollectionQuotesAsyncTask(this, this);

        getCollectionQuotesAsyncTask.execute(String.valueOf(intent.getIntExtra(
                "id",
                0
        )));
    }

    @Override
    public void onPostExecute(ArrayList<Collections> collections) {
        quotesList.clear();
        quotesList.addAll(collections);
        collectionQuotesAdapter.updateList(quotesList);

        if (quotesList.size() > 0) {
            constraintLayoutEmpty.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
        else {
            constraintLayoutEmpty.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
    }

}
