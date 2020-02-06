package com.saaty.models;


import com.google.gson.annotations.SerializedName;

import java.util.List;


public class GetProductImagesModel{

	@SerializedName("success")
	private boolean success;

	@SerializedName("message")
	private String message;

	@SerializedName("data")
	private List<ProductimagesItem> productimagesItems;

	public List<ProductimagesItem> getProductimagesItems() {
		return productimagesItems;
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
			"GetProductImagesModel{" + 
			"success = '" + success + '\'' + 
			",message = '" + message + '\'' + 
			"}";
		}
}