package com.example.myapplication.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.DataClasses.ShopDataClass;
import com.example.myapplication.Other.SplashActivity;
import com.example.myapplication.R;
import com.example.myapplication.Seller.EditDelProductActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ShopApprovalActivity extends AppCompatActivity {
    TextView tvName;
    TextView tvType;
    TextView tvCity;
    TextView tvId;
    TextView tvAddress;
    TextView tvShopaLocation;
    Button Confirm, Cancel;
    private DatabaseReference mFirebaseDatabase;
    private DatabaseReference mFirebaseDatabaseA;
    String name,type,city,address,id,image,location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_approval);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        tvName = findViewById(R.id.tvShopName1);
        tvCity = findViewById(R.id.tvShopCity1);
        tvAddress = findViewById(R.id.tvShopaddress);
        tvType = findViewById(R.id.tvShopType1);
        tvId = findViewById(R.id.tvShopId1);
        Confirm = findViewById(R.id.btnConfirmRequest);
        Cancel = findViewById(R.id.btnCancelRequest);
        tvShopaLocation = findViewById(R.id.tvShopalocation);
        Intent intent = getIntent();
        name = intent.getStringExtra("Name");
        type = intent.getStringExtra("Type");
        city = intent.getStringExtra("City");
        address = intent.getStringExtra("Address");
        id = intent.getStringExtra("Id");
        image = intent.getStringExtra("Image");
        location = intent.getStringExtra("Location");
        tvName.setText("Shop Name: "+name);
        tvType.setText("Shop Type: "+type);
        tvCity.setText("Shop City: "+city);
        tvId.setText("Shop Id: "+id);
        tvAddress.setText("Shop Address"+address);
        tvShopaLocation.setText("Shop Location: "+location);
        mFirebaseDatabase = FirebaseDatabase.getInstance().getReference("Shops");
        mFirebaseDatabaseA = FirebaseDatabase.getInstance().getReferenceFromUrl("https://login-b93ab-default-rtdb.firebaseio.com/");
        mFirebaseDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(id))
                {
                    Toast.makeText(ShopApprovalActivity.this, "Registered Shop", Toast.LENGTH_SHORT).show();
                    getSupportActionBar().setTitle("Registered Shop");
                }
                else {
                    Toast.makeText(ShopApprovalActivity.this, "UnRegistered Shop", Toast.LENGTH_SHORT).show();
                    Confirm.setVisibility(View.VISIBLE);
                    getSupportActionBar().setTitle("UnRegistered Shop");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
       Confirm.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               addShop();
           }
       });
       Cancel.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               finish();
           }
       });
    }
    private void addShop() {

        mFirebaseDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(id))
                {
                    Toast.makeText(ShopApprovalActivity.this, "Shop Already Registered", Toast.LENGTH_SHORT).show();
                }
                else {

                    ShopDataClass user = new ShopDataClass(name, type, city,address,location,id,image);
                    mFirebaseDatabase.child(id).setValue(user);
                    Toast.makeText(ShopApprovalActivity.this, "Shop Register Successfully", Toast.LENGTH_SHORT).show();
                    Dell();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    public void Dell()
    {
        mFirebaseDatabaseA.child("Admin").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                    {
                        dataSnapshot1.child(id).getRef().removeValue();
                    }
                    startActivity(new Intent(ShopApprovalActivity.this, AdminActivity.class));
                    finish();
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