package com.saaty.models;


import com.google.gson.annotations.SerializedName;


public class StoreDataRegisterObject{

	@SerializedName("store_ar_name")
	private String storeArName;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("user_id")
	private int userId;

	@SerializedName("store_logo")
	private String storeLogo;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("id")
	private int id;

	public void setStoreArName(String storeArName){
		this.storeArName = storeArName;
	}

	public String getStoreArName(){
		return storeArName;
	}

	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public void setUserId(int userId){
		this.userId = userId;
	}

	public int getUserId(){
		return userId;
	}

	public void setStoreLogo(String storeLogo){
		this.storeLogo = storeLogo;
	}

	public String getStoreLogo(){
		return storeLogo;
	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	@Override
 	public String toString(){
		return 
			"StoreDataRegisterObject{" + 
			"store_ar_name = '" + storeArName + '\'' + 
			",updated_at = '" + updatedAt + '\'' + 
			",user_id = '" + userId + '\'' + 
			",store_logo = '" + storeLogo + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",id = '" + id + '\'' + 
			"}";
		}
}