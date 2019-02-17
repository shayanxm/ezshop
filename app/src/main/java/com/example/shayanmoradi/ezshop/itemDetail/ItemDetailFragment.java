package com.example.shayanmoradi.ezshop.itemDetail;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shayanmoradi.ezshop.Model.Product;
import com.example.shayanmoradi.ezshop.R;
import com.example.shayanmoradi.ezshop.network.Api;
import com.example.shayanmoradi.ezshop.network.RetrofitClientInstance;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class ItemDetailFragment extends Fragment {
    private static final String PRODUCT_ID = "com.example.shayanmoradi.ezshop.itemDetail.productid";
    Product product;
    private TextView productName;
    private TextView productPrice;
    private ImageView productImage;

    public static ItemDetailFragment newInstance(int id) {

        Bundle args = new Bundle();
        args.putInt(PRODUCT_ID, id);
        ItemDetailFragment fragment = new ItemDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public ItemDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final List<Product> products = null;

        int productId = getArguments().getInt(PRODUCT_ID);
        RetrofitClientInstance.getRetrofitInstance().create(Api.class)
                .getProductById(productId).enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                product = response.body();
                Toast.makeText(getContext(), "t" + product.getmName(), Toast.LENGTH_SHORT).show();
                productName.setText(product.getmName());

                if (product.getImages() != null && product.getImages().size() > 0)
                    Picasso.get().load(product.getImages().get(0).getPath()).into(productImage);
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {

            }
        });


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view = inflater.inflate(R.layout.fragment_item_detail, container, false);
    productName=view.findViewById(R.id.item_detail_titile);
        //productPrice=view.findViewById(R.id.item_price);
        productImage=view.findViewById(R.id.detail_image);

        // Toast.makeText(getContext(), "test"+test, Toast.LENGTH_SHORT).show();
        return view;

    }

}
