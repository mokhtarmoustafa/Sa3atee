package com.saaty.sideMenuScreen;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.fourhcode.forhutils.FUtilsValidation;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.saaty.R;
import com.saaty.home.HomeActivity;
import com.saaty.loginAndRegister.LoginTraderUserActivity;
import com.saaty.models.LoginModel;
import com.saaty.models.UpdateProfileDataArrayModel;
import com.saaty.models.UpdateProfileModel;
import com.saaty.sideMenuScreen.myAds.EditAdsActivity;
import com.saaty.util.ApiClient;
import com.saaty.util.ApiServiceInterface;
import com.saaty.util.BaseActivity;
import com.saaty.util.DailogUtil;
import com.saaty.util.NetworkAvailable;
import com.saaty.util.PreferenceHelper;
import com.saaty.util.urls;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class EditProfileActivity extends BaseActivity {

    private static final String TAG =EditProfileActivity.class.getSimpleName() ;
    @BindView(R.id.toolbar_txt_id) TextView toolbarTxt;
    @BindView(R.id.user_name_input_id) TextInputEditText nameInputId;
    @BindView(R.id.email_input_id)TextInputEditText emailInputId;
    @BindView(R.id.phone_input_id) TextInputEditText phoneInputId;
    @BindView(R.id.store_name_input_id) TextInputEditText storeNameInputId;
    @BindView(R.id.store_desc_input_id)TextInputEditText storeDescInputId;
    @BindView(R.id.add_img_id) ImageView addImgId;
    @BindView(R.id.profile_img_id) CircleImageView profileImgId;
    @BindView(R.id.send_btn_id) MaterialButton editProfileBtn;
    NetworkAvailable networkAvailable;
    ApiServiceInterface apiServiceInterface;
    DailogUtil dailogUtil;
    private static final int image_pick_gallery_code=330;
    Uri image_uri;
    MultipartBody.Part body ;
    String storage_permission[];
    UpdateProfileDataArrayModel dataArrayModel;
    String type;
Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        ButterKnife.bind(this);
        toolbarTxt.setText(getString(R.string.edit_profile));
        dailogUtil=new DailogUtil();
        intent=getIntent();
        networkAvailable=new NetworkAvailable(getApplicationContext());

        if(intent.hasExtra("profile_model")) {
            dataArrayModel = intent.getParcelableExtra("profile_model");

            if (dataArrayModel.getType().equals("user")) {
                Log.v("TAG", "typeeeee" + dataArrayModel.getType());

                type = dataArrayModel.getType();
            } else if (dataArrayModel.getType().equals("store")) {
                Log.v("TAG", "typeeeee" + dataArrayModel.toString());
                type = dataArrayModel.getType();
                storeDescInputId.setVisibility(View.VISIBLE);
                storeNameInputId.setVisibility(View.VISIBLE);
                addImgId.setVisibility(View.VISIBLE);

                storeNameInputId.setText( ProfileActivity.storeName);
                storeDescInputId.setText( ProfileActivity.storeDesc);

                Log.v("TAG", "typeeeee" + dataArrayModel.getStoreArName() +
                        "   " + dataArrayModel.getStoreArDescription());

                if (ProfileActivity.logo != null) {
                    Picasso.with(getApplicationContext()).load(urls.base_url+"/"+ProfileActivity.logo).error(R.drawable.sidemenu_photo2)
                            .into(profileImgId);
                }

            }
            nameInputId.setText(dataArrayModel.getFullname());
            emailInputId.setText(dataArrayModel.getEmail());
            phoneInputId.setText(dataArrayModel.getMobile());

        }

        editProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(networkAvailable.isNetworkAvailable()){
                    Log.v(TAG,"type eeee"+type);
                    if(type.equals("user")) {
                        updateProfile();
                    }else if(type.equals("store")){
                        updateProfileForStore(body);
                    }
                }else {
                    Toast.makeText(getApplicationContext(), getString(R.string.error_connection), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

//    @OnClick(R.id.send_btn_id)
//    void updateProfileClick(){
//        if(networkAvailable.isNetworkAvailable()){
//            Log.v(TAG,"type eeee"+type);
//            if(type.equals("user")) {
//                updateProfile();
//            }else if(type.equals("store")){
//                updateProfileForStore();
//            }
//        }else {
//            Toast.makeText(this, getString(R.string.error_connection), Toast.LENGTH_LONG).show();
//        }
//    }


    private void updateProfile() {
        if (!FUtilsValidation.isEmpty(nameInputId, getString(R.string.field_required))
                && !FUtilsValidation.isEmpty(emailInputId, getString(R.string.field_required))
                && !FUtilsValidation.isEmpty(phoneInputId, getString(R.string.field_required))
                && FUtilsValidation.isValidEmail(emailInputId, getString(R.string.error_email_msg))) {
            apiServiceInterface = ApiClient.getClientService();
            Map<String, Object> map = new HashMap<>();
            map.put("fullname", nameInputId.getText().toString());
            map.put("mobile", phoneInputId.getText().toString());
            map.put("email", emailInputId.getText().toString());
            ProgressDialog progressDialog = dailogUtil.showProgressDialog(EditProfileActivity.this, getString(R.string.logging), false);
            Call<UpdateProfileModel> call = apiServiceInterface.updateProfile("application/json",  HomeActivity.access_token, map);
            call.enqueue(new Callback<UpdateProfileModel>() {
                @Override
                public void onResponse(Call<UpdateProfileModel> call, Response<UpdateProfileModel> response) {
                   if(response.code()==404){
                       Toast.makeText(EditProfileActivity.this, "error", Toast.LENGTH_LONG).show();

                   }else if(response.code()==200) {
                       if (response.body().isSuccess()) {
                           Toast.makeText(EditProfileActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                           progressDialog.dismiss();
                           finish();
                       } else {
                           Toast.makeText(EditProfileActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                           progressDialog.dismiss();
                       }
                   }

                }

                @Override
                public void onFailure(Call<UpdateProfileModel> call, Throwable t) {
                    Toast.makeText(EditProfileActivity.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
            });
        }

    }


    @OnClick(R.id.add_img_id)
    void addImageProfile(){
        try {
            if (ActivityCompat.checkSelfPermission(EditProfileActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(EditProfileActivity.this, storage_permission, image_pick_gallery_code);
            } else {
                pickGallery();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void pickGallery() {
        Intent gallery_intent = new Intent(Intent.ACTION_PICK);
        gallery_intent.setType("image/*");
        startActivityForResult(gallery_intent, image_pick_gallery_code);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case image_pick_gallery_code:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    pickGallery();
                else
                    Toast.makeText(this, "permission denied", Toast.LENGTH_SHORT).show();

                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == image_pick_gallery_code) {
                try {
                    Uri selectedImage = data.getData();
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                    profileImgId.setImageBitmap(bitmap);
                    InputStream is = getContentResolver().openInputStream(data.getData());
                    String [] proj={MediaStore.Images.Media.DATA};
                    Cursor cursor = managedQuery( data.getData(),
                            proj, // Which columns to return
                            null,       // WHERE clause; which rows to return (all rows)
                            null,       // WHERE clause selection arguments (none)
                            null); // Order-by clause (ascending by name)
                    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    cursor.moveToFirst();
               File file=new File(cursor.getString(column_index));
                    createMultiPartFile(file);
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


    private void createMultiPartFile(File file) {
       // File file = new File();
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), file);
        body = MultipartBody.Part.createFormData("store_logo", "image.jpg", requestFile);
    }

    private void updateProfileForStore(MultipartBody.Part body) {

        if (!FUtilsValidation.isEmpty(nameInputId, getString(R.string.field_required))
                && !FUtilsValidation.isEmpty(emailInputId, getString(R.string.field_required))
                && !FUtilsValidation.isEmpty(phoneInputId, getString(R.string.field_required))
                && !FUtilsValidation.isEmpty(storeNameInputId, getString(R.string.field_required))
                && !FUtilsValidation.isEmpty(storeDescInputId, getString(R.string.field_required))
                && FUtilsValidation.isValidEmail(emailInputId, getString(R.string.error_email_msg))
                  &&body !=null) {
            apiServiceInterface = ApiClient.getClientService();
            Map<String, Object> map = new HashMap<>();
            map.put("fullname", nameInputId.getText().toString());
            map.put("email", emailInputId.getText().toString());
            map.put("mobile", phoneInputId.getText().toString());
            map.put("store_ar_name", storeNameInputId.getText().toString());
            map.put("store_en_name", storeNameInputId.getText().toString());
            map.put("store_ar_description", storeDescInputId.getText().toString());
            map.put("store_en_description", storeDescInputId.getText().toString());

            ProgressDialog progressDialog = dailogUtil.showProgressDialog(EditProfileActivity.this, getString(R.string.logging), false);
            Call<UpdateProfileModel> call = apiServiceInterface.updateStoreProfile("application/json"
                    ,  HomeActivity.access_token
                    , map,body);
            call.enqueue(new Callback<UpdateProfileModel>() {
                @Override
                public void onResponse(Call<UpdateProfileModel> call, Response<UpdateProfileModel> response) {

                    Log.v(TAG,"codeeee"+response.code());

                    if(response.code()==404){
                        Toast.makeText(EditProfileActivity.this, "error", Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                    else if(response.code()==200){
                        if (response.body().isSuccess()) {
                        Toast.makeText(EditProfileActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                        if (body != null) {
                                //MainActivity.userData.setImage(user.getUser_image());
                        }
                        finish();
                        } else {
                            Toast.makeText(EditProfileActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                        }
                    }

                }

                @Override
                public void onFailure(Call<UpdateProfileModel> call, Throwable t) {
                    Toast.makeText(EditProfileActivity.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
            });
        }else {
            if (body == null) {
                Toast.makeText(this, getString(R.string.enter_image_please), Toast.LENGTH_LONG).show();
            }
        }
    }

}
