package com.example.myapplication.Buyer.Fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.os.UserHandle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Buyer.BuyerActivity;
import com.example.myapplication.Buyer.MyOrderActivity;
import com.example.myapplication.Other.SignInActivity;
import com.example.myapplication.R;
import com.example.myapplication.Settings.BottomSheetDialog;
import com.example.myapplication.Settings.NameInterface;
import com.example.myapplication.Settings.SettingActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Set;

public class AccountFrag extends Fragment {
    View v;
    TextView account;
    ImageView setting;
    String Fname="";
    String UserName="";
    Toolbar toolbar;
    SharedPreferences sharedPreferences;
    String fragment = "Which";
    TextView viewAll;
    public static final String filename = "login";
    public static final String name = "Abid";
    public static final String userName = "username";
    public static final String password = "password";
    public AccountFrag()
    {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_account, container, false);
        toolbar = v.findViewById(R.id.toolBar2);
        viewAll = v.findViewById(R.id.view_all);
        BottomNavigationView bottomNavigationView = v.findViewById(R.id.bottom_navigation_order);
        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Account");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(v.getContext(), BuyerActivity.class);
                startActivity(intent);
            }
        });
        sharedPreferences = v.getContext().getSharedPreferences(filename, Context.MODE_PRIVATE);
        if (sharedPreferences.contains(userName)){
            Fname =sharedPreferences.getString(name,"");
            UserName =sharedPreferences.getString(userName,"");

        }
        account = v.findViewById(R.id.add_account);
        setting = v.findViewById(R.id.setting);
        account.setText(Fname);
        if (Fname.isEmpty())
        {
            account.setText("SignIn or Register");
        }

            account.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (Fname==""){

                        Intent intent = new Intent(v.getContext(), SignInActivity.class);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(v.getContext(), "Already Account Added", Toast.LENGTH_LONG).show();
                    }

                }
            });

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(v.getContext(), SettingActivity.class);
                startActivity(intent);
            }
        });
        viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (UserName=="")
                {
                    Toast.makeText(v.getContext(), "Login to your account", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = new Intent(v.getContext(), MyOrderActivity.class);
                    intent.putExtra(fragment, "all");
                    intent.putExtra("U", UserName);
                    startActivity(intent);
                }
            }
        });
        return v;

    }
    BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener =  new BottomNavigationView.OnNavigationItemSelectedListener() {
        @SuppressLint("NonConstantResourceId")
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Intent intent = new Intent(v.getContext(), MyOrderActivity.class);
            switch (item.getItemId()){
                case R.id.to_pay:
                    if (UserName.equals(""))
                    {
                        Toast.makeText(v.getContext(), "Login to your account", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        intent.putExtra(fragment, "pay");
                        intent.putExtra("U", UserName);
                        startActivity(intent);
                    }
                    return true;
                case R.id.to_ship:
                    if (UserName.equals(""))
                    {
                        Toast.makeText(v.getContext(), "Login to your account", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        intent.putExtra(fragment, "ship");
                        intent.putExtra("U", UserName);
                        startActivity(intent);
                    }
                    return true;
                case R.id.to_recieve:
                    if (UserName.equals(""))
                    {
                        Toast.makeText(v.getContext(), "Login to your account", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        intent.putExtra(fragment, "receive");
                        intent.putExtra("U", UserName);
                        startActivity(intent);
                    }
                    return true;
                case R.id.to_review:
                    if (UserName.equals(""))
                    {
                        Toast.makeText(v.getContext(), "Login to your account", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        intent.putExtra(fragment, "review");
                        intent.putExtra("U", UserName);
                        startActivity(intent);
                    }
                    return true;
            }
            return false;
        }
    };

    @Override
    public void onDetach() {
        super.onDetach();
        Intent intent = new Intent(v.getContext(), BuyerActivity.class);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
       boolean b = SettingActivity.bool;
       if (b){
           account.setText("SignIn or Register");
           Fname="";
           UserName="";
       }
        else
       {
           sharedPreferences = v.getContext().getSharedPreferences(filename, Context.MODE_PRIVATE);
           if (sharedPreferences.contains(userName)){
             String  Fname =sharedPreferences.getString(name,"");
             String Username = sharedPreferences.getString(userName,"");
             account.setText(Fname);
               UserName = Username;
           }
       }
    }
}