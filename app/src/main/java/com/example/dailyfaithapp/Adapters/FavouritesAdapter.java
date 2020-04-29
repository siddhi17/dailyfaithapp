package com.example.dailyfaithapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dailyfaithapp.Model.Quotes;
import com.example.dailyfaithapp.R;

import java.util.List;

public class FavouritesAdapter
        extends RecyclerView.Adapter<FavouritesAdapter.FavouritesViewHolder> {

    private Context mContext;

    private List<Quotes> favouritesArrayList;

    // Constructor of the class
    public FavouritesAdapter(
            List<Quotes> favouritesArrayList,
            Context mContext
    ) {
        this.favouritesArrayList = favouritesArrayList;
        this.mContext = mContext;

    }

    // get the size of the list
    @Override
    public int getItemCount() {
        return favouritesArrayList == null ? 0 : favouritesArrayList.size();
    }

    // specify the row layout file and click for each row
    @Override
    public FavouritesAdapter.FavouritesViewHolder onCreateViewHolder(
            ViewGroup parent, int viewType
    ) {

        View view =
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_favourites, parent, false);
        return new FavouritesAdapter.FavouritesViewHolder(view);

    }

    public void updateList(List<Quotes> favourites) {
        favouritesArrayList = favourites;
        notifyDataSetChanged();
    }

    // load data in each row element
    @Override
    public void onBindViewHolder(
            FavouritesAdapter.FavouritesViewHolder holder, int listPosition
    ) {

        Quotes favourites = favouritesArrayList.get(listPosition);

        if (listPosition % 2 == 1) {
            holder.constraintLayoutContainer.setBackgroundColor(
                    mContext.getResources().getColor(R.color.white));
        }
        else {
            holder.constraintLayoutContainer.setBackgroundColor(
                    mContext.getResources().getColor(R.color.light_grey));
        }

        holder.textViewCollection.setText(favourites.getQuote());
    }

    // Static inner class to initialize the views of rows
    static class FavouritesViewHolder extends RecyclerView.ViewHolder {
        TextView textViewCollection;
        ConstraintLayout constraintLayoutContainer;

        FavouritesViewHolder(View itemView) {
            super(itemView);
            constraintLayoutContainer =
                    itemView.findViewById(R.id.container);
            textViewCollection =
                    itemView.findViewById(R.id.textView_quote);
        }
    }
}