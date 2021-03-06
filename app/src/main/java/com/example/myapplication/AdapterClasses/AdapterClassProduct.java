package com.example.myapplication.AdapterClasses;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.DataClasses.ProductDataClass;
import com.example.myapplication.R;
import com.example.myapplication.Buyer.DetailActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AdapterClassProduct extends RecyclerView.Adapter<AdapterClassProduct.ViewHolder>{
    private List<ProductDataClass> productDataClasses;
    Context context;
    String category;

    public AdapterClassProduct(List<ProductDataClass> productDataClasses, Context context, String category ) {
        this.productDataClasses = productDataClasses;
        this.context = context;
        this.category = category;
    }
    @SuppressLint("NotifyDataSetChanged")
    public void filterList(ArrayList<ProductDataClass> filterllist) {
        // below line is to add our filtered
        // list in our course array list.
        productDataClasses = filterllist;
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged();
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
        holder.tvPPrice.setText(context.getString(R.string.rss)+" "+productDataClass.getProductPrice());
        Glide.with(context).load(productDataClass.getImage()).into(holder.imgproducts);
        holder.tvPmin.setText(productDataClass.getProductMinSale()+context.getString(R.string.min_order));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("image",productDataClass.getImage());
                intent.putExtra("name",productDataClass.getProductName());
                intent.putExtra("price",productDataClass.getProductPrice());
                intent.putExtra("max",productDataClass.getProductMaxSale());
                intent.putExtra("min",productDataClass.getProductMinSale());
                intent.putExtra("detail",productDataClass.getProductDetail());
                intent.putExtra("id", productDataClass.getProductId());
                intent.putExtra("company",productDataClass.getProductCompany());
                intent.putExtra("quantity",productDataClass.getProductQuantity());
                intent.putExtra("shopid",productDataClass.getProductShopId());
                intent.putExtra("type",productDataClass.getProductType());
                intent.putExtra("category",category);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return productDataClasses.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvPName, tvPPrice, tvPmin;
        ImageView imgproducts;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPName = itemView.findViewById(R.id.tvProductNameB);
            tvPPrice = itemView.findViewById(R.id.tvProductPrice);
            tvPmin = itemView.findViewById(R.id.tvProductMOQ);
            imgproducts = itemView.findViewById(R.id.ImgProducts);
        }
    }
}