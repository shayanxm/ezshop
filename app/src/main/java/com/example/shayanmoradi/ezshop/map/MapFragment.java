package com.example.shayanmoradi.ezshop.map;


import android.app.Fragment;
import android.os.Bundle;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends androidx.fragment.app.Fragment {
    public static MapFragment newInstance() {
        
        Bundle args = new Bundle();
        
        MapFragment fragment = new MapFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public MapFragment() {
        // Required empty public constructor
    }




}
