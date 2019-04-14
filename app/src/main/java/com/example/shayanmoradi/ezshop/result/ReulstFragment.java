package com.example.shayanmoradi.ezshop.result;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shayanmoradi.ezshop.Model.Product;
import com.example.shayanmoradi.ezshop.R;
import com.example.shayanmoradi.ezshop.customerhandler.HandleThings;
import com.example.shayanmoradi.ezshop.filter.FilterActivity;
import com.example.shayanmoradi.ezshop.itemDetail.ItemDetailActivity;
import com.example.shayanmoradi.ezshop.network.Api;
import com.example.shayanmoradi.ezshop.network.RetrofitClientInstance;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.shayanmoradi.ezshop.result.SortChoserDialogFragment.POSITOIN;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReulstFragment extends androidx.fragment.app.Fragment {
    public static String TRUE_FOR_CATEGORY = "com.example.shayanmoradi.ezshop.trueForCategory";
    public static String CATEGORY_ID = "com.example.shayanmoradi.ezshop.catergoryId";
    public static String SEARCHING_STRING = "com.example.shayanmoradi.ezshop.searchString";
    public static int SORT_BY_REC_CODE = 1;
    public static int SORT_BY_REC_SEARCHED_CODE = 2;
    private ConstraintLayout showSortByDialog;
    private ConstraintLayout showFilterFragmnet;
    private RecyclerView inSortRec;
    private CustomerAdapter inSortAdapter;

    private boolean trueForCategory;
    private int catergoryId;
    private String searchString;
    List<Product> currentProducts;
    TextView sortByWhatTv;


    public static ReulstFragment newInstance(boolean trueForCategory, int catergoryId, String searchString) {

        Bundle args = new Bundle();
        args.putBoolean(TRUE_FOR_CATEGORY, trueForCategory);
        args.putInt(CATEGORY_ID, catergoryId);
        args.putString(SEARCHING_STRING, searchString);
        ReulstFragment fragment = new ReulstFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public ReulstFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        trueForCategory = getArguments().getBoolean(TRUE_FOR_CATEGORY);
        if (trueForCategory) {
            catergoryId = getArguments().getInt(CATEGORY_ID);
        } else {
            searchString = getArguments().getString(SEARCHING_STRING);

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reulst, container, false);
        showSortByDialog = view.findViewById(R.id.show_sort_by_dialog);
        showFilterFragmnet = view.findViewById(R.id.show_filter_fragment);
        inSortRec = view.findViewById(R.id.in_sort_rec);
        sortByWhatTv=view.findViewById(R.id.sort_by_what_tv);
        inSortRec.setLayoutManager(new LinearLayoutManager(getContext()));

        if (trueForCategory) {
            showSortByDialog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ReulstFragment datePickerFragment = new ReulstFragment();

//                datePickerFragment.show(getFragmentManager(), "MyDialog");


                    DialogFragment dialogFrag = new SortChoserDialogFragment();
// This is the requestCode that you are sending.
                    dialogFrag.setTargetFragment(ReulstFragment.this, SORT_BY_REC_CODE);
// This is the tag, "dialog" being sent.
                    dialogFrag.show(getFragmentManager(), "dialog");
                }
            });
            showFilterFragmnet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = FilterActivity.newIntent(getActivity(), catergoryId);
                    startActivity(intent);
                }
            });

            RetrofitClientInstance.getRetrofitInstance().create(Api.class)
                    .sortCategoriesItemsByCategoryItem(catergoryId, "popularity","desc").enqueue(new Callback<List<Product>>() {
                @Override
                public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                    if (response.isSuccessful()) {
                        currentProducts = response.body();
                        inSortAdapter = new CustomerAdapter(currentProducts);
                        inSortRec.setAdapter(inSortAdapter);
                    }
                }

                @Override
                public void onFailure(Call<List<Product>> call, Throwable t) {

                    HandleThings.isOnline(getActivity(), getFragmentManager());
                }
            });
        } else {
            showSortByDialog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ReulstFragment datePickerFragment = new ReulstFragment();

//                datePickerFragment.show(getFragmentManager(), "MyDialog");


                    DialogFragment dialogFrag = new SortChoserDialogFragment();
// This is the requestCode that you are sending.
                    dialogFrag.setTargetFragment(ReulstFragment.this,SORT_BY_REC_SEARCHED_CODE );
// This is the tag, "dialog" being sent.
                    dialogFrag.show(getFragmentManager(), "dialog");
                }
            });
            showFilterFragmnet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = FilterActivity.newIntent(getActivity(), catergoryId);
                    startActivity(intent);
                }
            });

            RetrofitClientInstance.getRetrofitInstance().create(Api.class)
                    .searchWithName(searchString).enqueue(new Callback<List<Product>>() {
                @Override
                public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                    if (response.isSuccessful()) {
                        currentProducts = response.body();
                        inSortAdapter = new CustomerAdapter(currentProducts);
                        inSortRec.setAdapter(inSortAdapter);


                    }
                }

                @Override
                public void onFailure(Call<List<Product>> call, Throwable t) {

                    HandleThings.isOnline(getActivity(), getFragmentManager());
                }
            });


        }

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
            View view = inflater.inflate(R.layout.in_sort_rec_item, parent, false);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Stuff to do, dependent on requestCode and resultCode
        if (requestCode == SORT_BY_REC_CODE) { // 1 is an arbitrary number, can be any int
            // This is the return result of your DialogFragment

            int res = (int) data.getSerializableExtra(POSITOIN);
            String sortBy = "";
            String orderBy = "";
            String farsiSort="";

            switch (res) {
                case 1:
                    sortBy = "popularity";

                    orderBy = "desc";
                    farsiSort="پر فروش ترین";
                    break;

                case 2:
                    sortBy = "price";
                    orderBy = "asc";
                    farsiSort="قیمت کم به زیاد";
                    break;
                case 3:
                    sortBy = "price";
                    orderBy = "desc";
                    farsiSort="قیمت زیاد به کم";
                    break;
                case 4:
                    sortBy = "date";
                    orderBy = "desc";
                    farsiSort="جدید ترین";

                    break;

            }
            sortByWhatTv.setText(farsiSort);


            RetrofitClientInstance.getRetrofitInstance().create(Api.class)
                    .sortCategoriesItemsByCategoryItem(catergoryId, sortBy,orderBy).enqueue(new Callback<List<Product>>() {
                @Override
                public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                    if (response.isSuccessful()) {
                        currentProducts = response.body();
                        inSortAdapter = new CustomerAdapter(currentProducts);
                        inSortRec.setAdapter(inSortAdapter);
                        inSortAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<List<Product>> call, Throwable t) {

                    HandleThings.isOnline(getActivity(), getFragmentManager());
                }
            });

        }
        if (requestCode==SORT_BY_REC_SEARCHED_CODE){
            int res = (int) data.getSerializableExtra(POSITOIN);
            String farsiSort="";
            String sortBy = "";
            String orderBy = "";

            switch (res) {
                case 1:
                    sortBy = "popularity";
                    farsiSort="پر فروش ترین";
                    orderBy = "desc";
                    break;

                case 2:
                    sortBy = "price";
                    orderBy = "asc";
                    farsiSort="قیمت کم به زیاد";

                    break;
                case 3:
                    sortBy = "price";
                    orderBy = "desc";
                    farsiSort="قیمت زیاد به کم";
                    break;
                case 4:
                    sortBy = "date";
                    orderBy = "desc";
                    farsiSort="جدید ترین";
                    break;

            }
            sortByWhatTv.setText(farsiSort);
            RetrofitClientInstance.getRetrofitInstance().create(Api.class)
                    .sortCategoriesItemsByCategoryItemSearched( sortBy,orderBy,searchString).enqueue(new Callback<List<Product>>() {
                @Override
                public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                    if (response.isSuccessful()) {
                        currentProducts = response.body();
                        inSortAdapter = new CustomerAdapter(currentProducts);
                        inSortRec.setAdapter(inSortAdapter);
                        inSortAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<List<Product>> call, Throwable t) {

                    HandleThings.isOnline(getActivity(), getFragmentManager());
                }
            });

        }

        }
    }

