package com.saaty.models;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;


public class UpdateProfileDataArrayModel implements Parcelable {

	@SerializedName("store_id")
	private int storeId;

	@SerializedName("store_ar_name")
	private String storeArName;

	@SerializedName("store_en_name")
	private String storeEnName;

	@SerializedName("mobile_verified_at")
	private String mobileVerifiedAt;

	@SerializedName("store_ar_description")
	private String storeArDescription;

	@SerializedName("store_logo")
	private String storeLogo;

	@SerializedName("mobile")
	private String mobile;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("email_verified_at")
	private String emailVerifiedAt;

	@SerializedName("type")
	private String type;

	@SerializedName("store_en_description")
	private String storeEnDescription;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("user_id")
	private int userId;

	@SerializedName("id")
	private int id;

	@SerializedName("fullname")
	private String fullname;

	@SerializedName("email")
	private String email;

	protected UpdateProfileDataArrayModel(Parcel in) {
		mobile = in.readString();
		type = in.readString();
		id = in.readInt();
		fullname = in.readString();
		email = in.readString();
	}

	public static final Creator<UpdateProfileDataArrayModel> CREATOR = new Creator<UpdateProfileDataArrayModel>() {
		@Override
		public UpdateProfileDataArrayModel createFromParcel(Parcel in) {
			return new UpdateProfileDataArrayModel(in);
		}

		@Override
		public UpdateProfileDataArrayModel[] newArray(int size) {
			return new UpdateProfileDataArrayModel[size];
		}
	};

	public void setStoreId(int storeId){
		this.storeId = storeId;
	}

	public int getStoreId(){
		return storeId;
	}

	public void setStoreArName(String storeArName){
		this.storeArName = storeArName;
	}

	public String getStoreArName(){
		return storeArName;
	}

	public void setStoreEnName(String storeEnName){
		this.storeEnName = storeEnName;
	}

	public String getStoreEnName(){
		return storeEnName;
	}

	public void setMobileVerifiedAt(String mobileVerifiedAt){
		this.mobileVerifiedAt = mobileVerifiedAt;
	}

	public String getMobileVerifiedAt(){
		return mobileVerifiedAt;
	}

	public void setStoreArDescription(String storeArDescription){
		this.storeArDescription = storeArDescription;
	}

	public String getStoreArDescription(){
		return storeArDescription;
	}

	public void setStoreLogo(String storeLogo){
		this.storeLogo = storeLogo;
	}

	public String getStoreLogo(){
		return storeLogo;
	}

	public void setMobile(String mobile){
		this.mobile = mobile;
	}

	public String getMobile(){
		return mobile;
	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setEmailVerifiedAt(String emailVerifiedAt){
		this.emailVerifiedAt = emailVerifiedAt;
	}

	public String getEmailVerifiedAt(){
		return emailVerifiedAt;
	}

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return type;
	}

	public void setStoreEnDescription(String storeEnDescription){
		this.storeEnDescription = storeEnDescription;
	}

	public Object getStoreEnDescription(){
		return storeEnDescription;
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
			"UpdateProfileDataArrayModel{" + 
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

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(mobile);
		dest.writeString(type);
		dest.writeInt(id);
		dest.writeString(fullname);
		dest.writeString(email);
	}
}