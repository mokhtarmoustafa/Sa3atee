package com.saaty.models;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;


public class UserDataRegisterObject implements Parcelable {

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("mobile")
	private String mobile;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("fullname")
	private String fullname;

	@SerializedName("id")
	private int id;

	@SerializedName("type")
	private String type;

	@SerializedName("email")
	private String email;

	protected UserDataRegisterObject(Parcel in) {
		updatedAt = in.readString();
		mobile = in.readString();
		createdAt = in.readString();
		fullname = in.readString();
		id = in.readInt();
		type = in.readString();
		email = in.readString();
	}

	public static final Creator<UserDataRegisterObject> CREATOR = new Creator<UserDataRegisterObject>() {
		@Override
		public UserDataRegisterObject createFromParcel(Parcel in) {
			return new UserDataRegisterObject(in);
		}

		@Override
		public UserDataRegisterObject[] newArray(int size) {
			return new UserDataRegisterObject[size];
		}
	};

	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt(){
		return updatedAt;
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

	public void setFullname(String fullname){
		this.fullname = fullname;
	}

	public String getFullname(){
		return fullname;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return type;
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
			"UserDataRegisterObject{" + 
			"updated_at = '" + updatedAt + '\'' + 
			",mobile = '" + mobile + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",fullname = '" + fullname + '\'' + 
			",id = '" + id + '\'' + 
			",type = '" + type + '\'' + 
			",email = '" + email + '\'' + 
			"}";
		}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(updatedAt);
		dest.writeString(mobile);
		dest.writeString(createdAt);
		dest.writeString(fullname);
		dest.writeInt(id);
		dest.writeString(type);
		dest.writeString(email);
	}
}