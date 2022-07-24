package com.example.myapplication.Buyer.Fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.AdapterClasses.AdapterClassProduct;
import com.example.myapplication.AdapterClasses.AdapterClassProductCart;
import com.example.myapplication.AdapterClasses.ItemClickListener;
import com.example.myapplication.AdapterClasses.ItemClickListenerCart;
import com.example.myapplication.Buyer.BuyerActivity;
import com.example.myapplication.DataClasses.ProductDataClass;
import com.example.myapplication.R;
import com.example.myapplication.Seller.EditDelProductActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CartFrag extends Fragment {
    private DatabaseReference mFirebaseDatabase;
    RecyclerView recyclerView;
    AdapterClassProductCart adapterClassProduct;
    private List<ProductDataClass> productDataClassesList;
    ItemClickListener itemClickListener;
    ItemClickListenerCart itemClickListenerCart1;
    String u_id="";
    TextView cart;
    View v;
    Toolbar toolbar;
    Button btnDel;
    BottomNavigationView bottomNavigationView;
    public CartFrag(String name, ItemClickListenerCart itemClickListenerCart)
    {
        u_id =name;
        itemClickListenerCart1 = itemClickListenerCart;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       v =inflater.inflate(R.layout.fragment_cart, container, false);
        toolbar = v.findViewById(R.id.toolBar1);
        btnDel = v.findViewById(R.id.Del_Cart);
        btnDel.setVisibility(View.GONE);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("My Cart");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(v.getContext(),BuyerActivity.class);
                startActivity(intent);
            }
        });
        bottomNavigationView = v.findViewById(R.id.bottom_navigation);
        cart = v.findViewById(R.id.tvcart);
        recyclerView = v.findViewById(R.id.rc_cart);
        recyclerView.setHasFixedSize(true);
        productDataClassesList = new ArrayList<>();
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2,RecyclerView.VERTICAL,false));
        mFirebaseDatabase = FirebaseDatabase.getInstance().getReference("User");
        CartProductId();
        itemClickListener = new ItemClickListener() {
            @Override public void onClick(String s, int p)
            {
                // Notify adapter
                recyclerView.post(new Runnable() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override public void run()
                    {
                        adapterClassProduct.notifyDataSetChanged();
                    }
                });
                // Display toast
               // Toast.makeText(v.getContext(),s, Toast.LENGTH_SHORT).show();
                if (itemClickListener==null)
                {
                    btnDel.setVisibility(View.GONE);
                }
                else
                {
                    btnDel.setVisibility(View.VISIBLE);
                    btnDel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            DelCart(s);
                            productDataClassesList.remove(p);
                            adapterClassProduct.notifyItemRemoved(p);
                            adapterClassProduct.notifyDataSetChanged();
                            itemClickListenerCart1.onClick("sss");
                           // Toast.makeText(getContext(), "Ready For Del", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        };
       return v;
    }
    public void CartProductId()
    {
        mFirebaseDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(u_id)) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.child(u_id).child("my cart").getChildren()) {
                        ProductDataClass productDataClass = dataSnapshot1.getValue(ProductDataClass.class);
                        productDataClassesList.add(productDataClass);
                    }
                }
                else{
                    Toast.makeText(v.getContext(), "No Cart", Toast.LENGTH_LONG).show();
                }
                if (productDataClassesList.isEmpty())
                {
                    cart.setVisibility(View.VISIBLE);
                }
                adapterClassProduct = new AdapterClassProductCart(productDataClassesList, getContext(),itemClickListener,"");
                recyclerView.setAdapter(adapterClassProduct);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(v.getContext(), "Fail to Update Data." + databaseError, Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void DelCart(String id)
    {
        mFirebaseDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(u_id)) {
                   // for (DataSnapshot dataSnapshot1 : dataSnapshot.child(u_id).child("my cart").getChildren()){
                            dataSnapshot.child(u_id).child("my cart").child(id).getRef().removeValue();
                            adapterClassProduct.notifyDataSetChanged();
                  //  }
                    Toast.makeText(getContext(), "Cart Delete SuccessFully", Toast.LENGTH_LONG ).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    @Override
    public void onDetach() {
        super.onDetach();
        Intent intent = new Intent(v.getContext(), BuyerActivity.class);
        startActivity(intent);
    }
}