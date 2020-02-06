package com.saaty.sideMenuScreen.messages;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.saaty.R;
import com.saaty.home.HomeActivity;
import com.saaty.loginAndRegister.LoginTraderUserActivity;
import com.saaty.models.DeleteMessageModel;
import com.saaty.models.MessageArrayModel;
import com.saaty.util.ApiClient;
import com.saaty.util.ApiServiceInterface;
import com.saaty.util.BaseActivity;
import com.saaty.util.DailogUtil;
import com.saaty.util.NetworkAvailable;

public class MessageSendDetailsActivity extends BaseActivity {
    public static final String MESSAGE_MODEL_SEND= "message_model_send";
    @BindView(R.id.user_name_id) TextView userName;
    @BindView(R.id.message_body_id)TextView messageBody;
    @BindView(R.id.delete_btn_id) MaterialButton deleteId;
    @BindView(R.id.toolbar_txt_id) TextView toolarTxt;
    NetworkAvailable networkAvailable;
    ApiServiceInterface apiServiceInterface;
    DailogUtil dialogUtils;
    MessageArrayModel model;
    int user_message_id;
    Dialog mDailog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_send_details);
        ButterKnife.bind(this);

        dialogUtils=new DailogUtil();
        mDailog=new Dialog(this);
        networkAvailable=new NetworkAvailable(this);
        Intent intent=getIntent();
        if(intent.hasExtra(MESSAGE_MODEL_SEND)){
            model=MessageActivity.model;
            userName.setText(model.getRecepientData().getFullname());
            messageBody.setText(model.getMessage());
            toolarTxt.setText(model.getRecepientData().getFullname());
            user_message_id=model.getUsmId();
        }
    }

    @OnClick(R.id.delete_btn_id)
    void setDeleteId(){
        mDailog.setContentView(R.layout.delete_product_layout);
        MaterialButton deleteBtn = mDailog.findViewById(R.id.delete_btn_id);
        MaterialButton cancelBtn = mDailog.findViewById(R.id.cancel_btn_id);
        // ProgressBar progressBar1 = mDailog.findViewById(R.id.progress_id);
        mDailog.setCancelable(false);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (networkAvailable.isNetworkAvailable()) {
                    deleteMessage();
                } else {
                    Toast.makeText(MessageSendDetailsActivity.this, getString(R.string.error_connection), Toast.LENGTH_LONG).show();
                }

            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDailog.dismiss();
            }
        });

        mDailog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mDailog.show();




    }

    private void deleteMessage() {
        ProgressDialog progressDialog=dialogUtils.showProgressDialog(MessageSendDetailsActivity.this,getString(R.string.logging),false);
        apiServiceInterface= ApiClient.getClientService();
        String x=  HomeActivity.access_token;
        Call<DeleteMessageModel> call=apiServiceInterface.deleteMessage("application/json",x,model.getUsmId());
        call.enqueue(new Callback<DeleteMessageModel>() {
            @Override
            public void onResponse(Call<DeleteMessageModel> call, Response<DeleteMessageModel> response) {
                if(response.body().isSuccess()){
                    Toast.makeText(MessageSendDetailsActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                    finish();
                }else {
                    Toast.makeText(MessageSendDetailsActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<DeleteMessageModel> call, Throwable t) {
                Toast.makeText(MessageSendDetailsActivity.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();
                progressDialog.dismiss();


            }
        });
    }


    @OnClick(R.id.toolbar_back_left_btn_id)
    void backClick(){
        finish();
    }

}
