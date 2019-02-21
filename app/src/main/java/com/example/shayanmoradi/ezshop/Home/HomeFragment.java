package com.example.shayanmoradi.ezshop.Home;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
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
import com.example.shayanmoradi.ezshop.DissConectedFragment;
import com.example.shayanmoradi.ezshop.Model.Product;
import com.example.shayanmoradi.ezshop.R;
import com.example.shayanmoradi.ezshop.itemDetail.ItemDetailActivity;
import com.example.shayanmoradi.ezshop.network.Api;
import com.example.shayanmoradi.ezshop.network.RetrofitClientInstance;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ss.com.bannerslider.Slider;
import ss.com.bannerslider.adapters.SliderAdapter;
import ss.com.bannerslider.viewholder.ImageSlideViewHolder;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements NavigationView.OnNavigationItemSelectedListener{
    private RecyclerView newestRec;
    private DrawerLayout drawer;
    private CustomerAdapter customerAdapter;
    private RecyclerView topSalesRec;
    private CustomerAdapter newestAdapter;
    private RecyclerView topRatedsRec;
    private CustomerAdapter topRatedtAdapter;
    LottieAnimationView lottieAnimationView;
    Slider slider;
    private ImageButton test;
NavigationView navigationView;
    public static UnderHomeFragment newInstance() {

        Bundle args = new Bundle();

        UnderHomeFragment fragment = new UnderHomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view = inflater.inflate(R.layout.fragment_home, container, false);
        newestRec = view.findViewById(R.id.news_rec);
        setHasOptionsMenu(true);
        lottieAnimationView = view.findViewById(R.id.animation_view);
        topSalesRec = view.findViewById(R.id.top_sales_rec);
        topRatedsRec = view.findViewById(R.id.top_rated_rec);
        topRatedsRec.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayout.HORIZONTAL, false));
        topSalesRec.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayout.HORIZONTAL, false));
        newestRec.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayout.HORIZONTAL, false));
        LinearLayout linearLayout = view.findViewById(R.id.lottie_continer);
        test=view.findViewById(R.id.imageButton);
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getActivity(),CategoryActivity.class);
               // startActivity(intent);
            }
        });
        // Add ImageView to LinearLayout


//        if (!isOnline()) {
        //Toast.makeText(getContext(), "disconect", Toast.LENGTH_SHORT).show();
//        } e

        //  }
//        RetrofitClientInstance.getRetrofitInstance().create(Api.class)
//                .getCommitsByName("total_sale").enqueue(new Callback<List<Product>>() {
//            @Override
//            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
//                List<Product> productList = response.body();
//                customerAdapter = new CustomerAdapter(productList);
//                newestRec.setAdapter(customerAdapter);
//            }
//
//
//            @Override
//            public void onFailure(Call<List<Product>> call, Throwable t) {
//
//            }
//        });
        navigationView = view.findViewById(R.id.nav_view);
        drawer =  view.findViewById(R.id.drawer_layout);
        slider=view.findViewById(R.id.banner_slider2);


        navigationView.setNavigationItemSelectedListener(this);
        slider.setAdapter(new MainSliderAdapter());

//        RetrofitClientInstance.getRetrofitInstance().create(Api.class)
//                .getAllProducts().enqueue(new Callback<List<Product>>() {
//            @Override
//            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
//                Repository.getInstance().setAllProducts(response.body());
//                //topRatedtAdapter = new CustomerAdapter(Product.getTopHits());
//               // topRatedsRec.setAdapter(topRatedtAdapter);
//                ///
//             //   customerAdapter = new CustomerAdapter(Product.getTopsales());
//            //    topSalesRec.setAdapter(customerAdapter);
//                Toast.makeText(getContext(), "done", Toast.LENGTH_SHORT).show();
//                lottieAnimationView.setVisibility(View.GONE);
//
//            }
//
//
//            @Override
//            public void onFailure(Call<List<Product>> call, Throwable t) {
//                isOnline(getContext());
//
//            }
//        });
        RetrofitClientInstance.getRetrofitInstance().create(Api.class)
                .getNewest().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                List<Product> productList = response.body();
                     newestAdapter = new CustomerAdapter(productList);
                      newestRec.setAdapter(newestAdapter);
                Toast.makeText(getContext(), "done", Toast.LENGTH_SHORT).show();
                lottieAnimationView.setVisibility(View.GONE);
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
//                        newestAdapter = new CustomerAdapter(productList);
//                        newestRec.setAdapter(newestAdapter);
                    }

                    @Override
                    public void onFailure(Call<List<Product>> call, Throwable t) {

                    }
                });
        RetrofitClientInstance.getRetrofitInstance().create(Api.class)
                .getCommitsByName("rating").enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                List<Product> productList = response.body();
                topRatedtAdapter = new CustomerAdapter(productList);
                topRatedsRec.setAdapter(topRatedtAdapter);

                //topRatedtAdapter = new CustomerAdapter(Product.getTopHits());
                // topRatedsRec.setAdapter(topRatedtAdapter);
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

            }
        });
        RetrofitClientInstance.getRetrofitInstance().create(Api.class)
                .getCommitsByName("popularity").enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                List<Product> productList = response.body();
                customerAdapter = new CustomerAdapter(productList);
                topSalesRec.setAdapter(customerAdapter);

                //topRatedtAdapter = new CustomerAdapter(Product.getTopHits());
                // topRatedsRec.setAdapter(topRatedtAdapter);
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

            }
        });
//        RetrofitClientInstance.getRetrofitInstance().create(Api.class)
//                .getCommitsByName("average_rating").enqueue(new Callback<List<Product>>() {
//            @Override
//            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
//                List<Product> productList = response.body();
//                topRatedtAdapter = new CustomerAdapter(productList);
//                topRatedsRec.setAdapter(topRatedtAdapter);
//                lottieAnimationView.setVisibility(View.GONE);
//
//            }
//
//            @Override
//            public void onFailure(Call<List<Product>> call, Throwable t) {
//
//            }
//        });

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

            price.setText(product.getmPrice() );


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

    //    public boolean isOnline() {
//        ConnectivityManager conMgr = (ConnectivityManager) getActivity().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
//
//        if (netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()) {
//            Toast.makeText(getContext(), "No Internet connection!", Toast.LENGTH_LONG).show();
//            return false;
//        }
//        return true;
//    }
    public boolean isOnline(Context context) {
        ConnectivityManager conMgr = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

        if (netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()) {
            //  Toast.makeText(context, "No Internet connection!", Toast.LENGTH_LONG).show();
            DissConectedFragment datePickerFragment = new DissConectedFragment();
//            datePickerFragment.setTargetFragment(Dis.this,
//                    0);
            datePickerFragment.show(getFragmentManager(), "MyDialog");
            return false;

        }
        return true;
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



//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        int id = item.getItemId();
//
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
   // }



    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_categories) {

            Intent intent = new Intent(getActivity(), CategoryActivity.class);
            startActivity(intent);
        }
//        } else if (id == R.id.nav_gallery) {
//
//        } else if (id == R.id.nav_slideshow) {
//
//        } else if (id == R.id.nav_manage) {
//
//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }

         //   drawer.closeDrawer(GravityCompat.START);
            return true;
        }

    }
