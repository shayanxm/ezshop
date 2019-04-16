package com.example.shayanmoradi.ezshop.setting;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shayanmoradi.ezshop.R;
import com.example.shayanmoradi.ezshop.services.PollService;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;

import static com.example.shayanmoradi.ezshop.result.SortChoserDialogFragment.POSITOIN;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends androidx.fragment.app.Fragment {
    private CardView openTimeShosserCW;
    private Button confrimSettingBtn;
    public static int TIME_FOR_NOTIFICATION = 1;
    int chossedTime;
    private EditText timeEnteredEt;

    public static SettingFragment newInstance() {

        Bundle args = new Bundle();

        SettingFragment fragment = new SettingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public SettingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        openTimeShosserCW = view.findViewById(R.id.open_time_chosser_cw);
        confrimSettingBtn = view.findViewById(R.id.confrim_setting_btn);
        timeEnteredEt = view.findViewById(R.id.enterd_time_edit_text);

        confrimSettingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chossedTime != 0) {
                    PollService.setServiceAlarm(getContext(), true, chossedTime );
                } else if (Integer.parseInt(timeEnteredEt.getText().toString()) != 0) {
                    int time = Integer.parseInt(timeEnteredEt.getText().toString());
                    PollService.setServiceAlarm(getContext(), true, time );
                } else {
                    Toast.makeText(getActivity(), "زمان جدیدی انتخاب نشد", Toast.LENGTH_LONG).show();
                }
                getActivity().finish();
            }
        });


        //////////////////
        openTimeShosserCW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialogFrag = new TimeChosserFragment();
// This is the requestCode that you are sending.
                dialogFrag.setTargetFragment(SettingFragment.this, TIME_FOR_NOTIFICATION);
// This is the tag, "dialog" being sent.
                dialogFrag.show(getFragmentManager(), "time_dialog");
            }
        });


        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Stuff to do, dependent on requestCode and resultCode
        if (requestCode == TIME_FOR_NOTIFICATION) { // 1 is an arbitrary number, can be any int
            // This is the return result of your DialogFragment

            int res = (int) data.getSerializableExtra(POSITOIN);


            switch (res) {
                case 1:

                    chossedTime = 3;
                    break;

                case 2:
                    chossedTime = 5;
                    break;
                case 3:
                    chossedTime = 8;
                    break;
                case 4:
                    chossedTime = 12;

                    break;

            }
            Toast.makeText(getContext(), chossedTime + "", Toast.LENGTH_SHORT).show();
        }
    }
}
