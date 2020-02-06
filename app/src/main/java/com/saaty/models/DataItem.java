package com.saaty.models;


import com.google.gson.annotations.SerializedName;


public class DataItem{

	@SerializedName("store_id")
	private Object storeId;

	@SerializedName("store_ar_name")
	private Object storeArName;

	@SerializedName("store_en_name")
	private Object storeEnName;

	@SerializedName("mobile_verified_at")
	private Object mobileVerifiedAt;

	@SerializedName("store_ar_description")
	private Object storeArDescription;

	@SerializedName("store_logo")
	private Object storeLogo;

	@SerializedName("mobile")
	private String mobile;

	@SerializedName("created_at")
	private Object createdAt;

	@SerializedName("email_verified_at")
	private Object emailVerifiedAt;

	@SerializedName("type")
	private String type;

	@SerializedName("store_en_description")
	private Object storeEnDescription;

	@SerializedName("updated_at")
	private Object updatedAt;

	@SerializedName("user_id")
	private Object userId;

	@SerializedName("id")
	private int id;

	@SerializedName("fullname")
	private String fullname;

	@SerializedName("email")
	private String email;

	@SerializedName("category_ar_name")
	private String categoryArName;

	@SerializedName("category_en_name")
	private String categoryEnName;

	@SerializedName("category_id")
	private int categoryId;

	@SerializedName("category_icon_link")
	private String categoryIconLink;


	public String getCategoryIconLink() {
		return categoryIconLink;
	}

	public String getCategoryArName() {
		return categoryArName;
	}

	public String getCategoryEnName() {
		return categoryEnName;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setStoreId(Object storeId){
		this.storeId = storeId;
	}

	public Object getStoreId(){
		return storeId;
	}

	public void setStoreArName(Object storeArName){
		this.storeArName = storeArName;
	}

	public Object getStoreArName(){
		return storeArName;
	}

	public void setStoreEnName(Object storeEnName){
		this.storeEnName = storeEnName;
	}

	public Object getStoreEnName(){
		return storeEnName;
	}

	public void setMobileVerifiedAt(Object mobileVerifiedAt){
		this.mobileVerifiedAt = mobileVerifiedAt;
	}

	public Object getMobileVerifiedAt(){
		return mobileVerifiedAt;
	}

	public void setStoreArDescription(Object storeArDescription){
		this.storeArDescription = storeArDescription;
	}

	public Object getStoreArDescription(){
		return storeArDescription;
	}

	public void setStoreLogo(Object storeLogo){
		this.storeLogo = storeLogo;
	}

	public Object getStoreLogo(){
		return storeLogo;
	}

	public void setMobile(String mobile){
		this.mobile = mobile;
	}

	public String getMobile(){
		return mobile;
	}

	public void setCreatedAt(Object createdAt){
		this.createdAt = createdAt;
	}

	public Object getCreatedAt(){
		return createdAt;
	}

	public void setEmailVerifiedAt(Object emailVerifiedAt){
		this.emailVerifiedAt = emailVerifiedAt;
	}

	public Object getEmailVerifiedAt(){
		return emailVerifiedAt;
	}

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return type;
	}

	public void setStoreEnDescription(Object storeEnDescription){
		this.storeEnDescription = storeEnDescription;
	}

	public Object getStoreEnDescription(){
		return storeEnDescription;
	}

	public void setUpdatedAt(Object updatedAt){
		this.updatedAt = updatedAt;
	}

	public Object getUpdatedAt(){
		return updatedAt;
	}

	public void setUserId(Object userId){
		this.userId = userId;
	}

	public Object getUserId(){
		return userId;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setFullname(String fullname){
		this.fullname = fullname;
	}

	public String getFullname(){
		return fullname;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	@Override
 	public String toString(){
		return 
			"DataItem{" + 
			"store_id = '" + storeId + '\'' + 
			",store_ar_name = '" + storeArName + '\'' + 
			",store_en_name = '" + storeEnName + '\'' + 
			",mobile_verified_at = '" + mobileVerifiedAt + '\'' + 
			",store_ar_description = '" + storeArDescription + '\'' + 
			",store_logo = '" + storeLogo + '\'' + 
			",mobile = '" + mobile + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",email_verified_at = '" + emailVerifiedAt + '\'' + 
			",type = '" + type + '\'' + 
			",store_en_description = '" + storeEnDescription + '\'' + 
			",updated_at = '" + updatedAt + '\'' + 
			",user_id = '" + userId + '\'' + 
			",id = '" + id + '\'' + 
			",fullname = '" + fullname + '\'' + 
			",email = '" + email + '\'' + 
			"}";
		}
}