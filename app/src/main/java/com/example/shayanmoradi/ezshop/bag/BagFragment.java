package com.example.shayanmoradi.ezshop.bag;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.shayanmoradi.ezshop.R;
import com.example.shayanmoradi.ezshop.database.SavedProduct;
import com.example.shayanmoradi.ezshop.database.SavedProductsManger;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class BagFragment extends androidx.fragment.app.Fragment {
    private TextView testing;



    public static BagFragment newInstance() {

        Bundle args = new Bundle();

        BagFragment fragment = new BagFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public BagFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_bag, container, false);
        testing=view.findViewById(R.id.testis);
        List<SavedProduct>savedProductList= SavedProductsManger.getInstance(getContext()).getBag();
        int first = savedProductList.get(0).getProductId();
        int sec = savedProductList.get(1).getProductId();
        testing.setText(first+sec+"");

        return view;
    }

}
