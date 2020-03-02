package com.example.bioregproject.DAOs;
import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Ignore;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.bioregproject.entities.Account;
import com.example.bioregproject.entities.Realtions.LogsAndProducts;
import com.example.bioregproject.entities.Realtions.LogsAndUser;


import java.util.ArrayList;
import java.util.List;



import io.reactivex.Flowable;

@Dao
public interface AccountDAO {

    @Insert
    void insert(Account account);

    @Delete
    void delete(Account account);

    @Update
    void update(Account account);

    @Query("DELETE FROM account_table")
    void deleteAllAccounts();

    @Query("SELECT * FROM account_table ORDER BY lastLoggedIn")
    LiveData<List<Account>> getAllAccounts();

    @Query("SELECT * from account_table where id = :id LIMIT 1")
    LiveData<List<Account>> loadAccountById(long id);

    @Query("SELECT * FROM account_table ")
    DataSource.Factory<Integer, Account> loadAllAccount();

    @Query("SELECT * FROM account_table where firstName LIKE  :name or lastName LIKE :name ")
    DataSource.Factory<Integer, Account> loadAllAccountbyName(String name);

    @Transaction
    @Query("SELECT * FROM account_table")
    LiveData<List<LogsAndUser>> LogsAndUser();


}

