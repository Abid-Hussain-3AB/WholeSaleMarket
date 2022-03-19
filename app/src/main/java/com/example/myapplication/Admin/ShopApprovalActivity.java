package com.example.myapplication.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.DataClasses.ShopDataClass;
import com.example.myapplication.R;
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
    TextView tvstatus;
    Button Confirm, Cancel;
    private DatabaseReference mFirebaseDatabase;
    String name,type,city,id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_approval);
        tvName = findViewById(R.id.tvShopName1);
        tvCity = findViewById(R.id.tvShopCity1);
        tvType = findViewById(R.id.tvShopType1);
        tvId = findViewById(R.id.tvShopId1);
        tvstatus = findViewById(R.id.tvStatus);
        Confirm = findViewById(R.id.btnConfirmRequest);
        Cancel = findViewById(R.id.btnCancelRequest);
        Intent intent = getIntent();
        name = intent.getStringExtra("Name");
        type = intent.getStringExtra("Type");
        city = intent.getStringExtra("City");
        id = intent.getStringExtra("Id");
        tvName.setText(name);
        tvType.setText(type);
        tvCity.setText(city);
        tvId.setText(id);
        mFirebaseDatabase = FirebaseDatabase.getInstance().getReference("Shops");
        mFirebaseDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(id))
                {
                    Toast.makeText(ShopApprovalActivity.this, "This Shop is already Register", Toast.LENGTH_SHORT).show();
                    tvstatus.setText("Registered Shop");
                }
                else {
                    Confirm.setVisibility(View.VISIBLE);
                    tvstatus.setText("Unregistered Shop");
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
               finish();
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

                    ShopDataClass user = new ShopDataClass(name, type, city,id);
                    mFirebaseDatabase.child(id).setValue(user);
                    Toast.makeText(ShopApprovalActivity.this, "Shop Register Successfully", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}