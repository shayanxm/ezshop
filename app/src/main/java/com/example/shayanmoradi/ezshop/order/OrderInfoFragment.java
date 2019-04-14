package com.example.shayanmoradi.ezshop.order;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.shayanmoradi.ezshop.Model.orderingModels.Biling;
import com.example.shayanmoradi.ezshop.Model.orderingModels.Coupon;
import com.example.shayanmoradi.ezshop.Model.orderingModels.Customer;
import com.example.shayanmoradi.ezshop.Model.orderingModels.Order;
import com.example.shayanmoradi.ezshop.Model.orderingModels.OrderJsonBody;
import com.example.shayanmoradi.ezshop.R;
import com.example.shayanmoradi.ezshop.location.MapsMarkerActivity;
import com.example.shayanmoradi.ezshop.network.Api;
import com.example.shayanmoradi.ezshop.network.RetrofitClientInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class OrderInfoFragment extends androidx.fragment.app.Fragment {
    private Button pickFroMapBtn;

    public static OrderInfoFragment newInstance() {

        Bundle args = new Bundle();

        OrderInfoFragment fragment = new OrderInfoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public OrderInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order_info, container, false);
        pickFroMapBtn = view.findViewById(R.id.pick_address_from_map);
        /////////
        pickFroMapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //orderCreator();
                Intent intent= new Intent(getActivity(),MapsMarkerActivity.class);
                startActivity(intent);


            }
        });
        ////////////
        return view;
    }

    private void orderCreator() {
        Biling biling = new Biling();
        List<Order> orders = new ArrayList<>();
        Order order = new Order(87, 1);
        Order order2 = new Order(81, 1);
        orders.add(0, order);
        orders.add(1, order2);
        List<Coupon>coupons= new ArrayList<>();
        Coupon coupon= new Coupon("code10");
        coupons.add(0,coupon);
        OrderJsonBody orderJsonBody = new OrderJsonBody(biling, orders, coupons,67);
        RetrofitClientInstance.getRetrofitInstance().create(Api.class).sendOrder(orderJsonBody)
                .enqueue(new Callback<Customer>() {
                    @Override
                    public void onResponse(Call<Customer> call, Response<Customer> response) {
if (response.isSuccessful()){
Toast.makeText(getContext(), "sucsefull", Toast.LENGTH_SHORT).show();
int orderId=response.body().getId();
Log.e("wtfz",orderId+"asdf");
Toast.makeText(getContext(), orderId+"", Toast.LENGTH_SHORT).show();

}
                    }

                    @Override
                    public void onFailure(Call<Customer> call, Throwable t) {
                    }
                });
    }


}
