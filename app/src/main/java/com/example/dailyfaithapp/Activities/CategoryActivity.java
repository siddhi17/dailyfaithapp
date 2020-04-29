package com.example.dailyfaithapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dailyfaithapp.Adapters.CategoryAdapter;
import com.example.dailyfaithapp.Helper.GridSpacingItemDecoration;
import com.example.dailyfaithapp.Model.Category;
import com.example.dailyfaithapp.R;

import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity implements
                                                        View.OnClickListener {

    ArrayList<Category> categoryArrayList = new ArrayList<>();
    private RecyclerView recyclerView;
    private CategoryAdapter categoryAdapter;
    private ImageView imageViewBack;
    private View bottomLayout;
    private ConstraintLayout constraintLayoutBottom;

    private RelativeLayout relativeLayoutCategory, relativeLayoutReminders,
            relativeLayoutMore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        setUpUI();

        setUpCategoryList();

        setUpRecyclerView();

    }

    public void setUpUI() {


        imageViewBack = findViewById(R.id.imageView_back);
        recyclerView = findViewById(R.id.recycler_view);


        relativeLayoutCategory = findViewById(R.id.relative_category);
        relativeLayoutReminders = findViewById(R.id.relative_reminders);
        relativeLayoutMore = findViewById(R.id.relative_more);
        relativeLayoutCategory.setOnClickListener(this);
        relativeLayoutReminders.setOnClickListener(this);
        relativeLayoutMore.setOnClickListener(this);
        imageViewBack.setOnClickListener(this);

    }

    public void setUpRecyclerView() {

        categoryAdapter = new CategoryAdapter(categoryArrayList, this);
        RecyclerView.LayoutManager mLayoutManager =
                new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, 40,
                true, 0
        ));

        recyclerView.setAdapter(categoryAdapter);

    }

    public void setUpCategoryList() {

        Category category = new Category();

        category.setCategory("Anxiety");
        category.setColor(getResources().getColor(R.color.Anxiety));

        categoryArrayList.add(category);

        category = new Category();

        category.setCategory("Courage");
        category.setColor(getResources().getColor(R.color.Courage));

        categoryArrayList.add(category);

        category = new Category();
        category.setCategory("Depression");
        category.setColor(getResources().getColor(R.color.Depression));

        categoryArrayList.add(category);

        category = new Category();

        category.setCategory("Doubt");
        category.setColor(getResources().getColor(R.color.Doubt));

        categoryArrayList.add(category);

        category = new Category();

        category.setCategory("Encouragement");
        category.setColor(getResources().getColor(R.color.Encouragement));

        categoryArrayList.add(category);

        category = new Category();

        category.setCategory("Faith");
        category.setColor(getResources().getColor(R.color.Faith));

        categoryArrayList.add(category);

        category = new Category();

        category.setCategory("Fear");
        category.setColor(getResources().getColor(R.color.Fear));

        categoryArrayList.add(category);

        category = new Category();

        category.setCategory("Forgiveness");
        category.setColor(getResources().getColor(R.color.Forgiveness));

        categoryArrayList.add(category);

        category = new Category();

        category.setCategory("Grace");
        category.setColor(getResources().getColor(R.color.Grace));

        categoryArrayList.add(category);

        category = new Category();

        category.setCategory("Gratitude");
        category.setColor(getResources().getColor(R.color.Gratitude));

        categoryArrayList.add(category);

        category = new Category();

        category.setCategory("Guidance");
        category.setColor(getResources().getColor(R.color.Guidance));

        categoryArrayList.add(category);

        category = new Category();

        category.setCategory("Health & Healing");
        category.setColor(getResources().getColor(R.color.HealthHealing));

        categoryArrayList.add(category);

        category = new Category();

        category.setCategory("Hope");
        category.setColor(getResources().getColor(R.color.Hope));

        categoryArrayList.add(category);

        category = new Category();

        category.setCategory("Inspiration");
        category.setColor(getResources().getColor(R.color.Inspiration));

        categoryArrayList.add(category);

        category = new Category();

        category.setCategory("Kids");
        category.setColor(getResources().getColor(R.color.Kids));

        categoryArrayList.add(category);

        category = new Category();

        category.setCategory("Marriage");
        category.setColor(getResources().getColor(R.color.Marriage));

        categoryArrayList.add(category);

        category = new Category();

        category.setCategory("Mercy");
        category.setColor(getResources().getColor(R.color.Mercy));

        categoryArrayList.add(category);

        category = new Category();

        category.setCategory("Patience");
        category.setColor(getResources().getColor(R.color.Patience));

        categoryArrayList.add(category);

        category = new Category();

        category.setCategory("Peace");
        category.setColor(getResources().getColor(R.color.Peace));

        categoryArrayList.add(category);

        category = new Category();

        category.setCategory("Relationships");
        category.setColor(getResources().getColor(R.color.Relationships));

        categoryArrayList.add(category);

        category = new Category();

        category.setCategory("Salvation");
        category.setColor(getResources().getColor(R.color.Salvation));

        categoryArrayList.add(category);

        category = new Category();

        category.setCategory("Strength");
        category.setColor(getResources().getColor(R.color.Strength));

        categoryArrayList.add(category);

        category = new Category();

        category.setCategory("Trust");
        category.setColor(getResources().getColor(R.color.Trust));

        categoryArrayList.add(category);

        category = new Category();

        category.setCategory("Worry");
        category.setColor(getResources().getColor(R.color.Worry));

        categoryArrayList.add(category);


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.relative_reminders:

                openRemindersActivity();

                break;

            case R.id.relative_more:

                openMotivationActivity();

                break;

            case R.id.imageView_back:

                openMainActivity();

        }
    }

    public void openRemindersActivity() {

        startActivity(new Intent(this, RemindersActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK));
        CategoryActivity.this.finish();

    }

    public void openMotivationActivity() {

        startActivity(new Intent(this, MotivationActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK));
        CategoryActivity.this.finish();

    }

    public void openMainActivity() {

        startActivity(new Intent(this, MainActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK));
        CategoryActivity.this.finish();

    }
}
