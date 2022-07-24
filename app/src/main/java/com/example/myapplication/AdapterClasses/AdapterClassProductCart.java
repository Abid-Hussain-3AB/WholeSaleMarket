package com.example.myapplication.AdapterClasses;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.Buyer.DetailActivity;
import com.example.myapplication.DataClasses.ProductDataClass;
import com.example.myapplication.R;

import java.util.List;

public class AdapterClassProductCart extends RecyclerView.Adapter<AdapterClassProductCart.ViewHolder>{
    private List<ProductDataClass> productDataClasses;
    Context context;
    ItemClickListener itemClickListener;
    int selectedPosition = -1;
    String category;


    public AdapterClassProductCart(List<ProductDataClass> productDataClasses, Context context, ItemClickListener itemClickListener,String category) {
        this.productDataClasses = productDataClasses;
        this.context = context;
        this.itemClickListener = itemClickListener;
        this.category = category;
    }

    @NonNull
    @Override
    public AdapterClassProductCart.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_view_cart, parent, false);
        return new AdapterClassProductCart.ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull AdapterClassProductCart.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        ProductDataClass productDataClass = productDataClasses.get(position);
        holder.tvPName.setText(productDataClass.getProductName());
        holder.tvPPrice.setText("Rs. "+productDataClass.getProductPrice());
        Glide.with(context).load(productDataClass.getImage()).into(holder.imgproducts);
        holder.tvPmin.setText(productDataClass.getProductMinSale()+" (Min. Order)");
        holder.radioButton.setChecked(selectedPosition == position);
       // holder.radioButton.setChecked(lastSelectedPosition == position);
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
       holder.radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedPosition = position;
                notifyDataSetChanged();
                //Toast.makeText(context, productDataClass.getProductName(), Toast.LENGTH_SHORT).show();
                itemClickListener.onClick(productDataClass.getProductId(),position);
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
        RadioButton radioButton;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPName = itemView.findViewById(R.id.tvProductNameB);
            tvPPrice = itemView.findViewById(R.id.tvProductPrice);
            tvPmin = itemView.findViewById(R.id.tvProductMOQ);
            imgproducts = itemView.findViewById(R.id.ImgProducts);
            radioButton = itemView.findViewById(R.id.radio_button_cart);
        }
    }
}
