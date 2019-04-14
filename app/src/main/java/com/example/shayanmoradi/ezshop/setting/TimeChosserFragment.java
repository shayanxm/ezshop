package com.example.shayanmoradi.ezshop.setting;


import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.shayanmoradi.ezshop.R;

import androidx.fragment.app.DialogFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class TimeChosserFragment extends DialogFragment {
    private RadioButton pos1;
    private RadioButton pos2;
    private RadioButton pos3;
    private RadioButton pos4;
    private RadioGroup radioGroup;
    public static String POSITOIN = "com.example.shayanmoradi.ezshop.setting";


    public TimeChosserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=  inflater.inflate(R.layout.fragment_time_chosser, container, false);

        pos1 = view.findViewById(R.id.radioButton1);
        pos2 = view.findViewById(R.id.radioButton2);
        pos3 = view.findViewById(R.id.radioButton3);
        pos4 = view.findViewById(R.id.radioButton4);
        radioGroup = view.findViewById(R.id.radio_group);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // find which radio button is selected
                if (checkedId == R.id.radioButton1) {
                    sendResult(1);
                    dismiss();

                } else if (checkedId == R.id.radioButton2) {
                    sendResult(2);
                    dismiss();
                } else if (checkedId == R.id.radioButton3) {
                    sendResult(3);
                    dismiss();
                } else {
                    sendResult(4);
                    dismiss();
                }
            }

        });




        return view;
   }
    private void sendResult(int postion) {
        Intent intent = new Intent();
        intent.putExtra(POSITOIN, postion);
        getTargetFragment().
                onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);

        getTargetFragment().onResume();
    }
}
