package com.saaty.loginAndRegister;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import com.afollestad.materialdialogs.util.DialogUtils;
import com.fourhcode.forhutils.FUtilsValidation;
import com.fourhcode.forhutils.Futils;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.saaty.R;
import com.saaty.home.HomeActivity;
import com.saaty.models.Data;
import com.saaty.models.RegisterModel;
import com.saaty.util.ApiClient;
import com.saaty.util.ApiServiceInterface;
import com.saaty.util.DailogUtil;
import com.saaty.util.NetworkAvailable;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;
import static androidx.constraintlayout.widget.Constraints.TAG;


public class TraderRegisterFragment extends Fragment {

    ApiServiceInterface serviceInterface;
    public static final String MY_PREFS_NAME ="my_data" ;
    NetworkAvailable networkAvailable;
    TextInputEditText nameInput, phoneInput, emailInput, shopType, password, confirmPssword;
    ImageView uploadImg;
    MaterialButton registerBtn;
     DailogUtil dailogUtil;
    String storage_permission[];
    private static final int image_pick_gallery_code = 500;
    CheckBox acceptTerms;
    MultipartBody.Part body=null;
    SharedPreferences.Editor editor;
    Data data;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.register_trader_fragment_layout, container, false);

        nameInput = view.findViewById(R.id.user_name_input_id);
        phoneInput = view.findViewById(R.id.phone_input_id);
        emailInput = view.findViewById(R.id.email_input_id);
        shopType = view.findViewById(R.id.store_type_input_id);
        password = view.findViewById(R.id.password_input_id);
        confirmPssword = view.findViewById(R.id.confirm_password_input_id);
        uploadImg=view.findViewById(R.id.upload_store_img);
        acceptTerms=view.findViewById(R.id.accept_terms_id);

        storage_permission = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};

        registerBtn = view.findViewById(R.id.confirm_btn_id);
        networkAvailable=new NetworkAvailable(getActivity());
        dailogUtil=new DailogUtil();

        uploadImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(getActivity(), storage_permission, image_pick_gallery_code);
                    } else {
                        pickGallery();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });



        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(TAG, "clicked k");
                registerTrader();
            }
        });

        return view;

    }

    private void pickGallery() {
        Intent gallery_intent = new Intent(Intent.ACTION_PICK);
        gallery_intent.setType("image/*");
        startActivityForResult(gallery_intent, image_pick_gallery_code);

    }

    private void registerTrader() {

        if (networkAvailable.isNetworkAvailable()) {
            if (!FUtilsValidation.isEmpty(nameInput, getString(R.string.field_required))
                    && !FUtilsValidation.isEmpty(emailInput, getString(R.string.field_required))
                    && !FUtilsValidation.isEmpty(phoneInput, getString(R.string.field_required))
                    && !FUtilsValidation.isEmpty(password, getString(R.string.field_required))
                    && !FUtilsValidation.isEmpty(confirmPssword, getString(R.string.field_required))
                    && !FUtilsValidation.isEmpty(shopType, getString(R.string.field_required))
                    && FUtilsValidation.isPasswordEqual(password, confirmPssword, getString(R.string.password_equal_error))
                    && FUtilsValidation.isValidEmail(emailInput, getString(R.string.error_email_msg))
                    && body!=null
                    && acceptTerms.isChecked()==true) {
                if (password.getText().length() < 6) {
                    password.setError(getString(R.string.weak_password));
                    password.requestFocus();
                    return;
                }


                Map<String, Object> map = new HashMap<>();
                map.put("fullname", nameInput.getText().toString());
                map.put("email", emailInput.getText().toString());
                map.put("mobile", phoneInput.getText().toString());
                map.put("type", "store");
                map.put("password", password.getText().toString());
                map.put("c_password", confirmPssword.getText().toString());
                map.put("store_name", shopType.getText().toString());


                ProgressDialog progressDialog=DailogUtil.showProgressDialog(getContext(),getString(R.string.logging),false);
                serviceInterface = ApiClient.getClientService();
                Call<RegisterModel> registerModelCall = serviceInterface.registerStore(map,body);
                registerModelCall.enqueue(new Callback<RegisterModel>() {
                    @Override
                    public void onResponse(Call<RegisterModel> call, Response<RegisterModel> response) {
                       // Log.v("TraderRegisterFragment", "dddddddddddd" + response.body().toString());
                        if(response.code()==200){
                    if (response.body().getSuccess()==true) {
                        Log.v(TAG,"valid error1 ");
                        Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        data=response.body().getData();
                         data.setToken(response.body().getData().getToken());
                         data.getStoreDataRegisterObject().setStoreLogo(data.getStoreDataRegisterObject().getStoreLogo());
                         data.getUserDataRegisterObject().setEmail(data.getUserDataRegisterObject().getEmail());
                         data.getUserDataRegisterObject().setFullname(data.getUserDataRegisterObject().getFullname());
                         data.getUserDataRegisterObject().setMobile(data.getUserDataRegisterObject().getMobile());
                         data.getUserDataRegisterObject().setId(data.getUserDataRegisterObject().getId());
                        Gson gson = new Gson();
                        String user_data = gson.toJson(data);
                        editor = getContext().getSharedPreferences(LoginTraderUserActivity.MY_PREFS_NAME, MODE_PRIVATE).edit();
                        editor.putString("register_data", user_data);
                        Log.v("TAG","regggg"+gson.toString());
                        editor.commit();
                        Intent intent=new Intent(getActivity(), HomeActivity.class);
                        intent.putExtra("register_store_model",response.body().getData());
                        startActivity(intent);
                        progressDialog.dismiss();



                    }
                    else if(response.body().getSuccess()==false){
                        Log.v(TAG,"valid error2 ");
                        Toast.makeText(getContext(), response.body().getMessage()+response.body().getData().getEmail() + "\n" +
                                response.body().getData().getEmail() + "\n" , Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }}
                        else if(response.code()==404) {
                        Toast.makeText(getContext(), " The email or mobile has already been taken"+ "\n"
                             , Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                    }

                    @Override
                    public void onFailure(Call<RegisterModel> call, Throwable t) {
                        Log.v(TAG, "fail fail");
                        Toast.makeText(getContext(), t.getMessage().toString(), Toast.LENGTH_SHORT).show();

                    }
                });


        }else {
                if(body==null){
                    Toast.makeText(getContext(), "please choose one photo !", Toast.LENGTH_LONG).show();
                }
                if(acceptTerms.isChecked()==false){
                    Toast.makeText(getContext(), "you must check to accept terms", Toast.LENGTH_LONG).show();
                }
            }

        }

    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case image_pick_gallery_code:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    pickGallery();
                else
                    Toast.makeText(getActivity(), "permission denied", Toast.LENGTH_SHORT).show();
                break;
        }
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == image_pick_gallery_code) {
                try {
                    android.net.Uri selectedImage = data.getData();
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
                   // user_image.setImageBitmap(bitmap);
                    InputStream is = getActivity().getContentResolver().openInputStream(data.getData());
                    createMultiPartFile(getBytes(is));

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private byte[] getBytes(InputStream is) throws IOException {
        ByteArrayOutputStream byteBuff = new ByteArrayOutputStream();
        int buffSize = 1024;
        byte[] buff = new byte[buffSize];

        int len = 0;
        while ((len = is.read(buff)) != -1) {
            byteBuff.write(buff, 0, len);
        }
        return byteBuff.toByteArray();
    }

    private void createMultiPartFile(byte[] imageBytes) {
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), imageBytes);
        body = MultipartBody.Part.createFormData("store_logo", "image.jpg", requestFile);
    }
}
