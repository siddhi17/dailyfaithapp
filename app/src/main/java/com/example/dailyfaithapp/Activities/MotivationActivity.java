package com.example.dailyfaithapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dailyfaithapp.R;

public class MotivationActivity extends AppCompatActivity implements
                                                          View.OnClickListener {

    private ImageView imageViewBack;
    private RelativeLayout relativeLayoutCategory, relativeLayoutReminders,
            relativeLayoutMore;
    private RelativeLayout relativeCollections, relativeSearch,
            relativeFavourites;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motivation);

        setUpUI();

    }


    public void setUpUI() {


        imageViewBack = findViewById(R.id.imageView_motivation_back);

        relativeLayoutCategory = findViewById(R.id.relative_category);
        relativeLayoutReminders = findViewById(R.id.relative_reminders);
        relativeLayoutMore = findViewById(R.id.relative_more);

        relativeCollections = findViewById(R.id.relative_collections);
        relativeFavourites = findViewById(R.id.relative_favourites);
        relativeSearch = findViewById(R.id.relative_search);

        relativeLayoutCategory.setOnClickListener(this);
        relativeLayoutReminders.setOnClickListener(this);
        relativeLayoutMore.setOnClickListener(this);

        relativeCollections.setOnClickListener(this);
        relativeSearch.setOnClickListener(this);
        relativeFavourites.setOnClickListener(this);
        imageViewBack.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.relative_reminders:

                openRemindersActivity();

                break;

            case R.id.relative_category:

                openCategoryActivity();

                break;

            case R.id.relative_collections:

                openCollectionsActivity();

                break;

            case R.id.imageView_motivation_back:

                openMainActivity();

                break;

            case R.id.relative_search:


                break;

            case R.id.relative_favourites:

                openFavouritesActivity();

                break;
        }
    }

    public void openRemindersActivity() {

        finish();
        startActivity(new Intent(this, RemindersActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK));
        MotivationActivity.this.finish();
    }

    public void openCategoryActivity() {

        finish();
        startActivity(new Intent(this, CategoryActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK));
        MotivationActivity.this.finish();

    }

    public void openCollectionsActivity() {

        startActivity(new Intent(this, CollectionsActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK));
        MotivationActivity.this.finish();

    }

    public void openMainActivity() {

        startActivity(new Intent(this, MainActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK));
        MotivationActivity.this.finish();
    }

    public void openFavouritesActivity() {

        startActivity(new Intent(this, FavouritesActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK));
        MotivationActivity.this.finish();
    }
}
