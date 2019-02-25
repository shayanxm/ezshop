package com.example.shayanmoradi.ezshop;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class DissConectedFragment extends DialogFragment {

    private Button reconectBtn;

    public DissConectedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_diss_conected, container, false);
        reconectBtn = view.findViewById(R.id.try_to_reconect);
        reconectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//       FragmentManager fragmentManager= HandleThings.transfer();
//        Fragment frg ;
//        frg = fragmentManager.findFragmentByTag("test");
//        final FragmentTransaction ft = fragmentManager.beginTransaction();
//        ft.detach(frg);
//        ft.attach(frg);
//        ft.commit();
//                Intent intent = ItemDetailActivity.newIntent(getActivity(), 87);
//                startActivity(intent);
//                getActivity().finish();
                getActivity().finish();
                startActivity(getActivity().getIntent());
            }
        });

        return view;
    }

}
