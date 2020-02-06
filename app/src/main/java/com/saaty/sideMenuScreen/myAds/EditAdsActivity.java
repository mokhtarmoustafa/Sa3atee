package com.saaty.sideMenuScreen.myAds;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import gun0912.tedbottompicker.TedBottomPicker;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Multipart;
import retrofit2.http.PartMap;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.fourhcode.forhutils.FUtilsValidation;
import com.google.android.material.textfield.TextInputEditText;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.saaty.R;
import com.saaty.home.HomeActivity;
import com.saaty.loginAndRegister.LoginTraderUserActivity;
import com.saaty.models.AdsProductsModel;
import com.saaty.models.DataArrayModel;
import com.saaty.models.EditAdsModel;
import com.saaty.models.ProductDataModel;
import com.saaty.models.SendCodeModel;
import com.saaty.productDetails.ProductDetailsActivity;
import com.saaty.productDetails.ProductImagesListAdapter;
import com.saaty.util.ApiClient;
import com.saaty.util.ApiServiceInterface;
import com.saaty.util.BaseActivity;
import com.saaty.util.DailogUtil;
import com.saaty.util.NetworkAvailable;
import com.saaty.util.PreferenceHelper;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class EditAdsActivity extends BaseActivity {

    public static final String EDIT_ADS_PRODUCT ="edit_ads_product";
    public static final String ADD_NEW_AD ="add_new_ad";
    private static final String TAG =EditAdsActivity.class.getSimpleName() ;
    @BindView(R.id.progress_id) ProgressBar progressBar;
    @BindView(R.id.upload_images_img) ImageView uploadImg;
    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    @BindView(R.id.toolbar_txt_id) TextView toolbarTxt;
    @BindView(R.id.radio_group1) RadioGroup radioGroup1;
    @BindView(R.id.watch_radio_btn_id) RadioButton watchRadioBtn;
    @BindView(R.id.bracletes_radio_btn_id) RadioButton bracletesRadioBtn;
    @BindView(R.id.radio_group2) RadioGroup radioGroup2;
    @BindView(R.id.new_radio_btn_id) RadioButton newhRadioBtn;
    @BindView(R.id.old_radio_btn_id) RadioButton oldRadioBtn;
    @BindView(R.id.radio_group3) RadioGroup radioGroup3;
    @BindView(R.id.phone_radio_btn_id) RadioButton phoneRadioBtn;
    @BindView(R.id.email_radio_btn_id) RadioButton emailRadioBtn;
    @BindView(R.id.message_radio_btn_id) RadioButton msgRadioBtn;
    @BindView(R.id.all_radio_btn_id) RadioButton allRadioBtn;
    @BindView(R.id.product_name_input_id) TextInputEditText productNameInput;
    @BindView(R.id.product_price_input_id)TextInputEditText productPriceInput;
    @BindView(R.id.phone_number_input_id)TextInputEditText phoneNumberInput;
    @BindView(R.id.email_input_id)TextInputEditText emailInput;
    @BindView(R.id.product_details_input_id)TextInputEditText productDescriptionInput;
    String userName;
    UploadImageAdapter adapter;
    DataArrayModel dataArrayModel;
   InputStream inputStream ;
    private static final int CHOOSER=1;
    String imageEncoded;
    List<String> imagesEncodedList;
    private Uri selectedUri;
    public static List<Uri> selectedUriList=new ArrayList<>();
    ApiServiceInterface apiServiceInterface;
    NetworkAvailable networkAvailable;
    private int category_id;
    Intent intent;
    private String productNameAr,productNameEn,productDesc,phone,email,price,productShape,contactType;
    DailogUtil dailogUtil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_ads);
        ButterKnife.bind(this);
        intent=getIntent();
        if(intent.hasExtra(ADD_NEW_AD)){
            toolbarTxt.setText(getString(R.string.add_ads));
        }else if(intent.hasExtra(EDIT_ADS_PRODUCT)){
            toolbarTxt.setText(getString(R.string.edit_ads));
            dataArrayModel=intent.getParcelableExtra(EDIT_ADS_PRODUCT);
            getAdsProductDetails(dataArrayModel);
        }
        networkAvailable=new NetworkAvailable(this);
        dailogUtil=new DailogUtil();

    }



    @OnClick(R.id.add_ads_btn_id)
    void addAdsBtn(){
        inializeFields();

        Log.v(TAG,"product type"+productShape+"  contact type  "+contactType+"desc ar   "+productDesc+"product name  "+productNameAr);

        try {
            for (int i = 0; i < selectedUriList.size(); i++) {
                inputStream = getContentResolver().openInputStream(selectedUriList.get(i));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            if(intent.hasExtra(ADD_NEW_AD)){
                //getFile(selectedUriList);
                addAdsProducts();
            }else if(intent.hasExtra(EDIT_ADS_PRODUCT)){
                editAdsProducts();

            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void inializeFields() {
        if(!FUtilsValidation.isEmpty(emailInput,getString(R.string.field_required))&&
        !FUtilsValidation.isEmpty(phoneNumberInput,getString(R.string.field_required))&&
        !FUtilsValidation.isEmpty(productPriceInput,getString(R.string.field_required))&&
        !FUtilsValidation.isEmpty(productDescriptionInput,getString(R.string.field_required))&&
        FUtilsValidation.isValidEmail(emailInput,getString(R.string.error_email_msg))
                &&radioGroup1.getCheckedRadioButtonId()!=-1
                &&radioGroup2.getCheckedRadioButtonId()!=-1
                &&radioGroup3.getCheckedRadioButtonId()!=-1
                 &&selectedUriList.size()>0){
            int id1= radioGroup1.getCheckedRadioButtonId();
            RadioButton radioButton=(RadioButton) findViewById(id1);
            String radioBtnTxt=radioButton.getText().toString();
            Log.v(TAG,"rrrrrrrrrrrrr"+radioButton.getText().toString());
            if(radioBtnTxt.matches(getString(R.string.watch))){
                category_id=1;
                productNameAr="ساعة";
                productNameEn="watch";
                Log.v(TAG,"rrrrrrrrrrrrr   "+category_id);
            }else if(radioBtnTxt.matches(getString(R.string.bracletes))){
                category_id=2;
                productNameAr="أسورة";
                productNameEn="braclet";
                Log.v(TAG,"rrrrrrrrrrrrr   "+category_id);
            }
            int id2= radioGroup2.getCheckedRadioButtonId();
            RadioButton radioButton2=(RadioButton) findViewById(id2);
            String radioBtnTxt2=radioButton2.getText().toString();
            if(id2==R.id.new_radio_btn_id){
                productShape="New";
                Log.v(TAG,"shape type   "+productShape);
            }else if(id2==R.id.old_radio_btn_id){
                productShape="Used";
                Log.v(TAG,"shape type   "+productShape);
        }

            Log.v(TAG,"shape type final  "+productShape);

            int id3= radioGroup3.getCheckedRadioButtonId();
            RadioButton radioButton3=(RadioButton) findViewById(id3);
            String radioBtnTxt3=radioButton3.getText().toString();
            if(radioBtnTxt3.equals("All")){
                contactType="all";
            }else if(radioBtnTxt3.equals(getString(R.string.phone_number_input))){
                contactType="phone number";
            }else if(radioBtnTxt3.equals(getString(R.string.email_input))){
                contactType="email";
            }else if(radioBtnTxt3.equals(getString(R.string.private_message))){
                contactType="private message";
            }else {
                contactType="all";
            }


            productDesc=productDescriptionInput.getText().toString();
            phone=phoneNumberInput.getText().toString();
            email=emailInput.getText().toString();
            price=productPriceInput.getText().toString();



        }else {
            if(radioGroup1.getCheckedRadioButtonId()==-1){
                Toast.makeText(this, "you must choose the type of product", Toast.LENGTH_LONG).show();
            }if(radioGroup2.getCheckedRadioButtonId()==-1) {
                Toast.makeText(this, "you must choose the status of product", Toast.LENGTH_LONG).show();
            }if (radioGroup3.getCheckedRadioButtonId()==-1) {
                Toast.makeText(this, "you must choose the type of contact", Toast.LENGTH_LONG).show();
            }if(selectedUriList.size()==0){
                Toast.makeText(this, "you must choose the at least one photo", Toast.LENGTH_LONG).show();
            }

            }

    }

    @OnClick(R.id.upload_images_img)
    void uploadOmages(){
        PermissionListener permissionListener=new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                TedBottomPicker.with(EditAdsActivity.this).setPeekHeight(1600)
                        .setTitle("Choose product images:")
                        .setCompleteButtonText("Done")
                        .setEmptySelectionText("No images selected")
                        .setSelectedUri(selectedUri)
                        .showMultiImage(uriList ->{
                            selectedUriList=uriList;
                            showUriList(uriList);

                                }
                                );
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
            }
        };

        checkPermission(permissionListener);
    }

    private void showUriList(List<Uri> uriList) {
     selectedUriList=uriList;
        adapter=new UploadImageAdapter(this,selectedUriList);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 4));
        recyclerView.setAdapter(adapter);


    }


    private void checkPermission(PermissionListener permissionlistener) {

        TedPermission.with(EditAdsActivity.this)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .check();

    }



    private void addAdsProducts() throws FileNotFoundException {
        if(networkAvailable.isNetworkAvailable()){

            ProgressDialog progressDialog =dailogUtil.showProgressDialog(EditAdsActivity.this,getString(R.string.logging),false);
            progressDialog.show();
          apiServiceInterface= ApiClient.getClientService();
            Map<String,Object> map=new HashMap<>();
            map.put("category_id",category_id);
            map.put("ar_name",productNameAr);
            map.put("ar_description",productDesc);
            map.put("price",Integer.valueOf(productPriceInput.getText().toString()));
            map.put("shape",productShape);
            map.put("contact_type",contactType);
            map.put("contact_name",HomeActivity.full_name);
            map.put("contact_mobile",phone);
            map.put("contact_email",email);

            MultipartBody.Part[] parts=new MultipartBody.Part[selectedUriList.size()];
            RequestBody requestBody1 = null;
            for(int i=0;i<selectedUriList.size();i++){
                File file=new File(selectedUriList.get(i).getPath());
                 requestBody1=RequestBody.create(MediaType.parse("*/*"),file);
                parts[i]=MultipartBody.Part.createFormData("photos[]","photos[]",requestBody1);
                Log.v(TAG,"selected uri part "+requestBody1.toString());
            }
            Call<AdsProductsModel> call=null;
            if (parts != null) {
                 call=apiServiceInterface.addAdsProduct("application/json",
                         HomeActivity.access_token,map,parts);
            }


            call.enqueue(new Callback<AdsProductsModel>() {
                @Override
                public void onResponse(Call<AdsProductsModel> call, Response<AdsProductsModel> response) {
                    if(response.body().isSuccess()){
                        Toast.makeText(EditAdsActivity.this, response.body().getMessage().toString(), Toast.LENGTH_LONG).show();
                      finish();
                        progressDialog.dismiss();
                    }else {
                        Toast.makeText(EditAdsActivity.this, response.body().getMessage().toString(), Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                    if(response.code()==404){
                        Toast.makeText(EditAdsActivity.this, response.body().getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<AdsProductsModel> call, Throwable t) {
                    Toast.makeText(EditAdsActivity.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);
                    progressDialog.dismiss();
                }
            });

        }else {
            Toast.makeText(this, getString(R.string.error_connection), Toast.LENGTH_LONG).show();
        }

    }



    private void editAdsProducts() {
        if(networkAvailable.isNetworkAvailable()) {
            ProgressDialog progressDialog = dailogUtil.showProgressDialog(EditAdsActivity.this, getString(R.string.logging), false);
            progressDialog.show();
            apiServiceInterface = ApiClient.getClientService();
            Map<String, Object> map = new HashMap<>();
            map.put("category_id",category_id);
            map.put("ar_name",productNameAr);
            map.put("ar_description",productDesc);
            map.put("price",Integer.valueOf(productPriceInput.getText().toString()));
            map.put("shape",productShape);
            map.put("contact_type",contactType);
            map.put("contact_name",HomeActivity.full_name);
            map.put("contact_mobile",phone);
            map.put("contact_email",email);

            MultipartBody.Part[] parts = new MultipartBody.Part
                    [selectedUriList.size()];
            RequestBody requestBody1 = null;
            for (int i = 0; i < selectedUriList.size(); i++) {
                File file=new File(selectedUriList.get(i).getPath());
                requestBody1 = RequestBody.create(MediaType.parse("*/*"), file);
                parts[i] = MultipartBody.Part.createFormData("photos[]", "photos[]", requestBody1);
                Log.v(TAG, "selected uri part " + requestBody1.toString());
            }

            Call<EditAdsModel> call = null;
            if (parts != null) {
                call = apiServiceInterface.editAdsProduct(dataArrayModel.getProductId(), "application/json",
                        HomeActivity.access_token, map, parts);
            }
            call.enqueue(new Callback<EditAdsModel>() {
                @Override
                public void onResponse(Call<EditAdsModel> call, Response<EditAdsModel> response) {
                    if (response.body().isSuccess()) {
                        Toast.makeText(EditAdsActivity.this, response.body().getMessage().toString(), Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getApplicationContext(), MyAdsActivity.class));
                        progressDialog.dismiss();
                    } else {
                        Toast.makeText(EditAdsActivity.this, response.body().getMessage().toString(), Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                    if (response.code() == 404) {
                        Toast.makeText(EditAdsActivity.this, response.body().getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<EditAdsModel> call, Throwable t) {
                    Toast.makeText(EditAdsActivity.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                    progressDialog.dismiss();
                }
            });
        }else {
            Toast.makeText(this, getString(R.string.error_connection), Toast.LENGTH_LONG).show();
        }


    }


    private byte[] getByte(InputStream inputStream) throws IOException {
       ByteArrayOutputStream outputStream=new ByteArrayOutputStream();

        InputStream inputStream1 = null;

                int size = 1024;
                byte[] bytes = new byte[size];
                int len = 0;

                while ((len = inputStream.read(bytes)) != -1) {
                    outputStream.write(bytes, 0, len);
                }

        return  outputStream.toByteArray();
    }





    private void getAdsProductDetails(DataArrayModel dataArrayModel) {



        Log.v("TAG","data model   "+dataArrayModel.toString());
        if(dataArrayModel.getShape().equals("New")){
            radioGroup2.check((R.id.new_radio_btn_id));
        }else if(dataArrayModel.getShape().equals("Used")){
            radioGroup2.check((R.id.old_radio_btn_id));
        }

        if(dataArrayModel.getCategoryId()==1){
            radioGroup1.check(R.id.watch_radio_btn_id);
        }else if(dataArrayModel.getCategoryId()==2){
            radioGroup1.check(R.id.bracletes_radio_btn_id);
        }
        productNameInput.setText(dataArrayModel.getArName());
        productPriceInput.setText(String.valueOf(dataArrayModel.getPrice()));
        phoneNumberInput.setText(String.valueOf(dataArrayModel.getContactMobile()));
        emailInput.setText(dataArrayModel.getContactEmail());
        productDescriptionInput.setText(dataArrayModel.getArDescription());
        String contactType=dataArrayModel.getContactType();
        if(contactType.equals("all")){
            radioGroup3.check(R.id.all_radio_btn_id);
        }else if(contactType.equals("email")){
            radioGroup3.check(R.id.email_radio_btn_id);
        }else if(contactType.equals("message")){
            radioGroup3.check(R.id.message_radio_btn_id);
        }else if(contactType.equals("phone")){
            radioGroup3.check(R.id.phone_radio_btn_id);
        }

//        if (dataArrayModel.getProductimages().size() == 0) {
//            List<Uri> uris = new ArrayList<>();
//            Log.v("TAG","sssssssss"+dataArrayModel.getProductimages().size());
//            for (int i = 0; i < dataArrayModel.getProductimages().size(); i++) {
//                uris.add(Uri.parse(dataArrayModel.getProductimages().get(i).getImageLink()));
//            }
//            adapter = new UploadImageAdapter(this, uris);
//            recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 4));
//            recyclerView.setAdapter(adapter);
//
//        }

    }

    @OnClick(R.id.toolbar_back_left_btn_id)
    void onClick(){
        finish();
    }


}
