package com.saaty.models;


import com.google.gson.annotations.SerializedName;

public class EditAdsModel{

	@SerializedName("success")
	private boolean success;

	@SerializedName("data")
	private EditDataObjectModel editDataObjectModel;

	@SerializedName("message")
	private String message;

	public void setSuccess(boolean success){
		this.success = success;
	}

	public boolean isSuccess(){
		return success;
	}

	public void setX(EditDataObjectModel X){
		this.editDataObjectModel = editDataObjectModel;
	}

	public EditDataObjectModel getX(){
		return editDataObjectModel;
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
			"EditAdsModel{" + 
			"success = '" + success + '\'' + 
			",x = '" + editDataObjectModel + '\'' +
			",message = '" + message + '\'' + 
			"}";
		}
}