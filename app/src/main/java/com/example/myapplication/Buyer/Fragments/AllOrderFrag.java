package com.example.myapplication.Buyer.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.AdapterClasses.AdapterOrder;
import com.example.myapplication.DataClasses.OrderDataClass;
import com.example.myapplication.DataClasses.ProductDataClass;
import com.example.myapplication.R;
import com.example.myapplication.Seller.SellerActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AllOrderFrag extends Fragment {
    String UserName;
    TextView tvOrder;
    List<OrderDataClass> orderDataClass;
    List<ProductDataClass> productDataClasses;
    private DatabaseReference mFirebaseDatabase;
    AdapterOrder adapterOrder;
    RecyclerView rc;
    public AllOrderFrag(String UserName)
    {
        this.UserName = UserName;
    }
    View v;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_all_order, container, false);
        orderDataClass = new ArrayList<>();
        productDataClasses = new ArrayList<>();
        rc = v.findViewById(R.id.rc_all_orders);
        tvOrder = v.findViewById(R.id.no_order);
        rc.setLayoutManager(new LinearLayoutManager(v.getContext(), RecyclerView.VERTICAL, false));
        rc.setHasFixedSize(true);
        mFirebaseDatabase = FirebaseDatabase.getInstance().getReference("User");
        productDataClasses = AllProductFrag.productDataClassesList;
        cart();
        return v;
    }

    private void cart() {
        mFirebaseDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(UserName)) {
                     for (DataSnapshot dataSnapshot1 : dataSnapshot.child(UserName).child("Product Orders").getChildren()) {
                       OrderDataClass productDataClass = dataSnapshot1.getValue(OrderDataClass.class);
                       orderDataClass.add(productDataClass);
                     }
                     if (orderDataClass.isEmpty())
                     {
                         tvOrder.setVisibility(View.VISIBLE);
                     }
                     else {
                         check(orderDataClass);
                     }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //  Toast.makeText(v.getContext(), "Fail to Update Data." + databaseError, Toast.LENGTH_SHORT).show();
            }
        });
    }
    @SuppressLint("NotifyDataSetChanged")
    public void check(List<OrderDataClass> orderDataClass)
    {
        List<ProductDataClass> productDataClass;
        productDataClass = new ArrayList<>();
        List<String> st;
        st = new ArrayList<>();
        for (OrderDataClass od: orderDataClass)
        {
            st.add(od.getProductId());
        }
         Toast.makeText(v.getContext(), "+"+st.size(), Toast.LENGTH_SHORT).show();

        for (ProductDataClass temp : productDataClasses)
        {
            if (st.contains(temp.getProductId()))
            {
                ProductDataClass productDataClass1 = new ProductDataClass(temp.getProductName(),temp.getProductType(),temp.getProductCompany(),temp.getProductPrice(),temp.getProductMinSale(),
                        temp.getProductMaxSale(),temp.getProductQuantity(),temp.getProductDetail(),temp.getProductId(),temp.getProductShopId(),temp.getImage());
                productDataClass.add(productDataClass1);
            }
        }
        //Toast.makeText(v.getContext(), "+"+productDataClass.size(), Toast.LENGTH_SHORT).show();
        adapterOrder = new AdapterOrder(orderDataClass,productDataClass, v.getContext());
        rc.setAdapter(adapterOrder);
        adapterOrder.notifyDataSetChanged();
    }
}