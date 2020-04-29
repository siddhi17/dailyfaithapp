package com.example.dailyfaithapp.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.dailyfaithapp.Model.Quotes;
import com.example.dailyfaithapp.R;
import com.example.dailyfaithapp.ViewModels.QuotesViewModel;

import java.util.ArrayList;

public class QuotesFragment extends Fragment {

    private static final String ARG_COUNT = "param1";
    private QuotesViewModel mViewModel;
    private TextView textViewQuote;
    private Integer counter;

    private ArrayList<Quotes> quotesArrayList = new ArrayList<>();

    public static QuotesFragment newInstance() {
        return new QuotesFragment();
    }

    public static QuotesFragment newInstance(Integer counter) {
        QuotesFragment fragment = new QuotesFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COUNT, counter);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {

        View view = inflater.inflate(
                R.layout.quotes_fragment, container,
                false
        );

        setUpUI(view);

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            counter = quotesArrayList.size();
        }
    }

    @Override public void onViewCreated(
            @NonNull View view, @Nullable Bundle savedInstanceState
    ) {
        super.onViewCreated(view, savedInstanceState);
        //view.setBackgroundColor(ContextCompat.getColor(getContext(),
        //   COLOR_MAP[counter]));
        textViewQuote.setText("Doing a same thing over and over again and " +
                "expecting different results");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(QuotesViewModel.class);

        // TODO: Use the ViewModel
    }

    public void setUpUI(View view) {

        textViewQuote = view.findViewById(R.id.textView_quotes);

    }

}
