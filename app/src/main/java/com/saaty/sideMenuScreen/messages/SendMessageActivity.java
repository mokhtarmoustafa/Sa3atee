package com.saaty.sideMenuScreen.messages;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.fourhcode.forhutils.FUtilsValidation;
import com.google.android.material.textfield.TextInputEditText;
import com.saaty.R;
import com.saaty.home.HomeActivity;
import com.saaty.loginAndRegister.LoginTraderUserActivity;
import com.saaty.models.DeleteMessageModel;
import com.saaty.models.ReplyMessageModel;
import com.saaty.models.SendMessageProductModel;
import com.saaty.util.ApiClient;
import com.saaty.util.ApiServiceInterface;
import com.saaty.util.BaseActivity;
import com.saaty.util.DailogUtil;
import com.saaty.util.NetworkAvailable;

import java.util.HashMap;
import java.util.Map;

public class SendMessageActivity extends BaseActivity {
  @BindView(R.id.message_text_input_id) TextInputEditText messageInput;
  @BindView(R.id.toolbar_txt_id) TextView toolarTxt;
  NetworkAvailable networkAvailable;
  ApiServiceInterface apiServiceInterface;
  int user_message_id;
  DailogUtil dailogUtil;
  Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message2);
        ButterKnife.bind(this);
        networkAvailable=new NetworkAvailable(this);
        dailogUtil=new DailogUtil();
        toolarTxt.setText(getString(R.string.send_message));
         intent=getIntent();
        if(intent.hasExtra("id")){
            user_message_id=intent.getIntExtra("id",1);
        }else if(intent.hasExtra("product_id")){
            if(networkAvailable.isNetworkAvailable()){
                if(!FUtilsValidation.isEmpty(messageInput,getString(R.string.field_required))){
                    //sendProductMessage(intent.getIntExtra("product_id",1));
                }
            }else {
                Toast.makeText(this, getString(R.string.error_connection), Toast.LENGTH_LONG).show();
            }
        }


    }



    @OnClick(R.id.send_btn_id)
   void setSendReplyClick(){

        if(intent.hasExtra("id")){
            user_message_id=intent.getIntExtra("id",1);
            if(networkAvailable.isNetworkAvailable()){
                if(!FUtilsValidation.isEmpty(messageInput,getString(R.string.field_required))){
                    sendReplyMessage();
                }
            }else {
                Toast.makeText(this, getString(R.string.error_connection), Toast.LENGTH_LONG).show();
            }

        }else if(intent.hasExtra("product_id")){
            if(networkAvailable.isNetworkAvailable()){
                if(!FUtilsValidation.isEmpty(messageInput,getString(R.string.field_required))){
                    sendProductMessage(intent.getIntExtra("product_id",1));
                }
            }else {
                Toast.makeText(this, getString(R.string.error_connection), Toast.LENGTH_LONG).show();
            }
        }


    }

    private void sendReplyMessage() {
         ProgressDialog progressDialog=dailogUtil.showProgressDialog(SendMessageActivity.this,getString(R.string.logging),false);
        apiServiceInterface= ApiClient.getClientService();
        Map<String,Object>map=new HashMap<>();
        map.put("user_message_id",user_message_id);
        map.put("reply",messageInput.getText().toString());
        Call<ReplyMessageModel> call=apiServiceInterface.sendReplyMessage("application/json", HomeActivity.access_token,map);

        call.enqueue(new Callback<ReplyMessageModel>() {
            @Override
            public void onResponse(Call<ReplyMessageModel> call, Response<ReplyMessageModel> response) {
                if(response.body().isSuccess()){
                    Toast.makeText(SendMessageActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                    finish();
                }else {
                    Toast.makeText(SendMessageActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                   progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ReplyMessageModel> call, Throwable t) {
                Toast.makeText(SendMessageActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        });
    }



    private void sendProductMessage(int id) {
        ProgressDialog progressDialog=dailogUtil.showProgressDialog(SendMessageActivity.this,getString(R.string.logging),false);
        apiServiceInterface= ApiClient.getClientService();
        Map<String,Object>map=new HashMap<>();
        map.put("product_id",id);
        map.put("message",messageInput.getText().toString());
        Call<SendMessageProductModel> call=apiServiceInterface.sendProductMessage("application/json", HomeActivity.access_token,map);

        call.enqueue(new Callback<SendMessageProductModel>() {
            @Override
            public void onResponse(Call<SendMessageProductModel> call, Response<SendMessageProductModel> response) {
                if(response.body().isSuccess()){
                    Toast.makeText(SendMessageActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                    finish();
                }else {
                    Toast.makeText(SendMessageActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<SendMessageProductModel> call, Throwable t) {
                Toast.makeText(SendMessageActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        });

    }
}
