package com.saaty.loginAndRegister;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.fourhcode.forhutils.FUtilsValidation;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.saaty.R;
import com.saaty.home.HomeActivity;
import com.saaty.models.LoginModel;
import com.saaty.models.UserDataRegisterObject;
import com.saaty.models.UserModel;
import com.saaty.password.ForgetPasswordActivity;
import com.saaty.util.ApiClient;
import com.saaty.util.ApiServiceInterface;
import com.saaty.util.BaseActivity;
import com.saaty.util.DailogUtil;
import com.saaty.util.NetworkAvailable;

import java.util.HashMap;
import java.util.Map;

public class LoginTraderUserActivity extends BaseActivity {

    private static final String TAG =LoginTraderUserActivity.class.getSimpleName() ;
    public static final String MY_PREFS_NAME ="my_data" ;
    @BindView(R.id.email_input_id) TextInputEditText emailInput;
    @BindView(R.id.password_input_id)TextInputEditText passwordInput;
    @BindView(R.id.save_login_info_id) CheckBox  saveInfoCheckBox;
    @BindView(R.id.login_visitor_btn_id) MaterialButton loginVistorBtn;
    NetworkAvailable networkAvailable;
    ApiServiceInterface apiServiceInterface;
    DailogUtil dailogUtil;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    UserModel userModel;
    LoginModel loginModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_trader_user);
        ButterKnife.bind(this);
        networkAvailable=new NetworkAvailable(this);
        dailogUtil=new DailogUtil();

            sharedPreferences=getSharedPreferences(MY_PREFS_NAME,MODE_PRIVATE);
            passwordInput.setText(sharedPreferences.getString("password",""));
            emailInput.setText(sharedPreferences.getString("email",""));


    }

    @OnClick(R.id.login_btn_id)
    void loginTrader() {
        loginTraderUser();

    }



    @OnClick(R.id.login_visitor_btn_id)
    void loginVistor(){
        Intent intent=new Intent(getApplicationContext(), HomeActivity.class);
        intent.putExtra("login_visitor","visitor");
        startActivity(intent);
    }

    @OnClick(R.id.create_account_txt_id)
    void createAccount(){
        startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
    }

    @OnClick(R.id.forget_password_txt_id)
    void forgetPassword(){
        startActivity(new Intent(getApplicationContext(), ForgetPasswordActivity.class));
    }



    private void loginTraderUser() {
        if(networkAvailable.isNetworkAvailable()){
            if(!FUtilsValidation.isEmpty(emailInput,getString(R.string.field_required))
            &&!FUtilsValidation.isEmpty(passwordInput,getString(R.string.field_required))
            &&FUtilsValidation.isValidEmail(emailInput,getString(R.string.error_email_msg))){
                ProgressDialog progressDialog=dailogUtil.showProgressDialog(LoginTraderUserActivity.this,getString(R.string.logging),false);
          apiServiceInterface= ApiClient.getClientService();
                Map<String,Object> map=new HashMap<>();
                map.put("username",emailInput.getText().toString());
                map.put("password",passwordInput.getText().toString());
                map.put("grant_type","password");
                map.put("client_id","2");
                map.put("client_secret","yHYNPOpUoF11capN1xp3uMcHLAoIjycvCimU3xAD");
                Call<LoginModel> call=apiServiceInterface.loginUser(map);
                call.enqueue(new Callback<LoginModel>() {
                    @Override
                    public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                        if(response.code()==200) {
                            if (response.body().isSucess()) {
                                Log.v(TAG, "login sucess1");
                                userModel = response.body().getUserModel().get(0);
                                loginModel = response.body();
                                userModel.setEmail(userModel.getEmail());
                                userModel.setMobile(userModel.getMobile());
                                userModel.setFullname(userModel.getFullname());
                                userModel.setType(userModel.getType());
                                userModel.setUserId(userModel.getUserId());
                                userModel.setStoreArName(userModel.getStoreArName());
                                userModel.setStoreArDescription(userModel.getStoreArDescription());
                                userModel.setStoreLogo(userModel.getStoreLogo());
                                userModel.setStoreId(userModel.getStoreId());

                                loginModel.setUserModel(response.body().getUserModel());
                                loginModel.setAccessToken(response.body().getAccessToken());
                                loginModel.setTokenType(response.body().getTokenType());



                                Gson gson = new Gson();
                                String user_data = gson.toJson(loginModel);
                                editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                                editor.putString("user_data", user_data);
                                editor.commit();

                                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                intent.putExtra("user_model", loginModel);
                                startActivity(intent);
                                Toast.makeText(LoginTraderUserActivity.this, "loging sucess", Toast.LENGTH_LONG).show();
                                progressDialog.dismiss();
                            } else {
                                Log.v(TAG, "login sucess2");
                                Toast.makeText(LoginTraderUserActivity.this, response.body().getErrorDescription().toString(), Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                        }else if(response.code()==401){
                            Toast.makeText(LoginTraderUserActivity.this,"The user credentials were incorrect.", Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginModel> call, Throwable t) {
                        progressDialog.dismiss();
                    }
                });


            }

        }else {
            Toast.makeText(this, getString(R.string.error_connection), Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    protected void onPause() {
        super.onPause();
        if (saveInfoCheckBox.isChecked()) {
            editor = sharedPreferences.edit();
            editor.putString("password", passwordInput.getText().toString());
            editor.putString("email", emailInput.getText().toString());
            editor.putInt("used_id",userModel.getId());
            editor.commit();
            editor.apply();

        }else {
            editor = sharedPreferences.edit();
            editor.putString("password","");
            editor.putString("email","");
            editor.commit();
            editor.apply();
        }
    }


}