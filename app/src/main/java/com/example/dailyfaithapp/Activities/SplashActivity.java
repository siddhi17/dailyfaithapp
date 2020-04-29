package com.example.dailyfaithapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dailyfaithapp.R;

public class SplashActivity extends AppCompatActivity implements
                                                      View.OnClickListener {

    private TextView textViewSelfLove, textViewSelfCare, textViewSelfGrowth;
    private Button buttonGetStarted;
    private TranslateAnimation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        setUpUI();
    }

    public void setUpUI() {

        textViewSelfLove = findViewById(R.id.textView_love);
        textViewSelfCare = findViewById(R.id.textView_care);
        textViewSelfGrowth = findViewById(R.id.textView_growth);
        buttonGetStarted = findViewById(R.id.button);

        buttonGetStarted.setOnClickListener(this);

        setUpAnimation();
    }

    public void setUpAnimation() {

        int[] textViewIds = {
                R.id.textView_care, R.id.textView_love,
                R.id.textView_growth, R.id.button
        };

        int i = 1;

        for (int viewId : textViewIds) {

            Animation fadeAnimation = AnimationUtils
                    .loadAnimation(this, R.anim.fading_effect);
            fadeAnimation.setStartOffset(i * 1000);

            int textViewId = textViewIds[i - 1];
            TextView textView = (TextView) findViewById(textViewId);
            textView.startAnimation(fadeAnimation);

            i++;
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.button:

                openReminderActivity();

                break;

        }
    }

    public void openReminderActivity() {

        startActivity(new Intent(this, LaunchReminderActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK));
        SplashActivity.this.finish();

    }
}
