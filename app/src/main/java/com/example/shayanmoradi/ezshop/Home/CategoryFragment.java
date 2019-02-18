package com.example.shayanmoradi.ezshop.Home;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.shayanmoradi.ezshop.Model.Category;
import com.example.shayanmoradi.ezshop.Model.Repository;
import com.example.shayanmoradi.ezshop.R;
import com.example.shayanmoradi.ezshop.network.Api;
import com.example.shayanmoradi.ezshop.network.RetrofitClientInstance;
import com.example.shayanmoradi.ezshop.subcategory.SubCategoryActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends Fragment {
    LottieAnimationView lottieAnimationView;
    private RecyclerView recyclerView;
    private CustomerAdapter customerAdapter;

    public static CategoryFragment newInstance() {
        
        Bundle args = new Bundle();
        
        CategoryFragment fragment = new CategoryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public CategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_category, container, false);
        recyclerView = view.findViewById(R.id.category_rec);
        lottieAnimationView=view.findViewById(R.id.animation_view2);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        RetrofitClientInstance.getRetrofitInstance().create(Api.class)
                .getCatrgories().enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                List<Category>categories=response.body();
                Repository.getInstance().setAllCategories(categories);
                List<Category>partents=Category.filterParents(categories);
                customerAdapter = new CustomerAdapter(partents);
                recyclerView.setAdapter(customerAdapter);
                lottieAnimationView.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {

            }
        });


        return view;

    }

    private class CustoemrHolder extends RecyclerView.ViewHolder {

        private TextView name;


        private ImageView image;

        private Category category;


        public CustoemrHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.category_name);

            image = itemView.findViewById(R.id.category_image);


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
            View view = inflater.inflate(R.layout.rec_category_item, parent, false);
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



