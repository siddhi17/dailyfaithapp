package com.dailyfaithapp.dailyfaith.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.dailyfaithapp.dailyfaith.Model.Quotes;
import com.dailyfaithapp.dailyfaith.R;

import java.util.ArrayList;
import java.util.List;

public class PastQuotesAdapter
        extends RecyclerView.Adapter<PastQuotesAdapter.ViewHolder> {

    private Context mContext;
    private Boolean isCollections = false;

    private List<Quotes> quotesList;

    // Constructor of the class
    public PastQuotesAdapter(
            ArrayList<Quotes> quotesList,
            Context mContext
    ) {
        this.quotesList = quotesList;
        this.mContext = mContext;
    }

    // get the size of the list
    @Override
    public int getItemCount() {
        return quotesList == null ? 0 : quotesList.size();
    }

    // specify the row layout file and click for each row
    @Override
    public PastQuotesAdapter.ViewHolder onCreateViewHolder(
            ViewGroup parent, int viewType
    ) {

        View view =
                LayoutInflater.from(parent.getContext())
                        .inflate(
                                R.layout.item_quote, parent, false);
        return new PastQuotesAdapter.ViewHolder(view);

    }

    // load data in each row element
    @Override
    public void onBindViewHolder(
            PastQuotesAdapter.ViewHolder holder, int listPosition
    ) {

        Quotes quote = quotesList.get(listPosition);

        if (listPosition % 2 == 1) {
            holder.constraintLayoutContainer.setBackgroundColor(
                    mContext.getResources().getColor(R.color.white));
        }
        else {
            holder.constraintLayoutContainer.setBackgroundColor(
                    mContext.getResources().getColor(R.color.light_grey));
        }

        holder.textViewQuote.setText(quote.getQuote());

    }

    public void updateList(List<Quotes> quotesList1) {
        quotesList = quotesList1;
        notifyDataSetChanged();
    }

    // Static inner class to initialize the views of rows
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewQuote;
        ConstraintLayout constraintLayoutContainer;
        ImageView imageViewDelete;

        ViewHolder(View itemView) {
            super(itemView);
            constraintLayoutContainer =
                    itemView.findViewById(R.id.container);
            textViewQuote =
                    itemView.findViewById(R.id.textView_quote);
            imageViewDelete = itemView.findViewById(R.id.imageView_delete);

        }
    }
}
