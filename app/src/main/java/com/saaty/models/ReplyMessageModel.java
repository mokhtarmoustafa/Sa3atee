
package com.saaty.models;

import com.google.gson.annotations.SerializedName;


public class ReplyMessageModel{

    @SerializedName("data")
    private ReplyMessageDataObjectModel messageDataObjectModel;

    @SerializedName("success")
    private boolean success;

    @SerializedName("message")
    private String message;


    public ReplyMessageDataObjectModel getMessageDataObjectModel() {
        return messageDataObjectModel;
    }

    public void setMessageDataObjectModel(ReplyMessageDataObjectModel messageDataObjectModel) {
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