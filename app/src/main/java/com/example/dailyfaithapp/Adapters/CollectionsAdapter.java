package com.example.dailyfaithapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dailyfaithapp.Model.Collections;
import com.example.dailyfaithapp.R;

import java.util.ArrayList;

public class CollectionsAdapter
        extends RecyclerView.Adapter<CollectionsAdapter.CollectionsViewHolder> {

    private Context mContext;

    private ArrayList<Collections> collectionsArrayList;

    // Constructor of the class
    public CollectionsAdapter(
            ArrayList<Collections> collectionsArrayList,
            Context mContext
    ) {
        this.collectionsArrayList = collectionsArrayList;
        this.mContext = mContext;
    }

    // get the size of the list
    @Override
    public int getItemCount() {
        return collectionsArrayList == null ? 0 : collectionsArrayList.size();
    }

    // specify the row layout file and click for each row
    @Override
    public CollectionsViewHolder onCreateViewHolder(
            ViewGroup parent, int viewType
    ) {

        View view =
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_collections, parent, false);
        return new CollectionsViewHolder(view);

    }

    // load data in each row element
    @Override
    public void onBindViewHolder(
            CollectionsViewHolder holder, int listPosition
    ) {

        Collections collections = collectionsArrayList.get(listPosition);

        if (listPosition % 2 == 1) {
            holder.constraintLayoutContainer.setBackgroundColor(
                    mContext.getResources().getColor(R.color.white));
        }
        else {
            holder.constraintLayoutContainer.setBackgroundColor(
                    mContext.getResources().getColor(R.color.light_grey));
        }

        holder.textViewCollection.setText(collections.getCollection_msg());
    }

    // Static inner class to initialize the views of rows
    static class CollectionsViewHolder extends RecyclerView.ViewHolder {
        TextView textViewCollection;
        ConstraintLayout constraintLayoutContainer;

        CollectionsViewHolder(View itemView) {
            super(itemView);
            constraintLayoutContainer =
                    itemView.findViewById(R.id.container);
            textViewCollection =
                    itemView.findViewById(R.id.textView_quote);
        }
    }

}
