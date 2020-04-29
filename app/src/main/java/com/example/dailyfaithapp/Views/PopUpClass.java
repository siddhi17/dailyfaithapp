package com.example.dailyfaithapp.Views;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dailyfaithapp.R;

public class PopUpClass {


    public void showPopupWindow(final View view, AppCompatActivity context) {

        //Create a View object yourself through inflater
        LayoutInflater inflater = (LayoutInflater) view.getContext()
                .getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.instruction_layout, null);


        //Make Inactive Items Outside Of PopupWindow
        boolean focusable = true;

        PopupWindow popupWindow = new PopupWindow(
                popupView,
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );

        popupWindow.showAtLocation(view, Gravity.CENTER,
                0, 0
        );

        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);

        ImageView imageViewSwipeHand =
                popupView.findViewById(R.id.imageView_swipe_hand);

        Animation RightSwipe = AnimationUtils.loadAnimation(
                context,
                R.anim.swipe_left_to_right
        );

        RightSwipe.setRepeatMode(Animation.RESTART);

        RightSwipe.setInterpolator(new LinearInterpolator());
        RightSwipe.setRepeatCount(Animation.INFINITE);

        imageViewSwipeHand.startAnimation(RightSwipe);

        Button button = popupView.findViewById(R.id.button_got_it);

        button.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {

                popupWindow.dismiss();

            }
        });

/*
        //Handler for clicking on the inactive zone of the window

        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                //Close the window when clicked
                popupWindow.dismiss();
                return true;
            }
        });*/
    }


}