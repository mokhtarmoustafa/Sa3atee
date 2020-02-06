package com.saaty.models;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;


public class ProductDataItem implements Parcelable {

	@SerializedName("category_id")
	private int categoryId;

	@SerializedName("id")
	private  int id;

	@SerializedName("store_id")
	private int storeId;

	@SerializedName("contact_name")
	private String contactName;

	@SerializedName("shape")
	private String shape;

	@SerializedName("en_description")
	private String enDescription;

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


	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("user_id")
	private int userId;

	@SerializedName("price")
	private int price;

	@SerializedName("product_id")
	private int productId;

	@SerializedName("en_name")
	private String enName;

	@SerializedName("contact_mobile")
	private String contactMobile;


	@SerializedName("image_id")
	private int imageId;

	@SerializedName("image_link")
	private String imageLink;

	@SerializedName("image-ref_link")
	private String imageRefLink;

	public int getImageId() {
		return imageId;
	}

	public String getImageLink() {
		return imageLink;
	}

	public String getImageRefLink() {
		return imageRefLink;
	}

	protected ProductDataItem(Parcel in) {
		id = in.readInt();
		storeId = in.readInt();
		contactName = in.readString();
		shape = in.readString();
		enDescription = in.readString();
		createdAt = in.readString();
		arName = in.readString();
		arDescription = in.readString();
		contactEmail = in.readString();
		contactType = in.readString();
		categoryId = in.readInt();
		updatedAt = in.readString();
		userId = in.readInt();
		price = in.readInt();
		productId = in.readInt();
		enName = in.readString();
		contactMobile = in.readString();
	}

	public static final Creator<ProductDataItem> CREATOR = new Creator<ProductDataItem>() {
		@Override
		public ProductDataItem createFromParcel(Parcel in) {
			return new ProductDataItem(in);
		}

		@Override
		public ProductDataItem[] newArray(int size) {
			return new ProductDataItem[size];
		}
	};

	public int getId() {
		return id;
	}

	public void setStoreId(int storeId){
		this.storeId = storeId;
	}

	public int getStoreId(){
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

	public void setEnDescription(String enDescription){
		this.enDescription = enDescription;
	}

	public String getEnDescription(){
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

	public void setEnName(String enName){
		this.enName = enName;
	}

	public String getEnName(){
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
			"ProductDataItem{" + 
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
			",en_name = '" + enName + '\'' + 
			",contact_mobile = '" + contactMobile + '\'' + 
			"}";
		}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(id);
		dest.writeInt(storeId);
		dest.writeString(contactName);
		dest.writeString(shape);
		dest.writeString(enDescription);
		dest.writeString(createdAt);
		dest.writeString(arName);
		dest.writeString(arDescription);
		dest.writeString(contactEmail);
		dest.writeString(contactType);
		dest.writeInt(categoryId);
		dest.writeString(updatedAt);
		dest.writeInt(userId);
		dest.writeInt(price);
		dest.writeInt(productId);
		dest.writeString(enName);
		dest.writeString(contactMobile);
	}
}