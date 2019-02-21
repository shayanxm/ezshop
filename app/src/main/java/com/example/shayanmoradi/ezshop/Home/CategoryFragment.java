package com.example.shayanmoradi.ezshop.Home;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.shayanmoradi.ezshop.DissConectedFragment;
import com.example.shayanmoradi.ezshop.Model.Category;
import com.example.shayanmoradi.ezshop.Model.Repository;
import com.example.shayanmoradi.ezshop.R;
import com.example.shayanmoradi.ezshop.itemsofcategory.ItemsOfActivity;
import com.example.shayanmoradi.ezshop.itemsofcategory.ItemsOfCategoryFragment;
import com.example.shayanmoradi.ezshop.network.Api;
import com.example.shayanmoradi.ezshop.network.RetrofitClientInstance;
import com.example.shayanmoradi.ezshop.subcategory.SubCategoryActivity;
import com.example.shayanmoradi.ezshop.subcategory.SubCategoryFragment;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
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
    TabLayout tabLayout;
    ViewPager viewPager;
    List<Category> partents;
    private ViewPagerAdapter adapter;

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
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        // recyclerView = view.findViewById(R.id.category_rec);
        lottieAnimationView = view.findViewById(R.id.animation_view2);
//        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        RetrofitClientInstance.getRetrofitInstance().create(Api.class)
                .getCatrgories().enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                List<Category> categories = response.body();
                //  Toast.makeText(getContext(), "start", Toast.LENGTH_SHORT).show();
                Repository.getInstance().setAllCategories(categories);
                partents = Category.filterParents(categories);
                for (int i = 0; i < partents.size(); i++) {
                    if (partents.get(i).getId() == 15) {

                        ItemsOfCategoryFragment tabLayoutFragment = ItemsOfCategoryFragment.newInstance(partents.get(i).getId());

                        adapter.addFrag(tabLayoutFragment, partents.get(i).getName());
                    } else {
                        SubCategoryFragment tabLayoutFragment = SubCategoryFragment.newInstance(partents.get(i).getId());

                        adapter.addFrag(tabLayoutFragment, partents.get(i).getName());
                    }
                }
                viewPager.setAdapter(adapter);

                viewPager.setCurrentItem(partents.size());
                // Toast.makeText(getContext(), "end", Toast.LENGTH_SHORT).show();


//                customerAdapter = new CustomerAdapter(partents);
//                recyclerView.setAdapter(customerAdapter);
//                lottieAnimationView.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                isOnline(getContext());
            }
        });

        tabLayout = view.findViewById(R.id.tabs);
        viewPager = view.findViewById(R.id.viewPager);
        tabLayout.setupWithViewPager(viewPager);
        adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());

//        SubCategoryFragment tabLayoutFragment = SubCategoryFragment.newInstance(87);
//
//        adapter.addFrag(tabLayoutFragment, "دسته بندی");
//        SubCategoryFragment underHomeFragment = SubCategoryFragment.newInstance(87);
//        adapter.addFrag(underHomeFragment, "خانه");
////
//
//
//        SubCategoryFragment underHomeFragment2 = SubCategoryFragment.newInstance(87);
//        adapter.addFrag(underHomeFragment2, "خانه");

//        adapter.addFrag(tabLayoutFragment, "دسته بندی");
//        SubCategoryFragment underHomeFragment3 = SubCategoryFragment.newInstance(87);
//        adapter.addFrag(underHomeFragment3, "خانه");


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
                    if (category.getId() == 15) {
                        Intent intent = ItemsOfActivity.newIntent(getActivity(), category.getId());
                        startActivity(intent);
                    } else {
                        Toast.makeText(getActivity(), "this is " + category.getId(), Toast.LENGTH_SHORT).show();
                        Intent intent = SubCategoryActivity.newIntent(getActivity(), category.getId());
                        startActivity(intent);
                    }

                }
            });
        }

        public void bind(Category category) {
            this.category = category;

            name.setText(this.category.getName());


            if (this.category.getImages() != null)
                Picasso.get().load(this.category.getImages().getPath()).into(image);
            else image.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.category));
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

    class ViewPagerAdapter extends FragmentStatePagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }


        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

    }

}



