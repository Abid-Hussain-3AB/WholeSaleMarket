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
import com.example.myapplication.DataClasses.OrderDataClass;
import com.example.myapplication.DataClasses.ProductDataClass;
import com.example.myapplication.DataClasses.ShopDataClass;
import com.example.myapplication.R;

import java.util.List;

public class AdapterOrder extends RecyclerView.Adapter<AdapterOrder.ViewHolder> {
    private List<OrderDataClass> OrderDataClass;
    private List<ProductDataClass> productDataClasses;
    Context context;

    public AdapterOrder(List<OrderDataClass> orderDataClass, List<ProductDataClass> productDataClasses, Context context) {
        this.OrderDataClass = orderDataClass;
        this.productDataClasses = productDataClasses;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterOrder.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.order_view,parent,false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull AdapterOrder.ViewHolder holder, int position) {
        OrderDataClass orderDataClass = OrderDataClass.get(position);
        ProductDataClass productDataClass = productDataClasses.get(position);
        holder.tvName.setText("Product Name: "+productDataClass.getProductName());
        holder.tvBill.setText("Grand Total: "+orderDataClass.getGrandTotal());
        holder.tvquantity.setText("Total Quantity: "+orderDataClass.getTotalQuantity());
        Glide.with(context).load(productDataClass.getImage()).into(holder.ProductImage);
    }

    @Override
    public int getItemCount() {
        return OrderDataClass.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvquantity, tvBill;
        ImageView ProductImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ProductImage = itemView.findViewById(R.id.ImgOrder);
            tvName = itemView.findViewById(R.id.tvOrderProductName);
            tvquantity = itemView.findViewById(R.id.tvOrderQuantity);
            tvBill = itemView.findViewById(R.id.tvTotalBill);
        }
    }
}
