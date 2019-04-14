package com.example.shayanmoradi.ezshop.review;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shayanmoradi.ezshop.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReViewFragment extends Fragment {


    public ReViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_re_view, container, false);
    }

}
