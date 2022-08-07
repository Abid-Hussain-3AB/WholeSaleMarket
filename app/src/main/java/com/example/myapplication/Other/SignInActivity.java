package com.example.myapplication.Other;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.example.myapplication.Seller.SellerActivity;
import com.example.myapplication.R;
import com.example.myapplication.Buyer.BuyerActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SignInActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Button SignIn;
    EditText etSignInName, etSignInPassword;
    private DatabaseReference aFirebaseDatabase;
    String item;
    TextView tv6;
    public static final String filename = "login";
    public static final String userName = "username";
    public static final String password = "password";
    public static final String nameq = "Abid";
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_new);
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(SignInActivity.this);
        List Choice = new ArrayList();
        Choice.add("Buyer");
        Choice.add("Seller");
        ArrayAdapter dataAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, Choice);
        dataAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spinner.setAdapter(dataAdapter);
        SignIn = findViewById(R.id.button4);
        etSignInName = findViewById(R.id.etSignInPhone);
        etSignInPassword = findViewById(R.id.etSignInPassword);
        tv6 = findViewById(R.id.textView6);
        sharedPreferences = getSharedPreferences(filename,Context.MODE_PRIVATE);
        if (sharedPreferences.contains(userName)){
            Intent intent = new Intent(SignInActivity.this, BuyerActivity.class);
            startActivity(intent);
        }
        aFirebaseDatabase = FirebaseDatabase.getInstance().getReferenceFromUrl("https://login-b93ab-default-rtdb.firebaseio.com/");
        tv6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
        SignIn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                if (IsInterConnected())
                {
                    String Phone = etSignInName.getText().toString();
                String UPassword = etSignInPassword.getText().toString();
                if ((TextUtils.isEmpty(etSignInName.getText().toString()))) {
                    Toast.makeText(SignInActivity.this, "Please enter User Name.", Toast.LENGTH_LONG).show();
                } else if ((TextUtils.isEmpty(etSignInPassword.getText().toString()))) {
                    Toast.makeText(SignInActivity.this, "Please enter Password.", Toast.LENGTH_LONG).show();
                } else {
                    if (item.equals("Seller")) {

                        aFirebaseDatabase.child("Owner").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.hasChild(Phone)) {
                                    final String getpassword = dataSnapshot.child(Phone).child("password").getValue(String.class);
                                    String name = dataSnapshot.child(Phone).child("username").getValue(String.class);
                                    if (getpassword.equals(UPassword)) {
                                        Toast.makeText(SignInActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(SignInActivity.this, SellerActivity.class);
                                        intent.putExtra("SHOP", Phone);
                                        intent.putExtra("NAME", name);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(SignInActivity.this, "Wrong User Name or Password or Role", Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    Toast.makeText(SignInActivity.this, "Wrong User Name or Password or Role", Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    } else if (item.equals("Buyer")) {
                        aFirebaseDatabase.child("User").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.hasChild(Phone)) {
                                    final String getpassword = dataSnapshot.child(Phone).child("password").getValue(String.class);
                                    final String Nameq = dataSnapshot.child(Phone).child("username").getValue(String.class);
                                    if (getpassword.equals(UPassword)) {
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString(userName,Phone);
                                        editor.putString(password,getpassword);
                                        editor.putString(nameq,Nameq);
                                        editor.commit();
                                        Toast.makeText(SignInActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(SignInActivity.this, BuyerActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(SignInActivity.this, "Wrong User Name or Password or Role", Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    Toast.makeText(SignInActivity.this, "Wrong User Name or Password or Role", Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }
            }
                else {
                    Toast.makeText(SignInActivity.this, "No Internet Connection", Toast.LENGTH_LONG).show();
                    Toast.makeText(SignInActivity.this, "Please Try Again", Toast.LENGTH_LONG).show();
                }

            }

        });

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        item = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    boolean IsInterConnected()
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo!=null)
        {
            if (networkInfo.isConnected())
                 return true;
            else
                return false;
        }
        else {
            return false;
        }
    }
    public boolean isConnected() throws InterruptedException, IOException {
        String command = "ping -c 1 google.com";
        return Runtime.getRuntime().exec(command).waitFor() == 0;
    }
}