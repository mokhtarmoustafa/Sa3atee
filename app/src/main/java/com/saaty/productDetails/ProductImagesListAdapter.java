package com.saaty.productDetails;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.saaty.R;
import com.saaty.models.ProductDataItem;
import com.saaty.models.ProductimagesItem;
import com.saaty.util.urls;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ProductImagesListAdapter  extends RecyclerView.Adapter<ProductImagesListAdapter.Holder> {

    private static final String TAG =ProductImagesListAdapter.class.getSimpleName() ;
    Context mContext;
    List<ProductimagesItem> productimagesItemList;

    public ProductImagesListAdapter(Context mContext, List<ProductimagesItem> productimagesItemList) {
        this.mContext = mContext;
        this.productimagesItemList=productimagesItemList;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(mContext).inflate(R.layout.product_images_list_item,parent,false);
       return  new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        ProductimagesItem item=productimagesItemList.get(position);
        ImageView productImg=holder.itemView.findViewById(R.id.product_img_id);
        Picasso.with(mContext).load(urls.base_url+"/"+item.getImageLink()).placeholder(R.drawable.watch_item2).into(productImg);

        Log.v(TAG,"link url  "+urls.base_url+"/"+item.getImageLink());
    }

    @Override
    public int getItemCount() {
        Log.v(TAG,"link url  "+ productimagesItemList.size());
        return productimagesItemList.size();

    }

    public class Holder extends RecyclerView.ViewHolder {
        public Holder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
