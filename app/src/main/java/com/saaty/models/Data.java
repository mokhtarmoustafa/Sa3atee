package com.saaty.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;


public class Data implements Parcelable {

	@SerializedName("name")
	private Object name;

	@SerializedName("id")
	private int id;

	@SerializedName("token")
	private String token;

	@SerializedName("email")
	private  String email;

	@SerializedName("mobile")
	private  String mobile;

	@SerializedName("user_data")
	private UserDataRegisterObject userDataRegisterObject;


	@SerializedName("store_data")
	private StoreDataRegisterObject storeDataRegisterObject;

	public StoreDataRegisterObject getStoreDataRegisterObject() {
		return storeDataRegisterObject;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public void setUserDataRegisterObject(UserDataRegisterObject userDataRegisterObject) {
		this.userDataRegisterObject = userDataRegisterObject;
	}

	public void setStoreDataRegisterObject(StoreDataRegisterObject storeDataRegisterObject) {
		this.storeDataRegisterObject = storeDataRegisterObject;
	}

	protected Data(Parcel in) {
		id = in.readInt();
		token = in.readString();
		email = in.readString();
		mobile = in.readString();
		userDataRegisterObject = in.readParcelable(UserDataRegisterObject.class.getClassLoader());
	}

	public static final Creator<Data> CREATOR = new Creator<Data>() {
		@Override
		public Data createFromParcel(Parcel in) {
			return new Data(in);
		}

		@Override
		public Data[] newArray(int size) {
			return new Data[size];
		}
	};

	public UserDataRegisterObject getUserDataRegisterObject() {
		return userDataRegisterObject;
	}



	public String getEmail() {
		return email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setName(Object name){
		this.name = name;
	}

	public Object getName(){
		return name;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setToken(String token){
		this.token = token;
	}

	public String getToken(){
		return token;
	}

	@Override
 	public String toString(){
		return 
			"Data{" + 
			"name = '" + name + '\'' + 
			",id = '" + id + '\'' + 
			",token = '" + token + '\'' + 
			"}";
		}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(id);
		dest.writeString(token);
		dest.writeString(email);
		dest.writeString(mobile);
		dest.writeParcelable(userDataRegisterObject, flags);
	}
}