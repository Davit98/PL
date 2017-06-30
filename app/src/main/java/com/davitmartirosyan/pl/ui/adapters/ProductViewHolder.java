package com.davitmartirosyan.pl.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.davitmartirosyan.pl.R;
import com.davitmartirosyan.pl.db.entity.Product;

public class ProductViewHolder extends RecyclerView.ViewHolder {

    private ImageView productPicture;
    private TextView productName;
    private TextView productPrice;

    public ProductViewHolder(View itemView) {
        super(itemView);

        productPicture = (ImageView)itemView.findViewById(R.id.product_item_image_iv);
        productName = (TextView)itemView.findViewById(R.id.product_item_name_tv);
        productPrice = (TextView)itemView.findViewById(R.id.product_item_price_tv);
    }

    public void bind(Product product, Context context) {
        productName.setText(productName!=null ? product.getName() : "");
        productPrice.setText(productPrice!=null ? Integer.toString(product.getPrice()) : "");
        Glide.with(context).load(product.getImage()).into(productPicture);
    }
}
