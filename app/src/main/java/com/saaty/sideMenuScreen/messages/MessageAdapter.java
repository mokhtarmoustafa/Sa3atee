package com.saaty.sideMenuScreen.messages;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.saaty.R;
import com.saaty.models.MessageArrayModel;
import com.saaty.models.MessageObjectModel;
import com.saaty.util.OnItemClickRecyclerViewInterface;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

public class MessageAdapter  extends RecyclerView.Adapter<MessageAdapter.ViewHolder>implements MessageActivity.OnMessageDeleteInterface {
List<MessageArrayModel> messageArrayModelList;
OnItemClickRecyclerViewInterface mInterface;
ImageView emptyBox,checkedBox;
Context mContext;
int position;
int flag;

    public MessageAdapter(List<MessageArrayModel> messageArrayModelList, Context mContext,int flag) {
        this.messageArrayModelList = messageArrayModelList;
        this.mContext = mContext;
        this.flag=flag;
    }

    public  void setOnClick(OnItemClickRecyclerViewInterface mInterface){
        this.mInterface=mInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(mContext).inflate(R.layout.message_list_item,parent,false);
       return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        this.position=position;
        MessageArrayModel model=messageArrayModelList.get(position);
        TextView name=holder.itemView.findViewById(R.id.message_sent_receive_name);
        TextView time=holder.itemView.findViewById(R.id.message_time);
        TextView message=holder.itemView.findViewById(R.id.message_body);
          emptyBox=holder.itemView.findViewById(R.id.empty_box);
         checkedBox=holder.itemView.findViewById(R.id.checked_box);


        if(flag==1) {
            name.setText(model.getSenderData().getFullname());
            message.setText(model.getMessage());
            time.setText(model.getCreatedAt());
        }else if(flag==2){
            name.setText(model.getRecepientData().getFullname());
            message.setText(model.getMessage());
            time.setText(model.getCreatedAt());
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mInterface.OnItemClick(position);
            }
        });
        //emptyBox.setOnClickListener(this);

    }


    @Override
    public int getItemCount() {
        return messageArrayModelList.size();
    }

    @Override
    public void OnClick() {
         emptyBox.setVisibility(View.VISIBLE);
            checkedBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkedBox.setVisibility(View.VISIBLE);
                    mInterface.OnItemClick(position);
                }
            });

    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);



        }
    }
}
