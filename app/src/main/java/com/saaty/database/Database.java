package com.saaty.database;

import android.content.Context;

import com.saaty.models.Data;
import com.saaty.models.DataArrayModel;
import com.saaty.models.DataObjectModel;
import com.saaty.models.RoomModel;

import androidx.room.Room;
import androidx.room.RoomDatabase;

@androidx.room.Database(entities = {RoomModel.class},version = 2,exportSchema = false)
public abstract class Database extends RoomDatabase {
    private static final String DATABASE_NAME ="my_data";
    public  static Database mInstance;

    public static Database getInstance(Context context){
        if(mInstance==null){
            mInstance= Room.databaseBuilder(context.getApplicationContext(),Database.class,DATABASE_NAME)
                    .allowMainThreadQueries().build();
        }
        return  mInstance;

    }

    public  abstract Dao dao();


}
