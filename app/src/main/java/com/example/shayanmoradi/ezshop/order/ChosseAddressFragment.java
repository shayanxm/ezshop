package com.example.shayanmoradi.ezshop.order;


import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.shayanmoradi.ezshop.R;
import com.example.shayanmoradi.ezshop.database.Address;
import com.example.shayanmoradi.ezshop.database.AddressManager;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChosseAddressFragment extends DialogFragment {
    private RecyclerView addressKeeperRec;
public static final String TAG="com.example.shayanmoradi.ezshop.order.pos";
    private CustomerAdapter customerAdapter;
    public ChosseAddressFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_chosse_address, container, false);
        addressKeeperRec= view.findViewById(R.id.address_keeper_rec);
        addressKeeperRec.setLayoutManager(new LinearLayoutManager(getContext()));
        List<Address>addressList= AddressManager.getInstance(getContext()).getAllList();
        customerAdapter = new CustomerAdapter(addressList);
        addressKeeperRec.setAdapter(customerAdapter);
   return view;
    }
    private class CustoemrHolder extends RecyclerView.ViewHolder {

        private TextView addressKepperTv;
        private Address address;
    public CustoemrHolder(@NonNull View itemView) {
        super(itemView);
addressKepperTv=itemView.findViewById(R.id.address_can_shossed_tv);




    }

    public void bind(final Address address) {
        this.address = address;
        addressKepperTv.setText(address.getAddress1());
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendResult(address.getAddress1());
                dismiss();
            }
        });

    }}
    class CustomerAdapter extends RecyclerView.Adapter<CustoemrHolder> {

        private List<Address> reviews;

        public CustomerAdapter(List<Address> reviews1) {
            reviews = reviews1;
        }

        public void setCArs(List<Address> reviews1) {
            reviews = reviews1;
        }

        @NonNull
        @Override
        public CustoemrHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.chosse_address_rec_item, parent, false);
            CustoemrHolder custoemrHolder = new CustoemrHolder(view);

            return custoemrHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull CustoemrHolder holder, int position) {
            Address review = reviews.get(position);

            holder.bind(review);
        }

        @Override
        public int getItemCount() {
            return reviews.size();
        }
    }












    private void sendResult(String address1) {
        Intent intent = new Intent();
        intent.putExtra(TAG, address1);
        getTargetFragment().
                onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);

        getTargetFragment().onResume();
    }
}
