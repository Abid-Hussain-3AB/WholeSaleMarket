package com.example.myapplication.Settings;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityAccountInfoBinding;
public class AccountInfoActivity extends AppCompact {
    ActivityAccountInfoBinding binding;
    String Phone;
    String Name;
    NameInterface nameInterface;
    SharedPreferences sharedPreferences;
    public static final String filename = "login";
    public static final String name = "Abid";
    public static final String userName = "username";
    public static final String password = "password";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Account Information");
        sharedPreferences = this.getSharedPreferences(filename, Context.MODE_PRIVATE);
        if (sharedPreferences.contains(userName)){
            Name =sharedPreferences.getString(name,"");
            Phone = sharedPreferences.getString(userName,"");
        }
        nameInterface = new NameInterface() {
            @Override
            public void onclick(String Name) {
                binding.infoName.setText(Name);
            }
        };
        binding = DataBindingUtil.setContentView(this, R.layout.activity_account_info);
        binding.infoPhone.setText(Phone);
        binding.infoName.setText(Name);
        binding.infoName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(nameInterface);
                bottomSheetDialog.show(getSupportFragmentManager(),"Sheet");
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