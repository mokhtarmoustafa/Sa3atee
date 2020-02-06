package com.saaty.models;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;


public class MessageArrayModel implements Parcelable {

	@SerializedName("recipient_user_id")
	private int recipientUserId;

	@SerializedName("senderData")
	private SenderData senderData;

	@SerializedName("RecepientData")
	private RecepientData recepientData;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("usm_id")
	private int usmId;

	@SerializedName("sender_deleted")
	private int senderDeleted;

	@SerializedName("product_id")
	private int productId;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("recipient_deleted")
	private int recipientDeleted;

	@SerializedName("message")
	private String message;

	@SerializedName("sender_user_id")
	private int senderUserId;

	protected MessageArrayModel(Parcel in) {
		recipientUserId = in.readInt();
		updatedAt = in.readString();
		usmId = in.readInt();
		senderDeleted = in.readInt();
		productId = in.readInt();
		createdAt = in.readString();
		recipientDeleted = in.readInt();
		message = in.readString();
		senderUserId = in.readInt();
	}

	public static final Creator<MessageArrayModel> CREATOR = new Creator<MessageArrayModel>() {
		@Override
		public MessageArrayModel createFromParcel(Parcel in) {
			return new MessageArrayModel(in);
		}

		@Override
		public MessageArrayModel[] newArray(int size) {
			return new MessageArrayModel[size];
		}
	};

	public RecepientData getRecepientData() {
		return recepientData;
	}

	public void setRecipientUserId(int recipientUserId){
		this.recipientUserId = recipientUserId;
	}

	public int getRecipientUserId(){
		return recipientUserId;
	}

	public void setSenderData(SenderData senderData){
		this.senderData = senderData;
	}

	public SenderData getSenderData(){
		return senderData;
	}

	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public void setUsmId(int usmId){
		this.usmId = usmId;
	}

	public int getUsmId(){
		return usmId;
	}

	public void setSenderDeleted(int senderDeleted){
		this.senderDeleted = senderDeleted;
	}

	public int getSenderDeleted(){
		return senderDeleted;
	}

	public void setProductId(int productId){
		this.productId = productId;
	}

	public int getProductId(){
		return productId;
	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setRecipientDeleted(int recipientDeleted){
		this.recipientDeleted = recipientDeleted;
	}

	public int getRecipientDeleted(){
		return recipientDeleted;
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
			"MessageArrayModel{" + 
			"recipient_user_id = '" + recipientUserId + '\'' + 
			",senderData = '" + senderData + '\'' + 
			",updated_at = '" + updatedAt + '\'' + 
			",usm_id = '" + usmId + '\'' + 
			",sender_deleted = '" + senderDeleted + '\'' + 
			",product_id = '" + productId + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",recipient_deleted = '" + recipientDeleted + '\'' + 
			",message = '" + message + '\'' + 
			",sender_user_id = '" + senderUserId + '\'' + 
			"}";
		}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(recipientUserId);
		dest.writeString(updatedAt);
		dest.writeInt(usmId);
		dest.writeInt(senderDeleted);
		dest.writeInt(productId);
		dest.writeString(createdAt);
		dest.writeInt(recipientDeleted);
		dest.writeString(message);
		dest.writeInt(senderUserId);
	}
}