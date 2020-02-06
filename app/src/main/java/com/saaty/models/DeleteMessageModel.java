package com.saaty.models;

import com.google.gson.annotations.SerializedName;


public class DeleteMessageModel{

	@SerializedName("data")
	private String data;

	@SerializedName("success")
	private boolean success;

	@SerializedName("message")
	private String message;



	public void setData(String data){
		this.data = data;
	}

	public String getData(){
		return data;
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
			"DeleteMessageModel{" + 
			"data = '" + data + '\'' + 
			",success = '" + success + '\'' + 
			",message = '" + message + '\'' + 
			"}";
		}
}