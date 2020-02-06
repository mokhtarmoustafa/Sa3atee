package com.saaty.sideMenuScreen.wishlist;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.saaty.R;
import com.saaty.home.HomeActivity;
import com.saaty.loginAndRegister.LoginTraderUserActivity;
import com.saaty.models.DataArrayModel;
import com.saaty.models.ProductDataItem;
import com.saaty.util.OnItemClickInterface;
import com.saaty.util.OnItemClickRecyclerViewInterface;
import com.saaty.util.PreferenceHelper;
import com.saaty.util.urls;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class WishlistAdapter  extends RecyclerView.Adapter<WishlistAdapter.Holder> {
    Context mContext;
    List<DataArrayModel> wishlistProducts;
    OnItemClickInterface mOnItemInterface;
    OnItemClickRecyclerViewInterface mInterface;
    ImageView imageView;
    int checkMesg;
    public WishlistAdapter(Context mContext,  List<DataArrayModel> wishlistProducts) {
        this.mContext = mContext;
        this.wishlistProducts=wishlistProducts;
    }




    public void setOnItemClickListenerRecyclerView(OnItemClickRecyclerViewInterface mInterface){
        this.mInterface=mInterface;
    }
    public ImageView  getWishlistImg(){
        //this.imageView=imageView;
        return imageView;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(mContext).inflate(R.layout.store_product_list_item,parent,false);
       return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        DataArrayModel item=wishlistProducts.get(position);
        ImageView heartImage=holder.itemView.findViewById(R.id.wish_list_img);
        heartImage.setImageResource(R.drawable.wishlist_select);
        TextView wishlistPrice=holder.itemView.findViewById(R.id.product_price_id);
        TextView wishlistName=holder.itemView.findViewById(R.id.product_name_id);

        if(PreferenceHelper.getValue(mContext).equals("ar")){
            wishlistName.setText(item.getArName());
        }else if(PreferenceHelper.getValue(mContext).equals("en")){
            wishlistName.setText(item.getEnName());
        }

        wishlistPrice.setText(String.valueOf(item.getPrice())+" RS");


        if(item.getProductimages().size()==0){
            holder.productImg.setImageResource(R.drawable.store1);
        }else {
            Picasso.with(mContext).load(urls.base_url+"/"+item.getProductimages().get(0).getImageLink())
                    .error(R.drawable.store2).into(holder.productImg);
        }




    }

    @Override
    public int getItemCount() {
        return wishlistProducts.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        ImageView productImg;
        public Holder(@NonNull View itemView) {
            super(itemView);
     productImg=itemView.findViewById(R.id.store_img_id);




            ImageView wishlistImg=itemView.findViewById(R.id.wish_list_img);

            imageView=wishlistImg;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mInterface!=null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mInterface.OnItemClick(position);
                        }
                    }
                }
            });

            wishlistImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(HomeActivity.user_id!=0){
                        if(mInterface!=null){
                            int position=getAdapterPosition();
                            if(position!=RecyclerView.NO_POSITION){
                                mInterface.OnWishListClick(position,wishlistImg);
                            }
                        }
                    }else{
                        mContext.startActivity(new Intent(mContext, LoginTraderUserActivity.class));
                    }
                }
            });

        }
    }
}
