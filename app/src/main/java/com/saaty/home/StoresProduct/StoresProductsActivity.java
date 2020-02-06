package com.saaty.home.StoresProduct;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.tabs.TabLayout;
import com.saaty.R;
import com.saaty.home.HomeActivity;
import com.saaty.home.StoresActivity;
import com.saaty.loginAndRegister.LoginTraderUserActivity;
import com.saaty.models.DataArrayModel;
import com.saaty.models.ProductDataItem;
import com.saaty.models.StoreListModel;
import com.saaty.productDetails.ProductDetailsActivity;
import com.saaty.sideMenuScreen.wishlist.DealingWithWishList;
import com.saaty.sideMenuScreen.wishlist.WishlistActivity;
import com.saaty.util.ApiClient;
import com.saaty.util.ApiServiceInterface;
import com.saaty.util.BaseActivity;
import com.saaty.util.EndlessRecyclerViewScrollListener;
import com.saaty.util.FilterMethods;
import com.saaty.util.NetworkAvailable;
import com.saaty.util.OnItemClickRecyclerViewInterface;
import com.saaty.util.PreferenceHelper;
import com.saaty.util.urls;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class StoresProductsActivity extends BaseActivity {

    private static final String TAG = StoresProductsActivity.class.getSimpleName();
    @BindView(R.id.tab_layout_id) TabLayout tabLayout;
    @BindView(R.id.toolbar_txt_id)TextView toolbarName;
    @BindView(R.id.nav_filter_id) ImageView navFilter;
    @BindView(R.id.store_name_id) TextView storeName;
    @BindView(R.id.store_img_id) CircleImageView storeImgId;
    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    @BindView(R.id.empty_data_txt_id)TextView emptyData;
    @BindView(R.id.search_ed_id) EditText searchView;
   // @BindView(R.id.view_pager_id) ViewPager viewPager;
   List<DataArrayModel> newList=new ArrayList<>();
    FilterMethods filterMethods;
    int tab_selected;
    @BindView(R.id.progress_id)
    ProgressBar progressBar;
    String shape_type="New";
    int current_page=1;
    StoreProductsPagerAdaper pagerAdaper;
    DealingWithWishList dealingWithWishList;
    ApiServiceInterface apiServiceInterface;
    List<DataArrayModel> storeProductsList=new ArrayList<>();
    List<DataArrayModel> newProducts=new ArrayList<>();
    List<DataArrayModel> products=new ArrayList<>();
    List<DataArrayModel> wishlistProducts=new ArrayList<>();
    List<Integer> ids=new ArrayList<>();
    NetworkAvailable networkAvailable;
    StoreProductAdapter adapter;
    Dialog mDialog;
    String store_name;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stores_products);
        ButterKnife.bind(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        networkAvailable=new NetworkAvailable(this);
        dealingWithWishList=new DealingWithWishList(this);
        mDialog=new Dialog(this);
        pagerAdaper=new StoreProductsPagerAdaper(getSupportFragmentManager());
       if(networkAvailable.isNetworkAvailable()){
            if(HomeActivity.user_id !=0){
                getWishList();
            }
        }else {
            Toast.makeText(this, getString(R.string.error_connection), Toast.LENGTH_LONG).show();
        }

        Intent intent=getIntent();
        if (intent.getAction().equals("store_products_details")) {
          if(PreferenceHelper.getValue(getApplicationContext()).equals("ar")) {
              store_name = intent.getStringExtra("store_name_ar");
              toolbarName.setText(store_name);
              storeName.setText(store_name);
          }else if(PreferenceHelper.getValue(getApplicationContext()).equals("en")){
              store_name = intent.getStringExtra("store_name_en");
              toolbarName.setText(store_name);
              storeName.setText(store_name);
          }
          id=intent.getIntExtra("store_id",1);
            Picasso.with(getApplicationContext()).load(urls.base_url+"/"+intent.getStringExtra("store_img"))
                    .error(R.drawable.store1).into(storeImgId);
        }
       if(networkAvailable.isNetworkAvailable()){
           String newProd=getString(R.string.new_products);
           tabLayout.addTab(tabLayout.newTab().setText("      "+newProd+"     "),0);
           newProducts.clear();
           emptyData.setVisibility(View.GONE);

           shape_type="New";
           buildRecyclerViewForCategory();
           getStoreProducts(current_page,"New");
           progressBar.setVisibility(View.GONE);
           tabLayout.addTab(tabLayout.newTab().setText("        "+getString(R.string.old_products)+"        "),1);

       }else {
           Toast.makeText(this, getString(R.string.error_connection), Toast.LENGTH_LONG).show();
       }

       tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
           @Override
           public void onTabSelected(TabLayout.Tab tab) {
               tab_selected=tab.getPosition();
               if(tab.getPosition()==0){
                   shape_type="New";
                   Log.v(TAG,"shape selected"+tab.getPosition());

               }else if(tab.getPosition()==1){
                   shape_type="Used";
                   Log.v(TAG,"shape selected"+tab.getPosition());
               }
               //buildRecyclerViewForCategory();
               if(networkAvailable.isNetworkAvailable()) {
                   emptyData.setVisibility(View.GONE);
                   storeProductsList.clear();
                   current_page=1;
                   buildRecyclerViewForCategory();
                   getStoreProducts(current_page,shape_type);
               }else {
                   Toast.makeText(getApplicationContext(), getString(R.string.error_connection), Toast.LENGTH_LONG).show();
               }
           }

           @Override
           public void onTabUnselected(TabLayout.Tab tab) {

           }

           @Override
           public void onTabReselected(TabLayout.Tab tab) {

           }
       });



        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String newText = s.toString();
                Log.v("TAG", "sssssssssss" + newProducts.size() + s.toString().toLowerCase());
                ArrayList<DataArrayModel> newlist = new ArrayList<>();

                for (DataArrayModel item : newProducts) {
                    Log.v("TAG", "ar name" + item.getArName().contains(newText));
                    if (item.getArName().contains(newText)) {
                        newlist.add(item);
                    }
                }
                newProducts.clear();
                if(newlist.size()>0) {
                    for (int i = 0; i < newlist.size(); i++) {
                        newProducts.add(newlist.get(i));
                        adapter.notifyDataSetChanged();

                    }
                }else {
                    emptyData.setVisibility(View.VISIBLE);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });




    }




    private void getStoreProducts(int current_pag,String shape_type) {
        if(networkAvailable.isNetworkAvailable()){
            progressBar.setVisibility(View.VISIBLE);
            apiServiceInterface= ApiClient.getClientService();
            Map<String,Object> map=new HashMap<>();
            map.put("page",current_pag);
            map.put("limit",10);
            map.put("product_shape",shape_type);
            map.put("store_id",id);
            Log.v(TAG,"posss"+id);
            Call<StoreListModel> call=apiServiceInterface.getNewOldStoreProduct(map);
            call.enqueue(new Callback<StoreListModel>() {
                @Override
                public void onResponse(Call<StoreListModel> call, Response<StoreListModel> response) {
                    if (response.body().isSuccess()) {
                        if(response.body().getDataObjectModel().getDataArrayModelList().size()>0) {
                            storeProductsList.addAll(response.body().getDataObjectModel().getDataArrayModelList());
                            adapter.notifyDataSetChanged();
                            progressBar.setVisibility(View.GONE);
                        }else if(response.body().getDataObjectModel().getDataArrayModelList().size()==0&&current_pag==1){
                            progressBar.setVisibility(View.GONE);
                            emptyData.setVisibility(View.VISIBLE);
                        }

                    } else {
                        Toast.makeText(StoresProductsActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);
                        emptyData.setVisibility(View.VISIBLE);
                    }

                }
                @Override
                public void onFailure(Call<StoreListModel> call, Throwable t) {
                    Toast.makeText(StoresProductsActivity.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }
            });
        }else {
            Toast.makeText(StoresProductsActivity.this, getString(R.string.error_connection), Toast.LENGTH_LONG).show();
        }

    }



    void buildRecyclerViewForCategory() {
        GridLayoutManager layoutManager=new GridLayoutManager(StoresProductsActivity.this,2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        adapter =new StoreProductAdapter(StoresProductsActivity.this,storeProductsList);
        recyclerView.setAdapter(adapter);
        buildOnClickListener();

        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
               // if(tab_selected==0){
                    current_page++;
                    getStoreProducts(current_page,shape_type);
                    //searchStoreProduct(current_page,shape_type);
               // }
                 }
        });

    }

        private void buildOnClickListener() {
        adapter.setOnItemClickListenerRecyclerView(new OnItemClickRecyclerViewInterface() {
            @Override
            public void OnWishListClick(int position, ImageView wishlist_image) {
           // dealingWithWishList.addToWishList(newProducts.get(position),progressBar,wishlist_image);
                dealingWithWishList.addToWishList(storeProductsList.get(position),progressBar,wishlist_image);
            }

            @Override
            public void OnItemClick(int position) {
              DataArrayModel item=storeProductsList.get(position);
                Intent intent=new Intent(getApplicationContext(), ProductDetailsActivity.class);
                intent.putExtra(ProductDetailsActivity.CATEGORY_PRODUCTS_DETAILS,item);
                startActivity(intent);
                 
            }
        });

    }

    private void getWishList() {
        if(networkAvailable.isNetworkAvailable()){
            apiServiceInterface= ApiClient.getClientService();
            progressBar.setVisibility(View.VISIBLE);
            Map<String,Object> map=new HashMap<>();
            String token=  HomeActivity.access_token;
//            map.put("page",currentPage);
            map.put("page",1);
            map.put("limit",100);
            Call<StoreListModel> call=apiServiceInterface.getWishlist(map,"application/json",token);
            call.enqueue(new Callback<StoreListModel>() {
                @Override
                public void onResponse(Call<StoreListModel> call, Response<StoreListModel> response) {
                    if(response.body().isSuccess()){
                        if(response.body().isSuccess()) {
                            wishlistProducts=response.body().getDataObjectModel().getDataArrayModelList();
                            if(wishlistProducts.size()>0) {
                                Log.v("TAG","ssss"+wishlistProducts.size());
                                for(int i=0;i<wishlistProducts.size();i++){
                                    ids.add(wishlistProducts.get(i).getProductId());
                                    Log.v("TAG","iddddddddd"+ids.size());
                                }

                            }
                        }else if(wishlistProducts.size()==0) {
                            recyclerView.setVisibility(View.GONE);
                            progressBar.setVisibility(View.GONE);
                            // }
                        }

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
        }
    }



    @OnClick(R.id.nav_filter_id)
    void filterClick(){
        mDialog=new Dialog(StoresProductsActivity.this);
        mDialog.setCancelable(true);
        mDialog.setContentView(R.layout.filter_sort_dialog_layout);
        CheckBox newProduct,oldProduct;
        newProduct = mDialog.findViewById(R.id.new_product_check_box);
        oldProduct = mDialog.findViewById(R.id.old_product_product_check_box);
        Button done = mDialog.findViewById(R.id.login_visitor_btn_id);
        RadioGroup group = mDialog.findViewById(R.id.radio_group);
        filterMethods=new FilterMethods(getApplicationContext(),storeProductsList);
        mDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mDialog.show();
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int checked_id=group.getCheckedRadioButtonId();
                Log.v("TAG","category filter"+storeProductsList.size());

                if(checked_id == R.id.from_high_radio_btn_id && !newProduct.isChecked()&&!oldProduct.isChecked()){
                    newList=filterMethods.getHighPrice(storeProductsList);
                }
                else  if (checked_id == R.id.from_high_radio_btn_id && newProduct.isChecked()&&!oldProduct.isChecked()) {
                    List<DataArrayModel> x1 = filterMethods.getHighNewProducts(storeProductsList);
                    newList=filterMethods.getHighPrice(x1);
                }else if(checked_id == R.id.from_high_radio_btn_id && oldProduct.isChecked()&&!newProduct.isChecked()){
                    List<DataArrayModel> x=filterMethods.getHighUsedProducts(storeProductsList);
                    newList=filterMethods.getHighUsedProducts(x);
                }else if(checked_id==R.id.from_low_radio_btn_id&&!newProduct.isChecked()&&!oldProduct.isChecked()){
                    newList=filterMethods.getLowPice(storeProductsList);
                }else if(checked_id==R.id.from_low_radio_btn_id&&newProduct.isChecked()&&!oldProduct.isChecked()){
                    List<DataArrayModel>x=filterMethods.getHighNewProducts(storeProductsList);
                    newList=filterMethods.getLowPice(x);
                }else if(checked_id==R.id.from_low_radio_btn_id&&!newProduct.isChecked()&&oldProduct.isChecked()){
                    List<DataArrayModel>x=filterMethods.getHighUsedProducts(storeProductsList);
                    newList=filterMethods.getLowPice(x);
                }else if(checked_id==R.id.from_low_radio_btn_id&&newProduct.isChecked()&&oldProduct.isChecked()){
                    newList=filterMethods.getLowPice(storeProductsList);
                } else if(checked_id==R.id.from_high_radio_btn_id&&newProduct.isChecked()&&oldProduct.isChecked()){
                    newList=filterMethods.getHighPrice(storeProductsList);
                }else {
                    emptyData.setVisibility(View.VISIBLE);
                    adapter.notifyDataSetChanged();
                }
                if(newList.size()>0) {
                    storeProductsList = newList;
                    GridLayoutManager layoutManager=new GridLayoutManager(StoresProductsActivity.this,2);
                    adapter =new StoreProductAdapter(StoresProductsActivity.this,storeProductsList);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(adapter);
                   // buildOnClickListener();
                }
                mDialog.dismiss();
            }
        });


    }

}