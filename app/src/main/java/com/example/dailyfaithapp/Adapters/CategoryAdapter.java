package com.example.dailyfaithapp.Adapters;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.dailyfaithapp.Model.Category;
import com.example.dailyfaithapp.R;

import java.util.List;

public class CategoryAdapter
        extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {

    private List<Category> categoryList;
    private Context mContext;

    public CategoryAdapter(List<Category> categoryList, Context context) {
        this.categoryList = categoryList;
        this.mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_category_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Category category = categoryList.get(position);

     /*       Drawable unwrappedDrawable = AppCompatResources
                    .getDrawable(mContext, R.drawable.category_background);
            Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
            DrawableCompat.setTint(wrappedDrawable, category.getColor());
*/
        GradientDrawable gd = new GradientDrawable();
        gd.setColor(category.getColor());
        gd.setCornerRadius(20);
        gd.setStroke(2, mContext.getResources().getColor(R.color.Worry));
        holder.category.setBackground(gd);

        //   holder.category.setBackground(wrappedDrawable);
        holder.category.setText(category.getCategory());

    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView category;

        public MyViewHolder(View view) {
            super(view);
            category = (TextView) view.findViewById(R.id.textView_category);
        }
    }
}
