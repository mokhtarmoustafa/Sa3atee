package com.saaty.sideMenuScreen;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.saaty.R;
import com.saaty.home.HomeActivity;
import com.saaty.loginAndRegister.LoginTraderUserActivity;
import com.saaty.models.UpdateProfileDataArrayModel;
import com.saaty.models.UpdateProfileModel;
import com.saaty.util.ApiClient;
import com.saaty.util.ApiServiceInterface;
import com.saaty.util.BaseActivity;
import com.saaty.util.DailogUtil;
import com.saaty.util.NetworkAvailable;
import com.saaty.util.urls;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends BaseActivity {

    @BindView(R.id.edit_profile_back) ImageView editProfile;
    @BindView(R.id.toolbar_txt_id) TextView toolbarTxt;
    @BindView(R.id.user_name_txt_value_id) TextView userNameValeId;
    @BindView(R.id.phone_txt_value_id) TextView phoneValueId;
    @BindView(R.id.email_txt_value_id)TextView emailValueId;
    @BindView(R.id.password_txt_value_id) TextView passwordValueId;
    @BindView(R.id.profile_img_id) CircleImageView profileImg;
    @BindView(R.id.store_name_txt_value_id)TextView  storeNameTxtValue;
    @BindView(R.id.store_name_txt_id)TextView storeNameTxt;
    UpdateProfileDataArrayModel dataArrayModel;
    NetworkAvailable networkAvailable;
    ApiServiceInterface apiServiceInterface;
    public  static String logo,storeName,storeDesc;
    DailogUtil dailogUtil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);
        toolbarTxt.setText(getString(R.string.my_account));
        dailogUtil=new DailogUtil();
        networkAvailable=new NetworkAvailable(getApplicationContext());


        if(networkAvailable.isNetworkAvailable()){
           getProfileData();
        }else {
            Toast.makeText(this, getString(R.string.error_connection), Toast.LENGTH_LONG).show();
        }


    }

    private void getProfileData() {
        apiServiceInterface= ApiClient.getClientService();
        ProgressDialog progressDialog=dailogUtil.showProgressDialog(ProfileActivity.this,getString(R.string.logging),false);
        Call<UpdateProfileModel> call=apiServiceInterface.getProfile("application/json",  HomeActivity.access_token);
        call.enqueue(new Callback<UpdateProfileModel>() {
            @Override
            public void onResponse(Call<UpdateProfileModel> call, Response<UpdateProfileModel> response) {
                if(response.body().isSuccess()){
                    dataArrayModel=response.body().getDataArrayModels().get(0);
                    Toast.makeText(ProfileActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                    String email = response.body().getDataArrayModels().get(0).getEmail();
                    String phone = response.body().getDataArrayModels().get(0).getMobile();
                    String name = response.body().getDataArrayModels().get(0).getFullname();
                    emailValueId.setText(email);
                    phoneValueId.setText(phone);
                    userNameValeId.setText(name);
                    if(dataArrayModel.getType().equals("store")){
                        Log.v("TAG","ssssssss"+dataArrayModel.toString());
                        logo=dataArrayModel.getStoreLogo();
                        storeName=dataArrayModel.getStoreArName();
                        storeDesc=dataArrayModel.getStoreArDescription();
                        if(dataArrayModel.getStoreLogo()!=null)
                        Picasso.with(getApplicationContext()).load(urls.base_url+"/"+dataArrayModel.getStoreLogo())
                               .error(R.drawable.sidemenu_photo2).into(profileImg);
                   storeNameTxtValue.setVisibility(View.VISIBLE);
                   storeNameTxtValue.setText((CharSequence) dataArrayModel.getStoreArName());
                   storeNameTxt.setVisibility(View.VISIBLE);

                    }

                    progressDialog.dismiss();

                }else {
                    Toast.makeText(ProfileActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<UpdateProfileModel> call, Throwable t) {
                Toast.makeText(ProfileActivity.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        });

    }

    @OnClick(R.id.edit_profile_back)
    void editProfile(){
        Intent intent=new Intent(getApplicationContext(),EditProfileActivity.class);
        intent.putExtra("profile_model",dataArrayModel);
        startActivity(intent);
    }

    @OnClick(R.id.show_password_back)
    void changePassword(){
        Intent intent=new Intent(getApplicationContext(),ChangePasswordActivity.class);
        startActivity(intent);
    }


    @OnClick(R.id.toolbar_home_id)
    void homeClick(){
        finish();
    }


@OnClick(R.id.toolbar_back_left_btn_id)
    void backClick(){
    finish();
}
}
