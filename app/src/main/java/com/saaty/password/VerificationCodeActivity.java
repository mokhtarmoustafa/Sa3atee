package com.saaty.password;

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
import com.saaty.models.SendCodeModel;
import com.saaty.util.ApiClient;
import com.saaty.util.ApiServiceInterface;
import com.saaty.util.DailogUtil;
import com.saaty.util.NetworkAvailable;

import java.util.HashMap;
import java.util.Map;

public class VerificationCodeActivity extends AppCompatActivity {

    @BindView(R.id.verify_code_input_id) TextInputEditText verifyCodeInputId;
    @BindView(R.id.toolbar_txt_id) TextView toolbarText;
    NetworkAvailable networkAvailable;
    DailogUtil dailogUtil;
    ApiServiceInterface apiServiceInterface;
    String phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification_code);
        ButterKnife.bind(this);
        dailogUtil=new DailogUtil();
        networkAvailable=new NetworkAvailable(this);

        toolbarText.setText(getString(R.string.reset_password));

        if(getIntent().hasExtra("phone")){
            phoneNumber=getIntent().getStringExtra("phone");
        }

    }

    @OnClick(R.id.confirm_btn_id)
    void confirmClick(){
        retreiveConfirmationCode();

    }

    private void retreiveConfirmationCode() {
        if(networkAvailable.isNetworkAvailable()){
            if(!FUtilsValidation.isEmpty(verifyCodeInputId,getString(R.string.field_required))){
                Map<String,Object> map=new HashMap<>();
            map.put("token",verifyCodeInputId.getText().toString());
            map.put("mobile",phoneNumber);

            ProgressDialog progressDialog=dailogUtil.showProgressDialog(VerificationCodeActivity.this,getString(R.string.logging),false);
                apiServiceInterface= ApiClient.getClientService();
                Call<SendCodeModel> call=apiServiceInterface.checkResetCode(map);
                call.enqueue(new Callback<SendCodeModel>() {
                    @Override
                    public void onResponse(Call<SendCodeModel> call, Response<SendCodeModel> response) {
                        if (response.code() == 200) {
                            if (response.body().isSuccess()) {
                                Toast.makeText(VerificationCodeActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), ResetPasswordActivity.class);
                                intent.putExtra("phone", phoneNumber);
                                intent.putExtra("token", verifyCodeInputId.getText().toString());
                                startActivity(intent);
                                progressDialog.dismiss();

                            } else {
                                Toast.makeText(VerificationCodeActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                        }else if(response.code()==404){
                            Toast.makeText(VerificationCodeActivity.this, "this code is invalid", Toast.LENGTH_LONG).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<SendCodeModel> call, Throwable t) {
                        Toast.makeText(VerificationCodeActivity.this, t.getMessage().toString(), Toast.LENGTH_SHORT).show();

                    }
                });

            }

        }else {
            Toast.makeText(this, getString(R.string.error_connection), Toast.LENGTH_SHORT).show();
        }

    }
}
