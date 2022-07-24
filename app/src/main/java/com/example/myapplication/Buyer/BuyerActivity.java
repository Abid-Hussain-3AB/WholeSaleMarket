package com.example.myapplication.Buyer;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.myapplication.AdapterClasses.AdapterClassProduct;
import com.example.myapplication.AdapterClasses.AdapterFragments;
import com.example.myapplication.AdapterClasses.ItemClickListener;
import com.example.myapplication.AdapterClasses.ItemClickListenerCart;
import com.example.myapplication.AdapterClasses.ItemClickListenerCartAdd;
import com.example.myapplication.Buyer.Fragments.AccountFrag;
import com.example.myapplication.Buyer.Fragments.CartFrag;
import com.example.myapplication.DataClasses.ProductDataClass;
import com.example.myapplication.Other.SignInActivity;
import com.example.myapplication.R;
import com.example.myapplication.Settings.AppCompact;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BuyerActivity extends AppCompact {
    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    AdapterFragments fragmentAdapter;
    BottomNavigationView bottomNavigationView;
    private DatabaseReference mFirebaseDatabase;
    public static final String filename = "login";
    public static final String name = "Abid";
    public static final String userName = "username";
    public static final String password = "password";
    ImageButton searchView;
    SharedPreferences sharedPreferences;
    public static final String Cart = "cart";
    public static final String CartNumber = "CartNumber";
    SharedPreferences sharedPreferences1;
    String Uname="";
    String Uid ="";
    int total=0;
    ItemClickListenerCart itemClickListenerCart;
    ItemClickListenerCartAdd itemClickListenerCartAdd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer);
        mFirebaseDatabase = FirebaseDatabase.getInstance().getReference("User");
        sharedPreferences = getSharedPreferences(filename, Context.MODE_PRIVATE);
        if (sharedPreferences.contains(userName)){
            Uname =sharedPreferences.getString(name,"");
            Uid =sharedPreferences.getString(userName,"");
        }
        sharedPreferences1 = getSharedPreferences(Cart, Context.MODE_PRIVATE);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
        toolbar = findViewById(R.id.toolBar);
        tabLayout = findViewById(R.id.tablayout);
        viewPager = findViewById(R.id.viewpager);
        setSupportActionBar(toolbar);
        searchView = findViewById(R.id.btnSearch);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.all_products));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.electronics));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.groceries));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.home_appliances));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.cosmetics));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.jewelry));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.apparel));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.vehicle_parts));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.constructions));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.fertilizers));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        fragmentAdapter = new AdapterFragments(getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(fragmentAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BuyerActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });
        if (Uid.isEmpty())
        {
            bottomNavigationView.getOrCreateBadge(R.id.cart).setNumber(0);
        }
        else {
            mFirebaseDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.hasChild(Uid)) {
                       // for (DataSnapshot dataSnapshot1 : dataSnapshot.child(Uid).child("my cart").getChildren()) {
                         //   ProductDataClass productDataClass = dataSnapshot1.getValue(ProductDataClass.class);
                           total = (int) dataSnapshot.child(Uid).child("my cart").getChildrenCount();
                           bottomNavigationView.getOrCreateBadge(R.id.cart).setNumber(total);
                           SharedPreferences.Editor editor = sharedPreferences1.edit();
                           editor.putString(CartNumber,"Abid");
                           editor.commit();
                       // }
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                  //  Toast.makeText(v.getContext(), "Fail to Update Data." + databaseError, Toast.LENGTH_SHORT).show();
                }
            });
        }
        itemClickListenerCart = new ItemClickListenerCart() {
            @Override
            public void onClick(String s) {
                bottomNavigationView.getOrCreateBadge(R.id.cart).setNumber(total-1);
            }
        };
        itemClickListenerCartAdd = new ItemClickListenerCartAdd() {
            @Override
            public void onClick(String s) {
                bottomNavigationView.getOrCreateBadge(R.id.cart).setNumber(total+1);
            }
        };
    }
    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @SuppressLint("NonConstantResourceId")
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()){
                case R.id.home:
                    Intent intent = new Intent(BuyerActivity.this,BuyerActivity.class);
                    startActivity(intent);
                    return true;
                case R.id.account:
                    viewPager.removeAllViews();
                    fragment = new AccountFrag();
                    loadFragment(fragment);
                    return true;
                case R.id.chat:
                    Toast.makeText(BuyerActivity.this, "Message", Toast.LENGTH_LONG).show();
                    return true;
                case R.id.cart:
                    if (Uid.isEmpty())
                    {
                        Intent intent1 = new Intent(BuyerActivity.this,SignInActivity.class);
                        startActivity(intent1);
                        finish();
                    }
                    else {
                      //  bottomNavigationView.getOrCreateBadge(R.id.cart).setNumber(1);
                        viewPager.removeAllViews();
                        fragment = new CartFrag(Uid,itemClickListenerCart);
                        loadFragment(fragment);
                    }
                    return true;

            }
            return false;
        }
    };
    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}