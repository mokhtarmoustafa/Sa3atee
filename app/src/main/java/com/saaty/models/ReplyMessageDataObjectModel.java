package com.saaty.models;

import com.google.gson.annotations.SerializedName;

public class ReplyMessageDataObjectModel{

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("user_message_id")
	private String userMessageId;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("id")
	private int id;

	@SerializedName("reply")
	private String reply;

	@SerializedName("sender_user_id")
	private int senderUserId;

	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public void setUserMessageId(String userMessageId){
		this.userMessageId = userMessageId;
	}

	public String getUserMessageId(){
		return userMessageId;
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

	public void setReply(String reply){
		this.reply = reply;
	}

	public String getReply(){
		return reply;
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
			"ReplyMessageDataObjectModel{" + 
			"updated_at = '" + updatedAt + '\'' + 
			",user_message_id = '" + userMessageId + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",id = '" + id + '\'' + 
			",reply = '" + reply + '\'' + 
			",sender_user_id = '" + senderUserId + '\'' + 
			"}";
		}
}