package com.example.dailyfaithapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dailyfaithapp.R;


public class SelectionActivity extends AppCompatActivity implements
                                                         View.OnClickListener {

    private RelativeLayout relativeLayoutBibleVerses, relativeLayoutPrayers;
    private Button buttonContinue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);

        setUpUI();

    }

    public void setUpUI() {


        relativeLayoutBibleVerses = findViewById(R.id.relative_bible_verses);
        relativeLayoutPrayers = findViewById(R.id.relative_prayers);
        buttonContinue = findViewById(R.id.button);

        relativeLayoutBibleVerses.setOnClickListener(this);
        relativeLayoutPrayers.setOnClickListener(this);
        buttonContinue.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.relative_bible_verses:

                relativeLayoutBibleVersesClicked();
                break;
            case R.id.relative_prayers:

                relativeLayoutPrayersClicked();
                break;
            case R.id.button:

                openMainActivity();
                break;
            default:
                break;
        }
    }

    private void relativeLayoutBibleVersesClicked() {
        relativeLayoutPrayers.setBackground(
                getResources().getDrawable(R.drawable.layout_background));
        relativeLayoutBibleVerses.setBackground(getResources()
                .getDrawable(R.drawable.selected_layout_background));
    }

    private void relativeLayoutPrayersClicked() {
        relativeLayoutBibleVerses.setBackground(
                getResources().getDrawable(R.drawable.layout_background));
        relativeLayoutPrayers.setBackground(getResources()
                .getDrawable(R.drawable.selected_layout_background));
    }

    public void openMainActivity() {

        startActivity(new Intent(this, MainActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK));
        SelectionActivity.this.finish();

    }
}
