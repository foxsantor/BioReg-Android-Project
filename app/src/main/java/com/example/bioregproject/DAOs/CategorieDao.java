package com.example.bioregproject.DAOs;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;


import com.example.bioregproject.entities.CategorieTache;
import com.example.bioregproject.entities.Realtions.CategorywithSurfaces;

import java.util.List;

@Dao
public interface CategorieDao {


    @Query("SELECT * FROM CategorieTache")
    LiveData<List<CategorieTache>> getAllCategories();

    @Insert
    void insertOne(CategorieTache cat);

    @Delete
    void delete(CategorieTache cat);

    @Update
    void update(CategorieTache cat);

    @Query("DELETE FROM CategorieTache")
    void deleteAllCategorie();

    @Transaction
    @Query("select * from categorietache")
    public LiveData<List<CategorywithSurfaces>> getCategorieWithSurfaces();
}
