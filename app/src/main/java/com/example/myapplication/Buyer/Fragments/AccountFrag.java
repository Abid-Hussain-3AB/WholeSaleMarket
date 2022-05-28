package com.example.myapplication.Buyer.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
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
import com.example.myapplication.Settings.Activity_Settings;

import org.w3c.dom.Text;

public class AccountFrag extends Fragment {
    View v;
    TextView account;
    ImageView setting;
    String Fname="";
    Toolbar toolbar;
public AccountFrag(String fname)
{
Fname = fname;
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
                Intent intent = new Intent(v.getContext(), Activity_Settings.class);
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