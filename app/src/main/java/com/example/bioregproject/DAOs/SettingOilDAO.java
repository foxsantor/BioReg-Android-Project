package com.example.bioregproject.DAOs;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.bioregproject.entities.SettingOil;

import java.util.List;

@Dao
public interface SettingOilDAO {

    @Insert
    void insert(SettingOil SettingOil);

    @Delete
    void delete(SettingOil SettingOil);

    @Update
    void update(SettingOil SettingOil);

    @Query("DELETE FROM settingOil_table")
    void deleteAllSettingOil();

    @Query("SELECT * FROM SettingOil_table ")
    LiveData<List<SettingOil>> getAllSettingOils();

    @Query("SELECT * from SettingOil_table where id = :id LIMIT 1")
    LiveData<List<SettingOil>> loadSettingOilById(long id);

}
