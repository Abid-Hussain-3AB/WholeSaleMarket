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

import com.example.myapplication.AdapterClasses.AdapterFragments;
import com.example.myapplication.Buyer.Fragments.AccountFrag;
import com.example.myapplication.Buyer.Fragments.CartFrag;
import com.example.myapplication.Other.SignInActivity;
import com.example.myapplication.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
public class BuyerActivity extends AppCompatActivity {
    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    AdapterFragments fragmentAdapter;
    BottomNavigationView bottomNavigationView;
    public static final String filename = "login";
    public static final String name = "Abid";
    public static final String userName = "username";
    public static final String password = "password";
    ImageButton searchView;
    SharedPreferences sharedPreferences;
    String Uname="";
    String Uid ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer);
        sharedPreferences = getSharedPreferences(filename, Context.MODE_PRIVATE);
        if (sharedPreferences.contains(userName)){
            Uname =sharedPreferences.getString(name,"");
            Uid =sharedPreferences.getString(userName,"");
        }
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
        toolbar = findViewById(R.id.toolBar);
        tabLayout = findViewById(R.id.tablayout);
        viewPager = findViewById(R.id.viewpager);
        setSupportActionBar(toolbar);
        searchView = findViewById(R.id.btnSearch);
        tabLayout.addTab(tabLayout.newTab().setText("All Products"));
        tabLayout.addTab(tabLayout.newTab().setText("Electronics"));
        tabLayout.addTab(tabLayout.newTab().setText("Groceries"));
        tabLayout.addTab(tabLayout.newTab().setText("Home Appliances"));
        tabLayout.addTab(tabLayout.newTab().setText("Cosmetics"));
        tabLayout.addTab(tabLayout.newTab().setText("Jewelry"));
        tabLayout.addTab(tabLayout.newTab().setText("Apparel"));
        tabLayout.addTab(tabLayout.newTab().setText("VehicleParts"));
        tabLayout.addTab(tabLayout.newTab().setText("Constructions"));
        tabLayout.addTab(tabLayout.newTab().setText("Fertilizers"));
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
                        viewPager.removeAllViews();
                        fragment = new CartFrag(Uid);
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