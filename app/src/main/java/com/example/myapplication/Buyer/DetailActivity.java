package com.example.myapplication.Buyer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.example.myapplication.AdapterClasses.AdapterClassProduct;
import com.example.myapplication.AdapterClasses.AdapterClassProduct_more;
import com.example.myapplication.AdapterClasses.ItemClickListenerCart;
import com.example.myapplication.AdapterClasses.ItemClickListenerCartAdd;
import com.example.myapplication.DataClasses.ProductDataClass;
import com.example.myapplication.Other.SignInActivity;
import com.example.myapplication.R;
import com.example.myapplication.Settings.AppCompact;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompact {
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
    String productname, producttype,productmompany, productprice, productminsale, productmaxsale, productquantity, productdetail,shopid,category;
    private DatabaseReference mFirebaseDatabase;
    private DatabaseReference mFirebaseDatabase1;
    RecyclerView recyclerView;
    AdapterClassProduct_more adapterClassProduct;
    private List<ProductDataClass> productDataClassesList1;
    SharedPreferences sharedPreferences;
    public static final String Cart = "cart";
    public static final String CartNumber = "CartNumber";
    SharedPreferences sharedPreferences1;
    String cart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        sharedPreferences = getSharedPreferences(filename, Context.MODE_PRIVATE);
        if (sharedPreferences.contains(userName)){
            user_id =sharedPreferences.getString(userName,"");
        }
        sharedPreferences1 = getSharedPreferences(Cart,Context.MODE_PRIVATE);
        if (sharedPreferences1.contains(Cart)){
          cart =  sharedPreferences1.getString(CartNumber,"");
        }
        Toast.makeText(this, "Cart="+cart, Toast.LENGTH_SHORT).show();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(R.string.product_detail);
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
        producttype = intent.getStringExtra("type");
        productmompany = intent.getStringExtra("company");
        productprice = intent.getStringExtra("price");
        productminsale = intent.getStringExtra("min");
        productmaxsale = intent.getStringExtra("max");
        product_id = intent.getStringExtra("id");
        productquantity = intent.getStringExtra("quantity");
        productdetail = intent.getStringExtra("detail");
        shopid = intent.getStringExtra("shopid");
        category = intent.getStringExtra("category");
        tvName.setText(productname);
        tvprice.setText(getString(R.string.rs)+productprice);
        tvmax.setText(productmaxsale+getString(R.string.min));
        tvmin.setText(productminsale+getString(R.string.max));
        tvdetail.setText(Html.fromHtml("<font><b>Detail: </b></font>"+productdetail));
        imgUrl = intent.getStringExtra("image");
        Glide.with(this).load(imgUrl).into(imageView);
        recyclerView = findViewById(R.id.rc_detail);
        productDataClassesList1 = new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2,RecyclerView.VERTICAL,false));
        // mFirebaseDatabase = FirebaseDatabase.getInstance().getReferenceFromUrl("https://login-b93ab-default-rtdb.firebaseio.com/");
        mFirebaseDatabase = FirebaseDatabase.getInstance().getReference("User");
        mFirebaseDatabase1 = FirebaseDatabase.getInstance().getReference("Products");
        getData(category);

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
                    if (user_id.isEmpty()){
                        Toast.makeText(DetailActivity.this, "Login Or Signup", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(DetailActivity.this,SignInActivity.class);
                        startActivity(intent);
                    }
                    else {
                        Intent intent1 = new Intent(DetailActivity.this, StartOrderActivity.class);
                        intent1.putExtra("price", productprice);
                        intent1.putExtra("image", imgUrl);
                        intent1.putExtra("min", productminsale);
                        intent1.putExtra("max", productmaxsale);
                        intent1.putExtra("name", productname);
                        intent1.putExtra("uid", user_id);
                        intent1.putExtra("product_id",product_id);
                        intent1.putExtra("shop_id",shopid);
                        startActivity(intent1);
                    }
                    //Toast.makeText(DetailActivity.this, "Buy", Toast.LENGTH_LONG).show();
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

    public void AddCart()
    {
        mFirebaseDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ProductDataClass user = new ProductDataClass(productname,producttype,productmompany,productprice,productminsale,productmaxsale,productquantity,productdetail,product_id,shopid, imgUrl);
                        if (dataSnapshot.hasChild(user_id)) {
                           mFirebaseDatabase.child(user_id).child("my cart").child(product_id).setValue(user);
                            Toast.makeText(DetailActivity.this, "Add to cart Successfully", Toast.LENGTH_LONG).show();
                            bottomNavigationView.getOrCreateBadge(R.id.add_to_cart).setNumber(1);
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
    private void getData(String ct)
    {
        if (ct.equals(""))
        {
            ct = "Apparel";
        }
        String finalCt = ct;
        mFirebaseDatabase1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (ds.hasChild(finalCt)) {
                        for (DataSnapshot dataSnapshot1 : ds.child(finalCt).getChildren()) {
                            ProductDataClass sdc = dataSnapshot1.getValue(ProductDataClass.class);
                            productDataClassesList1.add(sdc);
                        }
                    }
                }
                adapterClassProduct = new AdapterClassProduct_more(productDataClassesList1, DetailActivity.this,category);
                recyclerView.setAdapter(adapterClassProduct);
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