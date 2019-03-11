package com.example.shayanmoradi.ezshop.bag;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.shayanmoradi.ezshop.R;
import com.example.shayanmoradi.ezshop.database.SavedProduct;
import com.example.shayanmoradi.ezshop.database.SavedProductsManger;
import com.example.shayanmoradi.ezshop.itemDetail.ItemDetailActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import it.sephiroth.android.library.numberpicker.NumberPicker;


/**
 * A simple {@link Fragment} subclass.
 */
public class BagFragment extends androidx.fragment.app.Fragment {
    private RecyclerView bagsRec;
    private CustomerAdapter bagtAdapter;
    private TextView fullBagPriceTv;



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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_bag, container, false);
        fullBagPriceTv=view.findViewById(R.id.full_bag_price_tv);

        bagsRec=view.findViewById(R.id.bag_rec);


        bagsRec.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayout.VERTICAL, false));
        List<SavedProduct>savedProductList= SavedProductsManger.getInstance(getContext()).getBag();
        bagtAdapter = new CustomerAdapter(savedProductList);
        bagsRec.setAdapter(bagtAdapter);

        fullBagPriceTv.setText(calcFullPrice()+"");








        return view;
    }
    private class CustoemrHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private TextView price;

        private ImageView image;

        private SavedProduct product;


        public CustoemrHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.titile_bag);
            price = itemView.findViewById(R.id.bag_price);
            image = itemView.findViewById(R.id.bag_image);


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


            if (product.getProductImagePath() != null )
                Picasso.get().load(product.getProductImagePath()).into(image);
              //customerAge.setText();
            //set age


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
private int calcFullPrice(){
    List<SavedProduct>savedProductList= SavedProductsManger.getInstance(getContext()).getBag();
    int result=0;
    for (int i=0;i<savedProductList.size();i++){

        result+=Integer.valueOf(savedProductList.get(i).getProductPrice());

    }
    return result;
}

}
