package com.saaty.models;


import com.google.gson.annotations.SerializedName;


public class CheckWishlistModel{

	@SerializedName("success")
	private boolean success;

	@SerializedName("message")
	private String message;

	@SerializedName("data")
  private DataObjectModel dataObjectModel;

//	@SerializedName("data")
//	private String dataMsg;
////
//	public String getDataMsg() {
//		return dataMsg;
//	}

	public DataObjectModel getDataObjectModel() {
		return dataObjectModel;
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
			"CheckWishlistModel{" + 
			"success = '" + success + '\'' + 
			",message = '" + message + '\'' + 
			"}";
		}
}