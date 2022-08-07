package com.example.myapplication.Seller;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.myapplication.AdapterClasses.AdapterOrder;
import com.example.myapplication.DataClasses.OrderDataClass;
import com.example.myapplication.DataClasses.ProductDataClass;
import com.example.myapplication.R;
import com.example.myapplication.Settings.AppCompact;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SellerActivity extends AppCompact {
    Button addShop, addproducr, btnviewPD, btnOrders;
    private DatabaseReference mFirebaseDatabase;
    private DatabaseReference mFirebaseDatabase1;
    private DatabaseReference mFirebaseDatabase2;
    RecyclerView rcOrder;
    private List<OrderDataClass> orderDataClassList;
    private List<ProductDataClass> productDataClassesList;
    AdapterOrder adapterOrder;
    String OrderId;
    public static  List<String> st;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner);
        st = new ArrayList<>();
        Intent intent = getIntent();
        String str = intent.getStringExtra("SHOP");
        String Sname = intent.getStringExtra("NAME");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(Sname);
        productDataClassesList = new ArrayList<>();
        orderDataClassList = new ArrayList<>();
        addShop = findViewById(R.id.btnAddShop);
        addproducr = findViewById(R.id.btnADDP);
        btnviewPD = findViewById(R.id.btnviewProducts);
        addproducr.setVisibility(View.GONE);
        btnviewPD.setVisibility(View.GONE);
        btnOrders = findViewById(R.id.btnOrders);
        rcOrder = findViewById(R.id.rc_orders);
        rcOrder.setHasFixedSize(true);
        rcOrder.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        rcOrder.setVisibility(View.GONE);
        mFirebaseDatabase = FirebaseDatabase.getInstance().getReference("Shops");
        mFirebaseDatabase1 = FirebaseDatabase.getInstance().getReference("Orders");
        mFirebaseDatabase2 = FirebaseDatabase.getInstance().getReference("Products");
        getOrderData(str);
        getProductData(str);
        mFirebaseDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.hasChild(str))
                {
                   addShop.setVisibility(View.GONE);
                    addproducr.setVisibility(View.VISIBLE);
                    btnviewPD.setVisibility(View.VISIBLE);
                }
                else
                {
                    addShop.setVisibility(View.VISIBLE);
                    addproducr.setVisibility(View.GONE);
                    btnviewPD.setVisibility(View.GONE);
                    btnOrders.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        btnviewPD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SellerActivity.this, ShowProductsActivity.class);
                intent.putExtra("shop", str);
                startActivity(intent);
            }
        });
        addproducr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SellerActivity.this, AddProductActivity.class);
                intent.putExtra("shop", str);
                startActivity(intent);
            }
        });
        addShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SellerActivity.this, AddShopActivity.class);
                intent.putExtra("shop", str);
                startActivity(intent);
            }
        });
        btnOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (orderDataClassList.size()!=0)
                {
                    rcOrder.setVisibility(View.VISIBLE);
                    check();
                }
                else {
                    Toast.makeText(SellerActivity.this, "No Order", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    public void check()
    {
        List<ProductDataClass> productDataClass;
        productDataClass = new ArrayList<>();

       for (OrderDataClass od: orderDataClassList)
       {
           st.add(od.getProductId());
       }
       for (ProductDataClass temp : productDataClassesList)
       {
           if (st.contains(temp.getProductId()))
           {
             ProductDataClass productDataClass1 = new ProductDataClass(temp.getProductName(),temp.getProductType(),temp.getProductCompany(),temp.getProductPrice(),temp.getProductMinSale(),
                      temp.getProductMaxSale(),temp.getProductQuantity(),temp.getProductDetail(),temp.getProductId(),temp.getProductShopId(),temp.getImage());
               productDataClass.add(productDataClass1);
           }
       }
        adapterOrder = new AdapterOrder(orderDataClassList,productDataClass, SellerActivity.this);
        rcOrder.setAdapter(adapterOrder);
        adapterOrder.notifyDataSetChanged();
    }

    private void getOrderData(String str) {
        mFirebaseDatabase1.child(str).addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            OrderDataClass sdc = dataSnapshot1.getValue(OrderDataClass.class);
                            orderDataClassList.add(sdc);
                            OrderId = sdc.getProductId();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void getProductData(String ShopId ) {
        mFirebaseDatabase2.child(ShopId).addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()) {
                            ProductDataClass sdc = dataSnapshot2.getValue(ProductDataClass.class);
                            productDataClassesList.add(sdc);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}