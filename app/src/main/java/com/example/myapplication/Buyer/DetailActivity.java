package com.example.myapplication.Buyer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myapplication.DataClasses.ProductDataClass;
import com.example.myapplication.Other.SignInActivity;
import com.example.myapplication.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DetailActivity extends AppCompatActivity {
TextView tvName, tvprice, tvmax, tvmin, tvdetail;
ImageView imageView;
String imgUrl;
BottomNavigationView bottomNavigationView;
    public static final String filename = "login";
    public static final String name = "Abid";
    public static final String userName = "username";
    public static final String password = "password";
    String product_id = "";
    String user_id = "";
    String productname, producttype,productmompany, productprice, productminsale, productmaxsale, productquantity, productdetail;
    private DatabaseReference mFirebaseDatabase;

    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        sharedPreferences = getSharedPreferences(filename, Context.MODE_PRIVATE);
        if (sharedPreferences.contains(userName)){
            user_id =sharedPreferences.getString(userName,"");
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Product Detail");
        bottomNavigationView = findViewById(R.id.bottom_navigationD);
        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
        tvName = findViewById(R.id.tvProductNameD);
        tvprice = findViewById(R.id.tvProductPriceD);
        tvmax = findViewById(R.id.tvProductMOQD);
        tvmin = findViewById(R.id.tvProductMinQD);
        tvdetail = findViewById(R.id.tvProductDetailD);
        imageView = findViewById(R.id.ImgProductsD);
        Intent intent = getIntent();
        productname = intent.getStringExtra("name");
        producttype = intent.getStringExtra("name");
        productmompany = intent.getStringExtra("name");
        productprice = intent.getStringExtra("price");
        productminsale = intent.getStringExtra("min");
        productmaxsale = intent.getStringExtra("max");
        product_id = intent.getStringExtra("id");
        productquantity = intent.getStringExtra("quantity");
        productdetail = intent.getStringExtra("detail");
        tvName.setText(productname);
        tvprice.setText("Rs. "+productprice);
        tvmax.setText(productmaxsale+" (Max. Order)");
        tvmin.setText(productminsale+" (Min. Order)");

        tvdetail.setText(Html.fromHtml("<font><b>Detail: </b></font>"+productdetail));
        imgUrl = intent.getStringExtra("image");
        Glide.with(this).load(imgUrl).into(imageView);
       // mFirebaseDatabase = FirebaseDatabase.getInstance().getReferenceFromUrl("https://login-b93ab-default-rtdb.firebaseio.com/");
        mFirebaseDatabase = FirebaseDatabase.getInstance().getReference("User");
    }
    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @SuppressLint("NonConstantResourceId")
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()){
                case R.id.store:
                    Toast.makeText(DetailActivity.this, "Store", Toast.LENGTH_LONG).show();
                    return true;
                case R.id.chat1:
                    Toast.makeText(DetailActivity.this, "Message", Toast.LENGTH_LONG).show();
                    return true;
                case R.id.buy:
                    Toast.makeText(DetailActivity.this, "Buy", Toast.LENGTH_LONG).show();
                    return true;
                case R.id.add_to_cart:
                    if (user_id.isEmpty())
                    {
                        Intent intent = new Intent(DetailActivity.this, SignInActivity.class);
                        startActivity(intent);
                    }
                    else {
                        AddCart();
                    }
                    return true;

            }
            return false;
        }
    };
    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void AddCart()
    {
        mFirebaseDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ProductDataClass user = new ProductDataClass(productname,producttype,productmompany,productprice,productminsale,productmaxsale,productquantity,productdetail,product_id, imgUrl);
                        if (dataSnapshot.hasChild(user_id)) {
                           mFirebaseDatabase.child(user_id).child("my cart").child(product_id).setValue(user);
                            Toast.makeText(DetailActivity.this, "Add to cart Successfully", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(DetailActivity.this, "Not Added", Toast.LENGTH_LONG).show();
                        }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(DetailActivity.this, "Fail to Update Data." + databaseError, Toast.LENGTH_SHORT).show();
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