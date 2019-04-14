package com.example.shayanmoradi.ezshop.itemsofcategory;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.shayanmoradi.ezshop.Model.Product;
import com.example.shayanmoradi.ezshop.R;
import com.example.shayanmoradi.ezshop.customerhandler.HandleThings;
import com.example.shayanmoradi.ezshop.itemDetail.ItemDetailActivity;
import com.example.shayanmoradi.ezshop.network.Api;
import com.example.shayanmoradi.ezshop.network.RetrofitClientInstance;
import com.example.shayanmoradi.ezshop.result.ReultActivity;
import com.google.android.material.appbar.AppBarLayout;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ItemsOfCategoryFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView recyclerViewSale;
    private CustomerAdapter TopHitsAdapter;
    private CustomerAdapter newestAdapter;
    int categoryId;
    private CardView goToSort;
    List<Product> currentProducts;
    private TextView itemsName;
    private String categoryName;
    private AppBarLayout appBarLayout;
    private TextView dasteBandiTv;
    private static final String CATEGORY_ID = "com.example.shayanmoradi.ezshop.itemsofcategory.categoryid";
    private static final String CATEGORY_NAME = "com.example.shayanmoradi.ezshop.itemsofcategory.categoryname";

    public static ItemsOfCategoryFragment newInstance(int categoryId, String categoryName) {

        Bundle args = new Bundle();
        args.putInt(CATEGORY_ID, categoryId);
        args.putString(CATEGORY_NAME, categoryName);
        ItemsOfCategoryFragment fragment = new ItemsOfCategoryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public ItemsOfCategoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        categoryId = getArguments().getInt(CATEGORY_ID);
        categoryName = getArguments().getString(CATEGORY_NAME);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_items_of_category, container, false);

        recyclerView = view.findViewById(R.id.items_of_category_rec_new);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayout.HORIZONTAL, false));
        itemsName = view.findViewById(R.id.items_names);
        appBarLayout = view.findViewById(R.id.items_of_top_app_bar);
goToSort=view.findViewById(R.id.go_to_sort_card);
        recyclerViewSale = view.findViewById(R.id.items_of_category_rec_top);
        dasteBandiTv=view.findViewById(R.id.daste_bandi_tv);
        recyclerViewSale.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayout.HORIZONTAL, false));
        if (categoryId == 15) {
            appBarLayout.setVisibility(View.GONE);
        } else {
            appBarLayout.setVisibility(View.VISIBLE);
        }
        //   List<Category> partents=Category.getChilderns(categoryId);
        RetrofitClientInstance.getRetrofitInstance().create(Api.class)
                .getCategoriesItemsByCategoryItem(categoryId).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()) {
                    currentProducts = response.body();
                    List<Product> forTop=Product.getTopHits(currentProducts);
                    TopHitsAdapter = new CustomerAdapter(forTop);
                    newestAdapter = new CustomerAdapter(currentProducts);
                    recyclerView.setAdapter(newestAdapter);
                    recyclerViewSale.setAdapter(TopHitsAdapter);



                    itemsName.setText(categoryName);
                    dasteBandiTv.setText(categoryName);
//                Toast.makeText(getActivity(), "te" + categoryId, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

                HandleThings.isOnline(getActivity(),getFragmentManager());
            }
        });

goToSort.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent= ReultActivity.newIntent(getActivity(),true,categoryId,"");
        startActivity(intent);
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
                    //    Toast.makeText(getActivity(), "this is " + category.getId(), Toast.LENGTH_SHORT).show();
                    Intent intent = ItemDetailActivity.newIntent(getActivity(), product.getmId());
                    startActivity(intent);

                }
            });
        }

        public void bind(Product product) {
            this.product = product;

            name.setText((this.product).getmName());

            price.setText(this.product.getmPrice());
            if (this.product.getImages() != null && this.product.getImages().size() > 0)
                Picasso.get().load(this.product.getImages().get(0).getPath()).into(image);
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






