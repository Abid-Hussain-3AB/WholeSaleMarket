package com.example.myapplication.Seller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.Admin.AdminActivity;
import com.example.myapplication.DataClasses.ProductDataClass;
import com.example.myapplication.Other.SplashActivity;
import com.example.myapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditDelProductActivity extends AppCompatActivity {
    EditText etproductname, etproducttype,etproductmompany, etproductprice, etminsale, etmaxsale, etproductquantity,etproductdetail;
    private DatabaseReference mFirebaseDatabase;
    Button btndel,btnedit,btnconfirm,btncancel;
    String PId,image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_del_product);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Delete, Edit Product");
        Intent intent = getIntent();
        etproductname = findViewById(R.id.etProductNameE);
        etproducttype = findViewById(R.id.etProductTypeE);
        etproductmompany = findViewById(R.id.etProductCompanyE);
        etproductprice = findViewById(R.id.etProducPriceE);
        etminsale = findViewById(R.id.etProductMinForSaleE);
        etmaxsale = findViewById(R.id.etProductMaxForSaleEE);
        etproductquantity = findViewById(R.id.etProductQuantityE);
        etproductdetail = findViewById(R.id.etProductDetailE);
        btndel = findViewById(R.id.btnDel);
        btnedit = findViewById(R.id.btnEdit);
        btnconfirm = findViewById(R.id.btnConfirm);
        btncancel = findViewById(R.id.btnCancel);
        btnconfirm.setVisibility(View.GONE);
        btncancel.setVisibility(View.GONE);
        etproductname.setVisibility(View.GONE);
        etproducttype.setVisibility(View.GONE);
        etproductmompany.setVisibility(View.GONE);
        etproductprice.setVisibility(View.GONE);
        etminsale.setVisibility(View.GONE);
        etmaxsale.setVisibility(View.GONE);
        etproductquantity.setVisibility(View.GONE);
        etproductdetail.setVisibility(View.GONE);
        PId = intent.getStringExtra("ID");
        image = intent.getStringExtra("image");
        mFirebaseDatabase = FirebaseDatabase.getInstance().getReference("Products");
        btndel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(EditDelProductActivity.this);
                alertDialog.setTitle("Do you want to delete this product?");
                alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DellProduct();
                    }
                });
                alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                alertDialog.show();
            }
        });
        btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etproductname.setVisibility(View.VISIBLE);
                etproducttype.setVisibility(View.VISIBLE);
                etproductmompany.setVisibility(View.VISIBLE);
                etproductprice.setVisibility(View.VISIBLE);
                etminsale.setVisibility(View.VISIBLE);
                etmaxsale.setVisibility(View.VISIBLE);
                etproductquantity.setVisibility(View.VISIBLE);
                etproductdetail.setVisibility(View.VISIBLE);
                getData();
                btndel.setVisibility(View.GONE);
                btnconfirm.setVisibility(View.VISIBLE);
                btncancel.setVisibility(View.VISIBLE);
            }
        });
        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  //  startActivity(new Intent(EditDelProductActivity.this, ShowProductsActivity.class));
                //Intent intent1 = new Intent();
               // intent1.putExtra("delete",true);
                //setResult(Activity.RESULT_OK,intent1);
                finish();
            }
        });
        btnconfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(EditDelProductActivity.this);
                alertDialog.setTitle("Do you want to Edit this product?");
                alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if ((TextUtils.isEmpty(etproductname.getText().toString()))) {
                            Toast.makeText(EditDelProductActivity.this, "Please enter Product Name .", Toast.LENGTH_LONG).show();
                        }
                        else if ((TextUtils.isEmpty(etproducttype.getText().toString()))){
                            Toast.makeText(EditDelProductActivity.this, "Please enter Product Type.", Toast.LENGTH_LONG).show();
                        }
                        else if ((TextUtils.isEmpty(etproductmompany.getText().toString()))){
                            Toast.makeText(EditDelProductActivity.this, "Please enter Product Company.", Toast.LENGTH_LONG).show();
                        }
                        else if ((TextUtils.isEmpty(etproductprice.getText().toString()))){
                            Toast.makeText(EditDelProductActivity.this, "Please enter Product Price.", Toast.LENGTH_LONG).show();
                        }
                        else if ((TextUtils.isEmpty(etminsale.getText().toString()))){
                            Toast.makeText(EditDelProductActivity.this, "Please enter Product Minimum For Sale.", Toast.LENGTH_LONG).show();
                        }
                        else if ((TextUtils.isEmpty(etmaxsale.getText().toString()))){
                            Toast.makeText(EditDelProductActivity.this, "Please enter Product Maximum For Sale.", Toast.LENGTH_LONG).show();
                        }
                        else if ((TextUtils.isEmpty(etproductquantity.getText().toString())))
                        {
                            Toast.makeText(EditDelProductActivity.this, "Please enter Product Quantity.", Toast.LENGTH_LONG).show();
                        }
                        else if ((TextUtils.isEmpty(etproductdetail.getText().toString()))){
                            Toast.makeText(EditDelProductActivity.this, "Please enter Product Detail.", Toast.LENGTH_LONG).show();
                        }
                        else {
                            EditProduct();
                        }
                    }
                });
                alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                alertDialog.show();
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
    public void DellProduct()
    {
        mFirebaseDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()) {
                            dataSnapshot2.child(PId).getRef().removeValue();
                            Toast.makeText(EditDelProductActivity.this, "Product Delete Successfully", Toast.LENGTH_LONG ).show();
                        }
                    }
                }
                btncancel.setVisibility(View.VISIBLE);
                btncancel.setText("Back to Products");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void getData()
    {
        mFirebaseDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()) {
                            if (dataSnapshot2.hasChild(PId)) {
                                ProductDataClass sdc = dataSnapshot2.child(PId).getValue(ProductDataClass.class);
                                 assert sdc != null;
                                String name = sdc.getProductName();
                                String type = sdc.getProductType();
                                String company = sdc.getProductCompany();
                                String price = sdc.getProductPrice();
                                String min = sdc.getProductMinSale();
                                String max = sdc.getProductMaxSale();
                                String quantity = sdc.getProductQuantity();
                                String detail = sdc.getProductDetail();
                                etproductname.setText(name);
                                etproducttype.setText(type);
                                etproductmompany.setText(company);
                                etproductprice.setText(price);
                                etminsale.setText(min);
                                etmaxsale.setText(max);
                                etproductquantity.setText(quantity);
                                etproductdetail.setText(detail);
                            }
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    public void EditProduct()
    {String name = etproductname.getText().toString();
        String type = etproducttype.getText().toString();
        String company = etproductmompany.getText().toString();
        String price = etproductprice.getText().toString();
        String min = etminsale.getText().toString();
        String max = etmaxsale.getText().toString();
        String quantity = etproductquantity.getText().toString();
        String detail = etproductdetail.getText().toString();
        mFirebaseDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ProductDataClass user = new ProductDataClass(name, type,company,price,min,max,quantity,detail,PId,image);
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()) {
                        if (dataSnapshot2.hasChild(PId)) {
                            dataSnapshot2.child(PId).getRef().setValue(user);
                            Toast.makeText(EditDelProductActivity.this, "Product Update Successfully", Toast.LENGTH_LONG).show();
                        }
                    }
                }
              //  Intent intent1 = new Intent();
               //  intent1.putExtra("delete",true);
                 //setResult(RESULT_OK,intent1);
                // finish();
                //btncancel.setText("Back to Products");
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(EditDelProductActivity.this, "Fail to Update Data." + databaseError, Toast.LENGTH_SHORT).show();
            }
        });
    }
}