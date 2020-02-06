package com.saaty.models;

import java.util.List;

import com.google.gson.annotations.SerializedName;


public class EditDataObjectModel {

	@SerializedName("store_id")
	private Object storeId;

	@SerializedName("contact_name")
	private String contactName;

	@SerializedName("shape")
	private String shape;

	@SerializedName("en_description")
	private Object enDescription;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("ar_name")
	private String arName;

	@SerializedName("ar_description")
	private String arDescription;

	@SerializedName("contact_email")
	private String contactEmail;

	@SerializedName("contact_type")
	private String contactType;

	@SerializedName("category_id")
	private int categoryId;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("user_id")
	private int userId;

	@SerializedName("price")
	private int price;

	@SerializedName("product_id")
	private int productId;

	@SerializedName("productimages")
	private List<ProductimagesItem> productimages;

	@SerializedName("en_name")
	private Object enName;

	@SerializedName("contact_mobile")
	private String contactMobile;

	public void setStoreId(Object storeId){
		this.storeId = storeId;
	}

	public Object getStoreId(){
		return storeId;
	}

	public void setContactName(String contactName){
		this.contactName = contactName;
	}

	public String getContactName(){
		return contactName;
	}

	public void setShape(String shape){
		this.shape = shape;
	}

	public String getShape(){
		return shape;
	}

	public void setEnDescription(Object enDescription){
		this.enDescription = enDescription;
	}

	public Object getEnDescription(){
		return enDescription;
	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setArName(String arName){
		this.arName = arName;
	}

	public String getArName(){
		return arName;
	}

	public void setArDescription(String arDescription){
		this.arDescription = arDescription;
	}

	public String getArDescription(){
		return arDescription;
	}

	public void setContactEmail(String contactEmail){
		this.contactEmail = contactEmail;
	}

	public String getContactEmail(){
		return contactEmail;
	}

	public void setContactType(String contactType){
		this.contactType = contactType;
	}

	public String getContactType(){
		return contactType;
	}

	public void setCategoryId(int categoryId){
		this.categoryId = categoryId;
	}

	public int getCategoryId(){
		return categoryId;
	}

	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public void setUserId(int userId){
		this.userId = userId;
	}

	public int getUserId(){
		return userId;
	}

	public void setPrice(int price){
		this.price = price;
	}

	public int getPrice(){
		return price;
	}

	public void setProductId(int productId){
		this.productId = productId;
	}

	public int getProductId(){
		return productId;
	}

	public void setProductimages(List<ProductimagesItem> productimages){
		this.productimages = productimages;
	}

	public List<ProductimagesItem> getProductimages(){
		return productimages;
	}

	public void setEnName(Object enName){
		this.enName = enName;
	}

	public Object getEnName(){
		return enName;
	}

	public void setContactMobile(String contactMobile){
		this.contactMobile = contactMobile;
	}

	public String getContactMobile(){
		return contactMobile;
	}

	@Override
 	public String toString(){
		return 
			"EditDataObjectModel{" +
			"store_id = '" + storeId + '\'' + 
			",contact_name = '" + contactName + '\'' + 
			",shape = '" + shape + '\'' + 
			",en_description = '" + enDescription + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",ar_name = '" + arName + '\'' + 
			",ar_description = '" + arDescription + '\'' + 
			",contact_email = '" + contactEmail + '\'' + 
			",contact_type = '" + contactType + '\'' + 
			",category_id = '" + categoryId + '\'' + 
			",updated_at = '" + updatedAt + '\'' + 
			",user_id = '" + userId + '\'' + 
			",price = '" + price + '\'' + 
			",product_id = '" + productId + '\'' + 
			",productimages = '" + productimages + '\'' + 
			",en_name = '" + enName + '\'' + 
			",contact_mobile = '" + contactMobile + '\'' + 
			"}";
		}
}