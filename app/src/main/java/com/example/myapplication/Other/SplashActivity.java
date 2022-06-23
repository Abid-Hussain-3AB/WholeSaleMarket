package com.example.myapplication.Other;

import android.content.Intent;
import android.os.Handler;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Admin.AdminActivity;
import com.example.myapplication.Buyer.BuyerActivity;
import com.example.myapplication.Buyer.DetailActivity;
import com.example.myapplication.Buyer.PurchaseProductActivity;
import com.example.myapplication.R;
import com.example.myapplication.Seller.SellerActivity;
import com.google.firebase.database.DataSnapshot;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, BuyerActivity.class));
                finish();
            }
        },2500);
    }
}