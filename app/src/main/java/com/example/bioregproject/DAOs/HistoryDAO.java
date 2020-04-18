package com.example.bioregproject.DAOs;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.bioregproject.entities.History;
import com.example.bioregproject.entities.Products;
import com.example.bioregproject.entities.Realtions.LogsAndProducts;

import java.util.List;
@Dao
public interface HistoryDAO {

    @Insert
    void insert(History History);

    @Delete
    void delete(History History);

    @Update
    void update(History History);

    @Query("DELETE FROM history_table")
    void deleteAllHistorys();

    @Query("SELECT * FROM history_table ORDER BY creation DESC")
    LiveData<List<History>> getAllHistory();

    @Query("SELECT * from history_table where id = :id LIMIT 1")
    LiveData<List<History>> loadHistoryById(long id);

    @Query("SELECT * FROM history_table ")
    DataSource.Factory<Integer, History> loadAllHistory();

    @Query("SELECT * FROM history_table where name LIKE  :name  or owner LIKE :name or description LIKE :name")
    DataSource.Factory<Integer, History> loadAllHistorybyName(String name);

    @Query("SELECT * FROM history_table where categoryName =:type ")
    DataSource.Factory<Integer, History> loadAllHistorybyType(String type);

}
