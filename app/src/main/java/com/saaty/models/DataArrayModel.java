package com.saaty.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import androidx.room.Entity;
import androidx.room.PrimaryKey;



public class DataArrayModel implements Parcelable {

	@SerializedName("store_id")
	private int storeId;

	@SerializedName("store_ar_name")
	private String storeArName;

	@SerializedName("store_en_description")
	private String storeEnDescription;

	@SerializedName("store_en_name")
	private String storeEnName;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("user_id")
	private int userId;

	@SerializedName("store_ar_description")
	private String storeArDescription;

	@SerializedName("store_logo")
	private String storeLogo;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("contact_name")
	private String contactName;

	@SerializedName("shape")
	private String shape;

	@SerializedName("en_description")
	private String enDescription;

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


	@SerializedName("price")
	private int price;

	@SerializedName("product_id")
	private int productId;

	@SerializedName("productimages")
	private List<ProductimagesItem> productimages;



	@SerializedName("en_name")
	private String enName;

	@SerializedName("contact_mobile")
	private String contactMobile;


	@PrimaryKey(autoGenerate = true)
	private  int idd;

//	public DataArrayModel(int idd) {
//		this.productId=productId;
//		this.idd = idd;
//	}



	public int getIdd() {
		return idd;
	}

	public void setIdd(int idd) {
		this.idd = idd;
	}



	public void setProductimages(List<ProductimagesItem> productimages) {
		this.productimages = productimages;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public void setShape(String shape) {
		this.shape = shape;
	}

	public void setEnDescription(String enDescription) {
		this.enDescription = enDescription;
	}

	public void setArName(String arName) {
		this.arName = arName;
	}

	public void setArDescription(String arDescription) {
		this.arDescription = arDescription;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public void setContactType(String contactType) {
		this.contactType = contactType;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public void setEnName(String enName) {
		this.enName = enName;
	}

	public void setContactMobile(String contactMobile) {
		this.contactMobile = contactMobile;
	}

	public DataArrayModel(Parcel in) {
		storeId = in.readInt();
		storeArName = in.readString();
		storeEnDescription = in.readString();
		storeEnName = in.readString();
		updatedAt = in.readString();
		userId = in.readInt();
		storeArDescription = in.readString();
		storeLogo = in.readString();
		createdAt = in.readString();
		contactName = in.readString();
		shape = in.readString();
		enDescription = in.readString();
		arName = in.readString();
		arDescription = in.readString();
		contactEmail = in.readString();
		contactType = in.readString();
		categoryId = in.readInt();
		price = in.readInt();
		productId = in.readInt();
		enName = in.readString();
		contactMobile = in.readString();
	}

	public static final Creator<DataArrayModel> CREATOR = new Creator<DataArrayModel>() {
		@Override
		public DataArrayModel createFromParcel(Parcel in) {
			return new DataArrayModel(in);
		}

		@Override
		public DataArrayModel[] newArray(int size) {
			return new DataArrayModel[size];
		}
	};

	public String getContactName() {
		return contactName;
	}

	public String getShape() {
		return shape;
	}

	public String getEnDescription() {
		return enDescription;
	}

	public String getArName() {
		return arName;
	}

	public String getArDescription() {
		return arDescription;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public String getContactType() {
		return contactType;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public int getPrice() {
		return price;
	}

	public int getProductId() {
		return productId;
	}

	public List<ProductimagesItem> getProductimages() {
		return productimages;
	}

	public String getEnName() {
		return enName;
	}

	public String getContactMobile() {
		return contactMobile;
	}

	public void setStoreId(int storeId){
		this.storeId = storeId;
	}

	public int getStoreId(){
		return storeId;
	}

	public void setStoreArName(String storeArName){
		this.storeArName = storeArName;
	}

	public String getStoreArName(){
		return storeArName;
	}

	public void setStoreEnDescription(String storeEnDescription){
		this.storeEnDescription = storeEnDescription;
	}

	public String getStoreEnDescription(){
		return storeEnDescription;
	}

	public void setStoreEnName(String storeEnName){
		this.storeEnName = storeEnName;
	}

	public String getStoreEnName(){
		return storeEnName;
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

	public void setStoreArDescription(String storeArDescription){
		this.storeArDescription = storeArDescription;
	}

	public String getStoreArDescription(){
		return storeArDescription;
	}

	public void setStoreLogo(String storeLogo){
		this.storeLogo = storeLogo;
	}

	public String getStoreLogo(){
		return storeLogo;
	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	@Override
 	public String toString(){
		return 
			"DataArrayModel{" + 
			"store_id = '" + storeId + '\'' + 
			",store_ar_name = '" + storeArName + '\'' + 
			",store_en_description = '" + storeEnDescription + '\'' + 
			",store_en_name = '" + storeEnName + '\'' + 
			",updated_at = '" + updatedAt + '\'' + 
			",user_id = '" + userId + '\'' + 
			",store_ar_description = '" + storeArDescription + '\'' + 
			",store_logo = '" + storeLogo + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			"}";
		}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(storeId);
		dest.writeString(storeArName);
		dest.writeString(storeEnDescription);
		dest.writeString(storeEnName);
		dest.writeString(updatedAt);
		dest.writeInt(userId);
		dest.writeString(storeArDescription);
		dest.writeString(storeLogo);
		dest.writeString(createdAt);
		dest.writeString(contactName);
		dest.writeString(shape);
		dest.writeString(enDescription);
		dest.writeString(arName);
		dest.writeString(arDescription);
		dest.writeString(contactEmail);
		dest.writeString(contactType);
		dest.writeInt(categoryId);
		dest.writeInt(price);
		dest.writeInt(productId);
		dest.writeString(enName);
		dest.writeString(contactMobile);
	}
}