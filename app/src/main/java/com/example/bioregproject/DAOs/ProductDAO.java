package com.example.bioregproject.DAOs;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;
import com.example.bioregproject.entities.Products;
import com.example.bioregproject.entities.Realtions.LogsAndProducts;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface ProductDAO {

    @Insert
    void insert(Products products);

    @Delete
    void delete(Products products);

    @Update
    void update(Products products);

    @Query("DELETE FROM product_table")
    void deleteAllProducts();

    @Query("SELECT * FROM product_table ORDER BY creationDate DESC")
    LiveData<List<Products>> getAllAccounts();

    @Query("SELECT * from product_table where id = :id LIMIT 1")
    LiveData<List<Products>> loadProductById(long id);

    @Query("SELECT * FROM product_table ")
    DataSource.Factory<Integer, Products> loadAllProducts();

    @Query("SELECT * FROM product_table where name LIKE  :name  or brandName LIKE :name")
    DataSource.Factory<Integer, Products> loadAllProductbyName(String name);

    @Query("SELECT * FROM product_table where type =:type ")
    DataSource.Factory<Integer, Products> loadAllProductbyType(String type);

    @Transaction
    @Query("SELECT * FROM product_table")
    LiveData<List<LogsAndProducts>> LogsAndProducts();


}

