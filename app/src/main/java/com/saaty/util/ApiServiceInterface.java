package com.saaty.util;

import com.saaty.models.AdsProductsModel;
import com.saaty.models.CategoryModel;
import com.saaty.models.CheckWishlistModel;
import com.saaty.models.DataObjectModel;
import com.saaty.models.DeleteAdsModel;
import com.saaty.models.DeleteMessageModel;
import com.saaty.models.EditAdsModel;
import com.saaty.models.GetProductImagesModel;
import com.saaty.models.LoginModel;
import com.saaty.models.MessageModel;
import com.saaty.models.ProductDataModel;
import com.saaty.models.RegisterModel;
import com.saaty.models.ReplyMessageModel;
import com.saaty.models.SendCodeModel;
import com.saaty.models.SendMessageProductModel;
import com.saaty.models.StoreListModel;
import com.saaty.models.UpdateProfileModel;
import com.saaty.sideMenuScreen.wishlist.WishlistActivity;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface ApiServiceInterface {


    @POST("/oauth/token")
    Call<LoginModel> loginUser(@Body Map<String, Object> map);

    @POST("/api/register")
    Call<RegisterModel> registerUser(@Body Map<String, Object> map);

////---------------------- register store---------------------------
    @Multipart
    @POST("/api/register")
    Call<RegisterModel> registerStore(@QueryMap Map<String, Object> map,@Part MultipartBody.Part part);


    @GET("/api/SendMobileResetCode/{id}")
    Call<SendCodeModel> sendMobileCode(@Path("id") String email);

    @POST("/api/CheckResetCode")
    Call<SendCodeModel> checkResetCode(@Body Map<String, Object> map);

    @POST("/api/ResetPassword")
    Call<SendCodeModel> resetPassword(@Body Map<String, Object> map);


    @GET("/api/categories")
    Call<CategoryModel> getCatogory();

    /////////////////stores//////////////////////////////
    @GET("/api/stores")
    Call<StoreListModel> getStoresList(@QueryMap Map<String, Object> map);

//////////////////////////////   get category product list  watchets or brackletes////////////////

    @GET("/api/products")
    Call<StoreListModel> getCategoryProductsList(@QueryMap Map<String, Object> map);

/////////////////////////////////////////


    @GET("/api/storeproducts/{id}")
    Call<StoreListModel> gerStoreProducts(@Path("id") int id, @QueryMap Map<String, Object> map);


    //   get wish list????////////////////
    @GET("/api/wishproducts")
    Call<StoreListModel> getWishlist(@QueryMap Map<String, Object> map
            , @Header("Accept") String accept
            , @Header("Authorization") String token);

    /////////////////////add product to wish list///////////////


    @POST("/api/addtowishlist")
    Call<AdsProductsModel> addProuctToWishlist(@Header("Accept") String accept
            , @Header("Authorization") String token
            , @Query("product_id") int product_id);


    /////// about app /////////////////
    @POST("/api/getscreen")
    Call<ProductDataModel> getAboutAppData(@Body Map<String, Object> map);


    //////////////////////////// contact details///////////////
    @GET("/api/getContactDetails")
    Call<ProductDataModel> getContactDetails(@Query("key") String key);

//////////////////////////////////add ads//////////////////////////

    @Multipart
    @POST("/api/products")
    Call<AdsProductsModel> addAdsProduct(@Header("Accept") String accept
            , @Header("Authorization") String token
            , @QueryMap Map<String, Object> map
            , @Part MultipartBody.Part[] parts);


    ///////////////////////////get slider images////////////////
    @GET("/api/sliderimages")
    Call<ProductDataModel> getSliderImages();
////////////////////////////////  getlist of product images//////////////

    @GET("/api/productimages/{id}")
    Call<GetProductImagesModel> getProductImagesList(@Path("id") int id);

    ////////////////////check wishlist product/////////////

    @GET("/api/wishlist/")
    Call<CheckWishlistModel> checkWishlistProduct(@Query("product_id") int id
            , @Header("Accept") String accept
            , @Header("Authorization") String auth);

    //////////////  delete from wishlist////////////

    @DELETE("/api/wishlist?")
    Call<SendCodeModel> deleteItemFromWishlist(
              @Query("product_id") int id
            , @Header("Accept") String accept
            , @Header("Authorization") String auth);

////////////////////////// update profile for user/////////////

    @POST("/api/UpdateProfile")
    Call<UpdateProfileModel> updateProfile(@Header("Accept") String accept
            , @Header("Authorization") String auth
            , @QueryMap Map<String, Object> map);

    /////////////////////////////// update profile for store////////////////////////
    @Multipart
    @POST("/api/UpdateProfile")
    Call<UpdateProfileModel> updateStoreProfile(@Header("Accept") String accept
            , @Header("Authorization") String auth
            , @QueryMap Map<String, Object> map
//            , @Part MultipartBody.Part image
            ,@Part MultipartBody.Part part);


    /////////////////////////  get Profile //////////////

    @GET("/api/GetProfile")
    Call<UpdateProfileModel> getProfile(@Header("Accept") String accept
            , @Header("Authorization") String auth);


    ///////////////////////////  change password////////////////////////

    @POST("/api/ChangePassword")
    Call<SendCodeModel> changePassword(
            @Header("Accept") String accept
            , @Header("Authorization") String auth
            , @QueryMap Map<String, Object> map);


    //-----------------------  my Ads Product ----------------------------------
    @GET("/api/myproducts")
    Call<StoreListModel> getAdsProducts(
            @Header("Accept") String accept
            , @Header("Authorization") String auth
            , @QueryMap Map<String, Object> map);


    //------------------- delete ads Product ----------------------------------

    @DELETE("/api/products/{id}")
    Call<DeleteAdsModel> deleteAdsProduct(
            @Path("id") int id
            , @Header("Accept") String accept
            , @Header("Authorization") String auth);


    ///------------------------- edit ads product -------------------------------
    @Multipart
    @POST("/api/products/{id}")
   Call<EditAdsModel> editAdsProduct(
           @Path("id") int id
            , @Header("Accept") String accept
            , @Header("Authorization") String auth
            , @QueryMap Map<String, Object> map
            , @Part MultipartBody.Part[] parts);


    ////---------------------------------search for store product --------------------

    @GET("/api/storeproduct/search")
    Call<StoreListModel> searchforStoreProduct(@QueryMap Map<String, Object> map);


    /////----------------------------get  received message -----------------------
    @GET("/api/messages/received")
    Call<MessageModel> getReceivedMessage(
            @Header("Accept") String accept
            , @Header("Authorization") String auth
            , @QueryMap Map<String, Object> map);

   ////----------------------get send message-----------------------------------
    @GET("/api/messages/sent")
    Call<MessageModel> getSendMessage(
            @Header("Accept") String accept
            , @Header("Authorization") String auth
            , @QueryMap Map<String, Object> map);

    ///----------------------delete message---------------------------------
    @DELETE("/api/messages/{id}")
    Call<DeleteMessageModel> deleteMessage(
            @Header("Accept") String accept
            , @Header("Authorization") String auth
            ,@Path("id") int id);

    //////---------------------- send reply message -----------------------
    @POST("/api/message/reply")
    Call<ReplyMessageModel> sendReplyMessage(
            @Header("Accept") String accept
            , @Header("Authorization") String auth
            ,@QueryMap Map<String, Object> map);


    //-----------------------get New -Old store Product--------------
    @GET("/api/storeproduct/search")
    Call<StoreListModel> getNewOldStoreProduct(@QueryMap Map<String,Object> map);



    @GET("/api/storeproduct/search")
    Call<StoreListModel> getStoreProduct(@QueryMap Map<String,Object> map);



    ///------------------send product message-----------------------
    @POST("/api/messages")
    Call<SendMessageProductModel> sendProductMessage(
            @Header("Accept") String accept
            , @Header("Authorization") String auth
            , @QueryMap Map<String, Object> map);

}
