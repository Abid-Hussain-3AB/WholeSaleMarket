package com.example.myapplication.Buyer.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

public class ApparelFrag extends Fragment {

    private DatabaseReference mFirebaseDatabase;
    RecyclerView recyclerView;
    AdapterClassProduct adapterClassProduct;
    private List<ProductDataClass> productDataClassesList;
    View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_apparel, container, false);
        recyclerView = v.findViewById(R.id.rc_apparel);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2,RecyclerView.VERTICAL,false));
        productDataClassesList = new ArrayList<>();
        mFirebaseDatabase = FirebaseDatabase.getInstance().getReference("Products");
        getData();
        return v;
    }
    private void getData()
    {
        mFirebaseDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (ds.hasChild("Apparel")) {
                        for (DataSnapshot dataSnapshot1 : ds.child("Apparel").getChildren()) {
                            ProductDataClass sdc = dataSnapshot1.getValue(ProductDataClass.class);
                            productDataClassesList.add(sdc);
                        }
                    }
                }
                adapterClassProduct = new AdapterClassProduct(productDataClassesList, getContext(),"Apparel");
                recyclerView.setAdapter(adapterClassProduct);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}