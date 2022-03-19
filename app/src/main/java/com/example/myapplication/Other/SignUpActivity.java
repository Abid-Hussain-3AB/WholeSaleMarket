package com.example.myapplication.Other;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.myapplication.DataClasses.SignUpDataClass;
import com.example.myapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUpActivity extends AppCompatActivity {
    EditText etname, etpassword, etPhone;
    RadioButton rdowner, rdUser;
    Button btnsubmit;
    private DatabaseReference mFirebaseDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        etname = findViewById(R.id.User_Name);
        etpassword = findViewById(R.id.Password);
        etPhone = findViewById(R.id.etPhoneNumber);
        rdowner = findViewById(R.id.rdOwner);
        rdUser = findViewById(R.id.rdUser);
        btnsubmit = findViewById(R.id.btnSubmit);
        if (etname.getText() != null && etpassword.getText() != null && etPhone.getText()!=null) {
       btnsubmit.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

                   if (rdowner.isChecked()) {
                       mFirebaseDatabase = FirebaseDatabase.getInstance().getReference("Owner");
                   } else if (rdUser.isChecked()) {
                       mFirebaseDatabase = FirebaseDatabase.getInstance().getReference("User");
                   }

                   createUser();
                   etname.setText("");
                   etpassword.setText("");
                   etPhone.setText("");

               Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
               startActivity(intent);
           }
       });

    }
    }
    private void createUser() {
        String Name = etname.getText().toString();
        String Password = etpassword.getText().toString();
        String Phone = etPhone.getText().toString();
        mFirebaseDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(Phone))
                {
                    Toast.makeText(SignUpActivity.this, "Already Exist", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    SignUpDataClass user = new SignUpDataClass(Name, Password);
                    mFirebaseDatabase.child(Phone).setValue(user);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
