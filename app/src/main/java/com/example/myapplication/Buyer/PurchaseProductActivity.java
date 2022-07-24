package com.example.myapplication.Buyer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myapplication.AdapterClasses.AdapterClassProduct;
import com.example.myapplication.AdapterClasses.ClickListenerAddress;
import com.example.myapplication.DataClasses.BuyerAddressDataClass;
import com.example.myapplication.DataClasses.OrderDataClass;
import com.example.myapplication.DataClasses.ProductDataClass;
import com.example.myapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PurchaseProductActivity extends AppCompatActivity {

    TextView pName,pPrice,pQuantity,totalPrice,subTotal,shipFee,grandtotal,tvship, tvAddress;
    String Name,Price, image;
    String uid;
    int Quantity = 0;
    int TotalPrice1 = 0;
    int ship=100;
    int gtotal= 0;
    String product_id;
    String shop_id;
    ImageView pImage;
    TextView proceedtopay;
    String uniqueID;
    BuyerAddressDataClass  buyerAddressDataClass;
    SharedPreferences sharedPreferences;
    public static final String filename = "order";
    public static final String TotalPrice = "TotalPrice";
    public static final String TotalQuantity = "TotalQuantity";
    public static final String ProductName = "ProductName";
    public static final String ProductImage = "ProductImage";
    public static final String ProductPrice = "ProductPrice";
    public static final String UserId = "UserId";
    public static final String ProductId = "ProductId";
    public static final String ShopId = "ShopId";
    private DatabaseReference mFirebaseDatabase;
    private DatabaseReference mFirebaseDatabase1;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_product);
        sharedPreferences = getSharedPreferences(filename, Context.MODE_PRIVATE);
        uniqueID = UUID.randomUUID().toString();
        if (sharedPreferences.contains(ProductName)){
          Name = sharedPreferences.getString(ProductName,"");
          Price = sharedPreferences.getString(ProductPrice,"");
          image = sharedPreferences.getString(ProductImage,"");
          product_id = sharedPreferences.getString(ProductId,"");
          shop_id = sharedPreferences.getString(ShopId,"");
          uid = sharedPreferences.getString(UserId,"");
          TotalPrice1 = sharedPreferences.getInt(TotalPrice,0);
          Quantity = sharedPreferences.getInt(TotalQuantity,0);
        }
        Toast.makeText(this, shop_id, Toast.LENGTH_SHORT).show();
        tvAddress = findViewById(R.id.Address);
        mFirebaseDatabase = FirebaseDatabase.getInstance().getReference("User");
        mFirebaseDatabase1 = FirebaseDatabase.getInstance().getReferenceFromUrl("https://login-b93ab-default-rtdb.firebaseio.com/");
        Shipment();
        pName = findViewById(R.id.tvProductNameP);
        pPrice = findViewById(R.id.tvProductPriceP);
        pQuantity = findViewById(R.id.tvProductQuantityP);
        totalPrice = findViewById(R.id.tvTotalPrice);
        subTotal = findViewById(R.id.subtotal);
        shipFee = findViewById(R.id.shipPrice);
        grandtotal = findViewById(R.id.grandTotal);
        pImage = findViewById(R.id.ImgProductP);
        tvship = findViewById(R.id.tvShip);
        proceedtopay = findViewById(R.id.proceedToPay);
        Glide.with(this).load(image).into(pImage);
        tvship.setText("  Rs. "+ship+"\n\n  Home Delivery\n\n  Get by 16-18 Jun");
        pName.setText(Name);
        pPrice.setText("Rs. "+Price+" (per item)");
        pQuantity.setText("Quantity: "+Quantity);
        totalPrice.setText("Rs. "+TotalPrice1);
        subTotal.setText("Rs. "+TotalPrice1);
        shipFee.setText("Rs. "+ship);
        gtotal = ship+TotalPrice1;
        grandtotal.setText("Grand Total: "+gtotal);
        tvAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(PurchaseProductActivity.this,AddressActivity.class);
                intent1.putExtra("uid",uid);
                startActivity(intent1);
            }
        });
        proceedtopay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tvAddress.getText().equals("+ Add Address")||buyerAddressDataClass.getAddress().equals(""))
                {
                    Toast.makeText(PurchaseProductActivity.this, "Please Add Shipment Address!", Toast.LENGTH_SHORT).show();
                }
                else {
                    String order_Id;
                    order_Id = uid+uniqueID;
                    OrderProduct(order_Id);
                    ShopRecord(order_Id);
                }
            }
        });
    }
    public void Shipment()
    {
        mFirebaseDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(uid)) {
                        if (dataSnapshot.child(uid).hasChild("Shipment Address"))
                        {
                            buyerAddressDataClass = dataSnapshot.child(uid).child("Shipment Address").getValue(BuyerAddressDataClass.class);
                            tvAddress.setText("Name: "+buyerAddressDataClass.getName()+"\n"+
                                  "Phone: "+buyerAddressDataClass.getPhone()+"\n"+
                                    "Address: "+buyerAddressDataClass.getAddress());
                        }
                        else {
                          //  Toast.makeText(PurchaseProductActivity.this, "Add Shipment Address", Toast.LENGTH_LONG).show();
                            tvAddress.setText("+ Add Address");
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(PurchaseProductActivity.this, "Fail to Update Data." + databaseError, Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void OrderProduct(String orderID)
    {
        mFirebaseDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                OrderDataClass orderDataClass = new OrderDataClass(orderID,product_id,Integer.toString(Quantity),Integer.toString(TotalPrice1),Integer.toString(ship),Integer.toString(gtotal));
                if (dataSnapshot.hasChild(uid)) {
                    mFirebaseDatabase.child(uid).child("Product Orders").child(orderID).setValue(orderDataClass);
                    Toast.makeText(PurchaseProductActivity.this, "Your Order Placed", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(PurchaseProductActivity.this, "Not Save", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(PurchaseProductActivity.this, "Fail to Update Data." + databaseError, Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void ShopRecord(String orderID)
    {
        mFirebaseDatabase1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                OrderDataClass orderDataClass = new OrderDataClass(orderID,product_id,Integer.toString(Quantity),Integer.toString(TotalPrice1),Integer.toString(ship),Integer.toString(gtotal));
                    mFirebaseDatabase1.child("Orders").child(shop_id).child(orderID).setValue(orderDataClass);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(PurchaseProductActivity.this, "Fail to Update Data." + databaseError, Toast.LENGTH_SHORT).show();
            }
        });
    }

}