package com.example.dailyfaithapp.Views;

import android.graphics.Point;
import android.os.Build;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dailyfaithapp.R;

public class AddNewCollectionPopUp {

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void showPopupWindow(View view, AppCompatActivity context) {

        //Create a View object yourself through inflater
        LayoutInflater inflater = (LayoutInflater) view.getContext()
                .getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        View popupView = inflater
                .inflate(R.layout.add_new_collection_layout, null);


        //Make Inactive Items Outside Of PopupWindow
        boolean focusable = true;
        Display display = context.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        PopupWindow popupWindow = new PopupWindow(popupView,
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT, true
        );

        popupWindow.setWidth(width - 100);

        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);

        //  view = (FrameLayout)view.findViewById( R.id.container_collection);
//            view.getForeground().setAlpha( 0);

        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);


        Button button = popupView.findViewById(R.id.button_add);
        EditText editTextAddQuote = popupView
                .findViewById(R.id.edit_text_add_quote);

        TextView textViewCancel =
                popupView.findViewById(R.id.textView_cancel);

        textViewCancel.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {

                popupWindow.dismiss();

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {


            }
        });


     /*       //Handler for clicking on the inactive zone of the window

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
