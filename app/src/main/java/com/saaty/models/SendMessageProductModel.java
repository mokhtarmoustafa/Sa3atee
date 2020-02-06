package com.saaty.models;

import com.google.gson.annotations.SerializedName;

public class SendMessageProductModel {

    @SerializedName("data")
    private SendMessageProductDataObjectModel messageDataObjectModel;

    @SerializedName("success")
    private boolean success;

    @SerializedName("message")
    private String message;


    public SendMessageProductDataObjectModel getMessageDataObjectModel() {
        return messageDataObjectModel;
    }

    public void setMessageDataObjectModel(SendMessageProductDataObjectModel messageDataObjectModel) {
        this.messageDataObjectModel = messageDataObjectModel;
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
                        "data = '" +  + '\'' +
                        ",success = '" + success + '\'' +
                        ",message = '" + message + '\'' +
                        "}";
    }

}
