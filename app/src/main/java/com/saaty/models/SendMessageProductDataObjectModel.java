package com.saaty.models;


import com.google.gson.annotations.SerializedName;


public class SendMessageProductDataObjectModel{

	@SerializedName("recipient_user_id")
	private int recipientUserId;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("product_id")
	private String productId;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("id")
	private int id;

	@SerializedName("message")
	private String message;

	@SerializedName("sender_user_id")
	private int senderUserId;

	public void setRecipientUserId(int recipientUserId){
		this.recipientUserId = recipientUserId;
	}

	public int getRecipientUserId(){
		return recipientUserId;
	}

	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public void setProductId(String productId){
		this.productId = productId;
	}

	public String getProductId(){
		return productId;
	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	public void setSenderUserId(int senderUserId){
		this.senderUserId = senderUserId;
	}

	public int getSenderUserId(){
		return senderUserId;
	}

	@Override
 	public String toString(){
		return 
			"SendMessageProductDataObjectModel{" + 
			"recipient_user_id = '" + recipientUserId + '\'' + 
			",updated_at = '" + updatedAt + '\'' + 
			",product_id = '" + productId + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",id = '" + id + '\'' + 
			",message = '" + message + '\'' + 
			",sender_user_id = '" + senderUserId + '\'' + 
			"}";
		}
}