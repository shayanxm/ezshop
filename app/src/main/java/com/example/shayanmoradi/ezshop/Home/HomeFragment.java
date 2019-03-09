package com.example.shayanmoradi.ezshop.Home;


import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.shayanmoradi.ezshop.Model.Product;
import com.example.shayanmoradi.ezshop.Model.Repository;
import com.example.shayanmoradi.ezshop.R;
import com.example.shayanmoradi.ezshop.bag.BagActivity;
import com.example.shayanmoradi.ezshop.category.CategoryActivity;
import com.example.shayanmoradi.ezshop.itemDetail.ItemDetailActivity;
import com.example.shayanmoradi.ezshop.search.SearchActivity;
import com.google.android.material.chip.Chip;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ss.com.bannerslider.Slider;
import ss.com.bannerslider.adapters.SliderAdapter;
import ss.com.bannerslider.viewholder.ImageSlideViewHolder;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements NavigationView.OnNavigationItemSelectedListener {
    private RecyclerView newestRec;
    private ImageButton goToBagBtn;
    private DrawerLayout drawer;
    private CustomerAdapter customerAdapter;
    private RecyclerView topSalesRec;
    private CustomerAdapter newestAdapter;
    private RecyclerView topRatedsRec;
    private CustomerAdapter topRatedtAdapter;
    LottieAnimationView lottieAnimationView;
    List<Product> topHits;
    List<Product> newest;
    List<Product> topRated;
    Chip chip;
    Toolbar toolbar;
    Slider slider;
    private ImageButton test;
    NavigationView navigationView;
    private ImageButton searchBtn;

    public static HomeFragment newInstance() {

        Bundle args = new Bundle();

        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        topHits = Repository.getInstance().getTopHits();
        newest = Repository.getInstance().getNewest();
        topRated = Repository.getInstance().getTopRated();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view = inflater.inflate(R.layout.fragment_home, container, false);
        newestRec = view.findViewById(R.id.news_rec);
        setHasOptionsMenu(true);
goToBagBtn=view.findViewById(R.id.bag_btn);
        lottieAnimationView = view.findViewById(R.id.animation_view);
        topSalesRec = view.findViewById(R.id.top_sales_rec);
        topRatedsRec = view.findViewById(R.id.top_rated_rec);
        topRatedsRec.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayout.HORIZONTAL, false));
        topSalesRec.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayout.HORIZONTAL, false));
        newestRec.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayout.HORIZONTAL, false));
        LinearLayout linearLayout = view.findViewById(R.id.lottie_continer);
        slider = view.findViewById(R.id.banner_slider2);
searchBtn=view.findViewById(R.id.searching_btn);
        toolbar = view.findViewById(R.id.toolbar);

        navigationView = view.findViewById(R.id.nav_view);
        drawer = view.findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                getActivity(), drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (drawer.isDrawerOpen(Gravity.LEFT)) {
                    drawer.closeDrawer(Gravity.RIGHT);
                } else {
                    drawer.openDrawer(Gravity.RIGHT);
                }
            }
        });

        navigationView.setNavigationItemSelectedListener(this);
        slider.setAdapter(new MainSliderAdapter());

        newestAdapter = new CustomerAdapter(newest);
        newestRec.setAdapter(newestAdapter);

        topRatedtAdapter = new CustomerAdapter(topRated);
        topRatedsRec.setAdapter(topRatedtAdapter);

        customerAdapter = new CustomerAdapter(topHits);
        topSalesRec.setAdapter(customerAdapter);



        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= SearchActivity.newIntent(getActivity());
                startActivity(intent);
            }
        });

        goToBagBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getActivity(),BagActivity.class);
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


    public class MainSliderAdapter extends SliderAdapter {
        int itemCount = 4;

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

                    viewHolder.bindImageSlide(R.drawable.ini4);

                    break;
                case 1:

                    viewHolder.bindImageSlide(R.drawable.ini5);
                    break;
                case 2:

                    viewHolder.bindImageSlide(R.drawable.ini4);
                    break;
                case 3:

                    viewHolder.bindImageSlide(R.drawable.ini5);
                    break;
                case 4:

                    viewHolder.bindImageSlide(R.drawable.digi);
                    break;


            }
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        getActivity().getMenuInflater().inflate(R.menu.top_app_menu, menu);
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_categories) {

            Intent intent = new Intent(getActivity(), CategoryActivity.class);
            startActivity(intent);
        }

        return true;
    }

}
