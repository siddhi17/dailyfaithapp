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
import com.dailyfaithapp.dailyfaith.AsyncTasks.AddPastQuoteAsyncTask;
import com.dailyfaithapp.dailyfaith.AsyncTasks.GetQuoteAsyncTask;
import com.dailyfaithapp.dailyfaith.Helper.SharedPreferencesData;
import com.dailyfaithapp.dailyfaith.Model.Quotes;
import com.dailyfaithapp.dailyfaith.Model.Themes;
import com.dailyfaithapp.dailyfaith.MyApplication;
import com.dailyfaithapp.dailyfaith.R;
import com.dailyfaithapp.dailyfaith.Utils.Utils;
import com.dailyfaithapp.dailyfaith.Views.PopUpClass;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import java.util.ArrayList;

public class HomeScreenActivity extends AppCompatActivity implements
                                                          View.OnClickListener,
                                                          AddPastQuoteAsyncTask.AddPastQuoteCallBack,
                                                          GetQuoteAsyncTask.GetQuoteCallBack {

    public static ViewPager2 viewPager;
    ArrayList<Quotes> quotesArrayList = new ArrayList<>();
    public static Query next;
    public static ViewPagerAdapter adapter;
    RelativeLayout relativeLayoutCategory, relativeLayoutReminders,
            relativeLayoutMore, relativeLayoutThemes;

    AlertDialog.Builder alertDialogBuilder;
    Button buttonGotIt;
    ImageView imageViewSwipeHand;
    ConstraintLayout constraintLayoutContainer;
    FirebaseFirestore db;
    SharedPreferencesData sharedPreferencesData;
    ArrayList<Quotes> categoryArrayList = new ArrayList<>();
    DocumentSnapshot lastVisible;

    ImageView imageViewCategory, imageViewReminders, imageViewThemes,
            imageViewMore;
    String categoryType;
    int color;
    Boolean isDark;
    Boolean pastQuotes = false;
    Boolean categoryIsSet = false;
    String quoteType = "";
    int mQuotePosition;
    Quotes mQuote;
    String json;
    Gson gson = new Gson();
    Boolean repeatData = false;
    Boolean mNotificationQuote = false;
    int mPositionCounter = 10;
    private Intent mIntent;
    private InterstitialAd interstitial;
    private boolean adClosed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        sharedPreferencesData =
                new SharedPreferencesData(getApplicationContext());

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(
                    InitializationStatus initializationStatus
            ) {
            }
        });


        mIntent = getIntent();

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

        //  fetchData();

    }

    private void setUpUI() {


        AdRequest adIRequest = new AdRequest.Builder().build();

        // Prepare the Interstitial Ad Activity
        interstitial = new InterstitialAd(HomeScreenActivity.this);

        // Insert the Ad Unit ID
        interstitial.setAdUnitId("ca-app-pub-3940256099942544/1033173712");

        // Interstitial Ad load Request
        interstitial.loadAd(adIRequest);


        interstitial.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.

                Log.d("AdFailedToLoad", String.valueOf(errorCode));
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when the ad is displayed.
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the interstitial ad is closed.
                interstitial.loadAd(new AdRequest.Builder().build());
                adClosed = true;
            }
        });


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

    public void displayInterstitial() {
        // If Interstitial Ads are loaded then show else show nothing.
        if (interstitial.isLoaded()) {
            interstitial.show();
        }
        else {
            Log.d("TAG", "The ad wasn't loaded.");
        }
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
            themes.setImageBitmap(anImage);
            ((MyApplication) getApplicationContext()).mBitmap =
                    anImage;
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
                HomeScreenActivity.this,
                (((MyApplication) getApplicationContext()).myGlobalArray));
        adapter.setItem(
                (((MyApplication) getApplicationContext()).myGlobalArray));

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


                        if (position == mPositionCounter) {

                            displayInterstitial();
                            mPositionCounter = mPositionCounter + 10;
                        }

                        if (position ==
                                (((MyApplication) getApplicationContext()).myGlobalArray)
                                        .size() - 1) {

                            if (Utils.isNetworkAvailable(
                                    HomeScreenActivity.this))
                                fetchData();
                            else {
                                Toast.makeText(
                                        HomeScreenActivity.this,
                                        "Could" +
                                                " not connect to internet. Please try" +
                                                " again later.",
                                        Toast.LENGTH_LONG
                                ).show();
                            }

                        }


                        if ((((MyApplication) getApplicationContext()).myGlobalArray)
                                .size() > 0) {
                            mQuotePosition = position;
                            mQuote
                                    = (((MyApplication) getApplicationContext()).myGlobalArray)
                                    .get(position);

                            getQuote(
                                    (((MyApplication) getApplicationContext()).myGlobalArray)
                                            .get(mQuotePosition),
                                    mQuotePosition
                            );
                            Log.d("Position", String.valueOf(position));
                        }

                    }

                    @Override public void onPageScrollStateChanged(int state) {
                        super.onPageScrollStateChanged(state);
                    }
                });
    }

    public void fetchData() {


        if (quoteType.equals("BibleVerses")) {

            /*   if (sharedPreferencesData.getStr("FirstTime").equals("true")) {
             */

            if (next == null) {

                Query first = db.collection("bible_verses")
                        .limit(2);

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
                                                            .size() - 1);
                                        }

                                        Log.d(
                                                "Data",
                                                documentSnapshots.getDocuments()
                                                        .toString()
                                        );


                                        if (lastVisible != null) {
                                            next = db.collection("bible_verses")
                                                    .startAfter(lastVisible)
                                                    .limit(2);
                                        }

                                        Log.d("docId", lastVisible.toString());

                                        if (documentSnapshots.size() != 0) {

                                            if ((!repeatData ||
                                                    !mNotificationQuote) &&
                                                    ((MyApplication) getApplicationContext()).myGlobalArray
                                                            .size() == 0) {

                                                ((MyApplication) getApplicationContext()).myGlobalArray
                                                        = new ArrayList<>();
                                            }

                                            for (QueryDocumentSnapshot doc : documentSnapshots) {
                                                Quotes quotes = new Quotes();
                                                if (doc.getId() != null) {
                                                    quotes.setDocId(
                                                            doc.getId());
                                                }
                                                if (doc.get("quote") != null) {
                                                    quotes.setQuote(
                                                            doc.getString(
                                                                    "quote"));
                                                }

                                                if (categoryIsSet) {
                                                    if (doc.get("category") !=
                                                            null && doc.get(
                                                            "category")
                                                            .equals(categoryType)) {
                                                        quotes.setCategory(
                                                                doc.getString(
                                                                        "category"));

                                                        quotes.setFavourite(
                                                                "0");
                                                        quotes.setIsPastQuoteSaved(
                                                                "0");
                                                        (((MyApplication) getApplicationContext()).myGlobalArray)
                                                                .add(quotes);
                                                    }

                                                }
                                                else {

                                                    quotes.setFavourite("0");
                                                    quotes.setIsPastQuoteSaved(
                                                            "0");
                                                    (((MyApplication) getApplicationContext()).myGlobalArray)
                                                            .add(quotes);
                                                }
                                            }

                                            adapter.setItem(
                                                    (((MyApplication) getApplicationContext()).myGlobalArray));

                                        }
                                        else {


                                        }

                                        sharedPreferencesData.setStr(
                                                "FirstTime", "false");
                                        mNotificationQuote = false;
                                    }
                                });
            }
        }

        //     else {

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
                                        next = db.collection(
                                                "bible_verses")
                                                .startAfter(lastVisible)
                                                .limit(2);
                                    }

                                    if (documentSnapshots.size() != 0) {
                                        for (QueryDocumentSnapshot doc : documentSnapshots) {
                                            Quotes quotes
                                                    = new Quotes();
                                            if (doc.getId() !=
                                                    null) {
                                                quotes.setDocId(
                                                        doc.getId());
                                            }
                                            if (doc.get("quote") !=
                                                    null) {
                                                quotes.setQuote(
                                                        doc.getString(
                                                                "quote"));
                                            }
                                            if (categoryIsSet) {
                                                if (doc.get(
                                                        "category") !=
                                                        null &&
                                                        doc.get(
                                                                "category")
                                                                .equals(categoryType)) {
                                                    quotes.setCategory(
                                                            doc.getString(
                                                                    "category"));

                                                    quotes.setFavourite(
                                                            "0");
                                                    quotes.setIsPastQuoteSaved(
                                                            "0");
                                                    (((MyApplication) getApplicationContext()).myGlobalArray)
                                                            .add(quotes);
                                                }
                                            }
                                            else {

                                                quotes.setFavourite(
                                                        "0");
                                                quotes.setIsPastQuoteSaved(
                                                        "0");
                                                (((MyApplication) getApplicationContext()).myGlobalArray)
                                                        .add(quotes);
                                            }
                                        }

                                        adapter.setItem(
                                                ((MyApplication) getApplicationContext()).myGlobalArray);
                                    }
                                    else {

                                        repeatData = true;
                                        next = null;

                                        if (Utils.isNetworkAvailable(
                                                HomeScreenActivity.this)) {
                                            fetchData();
                                        }
                                        else {
                                            Toast.makeText(
                                                    HomeScreenActivity.this,
                                                    "Could" +
                                                            " not connect to internet. Please try" +
                                                            " again later.",
                                                    Toast.LENGTH_LONG
                                            ).show();
                                        }

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
        //}
        // }

        else if (quoteType.equals("Prayers")) {

            //   if (sharedPreferencesData.getStr("FirstTime").equals
            //   ("true")) {

            if (next == null) {

                Query first = db.collection("prayers")
                        .limit(2);

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
                                                documentSnapshots
                                                        .getDocuments()
                                                        .toString()
                                        );
                                        if (lastVisible != null) {
                                            next = db.collection("prayers")
                                                    .startAfter(lastVisible)
                                                    .limit(2);
                                        }

                                        if (documentSnapshots.size() != 0) {
                                     /*   for (DocumentSnapshot snapshot : documentSnapshots) {
                                            quotesArrayList.add(snapshot
                                                    .toObject(
                                                            Quotes.class));

                                        }*/
                                            if ((!repeatData ||
                                                    !mNotificationQuote) &&
                                                    ((MyApplication) getApplicationContext()).myGlobalArray
                                                            .size() == 0) {

                                                ((MyApplication) getApplicationContext()).myGlobalArray
                                                        = new ArrayList<>();
                                            }


                                            for (QueryDocumentSnapshot doc : documentSnapshots) {
                                                Quotes quotes
                                                        = new Quotes();
                                                if (doc.getId() != null) {
                                                    quotes.setDocId(
                                                            doc.getId());
                                                }
                                                if (doc.get("quote") !=
                                                        null) {
                                                    quotes.setQuote(
                                                            doc.getString(
                                                                    "quote"));
                                                }
                                                if (categoryIsSet) {
                                                    if (doc.get(
                                                            "category") !=
                                                            null && doc.get(
                                                            "category")
                                                            .equals(categoryType)) {
                                                        quotes.setCategory(
                                                                doc.getString(
                                                                        "category"));

                                                        quotes.setFavourite(
                                                                "0");
                                                        quotes.setIsPastQuoteSaved(
                                                                "0");
                                                        (((MyApplication) getApplicationContext()).myGlobalArray)
                                                                .add(quotes);
                                                    }

                                                }
                                                else {

                                                    quotes.setFavourite(
                                                            "0");
                                                    quotes.setIsPastQuoteSaved(
                                                            "0");
                                                    (((MyApplication) getApplicationContext()).myGlobalArray)
                                                            .add(quotes);
                                                }
                                            }

                                            adapter.setItem(
                                                    (((MyApplication) getApplicationContext()).myGlobalArray));


                                        }
                                        else {

                                        }

                                        sharedPreferencesData.setStr(
                                                "FirstTime", "false");
                                        mNotificationQuote = false;
                                    }
                                });
            }

            //    else {
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
                                                    .limit(2);
                                        }

                                        if (documentSnapshots.size() != 0) {

                                            for (QueryDocumentSnapshot doc : documentSnapshots) {
                                                Quotes quotes
                                                        = new Quotes();
                                                if (doc.getId() !=
                                                        null) {
                                                    quotes.setDocId(
                                                            doc.getId());
                                                }
                                                if (doc.get("quote") !=
                                                        null) {
                                                    quotes.setQuote(
                                                            doc.getString(
                                                                    "quote"));
                                                }
                                                if (categoryIsSet) {
                                                    if (doc.get(
                                                            "category") !=
                                                            null &&
                                                            doc.get(
                                                                    "category")
                                                                    .equals(categoryType)) {
                                                        quotes.setCategory(
                                                                doc.getString(
                                                                        "category"));

                                                        quotes.setFavourite(
                                                                "0");
                                                        quotes.setIsPastQuoteSaved(
                                                                "0");
                                                        (((MyApplication) getApplicationContext()).myGlobalArray)
                                                                .add(quotes);
                                                    }
                                                }
                                                else {

                                                    quotes.setFavourite(
                                                            "0");
                                                    quotes.setIsPastQuoteSaved(
                                                            "0");
                                                    (((MyApplication) getApplicationContext()).myGlobalArray)
                                                            .add(quotes);
                                                }
                                            }

                                            adapter.setItem(
                                                    (((MyApplication) getApplicationContext()).myGlobalArray));

                                        }
                                        else {

                                            repeatData = true;

                                            next = null;

                                            if (Utils.isNetworkAvailable(
                                                    HomeScreenActivity.this)) {
                                                fetchData();
                                            }
                                            else {
                                                Toast.makeText(
                                                        HomeScreenActivity.this,
                                                        "Could" +
                                                                " not connect to internet. Please try" +
                                                                " again later.",
                                                        Toast.LENGTH_LONG
                                                )
                                                        .show();
                                            }

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

    }

    public void openRemindersActivity() {

        startActivity(new Intent(this, RemindersActivity.class));

    }

    public void openMotivationActivity() {

        startActivity(new Intent(this, MotivationActivity.class));

    }

    public void openThemesActivity() {

        startActivityForResult(new Intent(this, ThemesActivity.class), 3);
    }

    public void addPastQuote(Quotes quotes) {

        AddPastQuoteAsyncTask addPastQuoteAsyncTask = new AddPastQuoteAsyncTask(
                this,
                this
        );

        addPastQuoteAsyncTask.execute(quotes.getQuote(), quotes.getDocId(),
                String.valueOf(mQuotePosition)
        );
    }

    public void getQuote(Quotes quotes, int position) {

        GetQuoteAsyncTask getQuoteAsyncTask =
                new GetQuoteAsyncTask(this, this);
        getQuoteAsyncTask.execute(quotes.getDocId(), String.valueOf(position));

    }

    @Override
    public void onPostExecute(Quotes quotes, int position) {

        if (quotes != null) {

            if (quotes.getDocId()
                    .equals((((MyApplication) getApplicationContext()).myGlobalArray)
                            .get(position).getDocId())) {
                (((MyApplication) getApplicationContext()).myGlobalArray)
                        .get(position)
                        .setFavourite(quotes.getIsFavourite());
                (((MyApplication) getApplicationContext()).myGlobalArray)
                        .get(position)
                        .setIsPastQuoteSaved(quotes.getIsPastQuoteSaved());

                adapter.setItem(
                        (((MyApplication) getApplicationContext()).myGlobalArray));
            }

        }
        else {

            if (quoteType.equals("BibleVerses")) {

                if ((((MyApplication) getApplicationContext()).myGlobalArray)
                        .get(position) != null &&
                        (((MyApplication) getApplicationContext()).myGlobalArray)
                                .get(position)
                                .getIsPastQuoteSaved().equals("0")) {
                    addPastQuote(
                            (((MyApplication) getApplicationContext()).myGlobalArray)
                                    .get(position));
                }
            }
            else if (quoteType.equals("Prayers")) {
                if ((((MyApplication) getApplicationContext()).myGlobalArray)
                        .get(position) != null &&
                        (((MyApplication) getApplicationContext()).myGlobalArray)
                                .get(position)
                                .getIsPastQuoteSaved().equals("0")) {
                    addPastQuote(
                            (((MyApplication) getApplicationContext()).myGlobalArray)
                                    .get(position));
                }
            }

        }

    }


    @Override
    public void onPostExecute(Boolean b, int position) {

        if (b) {

            mQuotePosition = position;
            (((MyApplication) getApplicationContext()).myGlobalArray)
                    .get(position).setIsPastQuoteSaved("1");
            adapter.setItem(
                    (((MyApplication) getApplicationContext()).myGlobalArray));
            Log.d(
                    "past quote added", String.valueOf(
                            (((MyApplication) getApplicationContext()).myGlobalArray)));

        }
    }
    @Override
    public void onResume() {
        super.onResume();

        categoryIsSet = sharedPreferencesData.getBool("categorySet", false);

        categoryType = sharedPreferencesData.getStr("categoryType");

        quoteType = sharedPreferencesData.getStr("QuoteType");

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

        if (mIntent.getStringExtra("notificationQuote") != null) {

            if (!mIntent.getStringExtra("notificationQuote").equals("")) {
                Quotes quotes = new Quotes();
                quotes.setFavourite(
                        "0");
                quotes.setIsPastQuoteSaved(
                        "0");
                quotes.setDocId(mIntent.getStringExtra(
                        "notifyQuoteId"));
                quotes.setQuote(mIntent.getStringExtra(
                        "notificationQuote"));
                (((MyApplication) getApplicationContext()).myGlobalArray)
                        .add(0, quotes);
                mNotificationQuote = true;
            }
        }


        if ((((MyApplication) getApplicationContext()).myGlobalArray) != null
                && (((MyApplication) getApplicationContext()).myGlobalArray)
                .size() == 0) {

            if (Utils.isNetworkAvailable(HomeScreenActivity.this)) {
                fetchData();
            }
            else {
                Toast.makeText(HomeScreenActivity.this, "Could" +
                        " not connect to internet. Please try" +
                        " again later.", Toast.LENGTH_LONG).show();
            }


        }

        if (categoryIsSet) {
            setUpViewPager();
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Log.d(
                "onBackPressed",
                String.valueOf(
                        ((MyApplication) getApplicationContext()).myGlobalArray
                                .size())
        );

        if (viewPager.getCurrentItem() == 0) {
            super.onBackPressed();
        }
        else {
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        }

        adClosed = false;
    }


}
