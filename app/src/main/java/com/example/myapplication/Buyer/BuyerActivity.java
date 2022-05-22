package com.example.myapplication.Buyer;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.myapplication.AdapterClasses.AdapterFragments;
import com.example.myapplication.Buyer.Fragments.AccountFrag;
import com.example.myapplication.Buyer.Fragments.ApparelFrag;
import com.example.myapplication.Buyer.Fragments.VehiclePartsFrag;
import com.example.myapplication.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
public class BuyerActivity extends AppCompatActivity {
    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    AdapterFragments fragmentAdapter;
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
      //  toolbar = findViewById(R.id.toolBar);
        //tabLayout = findViewById(R.id.tablayout);
        //viewPager = findViewById(R.id.viewpager);
   /*     setSupportActionBar(toolbar);
//        tabLayout.addTab(tabLayout.newTab().setText("All Products"));
  //      tabLayout.addTab(tabLayout.newTab().setText("Electronics"));
    //    tabLayout.addTab(tabLayout.newTab().setText("Groceries"));
      //  tabLayout.addTab(tabLayout.newTab().setText("Home Appliances"));
       // tabLayout.addTab(tabLayout.newTab().setText("Cosmetics"));
        //tabLayout.addTab(tabLayout.newTab().setText("Jewelry"));
        //tabLayout.addTab(tabLayout.newTab().setText("Apparel"));
        //tabLayout.addTab(tabLayout.newTab().setText("VehicleParts"));
        //tabLayout.addTab(tabLayout.newTab().setText("Constructions"));//
        //tabLayout.addTab(tabLayout.newTab().setText("Fertilizers"));
        //tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        //fragmentAdapter = new AdapterFragments(getSupportFragmentManager(),tabLayout.getTabCount());
        //viewPager.setAdapter(fragmentAdapter);
        //viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        //tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
          //  @Override
            //public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
*/
        //AccountFrag accountFrag = new AccountFrag();
    }
    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @SuppressLint("NonConstantResourceId")
        final
        AccountFrag accountFrag = new AccountFrag();
        @SuppressLint("NonConstantResourceId")
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()){
                case R.id.home:
                    Toast.makeText(BuyerActivity.this, "Home", Toast.LENGTH_LONG).show();
                    return true;
                case R.id.account:
                    return true;
                case R.id.chat:
                    Toast.makeText(BuyerActivity.this, "Message", Toast.LENGTH_LONG).show();
                    return true;
                case R.id.cart:
                    fragment = new AccountFrag();
                    loadFragment(fragment);
                    Toast.makeText(BuyerActivity.this, "Cart", Toast.LENGTH_LONG).show();
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

}