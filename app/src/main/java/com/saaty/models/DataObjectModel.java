package com.saaty.models;


import com.google.gson.annotations.SerializedName;

import java.util.List;

import androidx.room.Entity;
import androidx.room.PrimaryKey;



public class DataObjectModel{

	@SerializedName("data")
	private List<DataArrayModel> dataArrayModelList;

	@SerializedName("first_page_url")
	private String firstPageUrl;

	@SerializedName("path")
	private String path;

	@SerializedName("per_page")
	private String perPage;

	@SerializedName("total")
	private int total;

	@SerializedName("last_page")
	private int lastPage;

	@SerializedName("last_page_url")
	private String lastPageUrl;

	@SerializedName("next_page_url")
	private Object nextPageUrl;

	@SerializedName("from")
	private int from;

	@SerializedName("to")
	private int to;

	@SerializedName("prev_page_url")
	private Object prevPageUrl;

	@SerializedName("current_page")
	private int currentPage;

	@SerializedName("id")
	private int id;

	@SerializedName("user_id")
	private int user_id;

	@SerializedName("product_id")
	private int product_id;

	@SerializedName("created_at")
	private String created_at;

	@SerializedName("updated_at")
	private String updated_at;

    @PrimaryKey(autoGenerate = true)
	private int idd;

	public DataObjectModel(int idd,List<DataArrayModel> dataArrayModelList) {
		this.dataArrayModelList = dataArrayModelList;
		this.idd=idd;
	}

	public int getIdd() {
		return idd;
	}

	public void setIdd(int idd) {
		this.idd = idd;
	}

	public void setDataArrayModelList(List<DataArrayModel> dataArrayModelList) {
		this.dataArrayModelList = dataArrayModelList;
	}

	public int getId() {
		return id;
	}

	public int getUser_id() {
		return user_id;
	}

	public int getProduct_id() {
		return product_id;
	}

	public String getCreated_at() {
		return created_at;
	}

	public String getUpdated_at() {
		return updated_at;
	}

	public List<DataArrayModel> getDataArrayModelList() {
		return dataArrayModelList;
	}

	public void setFirstPageUrl(String firstPageUrl){
		this.firstPageUrl = firstPageUrl;
	}

	public String getFirstPageUrl(){
		return firstPageUrl;
	}

	public void setPath(String path){
		this.path = path;
	}

	public String getPath(){
		return path;
	}

	public void setPerPage(String perPage){
		this.perPage = perPage;
	}

	public String getPerPage(){
		return perPage;
	}

	public void setTotal(int total){
		this.total = total;
	}

	public int getTotal(){
		return total;
	}

	public void setLastPage(int lastPage){
		this.lastPage = lastPage;
	}

	public int getLastPage(){
		return lastPage;
	}

	public void setLastPageUrl(String lastPageUrl){
		this.lastPageUrl = lastPageUrl;
	}

	public String getLastPageUrl(){
		return lastPageUrl;
	}

	public void setNextPageUrl(Object nextPageUrl){
		this.nextPageUrl = nextPageUrl;
	}

	public Object getNextPageUrl(){
		return nextPageUrl;
	}

	public void setFrom(int from){
		this.from = from;
	}

	public int getFrom(){
		return from;
	}

	public void setTo(int to){
		this.to = to;
	}

	public int getTo(){
		return to;
	}

	public void setPrevPageUrl(Object prevPageUrl){
		this.prevPageUrl = prevPageUrl;
	}

	public Object getPrevPageUrl(){
		return prevPageUrl;
	}

	public void setCurrentPage(int currentPage){
		this.currentPage = currentPage;
	}

	public int getCurrentPage(){
		return currentPage;
	}

	@Override
 	public String toString(){
		return 
			"DataObjectModel{" + 
			"first_page_url = '" + firstPageUrl + '\'' + 
			",path = '" + path + '\'' + 
			",per_page = '" + perPage + '\'' + 
			",total = '" + total + '\'' + 
			",last_page = '" + lastPage + '\'' + 
			",last_page_url = '" + lastPageUrl + '\'' + 
			",next_page_url = '" + nextPageUrl + '\'' + 
			",from = '" + from + '\'' + 
			",to = '" + to + '\'' + 
			",prev_page_url = '" + prevPageUrl + '\'' + 
			",current_page = '" + currentPage + '\'' + 
			"}";
		}
}