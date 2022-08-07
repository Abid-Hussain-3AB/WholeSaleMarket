package com.example.myapplication.Buyer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.myapplication.Buyer.Fragments.AllOrderFrag;
import com.example.myapplication.Buyer.Fragments.ToPayFrag;
import com.example.myapplication.Buyer.Fragments.ToReceiveFrag;
import com.example.myapplication.Buyer.Fragments.ToReviewFrag;
import com.example.myapplication.Buyer.Fragments.ToShipFrag;
import com.example.myapplication.R;
public class MyOrderActivity extends AppCompatActivity {
    String fragment = "Which";
    String value;
    String UserName;
    Button btnAll,btnPay,btnShip,btnReceive,bntReview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("My Orders");
        Intent intent = getIntent();
        value = intent.getStringExtra(fragment);
        UserName = intent.getStringExtra("U");
        btnAll = findViewById(R.id.all);
        btnPay = findViewById(R.id.pay);
        btnShip = findViewById(R.id.ship);
        btnReceive = findViewById(R.id.receive);
        bntReview = findViewById(R.id.review);
        //Toast.makeText(this, ""+UserName, Toast.LENGTH_SHORT).show();
        if (value.equals("pay")){
            loadFragment(new ToPayFrag());
            btnPay.setTextColor(Color.BLUE);
        }
        else if (value.equals("ship")){

            loadFragment(new ToShipFrag());
            btnShip.setTextColor(Color.BLUE);
        }
        else if (value.equals("receive")){
            loadFragment(new ToReceiveFrag());
            btnReceive.setTextColor(Color.BLUE);
        }
        else if (value.equals("review")){
            loadFragment(new ToReviewFrag());
            bntReview.setTextColor(Color.BLUE);
        }
        else if (value.equals("all")){
            loadFragment(new AllOrderFrag(UserName));
            btnAll.setTextColor(Color.BLUE);
        }
        btnAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new AllOrderFrag(UserName));
                btnPay.setTextColor(Color.BLACK);
                btnReceive.setTextColor(Color.BLACK);
                btnShip.setTextColor(Color.BLACK);
                btnAll.setTextColor(Color.BLUE);
                bntReview.setTextColor(Color.BLACK);
            }
        });
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new ToPayFrag());
                btnPay.setTextColor(Color.BLUE);
                btnReceive.setTextColor(Color.BLACK);
                btnShip.setTextColor(Color.BLACK);
                btnAll.setTextColor(Color.BLACK);
                bntReview.setTextColor(Color.BLACK);

            }
        });
        btnShip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new ToShipFrag());
                btnPay.setTextColor(Color.BLACK);
                btnReceive.setTextColor(Color.BLACK);
                btnShip.setTextColor(Color.BLUE);
                btnAll.setTextColor(Color.BLACK);
                bntReview.setTextColor(Color.BLACK);
            }
        });
        btnReceive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new ToReceiveFrag());
                btnPay.setTextColor(Color.BLACK);
                btnReceive.setTextColor(Color.BLUE);
                btnShip.setTextColor(Color.BLACK);
                btnAll.setTextColor(Color.BLACK);
                bntReview.setTextColor(Color.BLACK);
            }
        });
        bntReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new ToReviewFrag());
                btnPay.setTextColor(Color.BLACK);
                btnReceive.setTextColor(Color.BLACK);
                btnShip.setTextColor(Color.BLACK);
                btnAll.setTextColor(Color.BLACK);
                bntReview.setTextColor(Color.BLUE);
            }
        });
    }
    private void loadFragment(androidx.fragment.app.Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
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