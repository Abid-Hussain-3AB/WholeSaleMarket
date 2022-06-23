package com.example.myapplication.Buyer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.myapplication.AdapterClasses.AdapterClassProduct;
import com.example.myapplication.DataClasses.ProductDataClass;
import com.example.myapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SearchActivity extends AppCompatActivity {
    private DatabaseReference mFirebaseDatabase;
    RecyclerView recyclerView;
    AdapterClassProduct adapterClassProduct;
    private List<ProductDataClass> productDataClassesList;
    private List<ProductDataClass> productDataClassesListS;
    androidx.appcompat.widget.SearchView searchView;
    androidx.appcompat.widget.Toolbar toolbar;
    ProductDataClass sdc;
    ProductDataClass result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Search Products");
        mFirebaseDatabase = FirebaseDatabase.getInstance().getReferenceFromUrl("https://login-b93ab-default-rtdb.firebaseio.com/");
        getData();
        recyclerView = findViewById(R.id.rc_search);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2,RecyclerView.VERTICAL,false));
        productDataClassesList = new ArrayList<>();
        productDataClassesListS = new ArrayList<>();
        toolbar = findViewById(R.id.toolbarSearch);
        searchView = findViewById(R.id.search1);
        searchView.setIconifiedByDefault(false);
        searchView.setFocusable(true);
        searchView.requestFocus();
        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                productDataClassesListS.clear();
                for (int i = 0; i<productDataClassesList.size(); i++)
                {
                    if (productDataClassesList.get(i).getProductType().toLowerCase(Locale.ROOT).equals(query.toLowerCase(Locale.ROOT)))
                    {
                       // Toast.makeText(Activity_Settings.this, "Success", Toast.LENGTH_SHORT).show();
                        result = productDataClassesList.get(i);
                        productDataClassesListS.add(result);
                        adapterClassProduct = new AdapterClassProduct(productDataClassesListS, SearchActivity.this);
                        recyclerView.setAdapter(adapterClassProduct);
                    }
                }
              return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
               // adapter.getFilter().filter(newText);
                return false;
            }
        });

    }
    private void getData()
    {
        mFirebaseDatabase.child("Products").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    for (DataSnapshot dataSnapshot1 : ds.getChildren())
                    {
                        for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren())
                        {
                            sdc = dataSnapshot2.getValue(ProductDataClass.class);
                            productDataClassesList.add(sdc);
                        }
                    }
                }
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