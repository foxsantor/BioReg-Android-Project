package com.example.bioregproject.DAOs;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import com.example.bioregproject.entities.Realtions.TacheWithSurfaceAndCategoryTache;
import com.example.bioregproject.entities.Tache;

import java.util.List;

@Dao
public interface TacheDao {
    @Query("SELECT * FROM Tache")
    LiveData<List<TacheWithSurfaceAndCategoryTache>> getAllTache();

    @Query("SELECT * FROM Tache where idCategorie =:id ")
    LiveData<List<TacheWithSurfaceAndCategoryTache>> getTacheByCategorie(int id);

    @Query("SELECT * FROM Tache where statut =:statut ")
    LiveData<List<TacheWithSurfaceAndCategoryTache>> getTacheByStatus(boolean statut);

    @Insert
    void insertOne(Tache tache);

    @Delete
    void delete(Tache tache);

    @Update
    void update(Tache tache);

    @Query("DELETE FROM Tache")
    void deleteAllTache();

}
