package com.example.bioregproject.DAOs;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.bioregproject.entities.Fournisseur;

import java.util.List;

@Dao
public interface FournisseurDAO {

    @Insert
    void insert(Fournisseur fournisseur);

    @Delete
    void delete(Fournisseur fournisseur);

    @Update
    void update(Fournisseur fournisseur);

    @Query("DELETE FROM fournisseur_table")
    void deleteAllFournisseur();

    @Query("SELECT * FROM fournisseur_table ORDER BY name asc")
    LiveData<List<Fournisseur>> getAllFournisseur();


    @Query("SELECT * from fournisseur_table where id = :id LIMIT 1")
    LiveData<List<Fournisseur>> loadFrsById(long id);







}

