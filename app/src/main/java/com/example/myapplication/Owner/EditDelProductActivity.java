package com.example.myapplication.Owner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.DataClasses.ProductDataClass;
import com.example.myapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditDelProductActivity extends AppCompatActivity {
    EditText editproductname, editproducttype, editproductquantity;
    private DatabaseReference mFirebaseDatabase;
    Button btndel,btnedit,btnconfirm;
    String PId,image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_del_product);
        Intent intent = getIntent();
        editproductname = findViewById(R.id.EditProductName);
        editproductname.setVisibility(View.GONE);
        editproducttype = findViewById(R.id.EditProductType);
        editproducttype.setVisibility(View.GONE);
        editproductquantity = findViewById(R.id.EditQuantity);
        editproductquantity.setVisibility(View.GONE);
        btndel = findViewById(R.id.btnDel);
        btnedit = findViewById(R.id.btnEdit);
        btnconfirm = findViewById(R.id.btnConfirm);
        btnconfirm.setVisibility(View.GONE);
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
                editproductname.setVisibility(View.VISIBLE);
                editproducttype.setVisibility(View.VISIBLE);
                editproductquantity.setVisibility(View.VISIBLE);
                getData();
                btnconfirm.setVisibility(View.VISIBLE);
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
                        EditProduct();
                        editproductname.setVisibility(View.GONE);
                        editproducttype.setVisibility(View.GONE);
                        editproductquantity.setVisibility(View.GONE);
                        btnconfirm.setVisibility(View.GONE);
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
    public void DellProduct()
    {
        mFirebaseDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()) {
                            dataSnapshot2.child(PId).getRef().removeValue();
                        }
                    }

                }
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
                                String quantity = sdc.getProductQuantity();
                                editproductname.setText(name);
                                editproducttype.setText(type);
                                editproductquantity.setText(quantity);
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
    {
        String name = editproductname.getText().toString();
        String type = editproducttype.getText().toString();
        String quantity = editproductquantity.getText().toString();

        mFirebaseDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ProductDataClass user = new ProductDataClass(name, type, quantity,PId,image);
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()) {
                        if (dataSnapshot2.hasChild(PId)) {
                            dataSnapshot2.child(PId).getRef().setValue(user);
                            Toast.makeText(EditDelProductActivity.this, "Product Update", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(EditDelProductActivity.this, "Fail to Update Data " + databaseError, Toast.LENGTH_SHORT).show();
            }
        });
    }
}