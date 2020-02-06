package com.saaty.sideMenuScreen.myAds;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.saaty.R;
import com.saaty.home.HomeActivity;
import com.saaty.home.StoresProduct.StoreProductAdapter;
import com.saaty.home.StoresProduct.StoresProductsActivity;
import com.saaty.loginAndRegister.LoginTraderUserActivity;
import com.saaty.models.CheckWishlistModel;
import com.saaty.models.DataArrayModel;
import com.saaty.models.ProductDataItem;
import com.saaty.models.ProductDataModel;
import com.saaty.models.StoreListModel;
import com.saaty.productDetails.ProductDetailsActivity;
import com.saaty.sideMenuScreen.wishlist.DealingWithWishList;
import com.saaty.sideMenuScreen.wishlist.WishlistActivity;
import com.saaty.sideMenuScreen.wishlist.WishlistAdapter;
import com.saaty.util.ApiClient;
import com.saaty.util.ApiServiceInterface;
import com.saaty.util.BaseActivity;
import com.saaty.util.EndlessRecyclerViewScrollListener;
import com.saaty.util.FilterMethods;
import com.saaty.util.NetworkAvailable;
import com.saaty.util.OnItemClickInterface;
import com.saaty.util.OnItemClickRecyclerViewInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyAdsActivity extends BaseActivity {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.progress_id)
    ProgressBar progressBar;
    @BindView(R.id.empty_data_txt_id)
    TextView emptyData;
    @BindView(R.id.toolbar_txt_id) TextView toolbarTxt;
    @BindView(R.id.nav_filter_id)ImageView navFilterImg;
    @BindView(R.id.search_ed_id) EditText searchEditTxt;
    List<DataArrayModel> newSortedList;
    Dialog mDialog;
    List<DataArrayModel> adsProducts;
    WishlistAdapter wishlistAdapter;
    ApiServiceInterface apiServiceInterface;
    NetworkAvailable networkAvailable;
    DealingWithWishList dealingWithWishList;
    int selected_product_id;
    FilterMethods filterMethods;
    int current_page=1;
    String shape_type;
    List<DataArrayModel> searchList=new ArrayList<>();
    int checkMsg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_ads);
        mDialog=new Dialog(this);
        ButterKnife.bind(this);
        networkAvailable=new NetworkAvailable(getApplicationContext());
        toolbarTxt.setText(getString(R.string.my_ads));
        dealingWithWishList=new DealingWithWishList(getApplicationContext());
        adsProducts=new ArrayList<>();
        adsProducts.clear();
        buildRecyclerViewForCategory();
        getAdsroductList(current_page);


        searchEditTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String newText = s.toString();
                Log.v("TAG", "sssssssssss" + adsProducts.size() + s.toString().toLowerCase());
                ArrayList<DataArrayModel> newlist = new ArrayList<>();

                for (DataArrayModel item : adsProducts) {
                    Log.v("TAG", "ar name" + item.getArName().contains(newText));
                    if (item.getArName().contains(newText)) {
                        newlist.add(item);
                    }
                }
                adsProducts.clear();
                if(newlist.size()>0) {
                    for (int i = 0; i < newlist.size(); i++) {
                        adsProducts.add(newlist.get(i));
                        wishlistAdapter.notifyDataSetChanged();

                    }
                }else {
                    emptyData.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });



    }


    @OnClick(R.id.nav_filter_id)
    void navFilterDailog(){
        mDialog.setCancelable(true);
        mDialog.setContentView(R.layout.myads_filter_sort_dialog_layout);
        CheckBox watch,bracletes,newProducts,oldproducts;
        watch=mDialog.findViewById(R.id.watch_check_box);
        bracletes=mDialog.findViewById(R.id.bracletes_check_box);
        newProducts=mDialog.findViewById(R.id.new_product_check_box);
        oldproducts=mDialog.findViewById(R.id.new_product_check_box);
        ConstraintLayout root=mDialog.findViewById(R.id.status_layout);
        MaterialButton done=mDialog.findViewById(R.id.login_visitor_btn_id);
        watch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    root.setVisibility(View.VISIBLE);
                }
            }
        });
        bracletes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    root.setVisibility(View.VISIBLE);
                }
            }
        });

        filterMethods=new FilterMethods(getApplicationContext(),adsProducts);

        mDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mDialog.show();

     done.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             List<DataArrayModel> newList;
             if(watch.isChecked()==true&&bracletes.isChecked()==false&&oldproducts.isChecked()==false&&newProducts.isChecked()){
                 List<DataArrayModel> watchProducts=filterMethods.getWatchesCategory(adsProducts);
                 adsProducts.addAll(watchProducts);
                 wishlistAdapter.notifyDataSetChanged();
             }else if(bracletes.isChecked()==true){
                 List<DataArrayModel> bracletsProducts=filterMethods.getBracletCategory(adsProducts);
                 adsProducts.addAll(bracletsProducts);
                 wishlistAdapter.notifyDataSetChanged();
             }else if(newProducts.isChecked()==true){
                 List<DataArrayModel> newProducts=filterMethods.getHighNewProducts(adsProducts);
                 adsProducts.addAll(newProducts);
                 wishlistAdapter.notifyDataSetChanged();
             }else if(oldproducts.isChecked()==true){
                 List<DataArrayModel> bracletsProducts=filterMethods.getHighUsedProducts(adsProducts);
                 adsProducts.addAll(bracletsProducts);
                 wishlistAdapter.notifyDataSetChanged();
             }else if(watch.isChecked()==true&&newProducts.isChecked()==true){
                 List<DataArrayModel> watchesCategory=filterMethods.getWatchesCategory(adsProducts);
                 List<DataArrayModel> watchNew=filterMethods.getHighNewProducts(watchesCategory);
                 adsProducts.addAll(watchNew);
                 wishlistAdapter.notifyDataSetChanged();
             }else if(watch.isChecked()==true&&oldproducts.isChecked()==true){
                 List<DataArrayModel> watchesCategory=filterMethods.getWatchesCategory(adsProducts);
                 List<DataArrayModel> watchUsed=filterMethods.getHighUsedProducts(watchesCategory);
                 adsProducts.addAll(watchUsed);
                 wishlistAdapter.notifyDataSetChanged();
             }else if(bracletes.isChecked()==true&&newProducts.isChecked()==true){
                 List<DataArrayModel> bracletesCategory=filterMethods.getBracletCategory(adsProducts);
                 List<DataArrayModel> bracletesNew=filterMethods.getHighNewProducts(bracletesCategory);
                 adsProducts.addAll(bracletesNew);
                 wishlistAdapter.notifyDataSetChanged();
             }else if(bracletes.isChecked()==true&&oldproducts.isChecked()==true){
                 List<DataArrayModel> bracletesCategory=filterMethods.getBracletCategory(adsProducts);
                 List<DataArrayModel> bracletesUsed=filterMethods.getHighUsedProducts(bracletesCategory);
                 adsProducts.addAll(bracletesUsed);
                 wishlistAdapter.notifyDataSetChanged();
             }else{
                emptyData.setVisibility(View.VISIBLE);
             }

         mDialog.dismiss();
         }
     });



    }





    private void getAdsroductList(int current_page) {
        if(networkAvailable.isNetworkAvailable()){
            apiServiceInterface= ApiClient.getClientService();
            progressBar.setVisibility(View.VISIBLE);
            Map<String,Object> map=new HashMap<>();
            String token=  HomeActivity.access_token;
            map.put("page",current_page);
            map.put("limit",10);
            Call<StoreListModel> call=apiServiceInterface.getAdsProducts("application/json",token,map);
            call.enqueue(new Callback<StoreListModel>() {
                @Override
                public void onResponse(Call<StoreListModel> call, Response<StoreListModel> response) {
                    if (response.code() == 200) {
                        if (response.body().isSuccess()) {
                            if (response.body().getDataObjectModel().getDataArrayModelList().size() > 0) {

                                adsProducts.addAll(response.body().getDataObjectModel().getDataArrayModelList());
                                wishlistAdapter.notifyDataSetChanged();
                              // adsProducts=response.body().getDataObjectModel().getDataArrayModelList();
                                Toast.makeText(MyAdsActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
//                                if (adsProducts.size() > 0) {
//                                    GridLayoutManager layoutManager=new GridLayoutManager(MyAdsActivity.this,2);
//                                    recyclerView.setHasFixedSize(true);
//                                    recyclerView.setLayoutManager(layoutManager);
//                                    wishlistAdapter =new WishlistAdapter(MyAdsActivity.this,adsProducts);
//                                    recyclerView.setAdapter(wishlistAdapter);
//                                    for(int i=0;i<adsProducts.size();i++){
//                                        selected_product_id=adsProducts.get(i).getProductId();
//                                        checkProductInWishlist(selected_product_id);
//                                    }
//
//
//                                    buildOnClickListener();
                                    progressBar.setVisibility(View.GONE);
                                    

                            } else {
                                recyclerView.setVisibility(View.GONE);
                                emptyData.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.GONE);
                            }
//

                        } else {
                            Toast.makeText(MyAdsActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }else if(response.code()==405){
                        Toast.makeText(MyAdsActivity.this, "error", Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);
                    }

                }
                @Override
                public void onFailure(Call<StoreListModel> call, Throwable t) {
               progressBar.setVisibility(View.GONE);
                }
            });

        }else {
            Toast.makeText(this, getString(R.string.error_connection), Toast.LENGTH_LONG).show();
        }
    }

    void buildRecyclerViewForCategory() {
        GridLayoutManager layoutManager=new GridLayoutManager(MyAdsActivity.this,2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        wishlistAdapter =new WishlistAdapter(MyAdsActivity.this,adsProducts);
//        for(int i=0;i<adsProducts.size();i++){
//            selected_product_id=adsProducts.get(i).getProductId();
//            checkProductInWishlist(selected_product_id);
//        }

        recyclerView.setAdapter(wishlistAdapter);


        buildOnClickListener();

        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                    current_page++;
                    getAdsroductList(current_page);

            }
        });
    }

    private void buildOnClickListener() {
        wishlistAdapter.setOnItemClickListenerRecyclerView(new OnItemClickRecyclerViewInterface() {
            @Override
            public void OnWishListClick(int position, ImageView wishlist_image) {
                dealingWithWishList.addToWishList(adsProducts.get(position),progressBar,wishlist_image);
            }

            @Override
            public void OnItemClick(int position) {
                DataArrayModel item=adsProducts.get(position);
                Intent intent=new Intent(getApplicationContext(), ProductDetailsActivity.class);
                intent.putExtra(ProductDetailsActivity.ADS_PRODUCTS_DETAILS,item);
                startActivity(intent);
            }
        });

    }


    void checkProductInWishlist(int selected_product_idd){
        apiServiceInterface=ApiClient.getClientService();
       
        Call<CheckWishlistModel> call=apiServiceInterface.checkWishlistProduct(selected_product_idd,"application/json",  HomeActivity.access_token);
        call.enqueue(new Callback<CheckWishlistModel>() {
            @Override
            public void onResponse(Call<CheckWishlistModel> call, Response<CheckWishlistModel> response) {
                ImageView img = null;
                img=wishlistAdapter.getWishlistImg();
                if(response.body().getMessage().equals("Product not found.")){
                   // Toast.makeText(MyAdsActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                    Log.v("TAG","product not foundddd  "+selected_product_idd);
                    img.setImageResource(R.drawable.wishlist_not_select);
                    checkMsg=0;
                }else if(response.body().getMessage().equals("Products retrieved successfully.")){
                    img.setImageResource(R.drawable.wishlist_select);
                    //Toast.makeText(MyAdsActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                    Log.v("TAG","product  foundddd  "+selected_product_idd);
                    checkMsg=1;
                }
            }

            @Override
            public void onFailure(Call<CheckWishlistModel> call, Throwable t) {

            }
        });
    }
    private void searchStoreProduct(int current_page,String shape_type){
        if(networkAvailable.isNetworkAvailable()){
            apiServiceInterface= ApiClient.getClientService();
            progressBar.setVisibility(View.VISIBLE);
            Map<String,Object> map=new HashMap<>();
            map.put("product_shape",shape_type);
            map.put("page",current_page);
            map.put("limit",10);
            Call<StoreListModel> call=apiServiceInterface.searchforStoreProduct(map);
            call.enqueue(new Callback<StoreListModel>() {
                @Override
                public void onResponse(Call<StoreListModel> call, Response<StoreListModel> response) {
                        if(response.body().isSuccess()) {
                            searchList.addAll(response.body().getDataObjectModel().getDataArrayModelList());

                            if(searchList.size()>0) {
                                GridLayoutManager layoutManager=new GridLayoutManager(MyAdsActivity.this,2);
                                recyclerView.setHasFixedSize(true);
                                recyclerView.setLayoutManager(layoutManager);
                                wishlistAdapter =new WishlistAdapter(MyAdsActivity.this,searchList);
                                recyclerView.setAdapter(wishlistAdapter);

                            }
                        }else if(searchList.size()==0) {
                            recyclerView.setVisibility(View.GONE);
                            progressBar.setVisibility(View.GONE);
                            emptyData.setVisibility(View.VISIBLE);
                            // }
                        }

                    else {
                        //Toast.makeText(WishlistActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFailure(Call<StoreListModel> call, Throwable t) {
                    progressBar.setVisibility(View.GONE);
                }
            });

        }else {
            Toast.makeText(this, getString(R.string.error_connection), Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.GONE);
        }
    }



}
