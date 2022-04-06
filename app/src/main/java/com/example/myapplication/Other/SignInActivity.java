package com.example.myapplication.Other;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.myapplication.Admin.AdminActivity;
import com.example.myapplication.Owner.OwnerActivity;
import com.example.myapplication.R;
import com.example.myapplication.User.BuyerActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SignInActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Button btnsignup, SignIn;
    EditText etSignInName, etSignInPassword;
    private DatabaseReference aFirebaseDatabase;
    String item;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(SignInActivity.this);
        List Choice = new ArrayList();
        Choice.add("Buyer");
        Choice.add("Owner");
        Choice.add("Admin");
        ArrayAdapter dataAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, Choice);
        dataAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spinner.setAdapter(dataAdapter);
        btnsignup = findViewById(R.id.btnSignUp);
        SignIn = findViewById(R.id.btnSignIn);
        etSignInName = findViewById(R.id.etSignInPhone);
        etSignInPassword = findViewById(R.id.etSignInPassword);

        aFirebaseDatabase = FirebaseDatabase.getInstance().getReferenceFromUrl("https://login-b93ab-default-rtdb.firebaseio.com/");
        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
        SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Phone = etSignInName.getText().toString();
                String UPassword = etSignInPassword.getText().toString();
                if ((TextUtils.isEmpty(etSignInName.getText().toString()))) {
                    Toast.makeText(SignInActivity.this, "Please enter User Name.", Toast.LENGTH_LONG).show();
                }
                else if ((TextUtils.isEmpty(etSignInPassword.getText().toString()))){
                    Toast.makeText(SignInActivity.this, "Please enter Password.", Toast.LENGTH_LONG).show();
                }
                else {
                         if (item.equals("Owner")) {

                             aFirebaseDatabase.child("Owner").addListenerForSingleValueEvent(new ValueEventListener() {
                                 @Override
                                 public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                     if (dataSnapshot.hasChild(Phone)) {
                                         final String getpassword = dataSnapshot.child(Phone).child("password").getValue(String.class);
                                         if (getpassword.equals(UPassword)) {
                                             Toast.makeText(SignInActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();

                                             Intent intent = new Intent(SignInActivity.this, OwnerActivity.class);
                                             intent.putExtra("SHOP", Phone);
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
                         }
                    else if(item.equals("Buyer")) {
                             aFirebaseDatabase.child("User").addListenerForSingleValueEvent(new ValueEventListener() {
                                 @Override
                                 public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                     if (dataSnapshot.hasChild(Phone)) {
                                         final String getpassword = dataSnapshot.child(Phone).child("password").getValue(String.class);
                                         if (getpassword.equals(UPassword)) {
                                             Toast.makeText(SignInActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                                             Intent intent = new Intent(SignInActivity.this, BuyerActivity.class);
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
                         }
                         else if(item.equals("Admin")) {
                             aFirebaseDatabase.child("Admin").addListenerForSingleValueEvent(new ValueEventListener() {
                                 @Override
                                 public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                     if (dataSnapshot.hasChild(Phone)) {
                                         final String getpassword = "admin";
                                         if (getpassword.equals(UPassword)) {
                                             Toast.makeText(SignInActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                                             Intent intent = new Intent(SignInActivity.this, AdminActivity.class);
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
                         }
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
}