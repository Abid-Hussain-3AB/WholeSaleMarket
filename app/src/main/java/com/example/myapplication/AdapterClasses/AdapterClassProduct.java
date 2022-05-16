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
import com.example.myapplication.R;
import com.example.myapplication.Buyer.DetailActivity;

import java.util.List;

public class AdapterClassProduct extends RecyclerView.Adapter<AdapterClassProduct.ViewHolder>{
    private List<ProductDataClass> productDataClasses;
    Context context;

    public AdapterClassProduct(List<ProductDataClass> productDataClasses, Context context) {
        this.productDataClasses = productDataClasses;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterClassProduct.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_view, parent, false);
        return new AdapterClassProduct.ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull AdapterClassProduct.ViewHolder holder, int position) {
        ProductDataClass productDataClass = productDataClasses.get(position);
        holder.tvPName.setText(productDataClass.getProductName());
        holder.tvPType.setText(productDataClass.getProductType());
        Glide.with(context).load(productDataClass.getImage()).into(holder.imgproducts);
        holder.tvPQuantity.setText(productDataClass.getProductQuantity());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("image",productDataClass.getImage());
                intent.putExtra("name",productDataClass.getProductName());
                intent.putExtra("type",productDataClass.getProductType());
                intent.putExtra("quantity",productDataClass.getProductQuantity());
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
            tvPName = itemView.findViewById(R.id.tvProductNameB);
            tvPType = itemView.findViewById(R.id.tvProductPrice);
            tvPQuantity = itemView.findViewById(R.id.tvProductMOQ);
            imgproducts = itemView.findViewById(R.id.ImgProducts);
        }
    }
}
