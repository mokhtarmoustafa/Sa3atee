package com.saaty.models;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "wishlist")
public class RoomModel {



    @PrimaryKey(autoGenerate = true)
    int id;

    int product_id;

    public RoomModel(int id,int product_id) {
        this.id=id;
        this.product_id = product_id;

    }


    @Ignore
    public RoomModel(int product_id) {
        this.product_id = product_id;

    }

    public int getProduct_id() {
        return product_id;
    }

    public int getId() {
        return id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
