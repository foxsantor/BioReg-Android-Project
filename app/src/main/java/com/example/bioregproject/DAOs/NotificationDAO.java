package com.example.bioregproject.DAOs;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.bioregproject.entities.Notification;
import com.example.bioregproject.entities.Products;
import com.example.bioregproject.entities.Realtions.LogsAndProducts;

import java.util.List;
@Dao
public interface NotificationDAO {

    @Insert
    void insert(Notification notification);

    @Delete
    void delete(Notification notification);

    @Update
    void update(Notification notification);

    @Query("DELETE FROM notification_table")
    void deleteAllNotifications();

    @Query("SELECT * FROM notification_table ORDER BY creation DESC")
    LiveData<List<Notification>> getAllNotification();

    @Query("SELECT * from notification_table where id = :id LIMIT 1")
    LiveData<List<Notification>> loadNotificationById(long id);

    @Query("SELECT * FROM notification_table ")
    DataSource.Factory<Integer, Notification> loadAllNotification();

    @Query("SELECT * FROM notification_table where name LIKE  :name  or ownerLastName  LIKE :name or ownerFirstName  LIKE :name")
    DataSource.Factory<Integer, Notification> loadAllNotificationbyName(String name);

    @Query("SELECT * FROM notification_table where categoryName =:type ")
    DataSource.Factory<Integer, Notification> loadAllNotificationbyType(String type);

}
