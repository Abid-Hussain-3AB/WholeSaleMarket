package com.example.myapplication.Buyer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.AdapterClasses.ClickListenerAddress;
import com.example.myapplication.DataClasses.BuyerAddressDataClass;
import com.example.myapplication.DataClasses.ProductDataClass;
import com.example.myapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddressActivity extends AppCompatActivity {

    EditText etName, etPhone, etProvince, etCity, etAddress;
    String Name, Phone, Province, City, Address;
    Button btnSave;
    private DatabaseReference mFirebaseDatabase;
    String user_id;
    ClickListenerAddress clickListenerAddress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        Intent intent = getIntent();
        user_id =  intent.getStringExtra("uid");
        etName = findViewById(R.id.User_NameAA);
        etPhone = findViewById(R.id.User_PhoneAA);
        etProvince = findViewById(R.id.User_ProvinceAA);
        etCity = findViewById(R.id.User_CityAA);
        etAddress = findViewById(R.id.User_AddressAA);
        btnSave = findViewById(R.id.idBtnSaveA);
        mFirebaseDatabase = FirebaseDatabase.getInstance().getReference("User");
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Name = etName.getText().toString();
                Phone = etPhone.getText().toString();
                Province = etProvince.getText().toString();
                City = etCity.getText().toString();
                Address = etAddress.getText().toString();
                AddAddress();
                Intent intent1 = new Intent(AddressActivity.this,PurchaseProductActivity.class);
             //   intent1.putExtra("Name",Name);
              //  intent1.putExtra("Phone",Phone);
               // intent1.putExtra("Province",Province);
               // intent1.putExtra("City",City);
               // intent1.putExtra("Address",Address);
               startActivity(intent1);
                finish();
               // Toast.makeText(AddressActivity.this, user_id, Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void AddAddress()
    {
        mFirebaseDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                BuyerAddressDataClass address = new BuyerAddressDataClass(Name,Phone,Province,City,Address);
                if (dataSnapshot.hasChild(user_id)) {
                    mFirebaseDatabase.child(user_id).child("Shipment Address").setValue(address);
                    Toast.makeText(AddressActivity.this, "Address added Successfully", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(AddressActivity.this, "Not Added", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(AddressActivity.this, "Fail to Update Data." + databaseError, Toast.LENGTH_SHORT).show();
            }
        });
    }
}