package com.example.myapplication.User;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import android.os.Bundle;

import com.example.myapplication.AdapterClasses.AdapterFragments;
import com.example.myapplication.R;
import com.google.android.material.tabs.TabLayout;
public class BuyerActivity extends AppCompatActivity {
    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    AdapterFragments fragmentAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer);
        toolbar = findViewById(R.id.toolBar);
        tabLayout = findViewById(R.id.tablayout);
        viewPager = findViewById(R.id.viewpager);
        viewPager = findViewById(R.id.viewpager);
        viewPager = findViewById(R.id.viewpager);
        viewPager = findViewById(R.id.viewpager);
        setSupportActionBar(toolbar);
        tabLayout.addTab(tabLayout.newTab().setText("All Products"));
        tabLayout.addTab(tabLayout.newTab().setText("Apparel"));
        tabLayout.addTab(tabLayout.newTab().setText("Apparel"));
        tabLayout.addTab(tabLayout.newTab().setText("Apparel"));
        tabLayout.addTab(tabLayout.newTab().setText("Jewelry"));
        tabLayout.addTab(tabLayout.newTab().setText("Jewelry"));
        tabLayout.addTab(tabLayout.newTab().setText("Jewelry"));
        tabLayout.addTab(tabLayout.newTab().setText("Jewelry"));
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

    }

}