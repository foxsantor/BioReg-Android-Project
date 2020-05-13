package com.example.bioregproject.DAOs;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.bioregproject.entities.Produit;
import java.util.List;

@Dao
public interface ProduitDAO {

    @Insert
    void insert(Produit produit);

    @Delete
    void delete(Produit produit);

    @Update
    void update(Produit produit);

    @Query("DELETE FROM produit_table")
    void deleteAllProduit();

    @Query("SELECT * FROM produit_table ORDER BY creation")
    LiveData<List<Produit>> getAllProduit();

    @Query("SELECT * FROM produit_table where categorie =:categorie ")
    LiveData<List<Produit>> getProduitByCategorie(String categorie);







}

