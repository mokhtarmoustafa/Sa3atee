package com.saaty.sideMenuScreen.myAds;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.saaty.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class UploadImageAdapter  extends RecyclerView.Adapter<UploadImageAdapter.Holder> {

    Context mContext;
    List<Uri> uriList;

    public UploadImageAdapter(Context mContext,List<Uri> uriList) {
        this.mContext = mContext;
        this.uriList=uriList;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.product_images_list_item,parent,false);
        return  new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        String path= String.valueOf(uriList.get(position));
        ImageView imageView=holder.itemView.findViewById(R.id.product_img_id);
        Picasso.with(mContext).load(path).into(imageView);
    }

    @Override
    public int getItemCount() {
        return uriList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        public Holder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
