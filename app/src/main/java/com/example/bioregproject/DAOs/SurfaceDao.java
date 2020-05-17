package com.example.bioregproject.DAOs;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import com.example.bioregproject.entities.Surface;

import java.util.List;

@Dao
public interface SurfaceDao {
    @Query("SELECT * FROM Surface")
    LiveData<List<Surface>> getAllSurface();


    @Insert
    void insertOne(Surface surface);

    @Delete
    void delete(Surface surface);

    @Update
    void update(Surface surface);

    @Query("DELETE FROM Surface")
    void deleteAllSurface();
    @Query("SELECT * from surface where idSurface = :id LIMIT 1")
    LiveData<List<Surface>> loadSurfaceById(long id);

}
