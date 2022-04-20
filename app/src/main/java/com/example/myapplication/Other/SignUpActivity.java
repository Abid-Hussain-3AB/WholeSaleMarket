package com.example.myapplication.Other;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;
import com.example.myapplication.DataClasses.SignUpDataClass;
import com.example.myapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class SignUpActivity extends AppCompatActivity {
    EditText etname, etpassword, etPhone;
    RadioButton rdowner, rdUser;
    Button btnsubmit;
    private DatabaseReference mFirebaseDatabase;

    private FirebaseAuth mAuth;
    private EditText edtOTP;
    private Button verifyOTPBtn, generateOTPBtn;
    private String verificationId;
    String s = "Create Account";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(s);
        etname = findViewById(R.id.User_Name);
        etpassword = findViewById(R.id.Password);
        etPhone = findViewById(R.id.etPhoneNumber);
        rdowner = findViewById(R.id.rdOwner);
        rdUser = findViewById(R.id.rdUser);
        btnsubmit = findViewById(R.id.btnSubmit);
        btnsubmit.setVisibility(View.GONE);
        mAuth = FirebaseAuth.getInstance();
        edtOTP = findViewById(R.id.idEdtOtp);
        verifyOTPBtn = findViewById(R.id.idBtnVerify);
        generateOTPBtn = findViewById(R.id.idBtnGetOtp);

        generateOTPBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(etPhone.getText().toString())) {
                    Toast.makeText(SignUpActivity.this, "Please enter a valid phone number.", Toast.LENGTH_SHORT).show();
                } else {
                    String phone = "+92" + etPhone.getText().toString();
                    sendVerificationCode(phone);
                }
            }
        });
        verifyOTPBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(edtOTP.getText().toString())) {
                    Toast.makeText(SignUpActivity.this, "Please enter OTP", Toast.LENGTH_SHORT).show();
                } else {
                    verifyCode(edtOTP.getText().toString());
                }
            }
        });
            btnsubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if ((TextUtils.isEmpty(etname.getText().toString()))) {
                        Toast.makeText(SignUpActivity.this, "Please enter User Name.", Toast.LENGTH_SHORT).show();
                    } else if ((TextUtils.isEmpty(etPhone.getText().toString()))) {
                        Toast.makeText(SignUpActivity.this, "Please enter a valid Phone Number..", Toast.LENGTH_SHORT).show();
                    } else if ((TextUtils.isEmpty(etpassword.getText().toString()))) {
                        Toast.makeText(SignUpActivity.this, "Please enter Password.", Toast.LENGTH_SHORT).show();
                    } else {
                        if (!rdowner.isChecked() && !rdUser.isChecked()) {
                            Toast.makeText(SignUpActivity.this, "Please Select any one Option.", Toast.LENGTH_SHORT).show();
                        } else {
                            if (rdowner.isChecked()) {
                                mFirebaseDatabase = FirebaseDatabase.getInstance().getReference("Owner");
                            } else if (rdUser.isChecked()) {
                                mFirebaseDatabase = FirebaseDatabase.getInstance().getReference("User");
                            }
                            createUser();
                            Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                            startActivity(intent);
                        }
                    }
                }
            });

    }
    private void signInWithCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                           btnsubmit.setVisibility(View.VISIBLE);
                        } else {
                            Toast.makeText(SignUpActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
    private void sendVerificationCode(String number) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(number)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(this)
                        .setCallbacks(mCallBack)
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }
    private final PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onCodeSent(String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationId = s;
        }
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            final String code = phoneAuthCredential.getSmsCode();
            if (code != null) {
                edtOTP.setText(code);
                verifyCode(code);
            }
        }
        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(SignUpActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    };
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithCredential(credential);
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
