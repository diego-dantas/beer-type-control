package com.blackscreen.beertypecontrol.beer;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.Objects;

@Database(entities = {Beer.class}, version = 1, exportSchema = false)
public abstract class BeerDatabase extends RoomDatabase {

    public abstract BeerDAO beerDAO();

    private static BeerDatabase instance;

    public static BeerDatabase getDatabase(final Context context){

        if(Objects.isNull(instance)){
            synchronized (BeerDatabase.class){
                if(Objects.isNull(instance)){
                    instance = Room.databaseBuilder(
                            context,
                            BeerDatabase.class,
                            "beers.db").allowMainThreadQueries().build();
                }
            }
        }

        return instance;
    }
}
