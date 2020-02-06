package com.saaty.models;

import java.util.List;

import com.google.gson.annotations.SerializedName;


public class DeleteAdsModel{

	@SerializedName("success")
	private boolean success;

	@SerializedName("data")
	private List<Object> data;

	@SerializedName("message")
	private String message;

	public void setSuccess(boolean success){
		this.success = success;
	}

	public boolean isSuccess(){
		return success;
	}

	public void setX(List<Object> X){
		this.data = data;
	}

	public List<Object> getData(){
		return data;
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
			"DeleteAdsModel{" + 
			"success = '" + success + '\'' + 
			",x = '" + data + '\'' +
			",message = '" + message + '\'' + 
			"}";
		}
}