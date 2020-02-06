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
import android.view.WindowManager;
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

public class AdsActivity extends BaseActivity {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.progress_id)
    ProgressBar progressBar;
    @BindView(R.id.empty_data_txt_id)
    TextView emptyData;
    @BindView(R.id.toolbar_txt_id)
    TextView toolbarTxt;
    @BindView(R.id.nav_filter_id)
    ImageView navFilterImg;
    @BindView(R.id.search_ed_id)
    EditText searchEditTxt;
    public static int flag;
    List<DataArrayModel> newList=new ArrayList<>();
    List<DataArrayModel> newAdsProduct=new ArrayList<>();
    Dialog mDialog;
    List<DataArrayModel> adsProducts;
    List<DataArrayModel> wishlistProducts = new ArrayList<>();
    WishlistAdapter wishlistAdapter;
    ApiServiceInterface apiServiceInterface;
    NetworkAvailable networkAvailable;
    DealingWithWishList dealingWithWishList;
    int selected_product_id;
    FilterMethods filterMethods;
    StoreProductAdapter storeProductAdapter;
    int x;
    List<Integer> ids = new ArrayList<>();
    int current_page = 1;
    String shape_type;
    List<DataArrayModel> searchList = new ArrayList<>();
    int checkMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_ads);
        mDialog = new Dialog(this);
        ButterKnife.bind(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        networkAvailable = new NetworkAvailable(getApplicationContext());
        toolbarTxt.setText(getString(R.string.my_ads));
        dealingWithWishList = new DealingWithWishList(getApplicationContext());
        adsProducts = new ArrayList<>();
        buildRecyclerViewForCategory();
        getAdsroductList(current_page);
        checkWishlist(newAdsProduct);
        storeProductAdapter.notifyDataSetChanged();
       // progressBar.setVisibility(View.GONE);


       // getWishList();

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
                if (newlist.size() > 0) {
                    for (int i = 0; i < newlist.size(); i++) {
                        adsProducts.add(newlist.get(i));
                        storeProductAdapter.notifyDataSetChanged();

                    }
                } else {
                    adsProducts.clear();
                    emptyData.setVisibility(View.VISIBLE);
                    storeProductAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });


    }


    private void getAdsroductList(int current_page) {
        if (networkAvailable.isNetworkAvailable()) {
            apiServiceInterface = ApiClient.getClientService();
            progressBar.setVisibility(View.VISIBLE);
            Map<String, Object> map = new HashMap<>();
            String token = HomeActivity.access_token;
            map.put("page", current_page);
            map.put("limit", 10);
            Call<StoreListModel> call = apiServiceInterface.getAdsProducts("application/json", token, map);
            call.enqueue(new Callback<StoreListModel>() {
                @Override
                public void onResponse(Call<StoreListModel> call, Response<StoreListModel> response) {
                    if (response.code() == 200) {
                        if (response.body().isSuccess()) {
                            if (response.body().getDataObjectModel().getDataArrayModelList().size() > 0) {
                                newAdsProduct=response.body().getDataObjectModel().getDataArrayModelList();
                                adsProducts.addAll(response.body().getDataObjectModel().getDataArrayModelList());
                                storeProductAdapter.notifyDataSetChanged();
                                checkWishlist(newAdsProduct);
//

//                                GridLayoutManager layoutManager = new GridLayoutManager(AdsActivity.this, 2);
//                                recyclerView.setHasFixedSize(true);
//                                recyclerView.setLayoutManager(layoutManager);
//                                storeProductAdapter =new StoreProductAdapter(AdsActivity.this,newAdsProduct);
//                                getWishList(adsProducts);
//                                recyclerView.setAdapter(storeProductAdapter);
//                                Log.v("TAG", "ads products" + adsProducts.size());
//                                buildOnClickListener();
                                Toast.makeText(AdsActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
////                                progressBar.setVisibility(View.GONE);
                                progressBar.setVisibility(View.GONE);
                            }
                        } else if(response.body().getDataObjectModel().getDataArrayModelList().size()==0&&current_page==1){
                            recyclerView.setVisibility(View.GONE);
                            emptyData.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                        }
//
                    } else if (response.code() == 405) {
                        Toast.makeText(AdsActivity.this, "error", Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);
                    }

                }

                @Override
                public void onFailure(Call<StoreListModel> call, Throwable t) {
                    progressBar.setVisibility(View.GONE);
                }
            });

        } else {
            Toast.makeText(this, getString(R.string.error_connection), Toast.LENGTH_LONG).show();

        }
    }

    void buildRecyclerViewForCategory() {
        GridLayoutManager layoutManager = new GridLayoutManager(AdsActivity.this, 2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
         storeProductAdapter =new StoreProductAdapter(AdsActivity.this,adsProducts);
          //getWishList(adsProducts);

        progressBar.setVisibility(View.GONE);
        recyclerView.setAdapter(storeProductAdapter);
        Log.v("TAG", "ads products" + adsProducts.size());
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
        storeProductAdapter.setOnItemClickListenerRecyclerView(new OnItemClickRecyclerViewInterface() {
            @Override
            public void OnWishListClick(int position, ImageView wishlist_image) {
              // dealingWithWishList.checkWishList(adsProducts.get(position),progressBar);
                //if(status==true){
                    dealingWithWishList.addToWishList(adsProducts.get(position), progressBar, wishlist_image);
               // }else if(status==false){
                   // dealingWithWishList.deleteWishList(adsProducts.get(position), progressBar, wishlist_image);

                //}
                //dealingWithWishList.addToWishList(adsProducts.get(position), progressBar, wishlist_image);
            }

            @Override
            public void OnItemClick(int position) {
                DataArrayModel item = adsProducts.get(position);
                Intent intent = new Intent(getApplicationContext(), ProductDetailsActivity.class);
                intent.putExtra(ProductDetailsActivity.ADS_PRODUCTS_DETAILS, item);
                startActivity(intent);
            }
        });

    }


    private void getWishList( List<DataArrayModel> newAds) {
        if (networkAvailable.isNetworkAvailable()) {
            apiServiceInterface = ApiClient.getClientService();
            //progressBar.setVisibility(View.VISIBLE);
            Map<String, Object> map = new HashMap<>();
            String token = HomeActivity.access_token;
//            map.put("page",currentPage);
            map.put("page", 1);
            map.put("limit", 100);
            Call<StoreListModel> call = apiServiceInterface.getWishlist(map, "application/json", token);
            call.enqueue(new Callback<StoreListModel>() {
                @Override
                public void onResponse(Call<StoreListModel> call, Response<StoreListModel> response) {
                    if (response.code()==200) {
                        if (response.body().isSuccess()) {
                            wishlistProducts = response.body().getDataObjectModel().getDataArrayModelList();
                            if (wishlistProducts.size() > 0) {
                                Log.v("TAG", "ssss wishlist" + wishlistProducts.size());
                                Log.v("TAG", "ssss ads" + newAds.size());
                                  //ImageView check=storeProductAdapter.getWishlistImg();
                                        for(int j=0;j<wishlistProducts.size();j++){
                                            for(int i=0;i<newAds.size();i++){
                                            if(newAds.get(i).getProductId()==wishlistProducts.get(j).getProductId()){
                                               // check.setImageResource(R.drawable.wishlist_select);
                                                Log.v("TAG","accepted   "+newAds.get(i).getProductId() +"     " +wishlistProducts.get(i).getProductId());
                                            }else {
                                                //check.setImageResource(R.drawable.wishlist_not_select);
                                               // Log.v("TAG","rejected   "+adsProducts.get(i).getProductId() +"     " +wishlistProducts.get(i).getProductId());

                                            }
                                           // storeProductAdapter.setWishList(check);
                                        }
                                    }




                            }
                        } else if (wishlistProducts.size() == 0) {
                            recyclerView.setVisibility(View.GONE);
                            //emptyData.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                            // }
                        }
//

                    } else {
                        Toast.makeText(AdsActivity.this, "error", Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFailure(Call<StoreListModel> call, Throwable t) {
                    progressBar.setVisibility(View.GONE);
                }
            });

        } else {
            Toast.makeText(this, getString(R.string.error_connection), Toast.LENGTH_LONG).show();
        }
    }


    @OnClick(R.id.nav_filter_id)
    void navFilterDailog() {
        mDialog.setCancelable(true);
        mDialog.setContentView(R.layout.myads_filter_sort_dialog_layout);
        CheckBox watch, bracletes, newProducts, oldproducts;

        ConstraintLayout root = mDialog.findViewById(R.id.status_layout);
        MaterialButton done = mDialog.findViewById(R.id.login_visitor_btn_id);
        watch = mDialog.findViewById(R.id.watch_check_box);
        bracletes = mDialog.findViewById(R.id.bracletes_check_box);
        newProducts = mDialog.findViewById(R.id.new_product_check_box);
        oldproducts = mDialog.findViewById(R.id.new_product_check_box);

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
                if (isChecked) {
                    root.setVisibility(View.VISIBLE);
                }
            }
        });

        filterMethods = new FilterMethods(getApplicationContext(), adsProducts);

        mDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mDialog.show();
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (watch.isChecked() == true && !bracletes.isChecked()&& !oldproducts.isChecked() && !newProducts.isChecked()) {
                    Log.v("TAG","exper 1");
                    newList = filterMethods.getWatchesCategory(newAdsProduct);
                } else if (bracletes.isChecked() == true && !watch.isChecked()&& !oldproducts.isChecked() && !newProducts.isChecked()) {
                    Log.v("TAG","exper 2");
                    newList = filterMethods.getBracletCategory(newAdsProduct);
                } else if (newProducts.isChecked() == true&&watch.isChecked() == true && !bracletes.isChecked()&& !oldproducts.isChecked() ) {
                    Log.v("TAG","exper 3");
                    List<DataArrayModel>x = filterMethods.getWatchesCategory(newAdsProduct);
                    newList=filterMethods.getHighNewProducts(x);
                } else if (watch.isChecked()&&oldproducts.isChecked()&& !bracletes.isChecked()&& !newProducts.isChecked()) {
                    Log.v("TAG","exper 4");
                    List<DataArrayModel> x=filterMethods.getWatchesCategory(newAdsProduct);
                    newList = filterMethods.getHighUsedProducts(x);
                } else if (bracletes.isChecked() && newProducts.isChecked()&&!oldproducts.isChecked()&&!watch.isChecked()) {
                    Log.v("TAG","exper 5");
                    List<DataArrayModel> watchesCategory = filterMethods.getBracletCategory(newAdsProduct);
                    newList = filterMethods.getHighNewProducts(watchesCategory);
                } else if (bracletes.isChecked() && oldproducts.isChecked()&&!newProducts.isChecked()&&!watch.isChecked()) {
                    Log.v("TAG","exper 6");
                    List<DataArrayModel> x = filterMethods.getBracletCategory(newAdsProduct);
                    newList = filterMethods.getHighUsedProducts(x);
                } else if (bracletes.isChecked() && newProducts.isChecked()&&oldproducts.isChecked()&&!watch.isChecked()) {
                    Log.v("TAG","exper 7");
                    newList = filterMethods.getBracletCategory(newAdsProduct);
                } else if (watch.isChecked() && oldproducts.isChecked()&&newProducts.isChecked()&&!bracletes.isChecked()) {
                    Log.v("TAG","exper 8");
                    newList  = filterMethods.getWatchesCategory(newAdsProduct);

                } else {
                    Log.v("TAG","exper else");
                    emptyData.setVisibility(View.VISIBLE);
                    storeProductAdapter.notifyDataSetChanged();
                }
               // adsProducts=newList;
               // storeProductAdapter.notifyDataSetChanged();
                adsProducts.clear();
                GridLayoutManager layoutManager = new GridLayoutManager(AdsActivity.this, 2);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(layoutManager);
                storeProductAdapter =new StoreProductAdapter(AdsActivity.this,newList);
                recyclerView.setAdapter(storeProductAdapter);
                mDialog.dismiss();
            }
        });


    }

    @OnClick(R.id.toolbar_back_left_btn_id)
    void onBack(){
        finish();
    }

    @OnClick(R.id.toolbar_home_id)
    void onBackHome(){
        finish();
    }


    private void checkWishlist(List<DataArrayModel> models) {
       // Log.v("TAG","ddddd"+models.size());
        int id = 0;
        for (int i = 0; i < models.size(); i++) {
            id = models.get(i).getProductId();
            apiServiceInterface = ApiClient.getClientService();
            String token = HomeActivity.access_token;
            //int check=storeProductAdapter.getWishlistImg();
            Call<CheckWishlistModel> call = apiServiceInterface.checkWishlistProduct(id, "application/json", token);
            call.enqueue(new Callback<CheckWishlistModel>() {
                @Override
                public void onResponse(Call<CheckWishlistModel> call, Response<CheckWishlistModel> response) {
                    if (response.body().getMessage().equals("Product not found.")) {
                        x=R.drawable.wishlist_not_select;
                        //storeProductAdapter.getWishlistImg(x);
                        flag=0;
                      Log.v("TAG","found"+flag);

                    }else if(response.body().getMessage().equals("Products retrieved successfully.")){
                        flag=1;
                        x =R.drawable.wishlist_select;
                       // storeProductAdapter.getWishlistImg(x);
                        Log.v("TAG","found not"+flag);

                    }
                    storeProductAdapter.getWishlistImg(flag);
                    storeProductAdapter.notifyDataSetChanged();

                }

                @Override
                public void onFailure(Call<CheckWishlistModel> call, Throwable t) {

                }
            });


        }
    }
}
