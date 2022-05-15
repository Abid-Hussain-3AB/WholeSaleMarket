package com.example.myapplication.Seller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

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
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Shop Products");
        Intent intent = getIntent();
        ShopId = intent.getStringExtra("shop");
        setContentView(R.layout.activity_show_products);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_show_product_seller);
        recyclerView = findViewById(R.id.rc_owner_products);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        productDataClassesList = new ArrayList<>();
        mFirebaseDatabase = FirebaseDatabase.getInstance().getReference("Products");
        getData();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onRefresh() {
                productDataClassesList.clear();
                adapterClassOwner.notifyDataSetChanged();
                getData();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

    }
    private void getData() {
        mFirebaseDatabase.child(ShopId).addListenerForSingleValueEvent(new ValueEventListener() {
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
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1)
        {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "START", Toast.LENGTH_LONG).show();
            }
            else if(resultCode == RESULT_CANCELED)
            {
                Toast.makeText(this, "NOT", Toast.LENGTH_LONG).show();
            }
        }
    }
}