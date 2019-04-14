package com.example.shayanmoradi.ezshop.filter;


import android.annotation.SuppressLint;
import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.shayanmoradi.ezshop.Model.Attribute;
import com.example.shayanmoradi.ezshop.Model.Product;
import com.example.shayanmoradi.ezshop.Model.Terms;
import com.example.shayanmoradi.ezshop.R;
import com.example.shayanmoradi.ezshop.customerhandler.HandleThings;
import com.example.shayanmoradi.ezshop.network.Api;
import com.example.shayanmoradi.ezshop.network.RetrofitClientInstance;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.shayanmoradi.ezshop.result.ReulstFragment.CATEGORY_ID;

/**
 * A simple {@link Fragment} subclass.
 */
public class FilterFragment extends androidx.fragment.app.Fragment {
    List<Product> inComingProducts;
    List<Attribute> forSearch;
    private RecyclerView attributePickerRec;
    private CustomerAdapter attributetAdapter;
    private RecyclerView optionPickerRec;
    private CustomerAdapter2 optionAdapter;
    private List<Product> currentProducts;
    List<String> attributeNameList = new ArrayList<>();
    List<Integer> attributeIdeList = new ArrayList<>();
    List<Terms> termsList;
    List<String> optionStings = new ArrayList<>();
    int categoryId;
    View beforeitemView;
    TextView beforeText;


    public static FilterFragment newInstance(int catergoryId) {

        Bundle args = new Bundle();

        args.putInt(CATEGORY_ID, catergoryId);

        FilterFragment fragment = new FilterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public FilterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        categoryId = getArguments().getInt(CATEGORY_ID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_filter, container, false);

        attributePickerRec = view.findViewById(R.id.attrebiute_selector_rec);
        optionPickerRec = view.findViewById(R.id.options_rec);

        attributePickerRec.setLayoutManager(new LinearLayoutManager(getContext()));
        optionPickerRec.setLayoutManager(new LinearLayoutManager(getContext()));
        RetrofitClientInstance.getRetrofitInstance().create(Api.class)
                .sortCategoriesItemsByCategoryItem(categoryId, "popularity", "asc").enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()) {
                    currentProducts = response.body();
                    int max = 0;
                    int holder = 0;
                    for (int i = 0; i < currentProducts.size(); i++) {
                        if (currentProducts.get(i).getAttributes().size() > max) {
                            max = currentProducts.get(i).getAttributes().size();
                            holder = i;
                        }
                    }

                    for (int i = 0; i < currentProducts.get(holder).getAttributes().size(); i++) {
                        attributeNameList.add(currentProducts.get(holder).getAttributes().get(i).getName());
                        attributeIdeList.add(currentProducts.get(holder).getAttributes().get(i).getId());
                    }

                }
                attributetAdapter = new CustomerAdapter(attributeNameList);
                attributePickerRec.setAdapter(attributetAdapter);


            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

                HandleThings.isOnline(getActivity(), getFragmentManager());
            }
        });


        return view;
    }

    private class CustoemrHolder extends RecyclerView.ViewHolder {

        private TextView name;
//        private TextView price;
//
//        private ImageView image;

        private String product;
        TextView nameBefore;
        private LinearLayout attributeBackgound;

        public CustoemrHolder(@NonNull final View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.attrebiute_name);
            attributeBackgound = itemView.findViewById(R.id.attrubite_back_gound);
//            price = itemView.findViewById(R.id.item_price);
//            image = itemView.findViewById(R.id.item_image);


            itemView.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("ResourceAsColor")
                @Override
                public void onClick(View v) {
                    if (beforeitemView != null) {
                        beforeitemView.setBackgroundColor(Color.parseColor("#353535"));
                        beforeText.setTextColor(Color.WHITE);


                    }
                    beforeitemView = itemView;
                    beforeText=name;

                    optionStings = new ArrayList<>();
                    attributeBackgound.setBackgroundColor(Color.WHITE);
                    name.setTextColor(Color.BLACK);
                    //

                    RetrofitClientInstance.getRetrofitInstance().create(Api.class)
                            .getTerms(attributeIdeList.get(getLayoutPosition())).enqueue(new Callback<List<Terms>>() {
                        @Override
                        public void onResponse(Call<List<Terms>> call, Response<List<Terms>> response) {
                            termsList = response.body();
                            for (int i = 0; i < termsList.size(); i++) {
                                optionStings.add(termsList.get(i).getName());

                            }
                            optionAdapter = new CustomerAdapter2(optionStings);
                            optionPickerRec.setAdapter(optionAdapter);
                        }

                        @Override
                        public void onFailure(Call<List<Terms>> call, Throwable t) {

                        }
                    });


                }
            });
        }

        public void bind(String mproduct) {
            product = mproduct;

            name.setText(product);


//            price.setText(product.getmPrice());
//
//
//            if (product.getImages() != null && product.getImages().size() > 0)
//                Picasso.get().load(product.getImages().get(0).getPath()).into(image);
            //  customerAge.setText();
            //set age


        }
    }

    class CustomerAdapter extends RecyclerView.Adapter<CustoemrHolder> {

        private List<String> mProduct;

        public CustomerAdapter(List<String> cars) {
            mProduct = cars;
        }

        public void setCArs(List<String> cars) {
            mProduct = cars;
        }

        @NonNull
        @Override
        public CustoemrHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.filter_attrebiute_item_rec, parent, false);
            CustoemrHolder custoemrHolder = new CustoemrHolder(view);
            return custoemrHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull CustoemrHolder holder, int position) {
            String product = mProduct.get(position);

            holder.bind(product);
        }

        @Override
        public int getItemCount() {
            return mProduct.size();
        }
    }

    private class CustoemrHolder2 extends RecyclerView.ViewHolder {

        private TextView name;
//        private TextView price;
//
//        private ImageView image;

        private String product;


        public CustoemrHolder2(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.attrebiute_name);
//            price = itemView.findViewById(R.id.item_price);
//            image = itemView.findViewById(R.id.item_image);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                }
            });
        }

        public void bind(String mproduct) {
            product = mproduct;

            name.setText(product);

//            price.setText(product.getmPrice());
//
//
//            if (product.getImages() != null && product.getImages().size() > 0)
//                Picasso.get().load(product.getImages().get(0).getPath()).into(image);
            //  customerAge.setText();
            //set age


        }
    }

    class CustomerAdapter2 extends RecyclerView.Adapter<CustoemrHolder2> {

        private List<String> mProduct;

        public CustomerAdapter2(List<String> cars) {
            mProduct = cars;
        }

        public void setCArs(List<String> cars) {
            mProduct = cars;
        }

        @NonNull
        @Override
        public CustoemrHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.filter_option_item_rec, parent, false);
            CustoemrHolder2 custoemrHolder2 = new CustoemrHolder2(view);
            return custoemrHolder2;
        }

        @Override
        public void onBindViewHolder(@NonNull CustoemrHolder2 holder, int position) {
            String product = mProduct.get(position);

            holder.bind(product);
        }

        @Override
        public int getItemCount() {
            return mProduct.size();
        }
    }


}
