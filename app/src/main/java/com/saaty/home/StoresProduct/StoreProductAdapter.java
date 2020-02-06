package com.saaty.home.StoresProduct;

import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.data.DataHolder;
import com.saaty.R;
import com.saaty.home.HomeActivity;
import com.saaty.loginAndRegister.LoginTraderUserActivity;
import com.saaty.models.DataArrayModel;
import com.saaty.models.DataItem;
import com.saaty.models.ProductDataItem;
import com.saaty.sideMenuScreen.myAds.AdsActivity;
import com.saaty.util.OnItemClickRecyclerViewInterface;
import com.saaty.util.PreferenceHelper;
import com.saaty.util.urls;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class StoreProductAdapter extends RecyclerView.Adapter<StoreProductAdapter.Holder> {

    private static final String TAG =StoreProductAdapter.class.getSimpleName() ;
    Context mContext;
    List<DataArrayModel> storeProductsList;
   OnItemClickRecyclerViewInterface mInterface;
   int imageRes;
   List<Integer> ids=new ArrayList<>();
       ImageView checkWIshlistImg;
       List<DataArrayModel> wishList;
       int x;
    public StoreProductAdapter(Context mContext, List<DataArrayModel> storeProductsList) {
        this.mContext = mContext;
        this.storeProductsList = storeProductsList;
    }

    public void setWislistProducts(List<DataArrayModel> wishlistProducts) {
        this.wishList=wishlistProducts;

    }


    public void setWishList(ImageView checkWIshlistImg){
        this.checkWIshlistImg=checkWIshlistImg;

    }



    public void setOnItemClickListenerRecyclerView(OnItemClickRecyclerViewInterface mInterface){
        this.mInterface=mInterface;
    }



    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.store_product_list_item,parent,false);
        return new Holder(view,mInterface);
    }
    public void setIds(List<Integer> ids) {
        this.ids=ids;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        DataArrayModel item = storeProductsList.get(position);
        TextView productName = holder.itemView.findViewById(R.id.product_name_id);
        TextView productPrice = holder.itemView.findViewById(R.id.product_price_id);
        ImageView productImg = holder.itemView.findViewById(R.id.store_img_id);


        productName.setText(item.getArName());
        productPrice.setText(String.valueOf(item.getPrice()));

        if (item.getProductimages().size() > 0) {
            Picasso.with(mContext).load(urls.base_url + "/" + item.getProductimages().get(0).getImageLink()).error(R.drawable.watch_item2).placeholder(R.drawable.watch_item2).into(productImg);
        } else {
            productImg.setImageResource(R.drawable.watch_item2);
        }

//
//        if(wishList.size()>0){
//            for (int j = 0; j < wishList.size(); j++) {
//            for(int i=0;i<storeProductsList.size();i++) {
//                    if (wishList.get(j).getProductId() == storeProductsList.get(i).getProductId()) {
//                        holder.wishlistImg.setImageResource(R.drawable.wishlist_not_select);
//                        Log.v("TAG", "ids num1 " + wishList.size());
//                    } else {
//                        holder.wishlistImg.setImageResource(R.drawable.wishlist_select);
//                        Log.v("TAG", "ids num2 " + wishList.size());
//
//                    }
//                }
//            }
//            }
//


    }
    @Override
    public int getItemCount() {
        Log.v(TAG,"storessss size"+storeProductsList.size());
        return storeProductsList.size();
          }

    public void getWishlistImg(int x) {

        this.x=x;
        notifyDataSetChanged();
    }

    public void updateList(List<DataArrayModel>list){
        storeProductsList = list;
        notifyDataSetChanged();
    }
    public class Holder  extends RecyclerView.ViewHolder {
        ImageView wishlistImg;
        public Holder(@NonNull View itemView,OnItemClickRecyclerViewInterface mInterface) {
            super(itemView);
             wishlistImg=itemView.findViewById(R.id.wish_list_img);
       checkWIshlistImg=wishlistImg;
//
//       if(AdsActivity.flag==1){
//           wishlistImg.setImageResource(R.drawable.wishlist_not_select);
//           Log.v("TAG","ffffffffff");
//       }else{
//          wishlistImg.setImageResource(R.drawable.wishlist_select);
//           Log.v("TAG","ggggggg");
//       }
//
//       if(x==0){
//           wishlistImg.setImageResource(R.drawable.wishlist_not_select);
//           Log.v("TAG","ffffffffff");
//       }else if(x==1){
//           wishlistImg.setImageResource(R.drawable.wishlist_select);
//           Log.v("TAG","ggggggg");
//       }

//       if(x!=0){
//           wishlistImg.setImageResource(x);
//       }
       wishlistImg.setImageResource(R.drawable.wishlist_not_select);

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