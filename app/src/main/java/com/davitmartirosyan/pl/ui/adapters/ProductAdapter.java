package com.davitmartirosyan.pl.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.davitmartirosyan.pl.R;
import com.davitmartirosyan.pl.db.entity.Product;

import java.util.ArrayList;


public class ProductAdapter extends RecyclerView.Adapter<ProductViewHolder> {

    private ArrayList<Product> products;
    private Context context;

    public ProductAdapter(ArrayList<Product> products, Context context) {
        this.products = products;
        this.context = context;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.product_list_item, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        holder.bind(products.get(position),context);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }
}
