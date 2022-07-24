package com.example.myapplication.Buyer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;

public class StartOrderActivity extends AppCompatActivity {
    int minteger = 0;
    int totalQuantity = 0;
    int totalPrice = 0;
    TextView price,min,max,startOrder;
    ImageView imageView;
    String Price, Min, Max, Image,name;
    String replacePrice;
    String uid;
    String product_id;
    String shop_id;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_order);
        Intent intent = getIntent();
        Price = intent.getStringExtra("price");
        Min = intent.getStringExtra("min");
        Max = intent.getStringExtra("max");
        replacePrice = Price.replaceAll("\\p{Punct}", "");
        Image = intent.getStringExtra("image");
        name = intent.getStringExtra("name");
        uid = intent.getStringExtra("uid");
        product_id = intent.getStringExtra("product_id");
        shop_id = intent.getStringExtra("shop_id");
        imageView = findViewById(R.id.ImgProductSt);
        price = findViewById(R.id.tvProductPriceSt);
        min = findViewById(R.id.tvProductMinSt);
        max = findViewById(R.id.tvProductMaxSt);
        startOrder = findViewById(R.id.startOrder);
        price.setText("Rs. "+Price);
        min.setText("Min: "+Min);
        max.setText("Max: "+ Max);
        Glide.with(this).load(Image).into(imageView);
        sharedPreferences = getSharedPreferences(filename, Context.MODE_PRIVATE);
    }
    public void increaseInteger(View view) {
        if (minteger<Integer.parseInt(Max))
        {
        minteger = minteger + 1;
        display(minteger);
        } else {

            Toast.makeText(this, "Reach Max", Toast.LENGTH_SHORT).show();
        }

    }public void decreaseInteger(View view) {
        if (minteger>1)
        {
        minteger = minteger - 1;
        display(minteger);
        } else {
            Toast.makeText(this, "No -ve Value", Toast.LENGTH_SHORT).show();
        }
    }
    public void onClick(View view){
        EditText et = findViewById(R.id.integer_number1);
      //  et.setText(minteger);
        String i = et.getText().toString();
        int j = Integer.parseInt(i);
        minteger = j;
        display(minteger);
    }

    private void display(int number) {
        EditText displayInteger = (EditText) findViewById(R.id.integer_number1);
        totalQuantity = +number;
        displayInteger.setText("" + number);
        total(totalQuantity);
    }
    private void total(int number) {
        int p =Integer.parseInt(replacePrice);
        totalPrice = number*p;
        TextView displayInteger = (TextView) findViewById(R.id.grandTotalSt);
        displayInteger.setText("Product Value (" +" 1" +" type "+number+" items )        Rs. " + totalPrice);

            startOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (number<Integer.parseInt(Min))
                    {
                        Toast.makeText(StartOrderActivity.this, "Please Order at least required Minimum Quantity!", Toast.LENGTH_LONG).show();
                    }
                    else {
                        Intent intent1 = new Intent(StartOrderActivity.this, PurchaseProductActivity.class);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt(TotalPrice, totalPrice);
                        editor.putInt(TotalQuantity, totalQuantity);
                        editor.putString(ProductName, name);
                        editor.putString(ProductImage, Image);
                        editor.putString(ProductPrice, Price);
                        editor.putString(UserId, uid);
                        editor.putString(ProductId, product_id);
                        editor.putString(ShopId, shop_id);
                        editor.commit();
                        startActivity(intent1);
                    }
                }
            });
    }
}