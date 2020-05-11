package com.dailyfaithapp.dailyfaith.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.dailyfaithapp.dailyfaith.Model.Collections;
import com.dailyfaithapp.dailyfaith.R;

import java.util.ArrayList;
import java.util.List;

public class CollectionsDialogAdapter extends ArrayAdapter<Collections> {

    private Context mContext;
    private List<Collections> collectionsList = new ArrayList<>();

    public CollectionsDialogAdapter(
            @NonNull Context context,
            @SuppressLint("SupportAnnotationUsage") @LayoutRes
                    ArrayList<Collections> list
    ) {
        super(context, 0, list);
        mContext = context;
        collectionsList = list;
    }

    @NonNull
    @Override
    public View getView(
            int position, @Nullable View convertView, @NonNull ViewGroup parent
    ) {
        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(mContext)
                    .inflate(R.layout.item_quote,
                            parent, false
                    );
        }

        Collections collections = collectionsList.get(position);

        TextView title = listItem.findViewById(R.id.textView_quote);
        title.setText(collections.getTitle());

        return listItem;
    }
}