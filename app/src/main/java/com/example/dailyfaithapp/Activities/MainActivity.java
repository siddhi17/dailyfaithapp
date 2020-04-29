package com.example.dailyfaithapp.Activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager2.widget.ViewPager2;

import com.example.dailyfaithapp.Adapters.ViewPagerAdapter;
import com.example.dailyfaithapp.AsyncTasks.AddQuotesAsyncTask;
import com.example.dailyfaithapp.AsyncTasks.GetAllQuotesAsyncTask;
import com.example.dailyfaithapp.Model.Quotes;
import com.example.dailyfaithapp.R;
import com.example.dailyfaithapp.Views.PopUpClass;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements
                                                    View.OnClickListener,
                                                    AddQuotesAsyncTask.AddQuotesCallBack,
                                                    GetAllQuotesAsyncTask.GetAllQuotesCallBack {

    ViewPager2 viewPager;
    ArrayList<Quotes> quotesArrayList = new ArrayList<>();
    ViewPagerAdapter adapter;
    RelativeLayout relativeLayoutCategory, relativeLayoutReminders,
            relativeLayoutMore;

    AlertDialog.Builder alertDialogBuilder;
    Button buttonGotIt;
    ImageView imageViewSwipeHand;
    ConstraintLayout constraintLayoutContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            );
        }

        setUpQuotesData();
        setUpUI();
        setUpViewPager();

    }

    private void setUpUI() {


        viewPager = findViewById(R.id.view_pager);

        alertDialogBuilder = new AlertDialog.Builder(this);
        constraintLayoutContainer = findViewById(R.id.container);

        relativeLayoutCategory = findViewById(R.id.relative_category);
        relativeLayoutReminders = findViewById(R.id.relative_reminders);
        relativeLayoutMore = findViewById(R.id.relative_more);


        relativeLayoutCategory.setOnClickListener(this);
        relativeLayoutReminders.setOnClickListener(this);
        relativeLayoutMore.setOnClickListener(this);

        GetAllQuotesAsyncTask getAllQuotesAsyncTask =
                new GetAllQuotesAsyncTask(this, this);
        getAllQuotesAsyncTask.execute();

    }

    @Override
    public void onResume() {
        super.onResume();
        findViewById(R.id.container).post(new Runnable() {
            public void run() {
                PopUpClass popUpClass = new PopUpClass();
                popUpClass.showPopupWindow(
                        constraintLayoutContainer,
                        MainActivity.this
                );

            }
        });
    }

    private void setUpViewPager() {

        viewPager.setAdapter(createCardAdapter());

        viewPager.registerOnPageChangeCallback(
                new ViewPager2.OnPageChangeCallback() {
                    @Override public void onPageScrolled(
                            int position, float positionOffset,
                            int positionOffsetPixels
                    ) {
                        super.onPageScrolled(position, positionOffset,
                                positionOffsetPixels
                        );
                    }

                    @Override public void onPageSelected(int position) {
                        super.onPageSelected(position);

                        if (position == quotesArrayList.size() - 1) {
                            addQuotesData();
                        }

                        Log.d("Position", String.valueOf(position));

                    }

                    @Override public void onPageScrollStateChanged(int state) {
                        super.onPageScrollStateChanged(state);
                    }
                });


    }

    private ViewPagerAdapter createCardAdapter() {
        adapter = new ViewPagerAdapter(this, quotesArrayList);
        adapter.setItem(quotesArrayList);
        return adapter;
    }

    public void setUpQuotesData() {

        Quotes quotes = new Quotes();

        quotes.setQuote(
                "Doing a same thing over and over again and expecting different results");
        quotes.setFavourite("0");
        quotesArrayList.add(quotes);
        quotes = new Quotes();
        quotes.setQuote("Disappointment are like road hump,\n" +
                "they slow you down a bit but you enjoy\n" +
                "the smooth road afterward.\n" +
                "Don't stay on the humps too long.\n" +
                "MOVE ON….");
        quotes.setFavourite("0");
        quotesArrayList.add(quotes);
        quotes = new Quotes();
        quotes.setQuote("Time is like the water o'f a river.\n" +
                "You can't touch the same water twice.\n" +
                "Because\n" +
                "the flow that has passed\n" +
                "will never pass again.\n" +
                "So,\n" +
                "Enjoy every moment of life..");
        quotes.setFavourite("0");
        quotesArrayList.add(quotes);

        quotes = new Quotes();
        quotes.setQuote(
                "Regretting over yesterday and fear of tomorrow are the two thieves which steal our present..\n" +
                        "Live for today.. Life will be beautiful..!");
        quotes.setFavourite("0");
        quotesArrayList.add(quotes);

        quotes = new Quotes();
        quotes.setQuote(
                "Life is too short, So follwow some rules. Forgive quickly, Believe Slowly, Love Truely, Laugh Loudly and Never miss anything that makes you Happy");
        quotes.setFavourite("0");
        quotesArrayList.add(quotes);

        addQuotesData();
    }

    public void addQuotesData() {

        Quotes quotes = new Quotes();
        quotes = new Quotes();
        quotes.setQuote("Butterfly lives only few days,\n" +
                "but still it flies joyfully capturing many hearts.\n" +
                "Each moment is precious in life.\n" +
                "Live it fully and win many hearts.");
        quotes.setFavourite("0");
        quotesArrayList.add(quotes);
        quotes = new Quotes();
        quotes.setQuote("Always have a unique character like SALT!\n" +
                "Its presence is not felt but absence makes all things tasteless!!\n" +
                "Gud day! :-)");
        quotes.setFavourite("0");
        quotesArrayList.add(quotes);
        quotes = new Quotes();
        quotes.setQuote("A stronger and positive attitude\n" +
                "creates more miracles than any other thing.\n" +
                "Because\n" +
                "Life is 10 % how you make it..\n" +
                "and\n" +
                "90 % How you take it..!");
        quotes.setFavourite("0");
        quotesArrayList.add(quotes);

        quotes = new Quotes();
        quotes.setQuote("All people have fears,\n" +
                "but the brave one's put down their fears and go forward,\n" +
                "sometimes to death, but always to victory.");
        quotes.setFavourite("0");
        quotesArrayList.add(quotes);

        quotes = new Quotes();
        quotes.setQuote("A boy cried wen he had no shoes..\n" +
                "Suddenly he stopped crying wen he saw a man vdout legs..\n" +
                "Life is full of blessings!\n" +
                "Sometimes we dont understand it..\n" +
                "Gud mrng!! :-)");
        quotes.setFavourite("0");
        quotesArrayList.add(quotes);

        //adapter.setItem(quotesArrayList);

      /*  AddQuotesAsyncTask addQuotesAsyncTask = new AddQuotesAsyncTask(this,
                this);

        addQuotesAsyncTask.execute(quotesArrayList);*/

    }

    @Override
    public void onPostExecute(List<Quotes> quotesList) {

        if (quotesList.size() > 0) {
            Log.d("quotesList", String.valueOf(quotesList));

            quotesArrayList.clear();
            quotesArrayList.addAll(quotesList);
            adapter.setItem(quotesArrayList);
        }

    }

    @Override
    public void onPostExecute(Boolean b) {

        if (b) {
            Log.d("quotesList", String.valueOf(quotesArrayList));

        }

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.relative_category:

                openCategoryActivity();

                break;
            case R.id.relative_reminders:

                openRemindersActivity();

                break;

            case R.id.relative_more:

                openMotivationActivity();

                break;

        }
    }

    public void openCategoryActivity() {

        startActivity(new Intent(this, CategoryActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK));
        MainActivity.this.finish();

    }

    public void openRemindersActivity() {

        startActivity(new Intent(this, RemindersActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK));
        MainActivity.this.finish();

    }

    public void openMotivationActivity() {

        startActivity(new Intent(this, MotivationActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK));
        MainActivity.this.finish();

    }

}
