package com.example.exercitiu_1;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Aspirator.class}, version = 1, exportSchema = false)
public abstract class AspiratorDB extends RoomDatabase {

    public abstract AspiratorDao getAutobuzDao();

    private static AspiratorDB instanta;

    public static  AspiratorDB getInstance(Context context){
        if(instanta == null)
            instanta = Room.databaseBuilder(context,AspiratorDB.class, "autobuze")  //!!!!
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        return instanta;
    }
}
