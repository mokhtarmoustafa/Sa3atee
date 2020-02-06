package com.saaty.models;


import com.google.gson.annotations.SerializedName;


public class MessageModel{

	@SerializedName("success")
	private boolean success;

	@SerializedName("message")
	private String message;

	@SerializedName("data")
    MessageObjectModel messageObjectModel;

    public MessageObjectModel getMessageObjectModel() {
        return messageObjectModel;
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
			"MessageModel{" + 
			"success = '" + success + '\'' + 
			",message = '" + message + '\'' + 
			"}";
		}
}