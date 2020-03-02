package com.example.bioregproject.DAOs;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.bioregproject.entities.Account;
import com.example.bioregproject.entities.ProductLogs;

import java.util.List;

@Dao
public interface ProductLogsDAO {

    @Insert
    void insert(ProductLogs productLogs);

    @Delete
    void delete(ProductLogs productLogs);

    @Update
    void update(ProductLogs productLogs);

    @Query("DELETE FROM productlogs_table")
    void deleteAllProductLogs();

    @Query("SELECT * FROM productlogs_table ORDER BY creation")
    LiveData<List<ProductLogs>> getAllProductLogs();

    @Query("SELECT * from productlogs_table where id = :id LIMIT 1")
    LiveData<List<ProductLogs>> loadProductLogsById(long id);

    @Query("SELECT * FROM productlogs_table ")
    DataSource.Factory<Integer, ProductLogs> loadAllProductLogs();

    @Query("SELECT * FROM productlogs_table where title LIKE  :name")
    DataSource.Factory<Integer, ProductLogs> loadAllProductLogsbyName(String name);


}

