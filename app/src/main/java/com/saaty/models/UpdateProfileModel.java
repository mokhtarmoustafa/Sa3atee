package com.saaty.models;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class UpdateProfileModel{

	@SerializedName("data")
	private List<UpdateProfileDataArrayModel> dataArrayModels;

	@SerializedName("success")
	private boolean success;

	@SerializedName("message")
	private String message;

	public List<UpdateProfileDataArrayModel> getDataArrayModels() {
		return dataArrayModels;
	}

	public void setSuccess(boolean success){
		this.success = success;
	}

	public boolean isSuccess(){
		return success;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	@Override
 	public String toString(){
		return 
			"UpdateProfileModel{" +
			",success = '" + success + '\'' + 
			",message = '" + message + '\'' + 
			"}";
		}
}