package com.example.shayanmoradi.ezshop.subcategory;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shayanmoradi.ezshop.Model.Category;
import com.example.shayanmoradi.ezshop.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * A simple {@link Fragment} subclass.
 */
public class SubCategoryFragment extends Fragment {
    private static final String CATEGORY_ID="com.example.shayanmoradi.ezshop.subcategory.categoryid";

    private RecyclerView recyclerView;
    private CustomerAdapter customerAdapter;
    int parentId;
    public static SubCategoryFragment newInstance(int inComingId) {

        Bundle args = new Bundle();
        args.putInt(CATEGORY_ID,inComingId);
        SubCategoryFragment fragment = new SubCategoryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public SubCategoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         parentId=  getArguments().getInt(CATEGORY_ID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_sub_category, container, false);
        recyclerView = view.findViewById(R.id.sub_category_rec);

        List<Category>partents=Category.getChilderns(parentId);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        customerAdapter = new CustomerAdapter(partents);
        recyclerView.setAdapter(customerAdapter);
        Toast.makeText(getContext(), "pa"+partents.size(), Toast.LENGTH_SHORT).show();

        return view;
    }
    private class CustoemrHolder extends RecyclerView.ViewHolder {

        private TextView name;


        private ImageView image;

        private Category category;


        public CustoemrHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.sub_category_name);

            image = itemView.findViewById(R.id.sub_category_image);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(), "this is "+category.getId(), Toast.LENGTH_SHORT).show();
                    Intent intent= SubCategoryActivity.newIntent(getActivity(),category.getId());
                    startActivity(intent);

                }
            });
        }

        public void bind(Category category) {
            this.category = category;

            name.setText(this.category.getName());




            if (this.category.getImages() != null && this.category.getImages().size() > 0)
                Picasso.get().load(this.category.getImages().get(0).getPath()).into(image);
            //  customerAge.setText();
            //set age


        }
    }

    class CustomerAdapter extends RecyclerView.Adapter<CustoemrHolder> {

        private List<Category> mProduct;

        public CustomerAdapter(List<Category> cars) {
            mProduct = cars;
        }

        public void setCArs(List<Category> cars) {
            mProduct = cars;
        }

        @NonNull
        @Override
        public CustoemrHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.rec_sub_category_item, parent, false);
            CustoemrHolder custoemrHolder = new CustoemrHolder(view);
            return custoemrHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull CustoemrHolder holder, int position) {
            Category product = mProduct.get(position);

            holder.bind(product);
        }

        @Override
        public int getItemCount() {
            return mProduct.size();
        }
    }




}
