package com.example.bioregproject.DAOs;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.bioregproject.entities.Storage;

import java.util.List;

@Dao
public interface StorageDAO {

    @Insert
    void insert(Storage storage);

    @Delete
    void delete(Storage storage);

    @Update
    void update(Storage storage);

    @Query("DELETE FROM storage_table")
    void deleteAllStorage();

    @Query("SELECT * FROM storage_table ORDER BY creation")
    LiveData<List<Storage>> getAllStorage();

    @Query("SELECT * FROM storage_table where categorie =:categorie ")
    LiveData<List<Storage>> getStorageByCategorie(String categorie);







}

