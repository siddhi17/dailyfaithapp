package com.example.dailyfaithapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.dailyfaithapp.AsyncTasks.AddFavouriteAsyncTask;
import com.example.dailyfaithapp.Model.Quotes;
import com.example.dailyfaithapp.R;
import com.skydoves.balloon.Balloon;

import java.util.ArrayList;

public class ViewPagerAdapter
        extends RecyclerView.Adapter<ViewPagerAdapter.QuotesViewHolder>
        implements
        AddFavouriteAsyncTask.AddFavouriteCallBack {

    private ArrayList<Quotes> quotesArrayList;
    private Context mContext;

    public ViewPagerAdapter(
            Context context,
            ArrayList<Quotes> quotesArrayList
    ) {
        this.mContext = context;
        this.quotesArrayList = quotesArrayList;
    }

    @Override
    public QuotesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater
                .from(parent.getContext());
        View listItem = layoutInflater
                .inflate(R.layout.quotes_fragment, parent, false);
        QuotesViewHolder viewHolder = new QuotesViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(QuotesViewHolder holder, int position) {
        final Quotes quotes = quotesArrayList.get(position);

        holder.textView.setText(quotes.getQuote());


 /*       holder.imageViewShare.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {

                PopUpClass popUpClass = new PopUpClass();
                popUpClass.showPopupWindow(v);


            }
        });*/

        if (quotes.isFavourite() != null) {

            if (quotes.isFavourite().equals("1")) {

                holder.imageViewFavourite
                        .setImageResource(R.drawable.ic_favourite_filled);
            }
            else {

                holder.imageViewFavourite
                        .setImageResource(R.drawable.ic_favourite);
            }
        }

        holder.imageViewFavourite
                .setOnClickListener(new View.OnClickListener() {
                    @Override public void onClick(View v) {

                        addFavourite(quotes.getId(), "1");
                        holder.imageViewFavourite
                                .setImageResource(
                                        R.drawable.ic_favourite_filled);
                    }
                });


        holder.imageViewShare.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {

                Balloon balloon = new Balloon.Builder(mContext)
                        .setLayout(R.layout.popup_menu_layout)
                        .setBackgroundColor(mContext.getResources()
                                .getColor(R.color.transparent))
                        .setDismissWhenTouchOutside(true)
                        .setPaddingBottom(20)
                        .setArrowVisible(false)
                        .build();

                balloon.show(holder.imageViewShare);

            }
        });


    }


    public void addFavourite(int id, String isFav) {
        AddFavouriteAsyncTask addFavouriteAsyncTask =
                new AddFavouriteAsyncTask(mContext, this);
        addFavouriteAsyncTask.execute(String.valueOf(id), isFav);

    }

    @Override
    public int getItemCount() {
        return quotesArrayList.size();
    }

    public void setItem(ArrayList<Quotes> quotesArrayList) {
        this.quotesArrayList = quotesArrayList;
        notifyDataSetChanged();
    }

    @Override
    public void onPostExecute(Boolean b) {
        if (b) {
            Toast.makeText(mContext, "Favourite Added", Toast.LENGTH_LONG)
                    .show();
        }

    }

    public static class QuotesViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public ImageView imageViewShare, imageViewFavourite;

        public QuotesViewHolder(View itemView) {
            super(itemView);
            this.textView = (TextView) itemView
                    .findViewById(R.id.textView_quotes);
            this.imageViewShare = itemView.findViewById(R.id.imageView_share);
            this.imageViewFavourite =
                    itemView.findViewById(R.id.imageView_favourite);
        }

    }

}
