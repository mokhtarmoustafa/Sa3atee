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
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.fourhcode.forhutils.FUtilsValidation;
import com.google.android.material.textfield.TextInputEditText;
import com.saaty.R;
import com.saaty.home.HomeActivity;
import com.saaty.loginAndRegister.LoginTraderUserActivity;
import com.saaty.models.SendCodeModel;
import com.saaty.util.ApiClient;
import com.saaty.util.ApiServiceInterface;
import com.saaty.util.BaseActivity;
import com.saaty.util.DailogUtil;
import com.saaty.util.NetworkAvailable;
import com.saaty.util.urls;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class ChangePasswordActivity extends BaseActivity {

    private static final String TAG =ChangePasswordActivity.class.getSimpleName() ;
    @BindView(R.id.toolbar_txt_id)
    TextView toolbarTxt;
    @BindView(R.id.profile_img_id)
    CircleImageView profileImg;
    @BindView(R.id.old_password_input_id)
    TextInputEditText oldPasswordInput;
    @BindView(R.id.new_password_input_id)
    TextInputEditText newPasswordInput;
    @BindView(R.id.confirm_password_input_id)
    TextInputEditText confirmPasswordInput;
    NetworkAvailable networkAvailable;
    ApiServiceInterface apiServiceInterface;
    DailogUtil dailogUtil;
    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        ButterKnife.bind(this);
        toolbarTxt.setText(getString(R.string.change_password));
        dailogUtil = new DailogUtil();
        networkAvailable = new NetworkAvailable(getApplicationContext());
        type=HomeActivity.type;


        if(type.equals("store")){
         Picasso.with(getApplicationContext()).load(urls.base_url +"/"+HomeActivity.store_logo).error(R.drawable.sidemenu_photo2).into(profileImg);

        }else if(type.equals("user")){
         profileImg.setImageResource(R.drawable.sidemenu_photo2);
        }
    }

    @OnClick(R.id.change_password_btn)
    void changePasswordBtn() {
        Log.v(TAG,"clickeddddd");
        if (networkAvailable.isNetworkAvailable()) {
            changePassword();
            Log.v(TAG,"clickeddddd1");
        } else {
            Toast.makeText(getApplicationContext(), getString(R.string.error_connection), Toast.LENGTH_LONG).show();
            Log.v(TAG,"clickeddddd2");
        }
    }

    private void changePassword() {
        if (!FUtilsValidation.isEmpty(oldPasswordInput, getString(R.string.field_required))
                && !FUtilsValidation.isEmpty(newPasswordInput, getString(R.string.field_required))
                && !FUtilsValidation.isEmpty(confirmPasswordInput, getString(R.string.field_required))
                && FUtilsValidation.isPasswordEqual(newPasswordInput, oldPasswordInput, getString(R.string.password_equal_error))) {
            apiServiceInterface = ApiClient.getClientService();
            Map<String, Object> map = new HashMap<>();
            map.put("password", newPasswordInput.getText().toString());
            map.put("password_confirmation", confirmPasswordInput.getText().toString());
            map.put("old_password", oldPasswordInput.getText().toString());
            ProgressDialog progressDialog = dailogUtil.showProgressDialog(ChangePasswordActivity.this, getString(R.string.logging), false);
            Call<SendCodeModel> call = apiServiceInterface.changePassword("application/json",
                    HomeActivity.access_token, map);
            call.enqueue(new Callback<SendCodeModel>() {
                @Override
                public void onResponse(Call<SendCodeModel> call, Response<SendCodeModel> response) {
                    if (response.body().isSuccess()) {
                        Toast.makeText(ChangePasswordActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                        finish();
                    } else {
                        Toast.makeText(ChangePasswordActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<SendCodeModel> call, Throwable t) {
                    Toast.makeText(ChangePasswordActivity.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
            });
        }
    }
}