package com.example.shayanmoradi.ezshop.search;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shayanmoradi.ezshop.R;
import com.example.shayanmoradi.ezshop.result.ReultActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends androidx.fragment.app.Fragment {
    private EditText searchEdit;
private Button searchBtn;
    public static SearchFragment newInstance() {

        Bundle args = new Bundle();

        SearchFragment fragment = new SearchFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        searchEdit = view.findViewById(R.id.search_edit);
        searchBtn = view.findViewById(R.id.search_btn);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = ReultActivity.newIntent(getActivity(), false, 0, searchEdit.getText().toString());
                startActivity(intent);
            }
        });

        searchEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    Toast.makeText(getContext(), "sdfsdfsdf", Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }
        });


        return view;
    }
}
