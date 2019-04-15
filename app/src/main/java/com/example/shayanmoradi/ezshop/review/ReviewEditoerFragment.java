package com.example.shayanmoradi.ezshop.review;


import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.shayanmoradi.ezshop.R;

import androidx.fragment.app.DialogFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class ReviewEditoerFragment extends DialogFragment {
  private EditText reviewText;
  private Button submitReview;
    public static String POSITOIN = "com.example.shayanmoradi.ezshop.review";
    public ReviewEditoerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_review_editoer, container, false);
  reviewText=view.findViewById(R.id.review_edtior_et);
  submitReview=view.findViewById(R.id.edit_review_btn);
  submitReview.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
          sendResult(reviewText.getText().toString());
          dismiss();
      }
  });


    return view;

    }
    private void sendResult(String text) {
        Intent intent = new Intent();
        intent.putExtra(POSITOIN, text);
        getTargetFragment().
                onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);

        getTargetFragment().onResume();
    }

}
