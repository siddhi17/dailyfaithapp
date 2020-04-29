package com.example.dailyfaithapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dailyfaithapp.R;

public class UnlockActivity extends AppCompatActivity implements
                                                      View.OnClickListener {

    private RelativeLayout relativeFreeTrial, relativeQuotes,
            relativeCategories, relativeThemes, relativeAds, relativeReminder,
            relativeLayoutOffer;

    private Button buttonContinue;
    private ImageView imageViewClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unlock);

        setUpUI();

        setUpAnimation();

    }

    public void setUpUI() {


        relativeFreeTrial = findViewById(R.id.relative_trial);
        relativeQuotes = findViewById(R.id.relative_quotes);
        relativeCategories = findViewById(R.id.relative_categories);
        relativeThemes = findViewById(R.id.relative_themes);
        relativeAds = findViewById(R.id.relative_adds);
        relativeReminder = findViewById(R.id.relative_reminders);
        relativeLayoutOffer = findViewById(R.id.relative_offer);

        buttonContinue = findViewById(R.id.button);
        imageViewClose = findViewById(R.id.imageView_close);

        buttonContinue.setOnClickListener(this);
        imageViewClose.setOnClickListener(this);
    }

    public void setUpAnimation() {

        int[] ViewIds = {
                R.id.relative_trial, R.id.relative_quotes,
                R.id.relative_categories, R.id.relative_themes,
                R.id.relative_adds, R.id.relative_reminders,
                R.id.relative_offer, R.id.imageView_close
        };

        int i = 1;

        for (int viewId : ViewIds) {

            Animation fadeAnimation = AnimationUtils
                    .loadAnimation(this, R.anim.fading_effect);
            fadeAnimation.setStartOffset(i * 1000);

            int ViewId = ViewIds[i - 1];

            if (i < ViewIds.length) {

                RelativeLayout View = findViewById(ViewId);
                View.startAnimation(fadeAnimation);
            }

            if (i == ViewIds.length) {
                relativeLayoutOffer.setVisibility(View.VISIBLE);
                ImageView imageView = findViewById(ViewId);
                imageView.startAnimation(fadeAnimation);
            }

            i++;
        }

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.button:

                break;
            case R.id.imageView_close:

                openSelectionActivity();

                break;
        }
    }

    public void openSelectionActivity() {

        startActivity(new Intent(this, SelectionActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK));
        UnlockActivity.this.finish();
    }
}
