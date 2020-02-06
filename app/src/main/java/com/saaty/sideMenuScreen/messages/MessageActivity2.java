package com.saaty.sideMenuScreen.messages;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.saaty.R;
import com.saaty.home.HomeActivity;
import com.saaty.models.MessageArrayModel;
import com.saaty.models.MessageModel;
import com.saaty.util.ApiClient;
import com.saaty.util.ApiServiceInterface;
import com.saaty.util.BaseActivity;
import com.saaty.util.EndlessRecyclerViewScrollListener;
import com.saaty.util.NetworkAvailable;
import com.saaty.util.OnItemClickRecyclerViewInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessageActivity2 extends BaseActivity {

    @BindView(R.id.tab_layout_id)
    TabLayout tabLayout;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.progress_id)
    ProgressBar progressBar;
    @BindView(R.id.empty_data_txt_id)
    TextView emptyData;
    @BindView(R.id.toolbar_txt_id)
    TextView toolbarTxt;
    @BindView(R.id.toolbar_home_id)
    ImageView homeImg;
    @BindView(R.id.toolbar_edit_id)
    ImageView deleteImg;
    public static MessageArrayModel model;
    ApiServiceInterface apiServiceInterface;
    NetworkAvailable networkAvailable;
    MessageAdapter messageAdapter;
    private int current_page = 1;
    public static Boolean clicked;
    int tab_selected;
    int flag;
    List<MessageArrayModel> receivedMessage = new ArrayList<>();
    List<MessageArrayModel> sendMessage = new ArrayList<>();

    MessageActivity.OnMessageDeleteInterface messageDeleteInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        ButterKnife.bind(this);
        networkAvailable = new NetworkAvailable(this);

        toolbarTxt.setText(getString(R.string.message));
        deleteImg.setVisibility(View.GONE);
        deleteImg.setImageResource(R.drawable.nav_delete);


        if (networkAvailable.isNetworkAvailable()) {
            tabLayout.addTab(tabLayout.newTab().setText("         " + getString(R.string.received) + "      "), 0);
            sendMessage.clear();
            receivedMessage.clear();
            buildReceivedMessageRecycler();
            getReceivedMessage(current_page);
            tabLayout.addTab(tabLayout.newTab().setText("           " + getString(R.string.send) + "          "), 1);


        } else {
            Toast.makeText(this, getString(R.string.error_connection), Toast.LENGTH_LONG).show();
        }


        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab_selected=tab.getPosition();
                if(tab.getPosition()==0){
                    sendMessage.clear();
                    receivedMessage.clear();
                    current_page=1;
                    buildReceivedMessageRecycler();
                    getReceivedMessage(current_page);
                    Log.v("TAG","pos"+tab.getPosition());
                }else if(tab.getPosition()==1){
                    receivedMessage.clear();
                    sendMessage.clear();
                    current_page=1;
                    buildSendMessageRecycler();
                    getSendMessage(current_page);
                    Log.v("TAG","pos"+tab.getPosition());

                }
                //buildRecyclerViewForCategory();
                if(networkAvailable.isNetworkAvailable()) {
                    //emptyData.setVisibility(View.GONE);
                    // newProducts.clear();
                    // current_page=1;
                    //getStoreProducts(current_page, shape_type);
                }else {
                    // Toast.makeText(getApplicationContext(), getString(R.string.error_connection), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });



    }



    private void getSendMessage(int i) {
        apiServiceInterface= ApiClient.getClientService();
        Map<String,Object> map=new HashMap<>();
        // String token= LoginTraderUserActivity.loginModel.getTokenType()+" "+LoginTraderUserActivity.loginModel.getAccessToken();
        String x= HomeActivity.access_token;
        map.put("page",i);
        map.put("limit",10);
        Call<MessageModel> call=apiServiceInterface.getSendMessage("application/json",x,map);
        call.enqueue(new Callback<MessageModel>() {
            @Override
            public void onResponse(Call<MessageModel> call, Response<MessageModel> response) {
                if (response.body().isSuccess()) {
                    if (response.body().getMessageObjectModel().getMessageArrayModelList().size() > 0) {
                        Log.v("TAG","send mess"+sendMessage.size());
                        sendMessage.addAll(response.body().getMessageObjectModel().getMessageArrayModelList());
                        messageAdapter.notifyDataSetChanged();
                        Toast.makeText(MessageActivity2.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);

                    } else {
                        recyclerView.setVisibility(View.GONE);
                        emptyData.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                    }
//

                } else {
                    Toast.makeText(MessageActivity2.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);

                }
            }

            @Override
            public void onFailure(Call<MessageModel> call, Throwable t) {
                Log.v("TAG","send mess"+sendMessage.size());
                Toast.makeText(MessageActivity2.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();
            }
        });


    }

    private void getReceivedMessage(int current_page) {
        apiServiceInterface= ApiClient.getClientService();
        Map<String,Object> map=new HashMap<>();
        // String token= LoginTraderUserActivity.loginModel.getTokenType()+" "+LoginTraderUserActivity.loginModel.getAccessToken();
        String x= HomeActivity.access_token;
        map.put("page",current_page);
        map.put("limit",10);
        Call<MessageModel> call=apiServiceInterface.getReceivedMessage("application/json",x,map);
        call.enqueue(new Callback<MessageModel>() {
            @Override
            public void onResponse(Call<MessageModel> call, Response<MessageModel> response) {
                if (response.body().isSuccess()) {
                    if (response.body().getMessageObjectModel().getMessageArrayModelList().size() > 0) {
                        receivedMessage.addAll(response.body().getMessageObjectModel().getMessageArrayModelList());
                        Log.v("TAG","receive mess"+receivedMessage.size());
                        messageAdapter.notifyDataSetChanged();
                        Toast.makeText(MessageActivity2.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);

                    } else {
                        recyclerView.setVisibility(View.GONE);
                        emptyData.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                    }
//

                } else {
                    Toast.makeText(MessageActivity2.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<MessageModel> call, Throwable t) {
                Toast.makeText(MessageActivity2.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();
            }
        });


    }

    private void buildReceivedMessageRecycler() {

        GridLayoutManager layoutManager=new GridLayoutManager(this,1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        messageAdapter =new MessageAdapter(receivedMessage,MessageActivity2.this,1);
        recyclerView.setAdapter(messageAdapter);
        buildOnClickInterfaceReceived();
        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // if(tab_selected==0){
                current_page++;
                getReceivedMessage(current_page);
                // }
            }
        });

    }

    private void buildOnClickInterfaceReceived() {
        messageAdapter.setOnClick(new OnItemClickRecyclerViewInterface() {
            @Override
            public void OnWishListClick(int position, ImageView wishlist_image) {

            }

            @Override
            public void OnItemClick(int position) {
                model = receivedMessage.get(position);
                Intent intent = new Intent(getApplicationContext(), MessageDetailsActivity.class);
                Log.v("TAG","message model  "+model);
                intent.putExtra("receive_model",model);
                startActivity(intent);
            }
        });

    }


    private void buildOnClickInterfaceSend() {
        messageAdapter.setOnClick(new OnItemClickRecyclerViewInterface() {
            @Override
            public void OnWishListClick(int position, ImageView wishlist_image) {

            }

            @Override
            public void OnItemClick(int position) {
                model = sendMessage.get(position);
                Intent intent = new Intent(getApplicationContext(), MessageSendDetailsActivity.class);
                intent.putExtra(MessageSendDetailsActivity.MESSAGE_MODEL_SEND, model);
                startActivity(intent);
            }
        });

    }

    private void buildSendMessageRecycler() {
        GridLayoutManager layoutManager=new GridLayoutManager(this,1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        messageAdapter =new MessageAdapter(sendMessage,MessageActivity2.this,2);
        recyclerView.setAdapter(messageAdapter);
        buildOnClickInterfaceSend();
        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // if(tab_selected==0){
                current_page++;
                getSendMessage(current_page);
                // }
            }
        });

    }

    @OnClick(R.id.toolbar_edit_id)
    void deleteMessage(){
        clicked=true;
    }



    @OnClick(R.id.toolbar_back_left_btn_id)
    void backClick(){
        finish();
    }

    @OnClick(R.id.toolbar_home_id)
    void homeClick(){
        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
    }


    public interface  OnMessageDeleteInterface{
        void OnClick();
    }


}