package com.saaty.models;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class LoginModel implements Parcelable {

	@SerializedName("success")
	private boolean isSucess;
	@SerializedName("access_token")
	private String accessToken;

	@SerializedName("refresh_token")
	private String refreshToken;

	@SerializedName("token_type")
	private String tokenType;

	@SerializedName("expires_in")
	private int expiresIn;

	@SerializedName("error")
	private String error;

	@SerializedName("error_description")
	private String errorDescription;

	@SerializedName("message")
	private String message;

	@SerializedName("user")
	private List<UserModel> userModel;

	protected LoginModel(Parcel in) {
		isSucess = in.readByte() != 0;
		accessToken = in.readString();
		refreshToken = in.readString();
		tokenType = in.readString();
		expiresIn = in.readInt();
		error = in.readString();
		errorDescription = in.readString();
		message = in.readString();
		userModel = in.createTypedArrayList(UserModel.CREATOR);
	}

	public static final Creator<LoginModel> CREATOR = new Creator<LoginModel>() {
		@Override
		public LoginModel createFromParcel(Parcel in) {
			return new LoginModel(in);
		}

		@Override
		public LoginModel[] newArray(int size) {
			return new LoginModel[size];
		}
	};

	public boolean isSucess() {
		return isSucess;
	}

	public void setSucess(boolean sucess) {
		isSucess = sucess;
	}

	public void setError(String error) {
		this.error = error;
	}

	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setUserModel(List<UserModel> userModel) {
		this.userModel = userModel;
	}

	public List<UserModel> getUserModel() {
		return userModel;
	}

	public String getError() {
		return error;
	}

	public String getErrorDescription() {
		return errorDescription;
	}

	public String getMessage() {
		return message;
	}

	public void setAccessToken(String accessToken){
		this.accessToken = accessToken;
	}

	public String getAccessToken(){
		return accessToken;
	}

	public void setRefreshToken(String refreshToken){
		this.refreshToken = refreshToken;
	}

	public String getRefreshToken(){
		return refreshToken;
	}

	public void setTokenType(String tokenType){
		this.tokenType = tokenType;
	}

	public String getTokenType(){
		return tokenType;
	}

	public void setExpiresIn(int expiresIn){
		this.expiresIn = expiresIn;
	}

	public int getExpiresIn(){
		return expiresIn;
	}

	@Override
 	public String toString(){
		return 
			"LoginModel{" + 
			"access_token = '" + accessToken + '\'' + 
			",refresh_token = '" + refreshToken + '\'' + 
			",token_type = '" + tokenType + '\'' + 
			",expires_in = '" + expiresIn + '\'' + 
			"}";
		}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeByte((byte) (isSucess ? 1 : 0));
		dest.writeString(accessToken);
		dest.writeString(refreshToken);
		dest.writeString(tokenType);
		dest.writeInt(expiresIn);
		dest.writeString(error);
		dest.writeString(errorDescription);
		dest.writeString(message);
		dest.writeTypedList(userModel);
	}
}