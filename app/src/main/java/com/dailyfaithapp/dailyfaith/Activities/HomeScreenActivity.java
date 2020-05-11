package com.dailyfaithapp.dailyfaith.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager2.widget.ViewPager2;

import com.dailyfaithapp.dailyfaith.Adapters.ViewPagerAdapter;
import com.dailyfaithapp.dailyfaith.Helper.SharedPreferencesData;
import com.dailyfaithapp.dailyfaith.Model.Quotes;
import com.dailyfaithapp.dailyfaith.Model.Themes;
import com.dailyfaithapp.dailyfaith.MyApplication;
import com.dailyfaithapp.dailyfaith.R;
import com.dailyfaithapp.dailyfaith.Utils.Utils;
import com.dailyfaithapp.dailyfaith.Views.PopUpClass;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class HomeScreenActivity extends AppCompatActivity implements
                                                          View.OnClickListener {

    ViewPager2 viewPager;
    ArrayList<Quotes> quotesArrayList = new ArrayList<>();
    ViewPagerAdapter adapter;
    RelativeLayout relativeLayoutCategory, relativeLayoutReminders,
            relativeLayoutMore, relativeLayoutThemes;

    AlertDialog.Builder alertDialogBuilder;
    Button buttonGotIt;
    ImageView imageViewSwipeHand;
    ConstraintLayout constraintLayoutContainer;
    FirebaseFirestore db;
    SharedPreferencesData sharedPreferencesData;
    Query next;
    DocumentSnapshot lastVisible;

    ImageView imageViewCategory, imageViewReminders, imageViewThemes,
            imageViewMore;

    int color;
    Boolean isDark;
    Boolean pastQuotes = false;
    Boolean categoryIsSet = false;
    String quoteType = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        sharedPreferencesData =
                new SharedPreferencesData(getApplicationContext());

        setDefaultTheme();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            );
        }

        FirebaseApp.initializeApp(getApplicationContext());
        db = FirebaseFirestore.getInstance();

        setUpUI();
        setUpViewPager();

        fetchData();

    }

    private void setUpUI() {

        quoteType = sharedPreferencesData.getStr("QuoteType");

        viewPager = findViewById(R.id.view_pager);

        alertDialogBuilder = new AlertDialog.Builder(this);
        constraintLayoutContainer = findViewById(R.id.container);

        relativeLayoutCategory = findViewById(R.id.relative_category);
        relativeLayoutReminders = findViewById(R.id.relative_reminders);
        relativeLayoutMore = findViewById(R.id.relative_more);
        relativeLayoutThemes = findViewById(R.id.relative_themes);

        imageViewCategory = findViewById(R.id.imageView_category);
        imageViewMore = findViewById(R.id.imageView_more);
        imageViewReminders = findViewById(R.id.imageView_reminders);
        imageViewThemes = findViewById(R.id.imageView_themes);

        relativeLayoutCategory.setOnClickListener(this);
        relativeLayoutReminders.setOnClickListener(this);
        relativeLayoutMore.setOnClickListener(this);
        relativeLayoutThemes.setOnClickListener(this);

    }

    public void setDefaultTheme() {

        if (sharedPreferencesData.getStr("FirstTime").equals("true")) {

            Themes themes = new Themes();
            themes.setImage(getResources().getIdentifier("theme0",
                    "drawable", getPackageName()
            ));
            themes.setFont("OpenSans-CondBold.ttf");

            Drawable img = Utils.getDrawable(this, R.drawable.theme0);

            Bitmap anImage = ((BitmapDrawable) img).getBitmap();
            color = Utils.getDominantColor(anImage);
            Log.d("Bitmap", anImage.toString());
            isDark = Utils.isColorDark(color);

            if (isDark) {
                sharedPreferencesData.setStr(
                        "ThemeColor",
                        "dark"
                );
            }
            else {
                sharedPreferencesData.setStr(
                        "ThemeColor",
                        "light"
                );
            }
            sharedPreferencesData.setInt("ThemeName", themes.getImage());
            sharedPreferencesData.setStr("ThemeFont", themes.getFont());

        }

    }

    private ViewPagerAdapter createCardAdapter() {
        adapter = new ViewPagerAdapter(
                HomeScreenActivity.this, quotesArrayList);
        adapter.setItem(quotesArrayList);
        return adapter;
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

                        if (!categoryIsSet) {
                            if (position == quotesArrayList.size() - 1) {
                                fetchData();
                            }
                        }

                        Log.d("Position", String.valueOf(position));

                    }

                    @Override public void onPageScrollStateChanged(int state) {
                        super.onPageScrollStateChanged(state);
                    }
                });
    }

    public void fetchData() {

        if (quoteType.equals("BibleVerses")) {

            //    if(sharedPreferencesData.getStr("FirstTime").equals("true")) {
            // Construct query for first 25 cities, ordered by population
            Query first = db.collection("bible_verses")
                    .limit(20);
            first.get()
                    .addOnSuccessListener(
                            new OnSuccessListener<QuerySnapshot>() {
                                @Override
                                public void onSuccess(
                                        QuerySnapshot documentSnapshots
                                ) {
                                    if (documentSnapshots.size() != 0) {
                                        lastVisible
                                                = documentSnapshots
                                                .getDocuments()
                                                .get(documentSnapshots
                                                        .size() -
                                                        1);
                                    }

                                    Log.d(
                                            "Data",
                                            documentSnapshots.getDocuments()
                                                    .toString()
                                    );

                                    if (lastVisible != null) {
                                        next = db.collection("bible_verses")
                                                .startAfter(lastVisible)
                                                .limit(20);
                                    }

                                    if (documentSnapshots.size() != 0) {
                                        for (DocumentSnapshot snapshot : documentSnapshots) {
                                            quotesArrayList.add(snapshot
                                                    .toObject(
                                                            Quotes.class));
                                        }

                                        adapter.setItem(quotesArrayList);

                                    }
                                    else {

                                        Toast.makeText(
                                                HomeScreenActivity.this,
                                                "No more quotes",
                                                Toast.LENGTH_LONG
                                        ).show();
                                    }

                                    sharedPreferencesData.setStr(
                                            "FirstTime", "false");
                                }
                            });

        }
          /*  else {

                if (next != null) {

                    next.get()
                            .addOnSuccessListener(
                                    new OnSuccessListener<QuerySnapshot>() {
                                        @Override
                                        public void onSuccess(
                                                QuerySnapshot documentSnapshots
                                        ) {
                                            if (documentSnapshots.size() != 0) {
                                                lastVisible = documentSnapshots
                                                        .getDocuments()
                                                        .get(documentSnapshots
                                                                .size() -
                                                                1);
                                            }
                                            if(lastVisible != null)
                                                next = db.collection("bible_verses")
                                                        .startAfter(lastVisible)
                                                        .limit(20);

                                            if (documentSnapshots.size() != 0) {
                                                for (DocumentSnapshot snapshot : documentSnapshots) {

                                                    quotesArrayList.add(snapshot
                                                            .toObject(
                                                                    Quotes.class));

                                                }

                                                adapter.setItem(quotesArrayList);
                                            }
                                            else {

                                                Toast.makeText(
                                                        HomeScreenActivity.this,
                                                        "No more quotes",
                                                        Toast.LENGTH_LONG
                                                ).show();

                                            }
                                            Log.d(
                                                    "Data",
                                                    documentSnapshots
                                                            .getDocuments()
                                                            .toString()
                                            );
                                        }
                                    });
                }
            }*/
        //    }
        else if (quoteType.equals("Prayers")) {
            //  if(sharedPreferencesData.getStr("FirstTime").equals("true")) {
            // Construct query for first 25 cities, ordered by population
            Query first = db.collection("prayers")
                    .limit(20);

            first.get()
                    .addOnSuccessListener(
                            new OnSuccessListener<QuerySnapshot>() {
                                @Override
                                public void onSuccess(
                                        QuerySnapshot documentSnapshots
                                ) {
                                    if (documentSnapshots.size() != 0) {
                                        lastVisible
                                                = documentSnapshots
                                                .getDocuments()
                                                .get(documentSnapshots
                                                        .size() -
                                                        1);
                                    }
                                    Log.d(
                                            "Data",
                                            documentSnapshots.getDocuments()
                                                    .toString()
                                    );
                                    if (lastVisible != null) {
                                        next = db.collection("prayers")
                                                .startAfter(lastVisible)
                                                .limit(20);
                                    }

                                    if (documentSnapshots.size() != 0) {
                                        for (DocumentSnapshot snapshot : documentSnapshots) {
                                            quotesArrayList.add(snapshot
                                                    .toObject(
                                                            Quotes.class));

                                        }

                                        adapter.setItem(quotesArrayList);


                                    }
                                    else {

                                        Toast.makeText(
                                                HomeScreenActivity.this,
                                                "No more quotes",
                                                Toast.LENGTH_LONG
                                        ).show();

                                    }

                                    sharedPreferencesData.setStr(
                                            "FirstTime", "false");
                                }
                            });

        }
        else {
            if (next != null) {
                next.get()
                        .addOnSuccessListener(
                                new OnSuccessListener<QuerySnapshot>() {
                                    @Override
                                    public void onSuccess(
                                            QuerySnapshot documentSnapshots
                                    ) {

                                        if (documentSnapshots.size() != 0) {
                                            lastVisible = documentSnapshots
                                                    .getDocuments()
                                                    .get(documentSnapshots
                                                            .size() -
                                                            1);
                                        }
                                        if (lastVisible != null) {
                                            next = db.collection("prayers")
                                                    .startAfter(lastVisible)
                                                    .limit(20);
                                        }

                                        if (documentSnapshots.size() != 0) {
                                            for (DocumentSnapshot snapshot : documentSnapshots) {

                                                quotesArrayList.add(snapshot
                                                        .toObject(
                                                                Quotes.class));
                                            }

                                            adapter.setItem(quotesArrayList);

                                        }
                                        else {

                                            Toast.makeText(
                                                    HomeScreenActivity.this,
                                                    "No more quotes",
                                                    Toast.LENGTH_LONG
                                            ).show();

                                        }
                                        Log.d(
                                                "Data",
                                                documentSnapshots
                                                        .getDocuments()
                                                        .toString()
                                        );

                                    }
                                });
            }

        }

        //     }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.relative_category:

                imageViewCategory.setImageDrawable(getResources()
                        .getDrawable(R.drawable.ic_category_selected));
                sharedPreferencesData.setStr("selectedBottomMenu", "category");
                openCategoryActivity();

                break;
            case R.id.relative_reminders:

                imageViewReminders.setImageDrawable(getResources()
                        .getDrawable(R.drawable.ic_reminders_selected));
                sharedPreferencesData.setStr("selectedBottomMenu", "reminders");
                openRemindersActivity();

                break;

            case R.id.relative_more:

                imageViewMore.setImageDrawable(getResources()
                        .getDrawable(R.drawable.ic_more_selected));
                sharedPreferencesData
                        .setStr("selectedBottomMenu", "motivation");
                openMotivationActivity();

                break;

            case R.id.relative_themes:

                imageViewThemes.setImageDrawable(getResources()
                        .getDrawable(R.drawable.ic_themes_selected));
                sharedPreferencesData.setStr("selectedBottomMenu", "themes");
                openThemesActivity();

                break;

        }
    }

    public void openCategoryActivity() {

        //startActivityForResult(new Intent(this, CategoryActivity.class),1);

        startActivity(new Intent(this, SelectionActivity.class).putExtra(
                "isMainActivity", true));
        categoryIsSet = true;

    }

    public void openRemindersActivity() {

        startActivity(new Intent(this, RemindersActivity.class));

    }

    public void openMotivationActivity() {

        startActivity(new Intent(this, MotivationActivity.class));

    }

    public void openThemesActivity() {

        startActivity(new Intent(this, ThemesActivity.class));
    }

    @Override
    public void onResume() {
        super.onResume();
        sharedPreferencesData.setStr("selectedBottomMenu", "");

        if (sharedPreferencesData.getStr("selectedBottomMenu").equals("")) {
            imageViewCategory.setImageDrawable(
                    getResources().getDrawable(R.drawable.ic_category));
            imageViewThemes.setImageDrawable(
                    getResources().getDrawable(R.drawable.ic_theme));
            imageViewReminders.setImageDrawable(
                    getResources().getDrawable(R.drawable.ic_reminder));
            imageViewMore.setImageDrawable(
                    getResources().getDrawable(R.drawable.ic_more));
        }

        if (sharedPreferencesData.getStr("FirstTime").equals("true")) {

            findViewById(R.id.container).post(new Runnable() {
                public void run() {
                    PopUpClass popUpClass = new PopUpClass();
                    popUpClass.showPopupWindow(
                            constraintLayoutContainer,
                            HomeScreenActivity.this
                    );

                }
            });
        }
     /*   if(quotesArrayList.size() == 0)
        {
            fetchData();
        }*/

        if (categoryIsSet) {

            quotesArrayList.clear();
            quotesArrayList
                    .addAll(((MyApplication) getApplicationContext()).myGlobalArray);
            ((MyApplication) getApplicationContext()).myGlobalArray.clear();
            adapter.setItem(quotesArrayList);
        }

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                /*   quotesArrayList = data.getParcelableArrayListExtra("categoryList");*/

                categoryIsSet = true;
            }
        }
    }
}
