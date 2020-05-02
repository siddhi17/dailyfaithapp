package com.dailyfaithapp.dailyfaith.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.dailyfaithapp.dailyfaith.R;

import java.util.ArrayList;
import java.util.List;

public class CollectionQuotesAdapter
        extends RecyclerView.Adapter<CollectionQuotesAdapter.ViewHolder> {

    private Context mContext;
    private Boolean isCollections = false;

    private List<String> collectionQuotesList;

    // Constructor of the class
    public CollectionQuotesAdapter(
            ArrayList<String> collectionQuotesList,
            Context mContext
    ) {
        this.collectionQuotesList = collectionQuotesList;
        this.mContext = mContext;
    }

    // get the size of the list
    @Override
    public int getItemCount() {
        return collectionQuotesList == null ? 0 : collectionQuotesList.size();
    }

    // specify the row layout file and click for each row
    @Override
    public CollectionQuotesAdapter.ViewHolder onCreateViewHolder(
            ViewGroup parent, int viewType
    ) {

        View view =
                LayoutInflater.from(parent.getContext())
                        .inflate(
                                R.layout.item_collection_quotes, parent, false);
        return new CollectionQuotesAdapter.ViewHolder(view);

    }

    // load data in each row element
    @Override
    public void onBindViewHolder(
            CollectionQuotesAdapter.ViewHolder holder, int listPosition
    ) {

        String quote = collectionQuotesList.get(listPosition);

        if (listPosition % 2 == 1) {
            holder.constraintLayoutContainer.setBackgroundColor(
                    mContext.getResources().getColor(R.color.white));
        }
        else {
            holder.constraintLayoutContainer.setBackgroundColor(
                    mContext.getResources().getColor(R.color.light_grey));
        }

        holder.textViewQuote.setText(quote);

    }

    public void updateList(List<String> collectionQuotesList) {
        collectionQuotesList = collectionQuotesList;
        notifyDataSetChanged();
    }

    // Static inner class to initialize the views of rows
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewQuote;
        ConstraintLayout constraintLayoutContainer;

        ViewHolder(View itemView) {
            super(itemView);
            constraintLayoutContainer =
                    itemView.findViewById(R.id.container);
            textViewQuote =
                    itemView.findViewById(R.id.textView_quote);

        }
    }
}
