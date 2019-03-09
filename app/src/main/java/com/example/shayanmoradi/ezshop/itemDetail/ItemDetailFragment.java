package com.example.shayanmoradi.ezshop.itemDetail;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.shayanmoradi.ezshop.Model.Attribute;
import com.example.shayanmoradi.ezshop.Model.Product;
import com.example.shayanmoradi.ezshop.R;
import com.example.shayanmoradi.ezshop.customerhandler.HandleThings;
import com.example.shayanmoradi.ezshop.database.SavedProduct;
import com.example.shayanmoradi.ezshop.database.SavedProductsManger;
import com.example.shayanmoradi.ezshop.network.Api;
import com.example.shayanmoradi.ezshop.network.RetrofitClientInstance;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ss.com.bannerslider.Slider;
import ss.com.bannerslider.adapters.SliderAdapter;
import ss.com.bannerslider.viewholder.ImageSlideViewHolder;


/**
 * A simple {@link Fragment} subclass.
 */
public class ItemDetailFragment extends Fragment {
    private static final String PRODUCT_ID = "com.example.shayanmoradi.ezshop.itemDetail.productid";

    Product product;
    private Button addToBagBtn;
    private TextView productName;
    LottieAnimationView lottieAnimationView;
    private TextView productPrice;
    private ImageView productImage;
    private TextView descTv;
    Slider slider;
    int photoCounter;
    private LinearLayout attrebiutContiner;
    private LinearLayout optionContiner;
    private TextView productSlug;
    List<Attribute> attributes;
    private ImageButton backBtn;
    LinearLayout linearLayout;
    int productId;

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


        productId = getArguments().getInt(PRODUCT_ID);
        RetrofitClientInstance.getRetrofitInstance().create(Api.class)
                .getProductById(productId).enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                product = response.body();
                setViewUpWithIncomingRes();
                lottieAnimationView.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {

                HandleThings.isOnline(getContext(), getFragmentManager());

            }
        });


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_item_detail, container, false);
        descTv = view.findViewById(R.id.desc_continer);
        attrebiutContiner = view.findViewById(R.id.attrubite_coniner);
        lottieAnimationView = view.findViewById(R.id.animation_view3);
        addToBagBtn = view.findViewById(R.id.add_to_bag_btn);
        backBtn = view.findViewById(R.id.back_btn);
        linearLayout = view.findViewById(R.id.testingg);
        optionContiner = view.findViewById(R.id.option_coniner);
        //createOptionsq();
        productName = view.findViewById(R.id.item_detail_titile);
        productSlug = view.findViewById(R.id.item_detail_slug);
        lottieAnimationView.setAnimation("loading.json");
        slider = view.findViewById(R.id.banner_slider1);
        addToBagBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SavedProduct savedProduct = new SavedProduct();
                savedProduct.setProductId(productId);
                SavedProductsManger.getInstance(getActivity()).addToBag(savedProduct);
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });


        return view;

    }

    private void setViewUpWithIncomingRes() {
        productName.setText(product.getmName());
        productSlug.setText(product.getmPrice() + "  تومان ");

        photoCounter = product.getImages().size();
        MainSliderAdapter mainSliderAdapter = new MainSliderAdapter();
        mainSliderAdapter.setItemCount(photoCounter);
        attributes = product.getAttributes();
        slider.setAdapter(mainSliderAdapter);
        crateAttrebutes();
        createOptions();

        descTv.setText(product.getmCompliteDescription());
    }

    private void createOptions() {

        for (int i = 0; i < attributes.size(); i++) {
            TextView text = new TextView(getActivity());
            text.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            String optionString = "";
            for (int j = 0; j < attributes.get(i).getOptions().size(); j++) {
                optionString += attributes.get(i).getOptions().get(j);
                if (j >= 0 && j + 1 < attributes.get(i).getOptions().size())
                    optionString += " , ";
            }
            text.setText(optionString);
            text.setPadding(8, 8, 20, 8);
            text.setGravity(View.TEXT_ALIGNMENT_CENTER);
            optionContiner.addView(text);
        }
    }


    private void crateAttrebutes() {
        for (int i = 0; i < attributes.size(); i++) {
            TextView text = new TextView(getActivity());
            text.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            String optionString = "";

            text.setText(attributes.get(i).getName() + ":");
            text.setPadding(8, 8, 20, 8);
            attrebiutContiner.addView(text);
        }

    }

    public class MainSliderAdapter extends SliderAdapter {
        int itemCount = 3;

        public void setItemCount(int itemCount) {
            this.itemCount = itemCount;
        }

        @Override
        public int getItemCount() {
            return itemCount;

        }

        @Override
        public void onBindImageSlide(int position, ImageSlideViewHolder viewHolder) {
            switch (position) {
                case 0:
                    if (product.getImages().size() > 0)
                        viewHolder.bindImageSlide(product.getImages().get(0).getPath());
                    break;
                case 1:
                    if (product.getImages().size() > 1)
                        viewHolder.bindImageSlide(product.getImages().get(1).getPath());
                    break;
                case 2:
                    if (product.getImages().size() > 2)
                        viewHolder.bindImageSlide(product.getImages().get(2).getPath());
                    break;
                case 3:
                    if (product.getImages().size() > 3)
                        viewHolder.bindImageSlide(product.getImages().get(3).getPath());
                    break;
                case 4:
                    if (product.getImages().size() > 4)
                        viewHolder.bindImageSlide(product.getImages().get(4).getPath());
                    break;
                case 5:
                    if (product.getImages().size() > 5)
                        viewHolder.bindImageSlide(product.getImages().get(5).getPath());
                    break;
                case 6:
                    if (product.getImages().size() > 6)
                        viewHolder.bindImageSlide(product.getImages().get(6).getPath());
                    break;
                case 7:
                    if (product.getImages().size() > 7)
                        viewHolder.bindImageSlide(product.getImages().get(7).getPath());
                    break;
            }
        }
    }


}
