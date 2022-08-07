package com.example.myapplication.Settings;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Buyer.BuyerActivity;
import com.example.myapplication.Buyer.DetailActivity;
import com.example.myapplication.Buyer.Fragments.AccountFrag;
import com.example.myapplication.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SettingActivity extends AppCompact {
    Button btnInformation, btnlanguage, btnhelp, btnlogout;
    String selectedLaanguage = "English";
    SharedPreferences sharedPreferences;
    public static final String filename = "login";
    public static final String name = "Abid";
    public static final String userName = "username";
    public static final String password = "password";
    String Fname;
    String User;
    public static boolean bool = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Settings");
        bool = false;
        sharedPreferences = this.getSharedPreferences(filename, Context.MODE_PRIVATE);
        if (sharedPreferences.contains(userName)){
            Fname =sharedPreferences.getString(name,"");
            User = sharedPreferences.getString(userName,"");
        }
        btnInformation = findViewById(R.id.btnAccount_Information);
        btnlanguage = findViewById(R.id.btnLanguage);
        btnhelp = findViewById(R.id.btnHelp);
        btnlogout = findViewById(R.id.btnLogOut);
        if (Fname==null)
        {
            btnlogout.setVisibility(View.GONE);
        }
        LanguageManager languageManager = new LanguageManager(this);
        btnInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Fname==null)
                {
                    Toast.makeText(SettingActivity.this, "NO ACCOUNT", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = new Intent(SettingActivity.this,AccountInfoActivity.class);
                    startActivity(intent);
                }
            }
        });
        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(SettingActivity.this);
                alertDialog.setTitle("Do you want to sign out");
                alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear();
                        editor.commit();
                        btnlogout.setVisibility(View.GONE);
                        bool = true;
                        Toast.makeText(SettingActivity.this, "Log out", Toast.LENGTH_SHORT).show();
                    }
                });
                alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                alertDialog.show();
            }
        });
        btnlanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog();
            }

            private void alertDialog() {
                String[] language = {"English", "اردو"};
                AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
                        builder.setTitle("Language/زبان");
                        builder.setSingleChoiceItems(language, 0, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                selectedLaanguage = language[i];
                            }
                        });
                        builder.setPositiveButton("APPLY", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (selectedLaanguage.equals("English"))
                                {
                                    languageManager.updateResource("en");
                                }
                                else if (selectedLaanguage.equals("اردو"))
                                {
                                    languageManager.updateResource("ur");
                                }
                                dialogInterface.dismiss();
                                Intent intent = getBaseContext().getPackageManager()
                                        .getLaunchIntentForPackage( getBaseContext().getPackageName() );
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }
                        }); builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        builder.show();
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