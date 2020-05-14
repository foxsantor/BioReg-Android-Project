package com.example.bioregproject.DAOs;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.bioregproject.entities.PersoTask;


import java.util.Date;
import java.util.List;

@Dao
public interface PersoTaskDAO {

    @Insert
    void insert(PersoTask PersoTask);

    @Delete
    void delete(PersoTask PersoTask);

    @Update
    void update(PersoTask PersoTask);

    @Query("DELETE FROM parsotask_table")
    void deleteAllPersoTask();

    @Query("SELECT * FROM parsotask_table ORDER BY creation DESC")
    LiveData<List<PersoTask>> getAllPersoTasks();

    @Query("SELECT * from parsotask_table where id = :id LIMIT 1")
    LiveData<List<PersoTask>> loadPersoTasksOne (long id);

    @Query("SELECT * from parsotask_table where name = :name and assginedId=:id LIMIT 1")
    LiveData<List<PersoTask>> loadPersoTaskSpec (String name, long id);

    @Query("SELECT * from parsotask_table where assginedId = :idAssigne ")
    LiveData<List<PersoTask>> loadPersoTaskByAssgine (long idAssigne);

    @Query("SELECT * from parsotask_table where assginedId = :idAssigne and state = :state ")
    LiveData<List<PersoTask>> loadPersoTaskByAssgineState (long idAssigne,Boolean state);

    @Query("SELECT * FROM parsotask_table ")
    DataSource.Factory<Integer, PersoTask> loadAllPersoTask();

    @Query("SELECT * FROM parsotask_table where state=:state")
    DataSource.Factory<Integer, PersoTask> loadAllPersoTaskState(String state);
    @Query("SELECT * FROM parsotask_table where state=:state or state=:stat2")
    DataSource.Factory<Integer, PersoTask> loadAllPersoTaskState2(String state,String stat2);

    @Query("SELECT * FROM parsotask_table where state=:state and assginedId =:id")
    DataSource.Factory<Integer, PersoTask> loadAllPersoTaskStateAdmin(String state,long id);
    @Query("SELECT * FROM parsotask_table where state=:state or state=:stat2 and assginedId =:id")
    DataSource.Factory<Integer, PersoTask> loadAllPersoTaskState2Admin(String state,String stat2,long id);

    @Query("SELECT * FROM parsotask_table where name LIKE  :name  or description LIKE :name or assignedName like :name" )
    DataSource.Factory<Integer, PersoTask> loadAllProductbyName(String name);

    @Query("SELECT * FROM parsotask_table where name LIKE  :name  or description LIKE :name or assignedName like :name and assginedId =:id" )
    DataSource.Factory<Integer, PersoTask> loadAllProductbyNameAdmin(String name,long id);

    @Query("SELECT * FROM parsotask_table where assginedId =:id ")
    DataSource.Factory<Integer, PersoTask> loadAllTaskByAssgnie(long id);
    
}
