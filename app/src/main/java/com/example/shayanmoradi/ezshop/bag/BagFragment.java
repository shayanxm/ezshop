package com.example.shayanmoradi.ezshop.bag;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shayanmoradi.ezshop.R;
import com.example.shayanmoradi.ezshop.database.SavedProduct;
import com.example.shayanmoradi.ezshop.database.SavedProductsManger;
import com.example.shayanmoradi.ezshop.enterinfo.EnterInfoActivity;
import com.example.shayanmoradi.ezshop.itemDetail.ItemDetailActivity;
import com.example.shayanmoradi.ezshop.order.OrderInfoActivity;
import com.example.shayanmoradi.ezshop.prefs.QueryPreferences;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


/**
 * A simple {@link Fragment} subclass.
 */
public class BagFragment extends androidx.fragment.app.Fragment {
    private RecyclerView bagsRec;
    private CustomerAdapter bagtAdapter;
    private TextView fullBagPriceTv;
    private ConstraintLayout confrimBag;


    public static BagFragment newInstance() {

        Bundle args = new Bundle();

        BagFragment fragment = new BagFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public BagFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        doThings();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bag, container, false);
        fullBagPriceTv = view.findViewById(R.id.full_bag_price_tv);

        bagsRec = view.findViewById(R.id.bag_rec);
        confrimBag = view.findViewById(R.id.confrim_bag);


        doThings();


        return view;
    }

    private void doThings() {
        bagsRec.setLayoutManager(new LinearLayoutManager(getContext()));
        List<SavedProduct> savedProductList = SavedProductsManger.getInstance(getContext()).getBag();
        bagtAdapter = new CustomerAdapter(savedProductList);
        bagsRec.setAdapter(bagtAdapter);

        fullBagPriceTv.setText(calcFullPrice() + "");
        confrimBag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fullBagPriceTv.getText().equals("0")){
                    Toast.makeText(getActivity(), "سبد خرید خالی است", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!QueryPreferences.getIsLogin(getActivity())) {
                    Intent intent = EnterInfoActivity.newIntent(getActivity());
                    startActivity(intent);
                }else if (QueryPreferences.getIsLogin(getActivity())){
                    Intent intent = OrderInfoActivity.newIntent(getActivity());
                    startActivity(intent);
                }
            }
        });
    }

    private class CustoemrHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private TextView price;

        private ImageView image;
        private TextView fullItemPrice;
        private TextView deleteItem;
        private SavedProduct product;
        private Spinner countSpiner;

        public CustoemrHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.titile_bag);
            price = itemView.findViewById(R.id.bag_price);
            image = itemView.findViewById(R.id.bag_image);
            countSpiner = itemView.findViewById(R.id.count_item_spiner);
            deleteItem = itemView.findViewById(R.id.delete_item);
            fullItemPrice = itemView.findViewById(R.id.full_item_price);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //  Toast.makeText(getActivity(), "this is " + product.getProductId(), Toast.LENGTH_SHORT).show();
                    Intent intent = ItemDetailActivity.newIntent(getActivity(), product.getProductId());
                    startActivity(intent);

                }
            });
        }

        public void bind(SavedProduct mproduct) {
            product = mproduct;

            name.setText(product.getProductName());

            price.setText(product.getProductPrice());
            fullItemPrice.setText(Integer.valueOf(product.getProductPrice()) * product.getCount() + "");

            if (product.getProductImagePath() != null)
                Picasso.get().load(product.getProductImagePath()).into(image);
            //customerAge.setText();
            //set age
            Log.e("test",product.getCount()+"");

            deleteItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SavedProductsManger.getInstance(getContext()).delete(product);
                    List<SavedProduct> savedProductList = SavedProductsManger.getInstance(getContext()).getBag();
                    bagtAdapter = new CustomerAdapter(savedProductList);
                    bagsRec.setAdapter(bagtAdapter);
                    SavedProductsManger.getInstance(getContext()).update(product);
                    fullBagPriceTv.setText(calcFullPrice() + "");


                }
            });


            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                    R.array.spiner_counts, R.layout.support_simple_spinner_dropdown_item);
            adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

            countSpiner.setAdapter(adapter);
            countSpiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    // String posi=parent.getItemAtPosition(position)
                   // Toast.makeText(getContext(), +position + "", Toast.LENGTH_SHORT).show();
                    product.setCount(position + 1);
                    product.setCount(position + 1);



                    SavedProductsManger.getInstance(getContext()).update(product);
                    fullBagPriceTv.setText(calcFullPrice() + "");
                    fullItemPrice.setText(Integer.valueOf(product.getProductPrice()) * product.getCount() + "");

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


        }
    }

    class CustomerAdapter extends RecyclerView.Adapter<CustoemrHolder> {

        private List<SavedProduct> mProduct;

        public CustomerAdapter(List<SavedProduct> cars) {
            mProduct = cars;
        }

        public void setCArs(List<SavedProduct> cars) {
            mProduct = cars;
        }

        @NonNull
        @Override
        public CustoemrHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.item_in_bag_for_rec, parent, false);
            CustoemrHolder custoemrHolder = new CustoemrHolder(view);
            return custoemrHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull CustoemrHolder holder, int position) {
            SavedProduct product = mProduct.get(position);

            holder.bind(product);
        }

        @Override
        public int getItemCount() {
            return mProduct.size();
        }
    }

    private int calcFullPrice() {
        List<SavedProduct> savedProductList = SavedProductsManger.getInstance(getContext()).getBag();
        int result = 0;
        for (int i = 0; i < savedProductList.size(); i++) {

            result += Integer.valueOf(savedProductList.get(i).getProductPrice()) * savedProductList.get(i).getCount();

        }
        return result;
    }

}
