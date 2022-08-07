package com.example.myapplication.Buyer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.DataClasses.BuyerAddressDataClass;
import com.example.myapplication.R;
import com.example.myapplication.Settings.AppCompact;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddressActivity extends AppCompact {

    EditText etName, etPhone, etProvince, etCity, etAddress;
    String Name, Phone, Province, City, Address;
    Button btnSave;
    private DatabaseReference mFirebaseDatabase;
    BuyerAddressDataClass  buyerAddressDataClass;
    String user_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Shipment Address");
        Intent intent = getIntent();
        user_id =  intent.getStringExtra("uid");
        etName = findViewById(R.id.User_NameAA);
        etPhone = findViewById(R.id.User_PhoneAA);
        etProvince = findViewById(R.id.User_ProvinceAA);
        etCity = findViewById(R.id.User_CityAA);
        etAddress = findViewById(R.id.User_AddressAA);
        btnSave = findViewById(R.id.idBtnSaveA);
        mFirebaseDatabase = FirebaseDatabase.getInstance().getReference("User");
        Shipment();
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Name = etName.getText().toString();
                Phone = etPhone.getText().toString();
                Province = etProvince.getText().toString();
                City = etCity.getText().toString();
                Address = etAddress.getText().toString();
                AddAddress();
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
    public void Shipment()
    {
        mFirebaseDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(user_id)) {
                    if (dataSnapshot.child(user_id).hasChild("Shipment Address"))
                    {
                        buyerAddressDataClass = dataSnapshot.child(user_id).child("Shipment Address").getValue(BuyerAddressDataClass.class);
                        assert buyerAddressDataClass != null;
                        if (buyerAddressDataClass.getPhone() != null) {
                            etName.setText(buyerAddressDataClass.getName());
                            etPhone.setText(buyerAddressDataClass.getPhone());
                            etCity.setText(buyerAddressDataClass.getCity());
                            etProvince.setText(buyerAddressDataClass.getProvince());
                            etAddress.setText(buyerAddressDataClass.getAddress());
                        }

                    }
                    else {
                          Toast.makeText(AddressActivity.this, "Add Shipment Address", Toast.LENGTH_LONG).show();

                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(AddressActivity.this, "Fail to Update Data." + databaseError, Toast.LENGTH_SHORT).show();
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