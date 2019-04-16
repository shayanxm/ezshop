package com.example.shayanmoradi.ezshop.order;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shayanmoradi.ezshop.Model.orderingModels.Biling;
import com.example.shayanmoradi.ezshop.Model.orderingModels.Coupon;
import com.example.shayanmoradi.ezshop.Model.orderingModels.Customer;
import com.example.shayanmoradi.ezshop.Model.orderingModels.Order;
import com.example.shayanmoradi.ezshop.Model.orderingModels.OrderJsonBody;
import com.example.shayanmoradi.ezshop.R;
import com.example.shayanmoradi.ezshop.database.Address;
import com.example.shayanmoradi.ezshop.database.AddressManager;
import com.example.shayanmoradi.ezshop.database.SavedProduct;
import com.example.shayanmoradi.ezshop.database.SavedProductsManger;
import com.example.shayanmoradi.ezshop.location.MapsMarkerActivity;
import com.example.shayanmoradi.ezshop.network.Api;
import com.example.shayanmoradi.ezshop.network.RetrofitClientInstance;
import com.example.shayanmoradi.ezshop.prefs.QueryPreferences;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.shayanmoradi.ezshop.location.MapsMarkerActivity.ADDRESS_STRING;
import static com.example.shayanmoradi.ezshop.location.MapsMarkerActivity.LAT_INT;
import static com.example.shayanmoradi.ezshop.location.MapsMarkerActivity.LONG_INT;


/**
 * A simple {@link Fragment} subclass.
 */
public class OrderInfoFragment extends androidx.fragment.app.Fragment {
    public static final int CHOSSE_ADDRESS_DILAOG_TAG = 0;
    public static final String TAG = "com.example.shayanmoradi.ezshop.order.pos";
    private Button pickFroMapBtn;
    private EditText countryEt;
    private EditText cityEt;
    private EditText postalCodeEt;
    private EditText phoneNumber_et;
    private EditText address1Et;
    private EditText couponEt;
    private TextInputLayout couponLayout;
    private CheckBox useCouponCheckBox;
    private ConstraintLayout confrim_order_cons;
    private Button chosseFromAddressesBtn;

    private Coupon currentCoupon;
    private List<Coupon> couponList;
    private Coupon speseficCoupon;

    boolean trueCanSumbit = true;

    int lat;
    int lon;
    String address2;
    Address addressFromSaveds;

    public static OrderInfoFragment newInstance() {
        Bundle args = new Bundle();
        OrderInfoFragment fragment = new OrderInfoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lat = getArguments().getInt(LAT_INT);
        lon = getArguments().getInt(LONG_INT);
        address2 = getArguments().getString(ADDRESS_STRING);
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
        confrim_order_cons = view.findViewById(R.id.confrim_order_cons);
        countryEt = view.findViewById(R.id.country_et);
        cityEt = view.findViewById(R.id.city_et);
        postalCodeEt = view.findViewById(R.id.postal_code_et);
        phoneNumber_et = view.findViewById(R.id.phone_et);
        address1Et = view.findViewById(R.id.address_et);
        couponEt = view.findViewById(R.id.coupon_et);
        couponLayout = view.findViewById(R.id.coupon_layout);
        useCouponCheckBox = view.findViewById(R.id.use_coupon_check_box);

        couponEt.setVisibility(View.INVISIBLE);
        couponLayout.setVisibility(View.INVISIBLE);
        handleTitleTextField();

        RetrofitClientInstance.getRetrofitInstance().create(Api.class).getCoupons().enqueue(new Callback<List<Coupon>>() {
            @Override
            public void onResponse(Call<List<Coupon>> call, Response<List<Coupon>> response) {
                if (response.isSuccessful()) {
                    couponList = response.body();
                }
            }

            @Override
            public void onFailure(Call<List<Coupon>> call, Throwable t) {

            }
        });


        useCouponCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    trueCanSumbit = true;
                    couponEt.setVisibility(View.INVISIBLE);
                    couponLayout.setVisibility(View.INVISIBLE);
                } else {
                    couponEt.setVisibility(View.VISIBLE);
                    couponLayout.setVisibility(View.VISIBLE);
                }
            }
        });

        chosseFromAddressesBtn = view.findViewById(R.id.chose_from_saved_addresses_btn);
        /////////
        chosseFromAddressesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialogFrag = new ChosseAddressFragment();
// This is the requestCode that you are sending.
                dialogFrag.setTargetFragment(OrderInfoFragment.this, CHOSSE_ADDRESS_DILAOG_TAG);
// This is the tag, "dialog" being sent.
                dialogFrag.show(getFragmentManager(), "address_dialog");
            }
        });
        pickFroMapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //orderCreator();
                Intent intent = new Intent(getActivity(), MapsMarkerActivity.class);
                startActivityForResult(intent, 1);


            }
        });

        confrim_order_cons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (trueCanSumbit == false) {
                    Toast.makeText(getActivity(), "کوپن نا معتبر", Toast.LENGTH_SHORT).show();
                    return;
                }
                String name = QueryPreferences.getCustomerName(getActivity());
                String email = QueryPreferences.getCustomerEmail(getActivity());
                if (addressFromSaveds != null) {

                    address2 = addressFromSaveds.getAddress2();
                } else
                    address2 = null;
                orderCreator(name, address1Et.getText().toString(), address2, cityEt.getText().toString(), postalCodeEt.getText().toString(), countryEt.getText().toString(), email, phoneNumber_et.getText().toString());


                if (lat != 0 && lon != 0) {


                    Address address = new Address();
                    address.setAddress1(address1Et.getText().toString());
                    address.setAddress2(address2);
                    address.setLat(lat);

                    address.setLongtiude(lon);
                    AddressManager.getInstance(getContext()).addAddress(address);
                    Log.e("test", lat + lon + "latlong");
                } else {
                    Address address = new Address();
                    address.setAddress1(address1Et.getText().toString());
                    address.setAddress2(address2);
                    address.setLat(0);
                    address.setLongtiude(0);
                    AddressManager.getInstance(getContext()).addAddress(address);
                }


            }
        });

        ////////////
        return view;
    }

    private void orderCreator(String firstName, String address1, String address2, String city, String postalCode, String country, String emial, String phoneNumber) {
        Biling biling = new Biling(firstName, address1, address2, city, postalCode
                , country, emial, phoneNumber);
        List<Order> orders = new ArrayList<>();
        List<SavedProduct> savedProductList = SavedProductsManger.getInstance(getContext()).getBag();
        for (int i = 0; i < savedProductList.size(); i++) {
            Order order = new Order(savedProductList.get(i).getProductId(), savedProductList.get(i).getCount());
            orders.add(i, order);
        }

//        Order order = new Order(87, 1);
//        Order order2 = new Order(81, 1);
//        orders.add(0, order);
//        orders.add(1, order2);
        List<Coupon> coupons = new ArrayList<>();
        if (useCouponCheckBox.isChecked()) {

        }
        if (currentCoupon != null) {
            coupons.add(0, currentCoupon);
        }
        // Coupon coupon = new Coupon("code10");


        OrderJsonBody orderJsonBody = new OrderJsonBody(biling, orders, coupons, 67);
        RetrofitClientInstance.getRetrofitInstance().create(Api.class).sendOrder(orderJsonBody)
                .enqueue(new Callback<Customer>() {
                    @Override
                    public void onResponse(Call<Customer> call, Response<Customer> response) {
                        if (response.isSuccessful()) {
                           //Toast.makeText(getContext(), "sucsefull", Toast.LENGTH_SHORT).show();
                            int orderId = response.body().getId();
                            Log.e("wtf", orderId + "asdf");
                            /////order submited
                            Toast.makeText(getContext(), "سفارش به ثبت رسید", Toast.LENGTH_SHORT).show();

                            SavedProductsManger.getInstance(getContext()).deleteBgag();
                            getActivity().finish();



                            /////


                        } else {
                            Toast.makeText(getContext(), "مشکلی رخ داده دوباره امتحان کنید", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Customer> call, Throwable t) {
                    }
                });


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Stuff to do, dependent on requestCode and resultCode
        if (requestCode == CHOSSE_ADDRESS_DILAOG_TAG) { // 1 is an arbitrary number, can be any int
            // This is the return result of your DialogFragment

            String address1 = (String) data.getSerializableExtra(TAG);

            address1Et.setText(address1);
            Log.e("test", "" + address1);
            addressFromSaveds = AddressManager.getInstance(getContext()).getAddress(address1);
        }
    }

    private void handleTitleTextField() {

        couponEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String test = "code10";
                ////
                for (int i = 0; i < couponList.size(); i++) {
                    if (couponList.get(i).getCode().equals(s.toString())) {
                        speseficCoupon = couponList.get(i);
                        trueCanSumbit = true;


                        //    if (coupon.getAmount().equals("10.00")){
                        Toast.makeText(getContext(), "کد تخفیف " + speseficCoupon.getAmount() + "درصدی اعمال شد", Toast.LENGTH_LONG).show();

                    }

                }if (speseficCoupon==null){
                    trueCanSumbit = false;

                }
                /////
//                RetrofitClientInstance.getRetrofitInstance().create(Api.class).getCouponById(test).enqueue(new Callback<Coupon>() {
//                    @Override
//                    public void onResponse(Call<Coupon> call, Response<Coupon> response) {
//                        if (response.isSuccessful()) {
//                            trueCanSumbit = true;
//                            Coupon coupon = response.body();
//                            currentCoupon = coupon;
//                            //    if (coupon.getAmount().equals("10.00")){
//                            Toast.makeText(getContext(), "code takhfif" + coupon.getAmount() + " i emal shod", Toast.LENGTH_LONG).show();
//                        } else
//                            trueCanSumbit = false;
//                    }
//
//                    @Override
//                    public void onFailure(Call<Coupon> call, Throwable t) {
//                        trueCanSumbit = false;
//                    }
//                });
//
//                if (trueCanSumbit == false) {
//                    Toast.makeText(getActivity(), "coupon na motabar", Toast.LENGTH_SHORT).show();
//
//                }
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });
    }


}
