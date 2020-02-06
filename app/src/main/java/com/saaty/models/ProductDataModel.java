package com.saaty.models;


import com.google.gson.annotations.SerializedName;

import java.util.List;


public class ProductDataModel{

	@SerializedName("success")
	private boolean success;

	@SerializedName("message")
	private String message;

	@SerializedName("data")
    private List<ProductDataItem> productDataModels;

//	@SerializedName("data")
//	private ProductDataItem productDataItem;

//	public ProductDataItem getProductDataItem() {
//		return productDataItem;
//	}

	public List<ProductDataItem> getProductDataModels() {
        return productDataModels;
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
			"ProductDataModel{" + 
			"success = '" + success + '\'' + 
			",message = '" + message + '\'' + 
			"}";
		}
}