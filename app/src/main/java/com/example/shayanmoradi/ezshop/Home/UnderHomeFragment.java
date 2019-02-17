package com.example.shayanmoradi.ezshop.Home;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shayanmoradi.ezshop.Model.Product;
import com.example.shayanmoradi.ezshop.R;
import com.example.shayanmoradi.ezshop.itemDetail.ItemDetailActivity;
import com.example.shayanmoradi.ezshop.network.Api;
import com.example.shayanmoradi.ezshop.network.RetrofitClientInstance;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class UnderHomeFragment extends Fragment {
    private RecyclerView newestRec;
    private CustomerAdapter customerAdapter;
    private RecyclerView topSalesRec;
    private CustomerAdapter newestAdapter;
    private RecyclerView topRatedsRec;
    private CustomerAdapter topRatedtAdapter;
    public static UnderHomeFragment newInstance() {

        Bundle args = new Bundle();

        UnderHomeFragment fragment = new UnderHomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public UnderHomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_under_home, container, false);
        newestRec = view.findViewById(R.id.news_rec);
        topSalesRec = view.findViewById(R.id.top_sales_rec);
        topRatedsRec = view.findViewById(R.id.top_rated_rec);
        topRatedsRec.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayout.HORIZONTAL, false));
        topSalesRec.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayout.HORIZONTAL, false));
        newestRec.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayout.HORIZONTAL, false));




        RetrofitClientInstance.getRetrofitInstance().create(Api.class)
                .getCommitsByName("total_sale").enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                List<Product> productList = response.body();
                customerAdapter = new CustomerAdapter(productList);
                newestRec.setAdapter(customerAdapter);
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

            }
        });


        RetrofitClientInstance.getRetrofitInstance().create(Api.class)
                .getCommitsByName("date_created").enqueue(new Callback<List<Product>>() {
                    @Override
                    public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                        List<Product> productList = response.body();
                        newestAdapter = new CustomerAdapter(productList);
                        topSalesRec.setAdapter(newestAdapter);
                    }

                    @Override
                    public void onFailure(Call<List<Product>> call, Throwable t) {

                    }
                });
        RetrofitClientInstance.getRetrofitInstance().create(Api.class)
                .getCommitsByName("average_rating").enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                List<Product> productList = response.body();
                topRatedtAdapter = new CustomerAdapter(productList);
                topRatedsRec.setAdapter(topRatedtAdapter);
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

            }
        });
        return view;
    }

    private class CustoemrHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private TextView price;

        private ImageView image;

        private Product product;


        public CustoemrHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.item_name);
            price = itemView.findViewById(R.id.item_price);
            image = itemView.findViewById(R.id.item_image);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(), "this is " + product.getmId(), Toast.LENGTH_SHORT).show();
                    Intent intent = ItemDetailActivity.newIntent(getActivity(), product.getmId());
                    startActivity(intent);

                }
            });
        }

        public void bind(Product mproduct) {
            product = mproduct;

            name.setText(product.getmName());

            price.setText(product.getmPrice());


            if (product.getImages() != null && product.getImages().size() > 0)
                Picasso.get().load(product.getImages().get(0).getPath()).into(image);
            //  customerAge.setText();
            //set age


        }
    }

    class CustomerAdapter extends RecyclerView.Adapter<CustoemrHolder> {

        private List<Product> mProduct;

        public CustomerAdapter(List<Product> cars) {
            mProduct = cars;
        }

        public void setCArs(List<Product> cars) {
            mProduct = cars;
        }

        @NonNull
        @Override
        public CustoemrHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.rec_home_item, parent, false);
            CustoemrHolder custoemrHolder = new CustoemrHolder(view);
            return custoemrHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull CustoemrHolder holder, int position) {
            Product product = mProduct.get(position);

            holder.bind(product);
        }

        @Override
        public int getItemCount() {
            return mProduct.size();
        }
    }

}