package com.blackscreen.beertypecontrol.beer;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface BeerDAO {

    @Insert
    long insert(Beer beer);

    @Delete
    void delete(Beer beer);

    @Update
    void update(Beer beer);

    @Query("SELECT * FROM beer where id = :id")
    Beer findById(long id);

    @Query("SELECT * FROM beer")
    List<Beer> findAll();
}
