package com.example.myapplication.Settings;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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
import com.example.myapplication.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        sharedPreferences = this.getSharedPreferences(filename, Context.MODE_PRIVATE);
        btnInformation = findViewById(R.id.btnAccount_Information);
        btnlanguage = findViewById(R.id.btnLanguage);
        btnhelp = findViewById(R.id.btnHelp);
        btnlogout = findViewById(R.id.btnLogOut);
        LanguageManager languageManager = new LanguageManager(this);
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
               // languageManager.updateResource("en");
                //recreate();
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
}