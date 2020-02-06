package com.saaty.home;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.saaty.R;
import com.saaty.models.DataArrayModel;
import com.saaty.models.DataItem;
import com.saaty.util.OnItemClickInterface;
import com.saaty.util.PreferenceHelper;
import com.saaty.util.urls;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static android.view.View.OVER_SCROLL_NEVER;
import static androidx.constraintlayout.widget.Constraints.TAG;

public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.Holder> {
    Context mContext;
    List<DataArrayModel> storesList;
    OnItemClickInterface mOnItemClick;

    public StoreAdapter(Context mContext, List<DataArrayModel> list,OnItemClickInterface onItemClickInterface) {
        this.mContext = mContext;
        this.storesList=list;
        this.mOnItemClick=onItemClickInterface;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.store_list_item,parent,false);
        return  new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder,final int position) {
       DataArrayModel dataItem= storesList.get(position);
        TextView storeName=holder.itemView.findViewById(R.id.store_name_id);
        ImageView storeImgId=holder.itemView.findViewById(R.id.store_img_id);

        if(PreferenceHelper.getValue(mContext).equals("ar")){
            storeName.setText(dataItem.getStoreArName());
        }else if(PreferenceHelper.getValue(mContext).equals("en")){
            storeName.setText(dataItem.getStoreEnName());
        }

        if(dataItem.getStoreLogo()==null){
            storeImgId.setImageResource(R.drawable.store1);
            Log.v(TAG,"logooo"+dataItem.getStoreLogo());
        }
       // Log.v(TAG,"logooo"+dataItem.getStoreLogo());
        Picasso.with(mContext).load(urls.base_url+"/"+dataItem.getStoreLogo()).error(R.drawable.store1).into(storeImgId);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClick.onItemClick(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        Log.v(TAG,"stores size"+storesList.size());
        return storesList.size();

    }

    public void updateList(List<DataArrayModel> list){
        this.storesList = list;
        notifyDataSetChanged();
    }

    public class Holder  extends RecyclerView.ViewHolder {
        public Holder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
