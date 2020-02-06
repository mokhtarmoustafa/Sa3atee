package com.saaty.models;


import com.google.gson.annotations.SerializedName;


public class LoginObjectModel{

	@SerializedName("name")
	private Object name;

	@SerializedName("token")
	private String token;

	public void setName(Object name){
		this.name = name;
	}

	public Object getName(){
		return name;
	}

	public void setToken(String token){
		this.token = token;
	}

	public String getToken(){
		return token;
	}

	@Override
 	public String toString(){
		return 
			"LoginObjectModel{" + 
			"name = '" + name + '\'' + 
			",token = '" + token + '\'' + 
			"}";
		}
}