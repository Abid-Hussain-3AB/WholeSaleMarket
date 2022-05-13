package com.example.myapplication.Admin.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.AdapterClasses.AdapterClassAdmin;
import com.example.myapplication.AdapterClasses.AdapterClassProduct;
import com.example.myapplication.Admin.AdminActivity;
import com.example.myapplication.DataClasses.ProductDataClass;
import com.example.myapplication.DataClasses.ShopDataClass;
import com.example.myapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ApprovedShops extends Fragment {
    private DatabaseReference mFirebaseDatabase;
    private DatabaseReference mFirebaseDatabaseA;
    RecyclerView recyclerView;
    AdapterClassAdmin adapterClassAdmin;
    private List<ShopDataClass> shopDataClassList;
    List<String> arrayLists;
    View v;
    String id;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_approved_shops, container, false);
        recyclerView = v.findViewById(R.id.rc_approved_shops);
        recyclerView.setHasFixedSize(true);
        arrayLists = new ArrayList<String>();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        shopDataClassList = new ArrayList<>();
        mFirebaseDatabase = FirebaseDatabase.getInstance().getReferenceFromUrl("https://login-b93ab-default-rtdb.firebaseio.com/");
        mFirebaseDatabaseA = FirebaseDatabase.getInstance().getReference("Shops");
        data();
        return v;
    }
    private void data()
    {
        mFirebaseDatabaseA.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                    for (DataSnapshot dataSnapshot1 : snapshot.getChildren())
                    {
                        ShopDataClass sdc = dataSnapshot1.getValue(ShopDataClass.class);
                        shopDataClassList.add(sdc);
                    }
                    adapterClassAdmin = new AdapterClassAdmin(shopDataClassList, getContext());
                    recyclerView.setAdapter(adapterClassAdmin);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}