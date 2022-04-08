package com.example.myapplication.Seller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.myapplication.AdapterClasses.AdapterClassOwner;
import com.example.myapplication.DataClasses.ProductDataClass;
import com.example.myapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ShowProductsActivity extends AppCompatActivity {
    private DatabaseReference mFirebaseDatabase;
    RecyclerView recyclerView;
    AdapterClassOwner adapterClassOwner;
    private List<ProductDataClass> productDataClassesList;
    public String ShopId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        ShopId = intent.getStringExtra("shop");
        setContentView(R.layout.activity_show_products);
        recyclerView = findViewById(R.id.rc_owner_products);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        productDataClassesList = new ArrayList<>();
        mFirebaseDatabase = FirebaseDatabase.getInstance().getReference("Products");
        getData();
    }
    private void getData() {
        mFirebaseDatabase.child(ShopId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                        for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()) {
                            ProductDataClass sdc = dataSnapshot2.getValue(ProductDataClass.class);
                            productDataClassesList.add(sdc);
                        }
                    }
                    adapterClassOwner = new AdapterClassOwner(productDataClassesList, ShowProductsActivity.this);
                    recyclerView.setAdapter(adapterClassOwner);
                    adapterClassOwner.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}