package com.example.shayanmoradi.ezshop.review;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shayanmoradi.ezshop.Model.Review;
import com.example.shayanmoradi.ezshop.Model.orderingModels.Customer;
import com.example.shayanmoradi.ezshop.R;
import com.example.shayanmoradi.ezshop.network.Api;
import com.example.shayanmoradi.ezshop.network.RetrofitClientInstance;
import com.example.shayanmoradi.ezshop.prefs.QueryPreferences;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReViewFragment extends androidx.fragment.app.Fragment {
    private RecyclerView recyclerView;
    private CustomerAdapter customerAdapter;
    private int productId;
    private Button newReviewBtn;
    public static String POSITOIN = "com.example.shayanmoradi.ezshop.review";
    private static final String PRODUCT_ID = "com.example.shayanmoradi.ezshop.review";
    private boolean trueNewfalseEdit;
    private int currentRewViewId;

    public static ReViewFragment newInstance(int id) {

        Bundle args = new Bundle();
        args.putInt(PRODUCT_ID, id);
        ReViewFragment fragment = new ReViewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public ReViewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        productId = getArguments().getInt(PRODUCT_ID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_re_view, container, false);
        recyclerView = view.findViewById(R.id.review_rc);
        newReviewBtn = view.findViewById(R.id.add_new_review_btn);
        newReviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trueNewfalseEdit = true;
                DialogFragment dialogFrag = new ReviewEditoerFragment();
// This is the requestCode that you are sending.
                dialogFrag.setTargetFragment(ReViewFragment.this, 0);
// This is the tag, "dialog" being sent.
                dialogFrag.show(getFragmentManager(), "time_dialog");

            }
        });
        RetrofitClientInstance.getRetrofitInstance().create(Api.class)
                .getProductReviewsById(productId).enqueue(new Callback<List<Review>>() {
            @Override
            public void onResponse(Call<List<Review>> call, Response<List<Review>> response) {
                List<Review> reviewList = response.body();


                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                customerAdapter = new CustomerAdapter(reviewList);
                recyclerView.setAdapter(customerAdapter);
                Toast.makeText(getContext(), reviewList.size() + "", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<Review>> call, Throwable t) {

            }
        });


        return view;
    }

    private class CustoemrHolder extends RecyclerView.ViewHolder {


        private TextView reviewTxtTv;
        private Button delteReviewBtn;
        private Button editBtn;
        private TextView reviwerNameTv;
        private TextView reviewDateTv;
        private ImageView deleteIv;
        private ImageView editIv;

        private Review review;


        public CustoemrHolder(@NonNull View itemView) {
            super(itemView);

            reviewTxtTv = itemView.findViewById(R.id.review_text);
            delteReviewBtn = itemView.findViewById(R.id.delete_review_btn);
            editBtn = itemView.findViewById(R.id.edit_review_btn);
            reviwerNameTv = itemView.findViewById(R.id.reviewer_name);
            reviewDateTv = itemView.findViewById(R.id.review_date);
            deleteIv = itemView.findViewById(R.id.delete_image);
            editIv = itemView.findViewById(R.id.edit_image);


//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//
//                }
//            });
        }

        public void bind(final Review review) {
            this.review = review;

            reviewTxtTv.setText(review.getReview());
            reviwerNameTv.setText(review.getReviewer_name());
            reviewDateTv.setText(review.getDate_created());
            String userEmail;
            userEmail = QueryPreferences.getCustomerEmail(getActivity());


            if (review.getReviewer_email().equals(userEmail)) {

                editBtn.setClickable(true);
                delteReviewBtn.setClickable(true);
                deleteIv.setVisibility(View.VISIBLE);
                editIv.setVisibility(View.VISIBLE);

                delteReviewBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getActivity(), "clicked", Toast.LENGTH_SHORT).show();
                        RetrofitClientInstance.getRetrofitInstance().create(Api.class)
                                .deleteReview(review.getId()).enqueue(new Callback<Review>() {
                            @Override
                            public void onResponse(Call<Review> call, Response<Review> response) {
                                if (response.isSuccessful()){
                                    Toast.makeText(getContext(), "ba moafaghiyat hazf shod", Toast.LENGTH_LONG).show();
                                    refreshFragment();
                                }
                            }

                            @Override
                            public void onFailure(Call<Review> call, Throwable t) {

                            }
                        });
                    }
                });

                editBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        currentRewViewId=review.getId();
                        DialogFragment dialogFrag = new ReviewEditoerFragment();
// This is the requestCode that you are sending.
                        dialogFrag.setTargetFragment(ReViewFragment.this, 1);
// This is the tag, "dialog" being sent.
                        dialogFrag.show(getFragmentManager(), "time_dialog");
                    }
                });
            } else {
                editBtn.setClickable(false);
                delteReviewBtn.setClickable(false);
                deleteIv.setVisibility(View.INVISIBLE);
                editIv.setVisibility(View.INVISIBLE);
            }

            //  customerAge.setText();
            //set age


        }

        private void refreshFragment() {
            androidx.fragment.app.Fragment frg = null;
            frg = getFragmentManager().findFragmentByTag("review_tag");
            final FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.detach(frg);
            ft.attach(frg);
            ft.commit();
        }

    }

    class CustomerAdapter extends RecyclerView.Adapter<CustoemrHolder> {

        private List<Review> reviews;

        public CustomerAdapter(List<Review> reviews1) {
            reviews = reviews1;
        }

        public void setCArs(List<Review> reviews1) {
            reviews = reviews1;
        }

        @NonNull
        @Override
        public CustoemrHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.rec_review_item, parent, false);
            CustoemrHolder custoemrHolder = new CustoemrHolder(view);

            return custoemrHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull CustoemrHolder holder, int position) {
            Review review = reviews.get(position);

            holder.bind(review);
        }

        @Override
        public int getItemCount() {
            return reviews.size();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Stuff to do, dependent on requestCode and resultCode
        if (requestCode == 0) { // 1 is an arbitrary number, can be any int
            // This is the return result of your DialogFragment

            String res = (String) data.getSerializableExtra(POSITOIN);
            if (trueNewfalseEdit) {
                ////create new review

                RetrofitClientInstance.getRetrofitInstance().create(Api.class).sendNewReview(productId, res, QueryPreferences.getCustomerName(getContext()), QueryPreferences.getCustomerEmail(getContext()))
                        .enqueue(new Callback<Customer>() {
                            @Override
                            public void onResponse(Call<Customer> call, Response<Customer> response) {
                                if (response.isSuccessful()) {
                                    Log.e("test", "posted braaa" + response.body().getId());


                                }
                            }

                            @Override
                            public void onFailure(Call<Customer> call, Throwable t) {

                            }
                        });

            } {
                //eidt old review
            }

        }if (requestCode==1){
            trueNewfalseEdit=false;
            String res = (String) data.getSerializableExtra(POSITOIN);
            RetrofitClientInstance.getRetrofitInstance().create(Api.class).updateReview(currentRewViewId,res).enqueue(new Callback<Review>() {
                @Override
                public void onResponse(Call<Review> call, Response<Review> response) {
                    refreshFragment();
                }

                @Override
                public void onFailure(Call<Review> call, Throwable t) {

                }
            });


        }
    }
    private void refreshFragment() {
        androidx.fragment.app.Fragment frg = null;
        frg = getFragmentManager().findFragmentByTag("review_tag");
        final FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(frg);
        ft.attach(frg);
        ft.commit();
    }
}
