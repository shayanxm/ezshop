package com.example.shayanmoradi.ezshop.enterinfo;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shayanmoradi.ezshop.Model.orderingModels.Customer;
import com.example.shayanmoradi.ezshop.R;
import com.example.shayanmoradi.ezshop.network.Api;
import com.example.shayanmoradi.ezshop.network.RetrofitClientInstance;
import com.example.shayanmoradi.ezshop.order.OrderInfoActivity;
import com.example.shayanmoradi.ezshop.prefs.QueryPreferences;

import androidx.constraintlayout.widget.ConstraintLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class EnterInfoFragment extends androidx.fragment.app.Fragment {
    //private Button confrimInfo;
    private ConstraintLayout confrimInfoCons;
    private EditText customerName;
    private EditText customerLastName;
    private EditText customerUserName;
    private EditText customerEmail;
    private EditText custoemrPass;
    private boolean problemtrue = true;

    public static EnterInfoFragment newInstance() {

        Bundle args = new Bundle();

        EnterInfoFragment fragment = new EnterInfoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public EnterInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_enter_info, container, false);
        //   confrimInfo=view.findViewById(R.id.confrim__info_btn);
        confrimInfoCons = view.findViewById(R.id.confrim__info_constrain);
        customerName = view.findViewById(R.id.name_et);
        customerLastName = view.findViewById(R.id.last_name_et);
        customerUserName = view.findViewById(R.id.user_name_et);
        customerEmail = view.findViewById(R.id.email_et);
        custoemrPass = view.findViewById(R.id.pas);


        confrimInfoCons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createCustomer(customerName.getText().toString()
                        , customerLastName.getText().toString()
                        , customerUserName.getText().toString()
                        , customerEmail.getText().toString());
            }
        });
        return view;
    }

    private void createCustomer(final String firstName, String lastName, String userName, final String email) {


        RetrofitClientInstance.getRetrofitInstance().create(Api.class).createCustomer(firstName, lastName, userName, email)
                .enqueue(new Callback<Customer>() {

                    @Override
                    public void onResponse(Call<Customer> call, Response<Customer> response) {
                        //  Toast.makeText(getContext(), "clicked", Toast.LENGTH_SHORT).show();
                        if (response.isSuccessful()) {
                            int id = response.body().getId();
                            // String customerName=response.body().
                            Log.e("testz", id + "");

                            //Toast.makeText(getContext(), id + "", Toast.LENGTH_SHORT).show();
                            // Toast.makeText(getContext(), "worked", Toast.LENGTH_SHORT).show();


                            QueryPreferences.setIsLogin(getContext(), true);
                            QueryPreferences.setCustomerEmail(getContext(), email);
                            QueryPreferences.setCustomerId(getContext(), id);
                            QueryPreferences.setCustomerName(getContext(), firstName);
                            problemtrue = false;
                        } else {
                            Toast.makeText(getContext(), "مشکل در ثبت مشخصات", Toast.LENGTH_SHORT).show();
                            problemtrue = true;
                        }

                        if (problemtrue == true) {
                            Toast.makeText(getContext(), "مشکل در ثبت مشخصات", Toast.LENGTH_SHORT).show();
                            return;
                        } else {

                            Intent intent = OrderInfoActivity.newIntent(getActivity());
                            startActivity(intent);

                        }
                    }

                    @Override
                    public void onFailure(Call<Customer> call, Throwable t) {
                        Toast.makeText(getContext(), "fail" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
