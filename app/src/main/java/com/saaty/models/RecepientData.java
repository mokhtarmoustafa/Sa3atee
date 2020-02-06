package com.saaty.models;


import com.google.gson.annotations.SerializedName;


public class RecepientData{

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("mobile_verified_at")
	private Object mobileVerifiedAt;

	@SerializedName("mobile")
	private String mobile;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("email_verified_at")
	private Object emailVerifiedAt;

	@SerializedName("id")
	private int id;

	@SerializedName("fullname")
	private String fullname;

	@SerializedName("type")
	private String type;

	@SerializedName("email")
	private String email;

	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public void setMobileVerifiedAt(Object mobileVerifiedAt){
		this.mobileVerifiedAt = mobileVerifiedAt;
	}

	public Object getMobileVerifiedAt(){
		return mobileVerifiedAt;
	}

	public void setMobile(String mobile){
		this.mobile = mobile;
	}

	public String getMobile(){
		return mobile;
	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setEmailVerifiedAt(Object emailVerifiedAt){
		this.emailVerifiedAt = emailVerifiedAt;
	}

	public Object getEmailVerifiedAt(){
		return emailVerifiedAt;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setFullname(String fullname){
		this.fullname = fullname;
	}

	public String getFullname(){
		return fullname;
	}

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return type;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	@Override
 	public String toString(){
		return 
			"RecepientData{" + 
			"updated_at = '" + updatedAt + '\'' + 
			",mobile_verified_at = '" + mobileVerifiedAt + '\'' + 
			",mobile = '" + mobile + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",email_verified_at = '" + emailVerifiedAt + '\'' + 
			",id = '" + id + '\'' + 
			",fullname = '" + fullname + '\'' + 
			",type = '" + type + '\'' + 
			",email = '" + email + '\'' + 
			"}";
		}
}