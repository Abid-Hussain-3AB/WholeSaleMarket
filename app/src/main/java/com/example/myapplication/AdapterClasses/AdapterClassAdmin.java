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

import com.example.myapplication.DataClasses.ShopDataClass;
import com.example.myapplication.R;
import com.example.myapplication.Admin.ShopApprovalActivity;

import java.util.List;

public class AdapterClassAdmin extends RecyclerView.Adapter<AdapterClassAdmin.ViewHolder> {
    private List<ShopDataClass> shopDataClasses;
    Context context;

    public AdapterClassAdmin(List<ShopDataClass> shopDataClasses, Context context) {
        this.shopDataClasses = shopDataClasses;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterClassAdmin.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_view,parent,false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull AdapterClassAdmin.ViewHolder holder, int position) {
        ShopDataClass shopDataClass = shopDataClasses.get(position);
        holder.tvName.setText("Shop Name: "+shopDataClass.getShopName());
        holder.tvType.setText("Shop Type: "+shopDataClass.getShopType());
        holder.tvCity.setText("Shop City: "+shopDataClass.getShopCity());
        holder.ID.setText("Shop Id: "+shopDataClass.getShopId());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ShopApprovalActivity.class);
                intent.putExtra("Name",shopDataClass.getShopName());
                intent.putExtra("Type",shopDataClass.getShopType());
                intent.putExtra("City",shopDataClass.getShopCity());
                intent.putExtra("Id",shopDataClass.getShopId());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return shopDataClasses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvType, tvCity, ID;
        ImageView shopImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
           // shopImage = itemView.findViewById(R.id.ImgShop);
            tvName = itemView.findViewById(R.id.tvShopName);
            tvType = itemView.findViewById(R.id.tvShopType);
            tvCity = itemView.findViewById(R.id.tvShopCity);
            ID = itemView.findViewById(R.id.tvShopID);
        }
    }
}
