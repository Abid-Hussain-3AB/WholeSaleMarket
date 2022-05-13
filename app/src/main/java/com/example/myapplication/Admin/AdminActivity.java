package com.example.myapplication.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.myapplication.AdapterClasses.AdapterClassAdmin;
import com.example.myapplication.AdapterClasses.AdapterFragments;
import com.example.myapplication.AdapterClasses.AdapterFragmentsAdmin;
import com.example.myapplication.DataClasses.ShopDataClass;
import com.example.myapplication.R;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminActivity extends AppCompatActivity {

    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    AdapterFragmentsAdmin fragmentAdapter;

    private DatabaseReference mFirebaseDatabase;
    RecyclerView recyclerView;
    AdapterClassAdmin adapterClassAdmin;
    private List<ShopDataClass> shopDataClassList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        toolbar = findViewById(R.id.toolBaradmin);
        tabLayout = findViewById(R.id.tablayoutadmin);
        viewPager = findViewById(R.id.viewpageradmin);
        setSupportActionBar(toolbar);
        tabLayout.addTab(tabLayout.newTab().setText("Approved Shops"));
        tabLayout.addTab(tabLayout.newTab().setText("Requested Shops"));
       // recyclerView = findViewById(R.id.admin_rc);
       // recyclerView.setHasFixedSize(true);
       // recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        shopDataClassList = new ArrayList<>();
        mFirebaseDatabase = FirebaseDatabase.getInstance().getReferenceFromUrl("https://login-b93ab-default-rtdb.firebaseio.com/");
       // getData();
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        fragmentAdapter = new AdapterFragmentsAdmin(getSupportFragmentManager(),tabLayout.getTabCount());
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
    private void getData()
    {
        mFirebaseDatabase.child("Admin").child("admin1").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                    {
                        ShopDataClass sdc = dataSnapshot1.getValue(ShopDataClass.class);
                        shopDataClassList.add(sdc);
                    }
                    adapterClassAdmin = new AdapterClassAdmin(shopDataClassList,AdminActivity.this);
                    recyclerView.setAdapter(adapterClassAdmin);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}