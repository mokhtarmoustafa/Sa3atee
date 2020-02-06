package com.saaty.models;


import com.google.gson.annotations.SerializedName;

public class ProductimagesItem{

	@SerializedName("image_link")
	private String imageLink;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("product_id")
	private int productId;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("image_id")
	private int imageId;

	public void setImageLink(String imageLink){
		this.imageLink = imageLink;
	}

	public String getImageLink(){
		return imageLink;
	}

	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt(){
		return updatedAt;
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

	public void setImageId(int imageId){
		this.imageId = imageId;
	}

	public int getImageId(){
		return imageId;
	}

	@Override
 	public String toString(){
		return 
			"ProductimagesItem{" + 
			"image_link = '" + imageLink + '\'' + 
			",updated_at = '" + updatedAt + '\'' + 
			",product_id = '" + productId + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",image_id = '" + imageId + '\'' + 
			"}";
		}
}