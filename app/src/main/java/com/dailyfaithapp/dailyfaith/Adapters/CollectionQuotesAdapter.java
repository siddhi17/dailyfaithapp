package com.dailyfaithapp.dailyfaith.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.dailyfaithapp.dailyfaith.AsyncTasks.DeleteQuoteFromCollection;
import com.dailyfaithapp.dailyfaith.Model.Collections;
import com.dailyfaithapp.dailyfaith.R;

import java.util.ArrayList;
import java.util.List;

public class CollectionQuotesAdapter
        extends RecyclerView.Adapter<CollectionQuotesAdapter.ViewHolder>
        implements DeleteQuoteFromCollection.DeleteQuoteFromCollectionCallBack {

    private Context mContext;
    private Boolean isCollections = false;

    private List<Collections> collectionQuotesList;

    // Constructor of the class
    public CollectionQuotesAdapter(
            ArrayList<Collections> collectionQuotesList,
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

        Collections quote = collectionQuotesList.get(listPosition);

        if (listPosition % 2 == 1) {
            holder.constraintLayoutContainer.setBackgroundColor(
                    mContext.getResources().getColor(R.color.white));
        }
        else {
            holder.constraintLayoutContainer.setBackgroundColor(
                    mContext.getResources().getColor(R.color.light_grey));
        }

        holder.textViewQuote.setText(quote.getCollection_msg());


        holder.imageViewDelete.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {

                deleteCollection(String.valueOf(quote.getId()), listPosition);

            }
        });

    }

    public void deleteCollection(String id, int position) {
        DeleteQuoteFromCollection deleteQuoteFromCollection =
                new DeleteQuoteFromCollection(mContext, this);
        deleteQuoteFromCollection.execute(id, String.valueOf(position));

    }

    @Override
    public void onPostExecute(Boolean b, int position) {

        Toast.makeText(mContext, "Quote Deleted.", Toast.LENGTH_LONG).show();
        collectionQuotesList.remove(position);
        notifyDataSetChanged();
    }

    public void updateList(List<Collections> collectionQuotes) {
        collectionQuotesList = collectionQuotes;
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
