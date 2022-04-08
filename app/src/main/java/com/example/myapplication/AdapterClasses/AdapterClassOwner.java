package com.example.myapplication.AdapterClasses;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.DataClasses.ProductDataClass;
import com.example.myapplication.Seller.EditDelProductActivity;
import com.example.myapplication.R;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class AdapterClassOwner extends RecyclerView.Adapter<AdapterClassOwner.ViewHolder> {
    private List<ProductDataClass> productDataClasses;
    Context context;
    StorageReference mStorageReference;

    public AdapterClassOwner(List<ProductDataClass> productDataClasses, Context context) {
        this.productDataClasses = productDataClasses;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.owner_products_view, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductDataClass productDataClass = productDataClasses.get(position);
        holder.tvPName.setText("Product Name: " + productDataClass.getProductName());
        holder.tvPType.setText("Product Type: " + productDataClass.getProductType());
        Glide.with(context).load(productDataClass.getImage()).into(holder.imgproducts);
        holder.tvPQuantity.setText("Product Quantity: " + productDataClass.getProductQuantity());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EditDelProductActivity.class);
                intent.putExtra("image",productDataClass.getImage());
                intent.putExtra("ID", productDataClass.getProductId());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return productDataClasses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvPName, tvPType, tvPQuantity;
        ImageView imgproducts;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPName = itemView.findViewById(R.id.tvProductName);
            tvPType = itemView.findViewById(R.id.tvProductType);
            tvPQuantity = itemView.findViewById(R.id.tvProductQuantity);
            imgproducts = itemView.findViewById(R.id.ImgProductowner);
        }
    }
}
