package com.example.myapplication.User;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;

public class DetailActivity extends AppCompatActivity {
TextView Name, Type, Quantity;
ImageView imageView;
String imgUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Name = findViewById(R.id.tvProductNameD);
        Type = findViewById(R.id.tvProductTypeD);
        Quantity = findViewById(R.id.tvProductQuantityD);
        imageView = findViewById(R.id.ImgProductsD);
        Intent intent = getIntent();
        Name.setText(intent.getStringExtra("name"));
        Type.setText(intent.getStringExtra("type"));
        Quantity.setText(intent.getStringExtra("quantity"));
        imgUrl = intent.getStringExtra("image");
    }
}