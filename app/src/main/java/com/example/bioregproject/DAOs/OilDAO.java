package com.example.bioregproject.DAOs;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.bioregproject.entities.Oil;

import java.util.List;
@Dao
public interface OilDAO {

    @Insert
    void insert(Oil oil);

    @Delete
    void delete(Oil oil);

    @Update
    void update(Oil oil);

    @Query("DELETE FROM oil_table")
    void deleteAllOil();

    @Query("SELECT * FROM oil_table ORDER BY creationDate DESC")
    LiveData<List<Oil>> getAllOils();

    @Query("SELECT * from oil_table where id = :id LIMIT 1")
    LiveData<List<Oil>> loadOilById(long id);

    @Query("SELECT * from oil_table where `action` LIKE :recherche ORDER BY creationDate DESC")
    LiveData<List<Oil>> loadOilByAction(String recherche);
}
