package com.saaty.sideMenuScreen.wishlist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.saaty.PaginationScrollListener;
import com.saaty.R;
import com.saaty.database.Database;
import com.saaty.home.HomeActivity;
import com.saaty.home.StoresActivity;
import com.saaty.loginAndRegister.LoginTraderUserActivity;
import com.saaty.models.CheckWishlistModel;
import com.saaty.models.DataArrayModel;
import com.saaty.models.ProductDataItem;
import com.saaty.models.ProductDataModel;
import com.saaty.models.RoomModel;
import com.saaty.models.SendCodeModel;
import com.saaty.models.StoreListModel;
import com.saaty.productDetails.ProductDetailsActivity;
import com.saaty.util.ApiClient;
import com.saaty.util.ApiServiceInterface;
import com.saaty.util.BaseActivity;
import com.saaty.util.EndlessRecyclerViewScrollListener;
import com.saaty.util.NetworkAvailable;
import com.saaty.util.OnItemClickInterface;
import com.saaty.util.OnItemClickRecyclerViewInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WishlistActivity extends BaseActivity {

    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    @BindView(R.id.progress_id) ProgressBar progressBar;
    @BindView(R.id.empty_data_txt_id) TextView emptyData;
    @BindView(R.id.toolbar_txt_id) TextView toolbarTxt;
    @BindView(R.id.search_ed_id)EditText searchEditTxt;
    Database database;

    String sort_type;
    Dialog mDialog;
    List<DataArrayModel> wishlistProducts;
    List<DataArrayModel> newWishlist;
    static List<DataArrayModel> newSortedList;
    WishlistAdapter wishlistAdapter;
    ApiServiceInterface apiServiceInterface;
    int current_page;
    int selected_product_id;
    NetworkAvailable networkAvailable;
    DataArrayModel model;

    private GridLayoutManager layoutManager;

    // Indicates if footer ProgressBar is shown (i.e. next page is loading)
    private boolean isLoading = false;

    // If current page is the last page (Pagination will stop after this page load)
    private boolean isLastPage = false;

    // total no. of pages to load. Initial load is page 0, after which 2 more pages will load.
    private int TOTAL_PAGES;

    // indicates the current page which Pagination is fetching.
    private int currentPage = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);
        mDialog=new Dialog(this);
        ButterKnife.bind(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        networkAvailable=new NetworkAvailable(getApplicationContext());
        toolbarTxt.setText(getString(R.string.wishlist));
        wishlistProducts=new ArrayList<>();
        initializeComponents();
        database=Database.getInstance(this);

        //buildWishlisrRecycler();
        getWishList();

        searchEditTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String newText = s.toString();
               // Log.v("TAG", "sssssssssss" + adsProducts.size() + s.toString().toLowerCase());
                ArrayList<DataArrayModel> newlist = new ArrayList<>();

                for (DataArrayModel item : wishlistProducts) {
                    Log.v("TAG", "ar name" + item.getArName().contains(newText));
                    if (item.getArName().contains(newText)) {
                        newlist.add(item);
                    }
                }
                wishlistProducts.clear();
                if(newlist.size()>0) {
                    for (int i = 0; i < newlist.size(); i++) {
                        wishlistProducts.add(newlist.get(i));
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

    private void getWishList() {
        if(networkAvailable.isNetworkAvailable()){
            apiServiceInterface= ApiClient.getClientService();
            progressBar.setVisibility(View.VISIBLE);
            Map<String,Object> map=new HashMap<>();
          String token= HomeActivity.access_token;
//            map.put("page",currentPage);
            map.put("page",1);
            map.put("limit",100);
            Call<StoreListModel> call=apiServiceInterface.getWishlist(map,"application/json",token);
                    call.enqueue(new Callback<StoreListModel>() {
                @Override
                public void onResponse(Call<StoreListModel> call, Response<StoreListModel> response) {
                    if(response.code()==200){
                        if(response.body().isSuccess()) {
                            wishlistProducts=response.body().getDataObjectModel().getDataArrayModelList();
                                 if(wishlistProducts.size()>0) {
                                     Log.v("TAG", "ssss" + wishlistProducts.size());
                                     GridLayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 2);
                                     recyclerView.setLayoutManager(layoutManager);
                                     recyclerView.setItemAnimator(new DefaultItemAnimator());
                                     recyclerView.setHasFixedSize(true);
                                     wishlistAdapter = new WishlistAdapter(WishlistActivity.this, wishlistProducts);
                                     recyclerView.setAdapter(wishlistAdapter);
                                     Toast.makeText(WishlistActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                                     RoomModel roomModel = null;
                                    // for (int i = 0; i < wishlistProducts.size(); i++) {
                                         roomModel = new RoomModel(wishlistProducts.get(0).getProductId());
                                         Log.v("TAG", "\n database sssssss  " +roomModel.getProduct_id());
                                     //}
                                     //database.dao().insertWishlist(roomModel);

                                     //List<RoomModel> models = database.dao().getWishlist();


                                     buildOnClickInterface();
                                     progressBar.setVisibility(View.GONE);
                                 }
                        }else if(wishlistProducts.size()==0) {
                            recyclerView.setVisibility(View.GONE);
                            emptyData.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                       // }
                        }
//

                    }
                       else {
                       Toast.makeText(WishlistActivity.this,"error", Toast.LENGTH_LONG).show();
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

    private void buildOnClickInterface() {
        wishlistAdapter.setOnItemClickListenerRecyclerView(new OnItemClickRecyclerViewInterface() {
            @Override
            public void OnWishListClick(int position, ImageView wishlist_image) {
               deleteItemFromWishList(wishlistProducts.get(position).getProductId());
            }

            @Override
            public void OnItemClick(int position) {
                Intent intent=new Intent(WishlistActivity.this, ProductDetailsActivity.class);
                DataArrayModel item= wishlistProducts.get(position);
                intent.putExtra(ProductDetailsActivity.WISHLIST_PRODUCTS_DETAILS,item);
                startActivity(intent);
            }
        });
    }

    private void deleteItemFromWishList(int selected_product_id) {
        apiServiceInterface=ApiClient.getClientService();
        Call<SendCodeModel> call=apiServiceInterface.deleteItemFromWishlist(selected_product_id
                ,"application/json"
                ,HomeActivity.access_token);
        Log.v("TAG","product selected  "+selected_product_id);
        call.enqueue(new Callback<SendCodeModel>() {
            @Override
            public void onResponse(Call<SendCodeModel> call, Response<SendCodeModel> response) {
                if (response.code() == 404) {
                    Toast.makeText(WishlistActivity.this, "error ocuured", Toast.LENGTH_LONG).show();

                } else {
                    if (response.body().isSuccess()) {
                        Toast.makeText(WishlistActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                        getWishList();
                        Log.v("TAG", "product selected deleted " + selected_product_id);
                    } else {
                        Toast.makeText(WishlistActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                        Log.v("TAG", "product selected not deleted " + selected_product_id);
                    }
                }
            }
            @Override
            public void onFailure(Call<SendCodeModel> call, Throwable t) {
                Log.v("TAG","product selected failed "+selected_product_id);

            }
        });
    }

    private void buildWishlisrRecycler() {
        GridLayoutManager layoutManager=new GridLayoutManager(getApplicationContext(),2);
         recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        wishlistAdapter=new WishlistAdapter(WishlistActivity.this,wishlistProducts);
        recyclerView.setAdapter(wishlistAdapter);

        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                current_page=current_page+1;

                    getWishList();


            }
        });

    }


    protected void initializeComponents() {


        layoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
        wishlistAdapter= new WishlistAdapter(getApplicationContext(),wishlistProducts);
        recyclerView.setAdapter(wishlistAdapter);
        recyclerView.addOnScrollListener(new PaginationScrollListener(layoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 1;
                getWishList();
            }


            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        currentPage=1;
        getWishList();
    }

    @OnClick(R.id.nav_filter_id)
    void navFilterDailog() {
        final boolean[] isChecking = {true};
        final int[] mCheckedId = {R.id.from_high_radio_btn_id};
        mDialog.setCancelable(true);
        mDialog.setContentView(R.layout.filter_sort_dialog_wishlist_layout);
        mDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mDialog.show();
        CheckBox newProduct,oldProduct,watchChecked,bracletChecked;
        MaterialButton done = mDialog.findViewById(R.id.login_visitor_btn_id);
        RadioGroup group = mDialog.findViewById(R.id.radio_group);

        newProduct = mDialog.findViewById(R.id.new_product_check_box);
        oldProduct = mDialog.findViewById(R.id.old_product_product_check_box);
        watchChecked=mDialog.findViewById(R.id.watch_check_box);
        bracletChecked=mDialog.findViewById(R.id.braclet_product_check_box);



       // getWishList();
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
          List<DataArrayModel> newList=new ArrayList<>();

                int checked_id=group.getCheckedRadioButtonId();
                boolean isCkecked;
                if(checked_id==R.id.from_high_radio_btn_id&&!oldProduct.isChecked()&&!newProduct.isChecked()&&!bracletChecked.isChecked()&&!watchChecked.isChecked()){
                   Log.v("TAG","highhhh");
                    newList=getHighPrice(wishlistProducts);
                }else if(checked_id==R.id.from_low_radio_btn_id&&!oldProduct.isChecked()&&!newProduct.isChecked()&&!bracletChecked.isChecked()&&!watchChecked.isChecked()) {
                    Log.v("TAG","lowww");
                  newList=getLowPice(wishlistProducts);
                }
                else if(newProduct.isChecked()&&oldProduct.isChecked()==false&&watchChecked.isChecked()==false&&bracletChecked.isChecked()==false){
                    Log.v("TAG","sussss");
                     newList=getLowPriceNewProducts(wishlistProducts);
                }else if(oldProduct.isChecked()&&newProduct.isChecked()==false&&watchChecked.isChecked()==false&&bracletChecked.isChecked()==false){
                    Log.v("TAG","susss2");
                    newList=getHighUsedProducts(wishlistProducts);
                }
                else if(checked_id == R.id.from_high_radio_btn_id && oldProduct.isChecked()&&newProduct.isChecked()==false&&watchChecked.isChecked()==false&&bracletChecked.isChecked()==false){
                    List<DataArrayModel> highProducts=getHighPrice(wishlistProducts);
                    newList=getHighUsedProducts(highProducts);
                }else if(checked_id==R.id.from_low_radio_btn_id&&newProduct.isChecked()&&oldProduct.isChecked()==false&&watchChecked.isChecked()==false&&bracletChecked.isChecked()==false){
                    List<DataArrayModel> lowProducts=getLowPice(wishlistProducts);
                    newList=getLowPriceNewProducts(lowProducts);
                    Log.v("TAG","exper 1");
                }else if(checked_id==R.id.from_low_radio_btn_id&&oldProduct.isChecked()&&newProduct.isChecked()==false&&watchChecked.isChecked()==false&&bracletChecked.isChecked()==false){
                    List<DataArrayModel> lowProducts=getLowPice(wishlistProducts);
                   newList=getLowPriceUsedProducts(lowProducts);
                    Log.v("TAG","exper 2");
                }else if(checked_id == R.id.from_high_radio_btn_id&&newProduct.isChecked()==true&&oldProduct.isChecked()==true&&watchChecked.isChecked()==false&&bracletChecked.isChecked()==false){
                    Log.v("TAG","exper 3");
                    newList=getHighPrice(wishlistProducts);

                }else if(checked_id==R.id.from_low_radio_btn_id&&newProduct.isChecked()==true&&oldProduct.isChecked()==true&&watchChecked.isChecked()==false&&bracletChecked.isChecked()==false){
                   newList=getLowPice(wishlistProducts);
                }else if(checked_id==R.id.from_high_radio_btn_id&&watchChecked.isChecked()==true&&oldProduct.isChecked()==false&&newProduct.isChecked()==false&bracletChecked.isChecked()==false){
                    List<DataArrayModel> x1=getHighPrice(wishlistProducts);
                   newList=getWatchesCategory(x1);
                    Log.v("TAG","exper 5");
                }else if(checked_id==R.id.from_high_radio_btn_id&&bracletChecked.isChecked()==true&&oldProduct.isChecked()==false&&newProduct.isChecked()==false&watchChecked.isChecked()==false){
                    List<DataArrayModel> x1=getHighPrice(wishlistProducts);
                   newList=getBracletCategory(x1);
                    Log.v("TAG","exper 6");
                }else if(checked_id==R.id.from_low_radio_btn_id&&watchChecked.isChecked()==true&&oldProduct.isChecked()==false&&newProduct.isChecked()==false&bracletChecked.isChecked()==false){
                    List<DataArrayModel> x1=getLowPice(wishlistProducts);
                   newList=getWatchesCategory(x1);
                    Log.v("TAG","exper 6");
                }else if(checked_id==R.id.from_low_radio_btn_id&&bracletChecked.isChecked()==true&&oldProduct.isChecked()==false&&newProduct.isChecked()==false&watchChecked.isChecked()==false){
                    List<DataArrayModel> x1=getLowPice(wishlistProducts);
                    newList=getBracletCategory(x1);
                    Log.v("TAG","exper 7");
                }else if(checked_id==R.id.from_low_radio_btn_id&&bracletChecked.isChecked()==true&&oldProduct.isChecked()&&newProduct.isChecked()==false&watchChecked.isChecked()==false){
                    List<DataArrayModel> x1=getLowPice(wishlistProducts);
                    List<DataArrayModel> bracletes=getBracletCategory(x1);
                    newList=getLowPriceUsedProducts(bracletes);
                    Log.v("TAG","exper 7");
                }else if(checked_id==R.id.from_low_radio_btn_id&&bracletChecked.isChecked()==true&&newProduct.isChecked()&&newProduct.isChecked()==false&watchChecked.isChecked()==false) {
                    List<DataArrayModel> x1 = getLowPice(wishlistProducts);
                    List<DataArrayModel> bracletes = getBracletCategory(x1);
                    newList = getHighNewProducts(bracletes);
                    Log.v("TAG", "exper 7");
                }else if(checked_id==R.id.from_low_radio_btn_id&&watchChecked.isChecked()==true&&oldProduct.isChecked()&&newProduct.isChecked()==false&watchChecked.isChecked()==false){
                    List<DataArrayModel> x1=getLowPice(wishlistProducts);
                    List<DataArrayModel> bracletes=getWatchesCategory(x1);
                    newList=getLowPriceUsedProducts(bracletes);
                    Log.v("TAG","exper 7");
                }else if(checked_id==R.id.from_low_radio_btn_id&&watchChecked.isChecked()==true&&newProduct.isChecked()&&newProduct.isChecked()==false&watchChecked.isChecked()==false) {
                    List<DataArrayModel> x1 = getLowPice(wishlistProducts);
                    List<DataArrayModel> bracletes = getWatchesCategory(x1);
                    newList = getHighNewProducts(bracletes);
                    Log.v("TAG", "exper 7");
                } else if (checked_id == R.id.from_high_radio_btn_id && bracletChecked.isChecked() == true && oldProduct.isChecked() && newProduct.isChecked() == false & watchChecked.isChecked() == false) {
                    List<DataArrayModel> x1 = getLowPice(wishlistProducts);
                    List<DataArrayModel> bracletes = getBracletCategory(x1);
                    newList = getLowPriceUsedProducts(bracletes);
                    Log.v("TAG", "exper 7");
                } else if (checked_id == R.id.from_high_radio_btn_id && bracletChecked.isChecked() == true && newProduct.isChecked() && newProduct.isChecked() == false & watchChecked.isChecked() == false) {
                    List<DataArrayModel> x1 = getLowPice(wishlistProducts);
                    List<DataArrayModel> bracletes = getBracletCategory(x1);
                    newList = getHighNewProducts(bracletes);
                    Log.v("TAG", "exper 7");
                } else if (checked_id == R.id.from_high_radio_btn_id && watchChecked.isChecked() == true && oldProduct.isChecked() && newProduct.isChecked() == false & watchChecked.isChecked() == false) {
                    List<DataArrayModel> x1 = getLowPice(wishlistProducts);
                    List<DataArrayModel> bracletes = getWatchesCategory(x1);
                    newList = getLowPriceUsedProducts(bracletes);
                    Log.v("TAG", "exper 7");
                } else if (checked_id == R.id.from_high_radio_btn_id && watchChecked.isChecked() == true && newProduct.isChecked() && newProduct.isChecked() == false & watchChecked.isChecked() == false) {
                    List<DataArrayModel> x1 = getLowPice(wishlistProducts);
                    List<DataArrayModel> bracletes = getWatchesCategory(x1);
                    newList = getHighNewProducts(bracletes);
                    Log.v("TAG", "exper 7");
            }

                else {
                    wishlistProducts.clear();
                    wishlistAdapter.notifyDataSetChanged();
                    emptyData.setVisibility(View.VISIBLE);
                    Log.v("TAG","exper else");
                }
                mDialog.dismiss();


                if(newList.size()>0) {
                   // wishlistProducts.clear();
                    wishlistProducts=newList;
                    wishlistAdapter.notifyDataSetChanged();
                }else {
                    wishlistProducts.clear();
                    emptyData.setVisibility(View.VISIBLE);
                    wishlistAdapter.notifyDataSetChanged();
                }

            }
        });


    }

    public static List<DataArrayModel> getHighPrice(List<DataArrayModel>newWishlist){
        newSortedList=new ArrayList<>();
        for (int i = 0; i < newWishlist.size(); i++) {
            // find position of smallest num between (i + 1)th element and last element
            int pos = i;
            for (int j = i; j < newWishlist.size(); j++) {
                if (newWishlist.get(j).getPrice() > newWishlist.get(pos).getPrice())
                    pos = j;
            }
            // Swap min (smallest num) to current position on array
           DataArrayModel min = newWishlist.get(pos);
            newWishlist.set(pos, newWishlist.get(i));
            newWishlist.set(i, min);
        }
         return newWishlist;
    }

    public static List<DataArrayModel> getHighNewProducts(List<DataArrayModel>newWishlist){
        newSortedList=new ArrayList<>();
        for(int i=0;i<newWishlist.size();i++){
            if(newWishlist.get(i).getShape().equals("New")){
                newSortedList.add(newWishlist.get(i));
            }
        }
        return newSortedList;

    }


   public static List<DataArrayModel> getHighUsedProducts(List<DataArrayModel>newWishlist){
        newSortedList=new ArrayList<>();
        for(int i=0;i<newWishlist.size();i++){
            if(newWishlist.get(i).getShape().equals("Used")){
                newSortedList.add(newWishlist.get(i));
            }
        }
        return newSortedList;

    }


    public static List<DataArrayModel> getLowPice(List<DataArrayModel> newWishlist){
        for (int i = 0; i < newWishlist.size(); i++) {
            // find position of smallest num between (i + 1)th element and last element
            int pos = i;
            for (int j = i; j < newWishlist.size(); j++) {
                if (newWishlist.get(j).getPrice() < newWishlist.get(pos).getPrice())
                    pos = j;
            }
            // Swap min (smallest num) to current position on array
            DataArrayModel min = newWishlist.get(pos);
            newWishlist.set(pos, newWishlist.get(i));
            newWishlist.set(i, min);
        }
        return  newWishlist;
    }

    public static List<DataArrayModel> getLowPriceNewProducts(List<DataArrayModel>newWishlist){
        newSortedList=new ArrayList<>();
        for(int i=0;i<newWishlist.size();i++){
            if(newWishlist.get(i).getShape().equals("New")){
                newSortedList.add(newWishlist.get(i));
            }

        }
        return newSortedList;
    }


    public static List<DataArrayModel> getLowPriceUsedProducts(List<DataArrayModel>newWishlist){
        newSortedList=new ArrayList<>();
        for(int i=0;i<newWishlist.size();i++){
            if(newWishlist.get(i).getShape().equals("Used")){
                newSortedList.add(newWishlist.get(i));
            }

        }
        return newSortedList;
    }

    public static List<DataArrayModel> getWatchesCategory(List<DataArrayModel>newWishlist){
        newSortedList=new ArrayList<>();
        for(int i=0;i<newWishlist.size();i++){
            if(newWishlist.get(i).getCategoryId()==1){
                newSortedList.add(newWishlist.get(i));
            }

        }
        return newSortedList;
    }

    public static List<DataArrayModel> getBracletCategory(List<DataArrayModel>newWishlist){
        newSortedList=new ArrayList<>();
        for(int i=0;i<newWishlist.size();i++){
            if(newWishlist.get(i).getCategoryId()==2){
                newSortedList.add(newWishlist.get(i));
            }

        }
        return newSortedList;
    }




    @OnClick(R.id.toolbar_back_left_btn_id)
    void setBackClick(){
        finish();
    }
    @OnClick(R.id.toolbar_home_id)
    void setHomeBack(){
        finish();
    }


}
