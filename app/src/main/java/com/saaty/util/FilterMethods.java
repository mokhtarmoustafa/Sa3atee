package com.saaty.util;

import android.content.Context;

import com.saaty.models.DataArrayModel;

import java.util.ArrayList;
import java.util.List;

public class FilterMethods {
  public   List<DataArrayModel> newSortedList;
  Context context;

    public FilterMethods(Context context,List<DataArrayModel> newSortedList) {
        this.newSortedList = newSortedList;
        this.context=context;
    }

    public static List<DataArrayModel> getHighPrice(List<DataArrayModel>newWishlist){
        for (int i = 0; i < newWishlist.size(); i++) {
            // find position of smallest num between (i + 1)th element and last element
            int pos = i;
            for (int j = i; j < newWishlist.size(); j++) {
                if (newWishlist.get(j).getPrice() > newWishlist.get(pos).getPrice())
                    pos = j;
            }
            // Swap min (smallest num) to current position on array
            DataArrayModel min = newWishlist.get(pos);
            newWishlist.set(pos, newWishlist.get(i));
            newWishlist.set(i, min);
        }
        return newWishlist;
    }


    public List<DataArrayModel> getHighNewProducts(List<DataArrayModel>newWishlist){
        newSortedList=new ArrayList<>();
        for(int i=0;i<newWishlist.size();i++){
            if(newWishlist.get(i).getShape().equals("New")){
                newSortedList.add(newWishlist.get(i));
            }
        }
        return newSortedList;

    }


    public  List<DataArrayModel> getHighUsedProducts(List<DataArrayModel>newWishlist){
        newSortedList=new ArrayList<>();
        for(int i=0;i<newWishlist.size();i++){
            if(newWishlist.get(i).getShape().equals("Used")){
                newSortedList.add(newWishlist.get(i));
            }
        }
        return newSortedList;

    }


    public static List<DataArrayModel> getLowPice(List<DataArrayModel> newWishlist){
        for (int i = 0; i < newWishlist.size(); i++) {
            // find position of smallest num between (i + 1)th element and last element
            int pos = i;
            for (int j = i; j < newWishlist.size(); j++) {
                if (newWishlist.get(j).getPrice() < newWishlist.get(pos).getPrice())
                    pos = j;
            }
            // Swap min (smallest num) to current position on array
            DataArrayModel min = newWishlist.get(pos);
            newWishlist.set(pos, newWishlist.get(i));
            newWishlist.set(i, min);
        }
        return  newWishlist;
    }


    public  List<DataArrayModel> getLowPriceNewProducts(List<DataArrayModel>newWishlist){
        newSortedList=new ArrayList<>();
        for(int i=0;i<newWishlist.size();i++){
            if(newWishlist.get(i).getShape().equals("New")){
                newSortedList.add(newWishlist.get(i));
            }

        }
        return newSortedList;
    }


    public  List<DataArrayModel> getLowPriceUsedProducts(List<DataArrayModel>newWishlist){
        newSortedList=new ArrayList<>();
        for(int i=0;i<newWishlist.size();i++){
            if(newWishlist.get(i).getShape().equals("Used")){
                newSortedList.add(newWishlist.get(i));
            }

        }
        return newSortedList;
    }

    public  List<DataArrayModel> getWatchesCategory(List<DataArrayModel>newWishlist){
        newSortedList=new ArrayList<>();
        for(int i=0;i<newWishlist.size();i++){
            if(newWishlist.get(i).getCategoryId()==1){
                newSortedList.add(newWishlist.get(i));
            }

        }
        return newSortedList;
    }

    public  List<DataArrayModel> getBracletCategory(List<DataArrayModel>newWishlist){
        newSortedList=new ArrayList<>();
        for(int i=0;i<newWishlist.size();i++){
            if(newWishlist.get(i).getCategoryId()==2){
                newSortedList.add(newWishlist.get(i));
            }

        }
        return newSortedList;
    }







}
