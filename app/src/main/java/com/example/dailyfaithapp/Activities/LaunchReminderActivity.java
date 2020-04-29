package com.example.dailyfaithapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.dailyfaithapp.R;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;

public class LaunchReminderActivity extends AppCompatActivity implements
                                                              View.OnClickListener,
                                                              TimePickerDialog.OnTimeSetListener {

    private ConstraintLayout constraintLayoutContainer;
    private ImageView imageViewPlus, imageViewMinus;
    private Button buttonContinue;
    private TextView textViewStartTime, textViewEndTime, textViewCounter;
    private String startTime, endTime;
    private Boolean startTimeSelected = false, endTimeSelected = false;
    private TimePickerDialog tpd;
    private int counter = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_reminder);

        setUpUI();
        setUpAnimation();

    }

    public void setUpUI() {


        constraintLayoutContainer = findViewById(R.id.container);
        buttonContinue = findViewById(R.id.button);
        textViewStartTime = findViewById(R.id.textView_start_time);
        textViewEndTime = findViewById(R.id.textView_end_time);
        imageViewMinus = findViewById(R.id.imageView_minus);
        imageViewPlus = findViewById(R.id.imageView_plus);
        textViewCounter = findViewById(R.id.textView_count);

        buttonContinue.setOnClickListener(this);
        textViewStartTime.setOnClickListener(this);
        textViewEndTime.setOnClickListener(this);
        imageViewPlus.setOnClickListener(this);
        imageViewMinus.setOnClickListener(this);

    }

    public void setUpAnimation() {

        constraintLayoutContainer.setVisibility(View.VISIBLE);
        Animation fadeAnimation = AnimationUtils
                .loadAnimation(this, R.anim.fading_effect);
        fadeAnimation.setStartOffset(1000);
        constraintLayoutContainer.startAnimation(fadeAnimation);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.button:

                openUnlockActivity();

                break;
            case R.id.textView_start_time:

                startTimeSelected = true;
                endTimeSelected = false;
                openTimePickerDialog();

                break;

            case R.id.textView_end_time:

                endTimeSelected = true;
                startTimeSelected = false;
                openTimePickerDialog();

                break;

            case R.id.imageView_plus:

                counter++;
                textViewCounter.setText(counter + "X");

                break;

            case R.id.imageView_minus:

                if (counter == 0) {
                    imageViewMinus.setEnabled(false);
                }
                else {
                    counter--;
                    textViewCounter.setText(counter + "X");
                }
                break;

        }
    }

    public void openUnlockActivity() {

        startActivity(new Intent(this, UnlockActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK));
        LaunchReminderActivity.this.finish();

    }

    public void openTimePickerDialog() {
        Calendar now = Calendar.getInstance();

        if (tpd == null) {
            tpd = TimePickerDialog.newInstance(
                    this,
                    now.get(Calendar.HOUR_OF_DAY),
                    now.get(Calendar.MINUTE),
                    true
            );
        }
        else {
            tpd.initialize(
                    this,
                    now.get(Calendar.HOUR_OF_DAY),
                    now.get(Calendar.MINUTE),
                    now.get(Calendar.SECOND),
                    true
            );
        }
        tpd.setThemeDark(true);
        tpd.vibrate(true);
        tpd.dismissOnPause(true);
        tpd.setVersion(true ? TimePickerDialog.Version.VERSION_2 :
                TimePickerDialog.Version.VERSION_1);
        tpd.setAccentColor(getResources().getColor(R.color.colorAccent));
        tpd.setTitle("Select Time");

        tpd.setOnCancelListener(dialogInterface -> {
            Log.d("TimePicker", "Dialog was cancelled");
            tpd = null;
        });
        tpd.show(getSupportFragmentManager(), "Timepickerdialog");
    }

    @Override
    public void onResume() {
        super.onResume();
        if (tpd != null) tpd.setOnTimeSetListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        tpd = null;
    }

    @Override
    public void onTimeSet(
            TimePickerDialog view, int hourOfDay, int minute, int second
    ) {
        String hourString = hourOfDay < 10 ? "0" + hourOfDay : "" + hourOfDay;
        String minuteString = minute < 10 ? "0" + minute : ":" + minute;
        String time = hourString + minuteString;

        if (startTimeSelected) {
            textViewStartTime.setText(time);
        }
        else if (endTimeSelected) {
            textViewEndTime.setText(time);
        }
        tpd = null;
    }
}
