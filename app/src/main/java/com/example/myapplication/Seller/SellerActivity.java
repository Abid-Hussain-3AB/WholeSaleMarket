package com.example.myapplication.Seller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SellerActivity extends AppCompatActivity {
    Button addShop, addproducr, btnviewPD;
    private DatabaseReference mFirebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner);
        Intent intent = getIntent();
        String str = intent.getStringExtra("SHOP");
        String Sname = intent.getStringExtra("NAME");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(Sname);
        addShop = findViewById(R.id.btnAddShop);
        addproducr = findViewById(R.id.btnADDP);
        btnviewPD = findViewById(R.id.btnviewProducts);
        addproducr.setVisibility(View.GONE);
        btnviewPD.setVisibility(View.GONE);
        mFirebaseDatabase = FirebaseDatabase.getInstance().getReference("Shops");
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