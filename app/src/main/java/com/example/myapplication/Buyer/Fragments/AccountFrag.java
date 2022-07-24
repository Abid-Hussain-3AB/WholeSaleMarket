package com.example.myapplication.Buyer.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Buyer.BuyerActivity;
import com.example.myapplication.Other.SignInActivity;
import com.example.myapplication.R;
import com.example.myapplication.Buyer.SearchActivity;
import com.example.myapplication.Settings.SettingActivity;

public class AccountFrag extends Fragment {
    View v;
    TextView account;
    ImageView setting;
    String Fname="";
    Toolbar toolbar;
    SharedPreferences sharedPreferences;
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
        return v;

    }

    @Override
    public void onDetach() {
        super.onDetach();
        Intent intent = new Intent(v.getContext(), BuyerActivity.class);
        startActivity(intent);
    }
}