package com.example.dailyfaithapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dailyfaithapp.R;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;

public class RemindersActivity extends AppCompatActivity implements
                                                         View.OnClickListener,
                                                         TimePickerDialog.OnTimeSetListener {

    private TextView textViewStartTime, textViewEndTime;
    private String startTime, endTime;
    private Boolean startTimeSelected = false, endTimeSelected = false;
    private TimePickerDialog tpd;
    private ImageView imageViewClose;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminders);

        setUpUI();

    }

    public void setUpUI() {

        textViewStartTime = findViewById(R.id.textView_start_time);
        textViewEndTime = findViewById(R.id.textView_end_time);
        imageViewClose = findViewById(R.id.imageView_close);

        textViewStartTime.setOnClickListener(this);
        textViewEndTime.setOnClickListener(this);
        imageViewClose.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
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

            case R.id.imageView_close:

                openMainActivity();

        }
    }

    public void openMainActivity() {

        startActivity(new Intent(this, MainActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK));
        RemindersActivity.this.finish();

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
