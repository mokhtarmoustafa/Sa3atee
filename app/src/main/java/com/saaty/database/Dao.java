package com.saaty.database;

import com.saaty.models.DataArrayModel;
import com.saaty.models.DataObjectModel;
import com.saaty.models.RoomModel;

import java.util.List;

import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Room;

@androidx.room.Dao
public interface Dao {

    @Insert
    void insertWishlist(RoomModel roomModel);



    @Query("SELECT * FROM wishlist")
    List<RoomModel> getWishlist();


}
