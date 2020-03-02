package com.example.bioregproject.DAOs;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.bioregproject.entities.Account;
import com.example.bioregproject.entities.Category;
import com.example.bioregproject.entities.Realtions.ProductsAndCategory;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface CategoryDAO {

    @Insert
    void insert(Category category);

    @Delete
    void delete(Category category);

    @Update
    void update(Category category);

    @Query("DELETE FROM category_table")
    void deleteAllCategory();

    @Query("SELECT * FROM category_table ORDER BY creation")
    LiveData<List<Category>> getAllAccounts();

    @Query("SELECT * from category_table where id = :id LIMIT 1")
    LiveData<List<Category>> loadCategoryById(long id);

    @Query("SELECT * FROM category_table ")
    DataSource.Factory<Integer, Category> loadAllCategorys();

    @Query("SELECT * FROM category_table where name LIKE  :name ")
    DataSource.Factory<Integer, Category> loadAllCategorybyName(String name);

    @Transaction
    @Query("SELECT * FROM category_table")
    LiveData<List<ProductsAndCategory>> ProductsAndCategory();



}

